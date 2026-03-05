package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.AgendamentoDTO;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarAgendamentos(@RequestParam StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.listarAgendamentos(status));
    }

    @PostMapping
    public ResponseEntity<Agendamento> agendarProcedimento(@RequestBody @Valid AgendamentoDTO dto, UriComponentsBuilder uriBuilder) {
        Agendamento agendamento = agendamentoService.fazerAgendamento(dto);
        var uri = uriBuilder.path("/agendamento/{id}")
                .buildAndExpand(agendamento.getId())
                .toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificarAgendamento(@PathVariable("id") Long id, @RequestBody @Valid AgendamentoDTO dto) {
        agendamentoService.atualizarAgendamento(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable("id") Long id) {
        agendamentoService.removerAgendamento(id);
        return ResponseEntity.noContent().build();
    }
}
