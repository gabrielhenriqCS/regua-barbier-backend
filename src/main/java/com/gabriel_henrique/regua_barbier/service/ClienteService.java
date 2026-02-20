package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.repository.ClientesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClientesRepository clientesRepository;

    public List<Cliente> mostrarClientes() {
        return clientesRepository.findAll();
    }

    @Transactional
    public Cliente mostrarCliente(UUID id) {
        return clientesRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    @Transactional
    public void novoCliente(ClienteDTO dto) {
        var cliente = Cliente.builder()
                .nome(dto.nome())
                .telefone(dto.telefone())
                .email(dto.email())
                .build();

        clientesRepository.save(cliente);
    }

    @Transactional
    public void removerCliente(UUID id) {
        var cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        clientesRepository.delete(cliente);
    }

    @Transactional
    public void atualizarCliente(UUID id, ClienteDTO dto) {
        var cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        clientesRepository.save(cliente);
    }
}
