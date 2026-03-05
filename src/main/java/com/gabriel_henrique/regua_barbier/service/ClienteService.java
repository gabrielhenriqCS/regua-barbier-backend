package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.infra.DadosPreenchidosMultiplasVezesException;
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
        return clientesRepository.findById(id)
                .orElseThrow(ClienteNaoEncontrado::new);
    }

    @Transactional
    public Cliente novoCliente(ClienteDTO dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new DadosInvalidosException("Nome não preenchido. Por favor preencha o nome para prosseguir.");
        }

        if (dto.email() == null || dto.email().isBlank()) {
            throw new DadosInvalidosException("Email não preenchido. Por favor preencha o email para prosseguir.");
        }

        if (dto.telefone() == null || dto.telefone().isBlank()) {
            throw new DadosInvalidosException("Telefone não preenchido. Por favor preencha o telefone para prosseguir.");
        }

        var cliente = Cliente.builder()
                .nome(dto.nome())
                .telefone(dto.telefone())
                .email(dto.email())
                .build();

        return clientesRepository.save(cliente);
    }

    @Transactional
    public void removerCliente(UUID id) {
        if (!clientesRepository.existsById(id)) {
            throw new ClienteNaoEncontrado();
        }
        clientesRepository.deleteById(id);
    }

    @Transactional
    public void atualizarCliente(UUID id, ClienteDTO dto) {
        Cliente cliente = clientesRepository.findById(id)
                .orElseThrow(ClienteNaoEncontrado::new);

        if (cliente.getNome().equals(dto.nome()) &&
                cliente.getTelefone().equals(dto.telefone()) &&
                cliente.getEmail().equals(dto.email())) {
            throw new DadosPreenchidosMultiplasVezesException("Os dados informados são iguais aos dados atuais.");
        }

        if (dto.nome().isBlank() || dto.email().isBlank() || dto.telefone().isBlank()) {
            throw new DadosInvalidosException("Não é possível atualizar para campos vazios.");
        }

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());
    }
}