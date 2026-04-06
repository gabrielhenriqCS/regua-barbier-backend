package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.exceptions.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.exceptions.DadosPreenchidosMultiplasVezesException;
import com.gabriel_henrique.regua_barbier.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Cliente> mostrarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente mostrarCliente(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(ClienteNaoEncontrado::new);
    }

    @Transactional
    public Cliente novoCliente(ClienteDTO dto) {
        if (dto.nome() == null) {
            throw new DadosInvalidosException("Nome não preenchido. Por favor preencha para prosseguir.");
        }

        if (dto.email() == null) {
            throw new DadosInvalidosException("Email não preenchido. Por favor preencha para prosseguir.");
        }

        if (dto.senha() == null) {
            throw new DadosInvalidosException("Senha não preecnhida. Por favor preecnha para prosseguir");
        }

        if (dto.telefone() == null) {
            throw new DadosInvalidosException("Telefone não preenchido. Por favor preencha para prosseguir.");
        }

        Cliente cliente = Cliente.builder()
                .nome(dto.nome())
                .telefone(dto.telefone())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .build();

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void removerCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontrado();
        }
        clienteRepository.deleteById(id);
    }

    @Transactional
    public void atualizarCliente(UUID id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
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