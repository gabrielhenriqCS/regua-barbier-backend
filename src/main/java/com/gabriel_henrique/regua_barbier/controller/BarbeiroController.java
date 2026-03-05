package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.BarbeiroDTO;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.service.BarbeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @GetMapping
    public ResponseEntity<List<Barbeiro>> listarBarbeiros() {
        return ResponseEntity.ok(barbeiroService.mostrarBarbeiros());
    }

    @PostMapping
    public ResponseEntity<Barbeiro> cadastrarBarbeiro(@RequestBody @Valid BarbeiroDTO dto, UriComponentsBuilder uriBuilder) {
        Barbeiro barbeiro = barbeiroService.novoBarbeiro(dto);
        var uri = uriBuilder.path("/barbeiros/{id}")
                .buildAndExpand(barbeiro.getId())
                .toUri();
        return ResponseEntity.created(uri).body(barbeiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarBarbeiro(@PathVariable("id") UUID id, @RequestBody @Valid BarbeiroDTO dto) {
        barbeiroService.atualizarBarbeiro(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBarbeiro(@PathVariable("id") UUID id) {
        barbeiroService.removerBarbeiro(id);
        return ResponseEntity.noContent().build();
    }
}
