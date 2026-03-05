package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> encontrarCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.mostrarCliente(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid ClienteDTO dto, UriComponentsBuilder uriBuilder) {
        Cliente novoCliente = clienteService.novoCliente(dto);
        var uri = uriBuilder.path("/clientes/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();
        return ResponseEntity.created(uri).body(novoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(@PathVariable UUID id, @RequestBody @Valid ClienteDTO dto) {
        clienteService.atualizarCliente(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable UUID id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
