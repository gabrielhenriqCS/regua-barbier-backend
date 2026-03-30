package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.docs.ClienteApiDocs;
import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController implements ClienteApiDocs {

    private final ClienteService clienteService;

    @Override
    @GetMapping
    public ResponseEntity<List<Cliente>> mostrarTodosClientes() {
        return ResponseEntity.ok(clienteService.mostrarClientes());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> encontrarCliente(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(clienteService.mostrarCliente(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid ClienteDTO dto, UriComponentsBuilder uriBuilder) {
        Cliente novoCliente = clienteService.novoCliente(dto);
        URI uri = uriBuilder.path("/clientes/{id}")
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
