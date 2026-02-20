package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.BarbeiroDTO;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions.BarbeiroNaoEncontrado;
import com.gabriel_henrique.regua_barbier.infra.DadosNaoAtualizados;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.service.BarbeiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @GetMapping
    public ResponseEntity<List<Barbeiro>> listarBarbeiros() {
        return ResponseEntity.ok(barbeiroService.mostrarBarbeiros());
    }

    @PostMapping
    public ResponseEntity<Barbeiro> cadastrarBarbeiro(@RequestBody @Valid BarbeiroDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            var barbeiro = barbeiroService.novoBarbeiro(dto);
            var uri = uriBuilder.path("/barbeiros/{id}")
                    .buildAndExpand(barbeiro.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(barbeiro);
        } catch (BarbeiroNaoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbeiro> atualizarBarbeiro(@PathVariable("id") UUID id, @RequestBody BarbeiroDTO dto) {
        try {
            barbeiroService.atualizarBarbeiro(id, dto);
            return ResponseEntity.noContent().build();
        } catch (DadosNaoAtualizados e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (BarbeiroNaoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Barbeiro> deletarBarbeiro(@PathVariable("id") UUID id) {
        try {
            barbeiroService.removerBarbeiro(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar barbeiro!");
        }
    }
}
