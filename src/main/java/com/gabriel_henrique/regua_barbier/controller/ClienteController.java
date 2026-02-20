package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.infra.DadosNaoAtualizados;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> mostrarTodosClientes() {
        return ResponseEntity.ok(clienteService.mostrarClientes());
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO dto) {
        clienteService.novoCliente(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/atualizarBarbeiro")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        try {
            clienteService.atualizarCliente(id, dto);
            return ResponseEntity.noContent().build();
        } catch (DadosNaoAtualizados e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> removerCliente(@PathVariable UUID id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }

}
