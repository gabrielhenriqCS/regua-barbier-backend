package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.servico.ServicoDTO;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import com.gabriel_henrique.regua_barbier.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/serviços")
public class ServicoController {
    private final ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        return ResponseEntity.ok(servicoService.mostrarTodosServicos());
    }

    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody ServicoDTO dto) {
        try {
            var servico = servicoService.novoServico(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {

        }
    }
}
