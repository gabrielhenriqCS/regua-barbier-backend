package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.servico.ServicoDTO;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import com.gabriel_henrique.regua_barbier.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicos")
public class ServicoController {
    private final ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        return ResponseEntity.ok(servicoService.mostrarTodosServicos());
    }

    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody @Valid ServicoDTO dto, UriComponentsBuilder uriBuilder) {
        Servico novoServico = servicoService.novoServico(dto);
        var uri = uriBuilder.path("/servicos/{id}")
                .buildAndExpand(novoServico.getId())
                .toUri();
        return ResponseEntity.created(uri).body(novoServico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarServico(@PathVariable("id") Long id, @RequestBody @Valid ServicoDTO dto) {
        servicoService.atualizarServico(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable("id") Long id) {
        servicoService.removerServico(id);
        return ResponseEntity.noContent().build();
    }
}
