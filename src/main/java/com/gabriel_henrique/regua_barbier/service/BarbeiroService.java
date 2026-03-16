package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.BarbeiroDTO;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions.BarbeiroNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.infra.DadosPreenchidosMultiplasVezesException;
import com.gabriel_henrique.regua_barbier.repository.BarbeiroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;

    public List<Barbeiro> mostrarBarbeiros() {
        return barbeiroRepository.findAll();
    }

    @Transactional
    public Barbeiro novoBarbeiro(BarbeiroDTO dto) {

        Barbeiro barbeiro = Barbeiro.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .telefone(dto.telefone())
                .especialidade("Corte Masculino")
                .ativo(true)
                .build();

        if (!dto.senha().equals(barbeiro.getSenha())) {
            throw new DadosInvalidosException("Senha não condiz com a cadastrada!");
        }

        if (barbeiroRepository.existsByEmail(dto.email())) {
            throw new DadosPreenchidosMultiplasVezesException("E-mail já cadastrado");
        }

        return barbeiroRepository.save(barbeiro);
    }

    @Transactional
    public void removerBarbeiro(UUID id) {
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(BarbeiroNaoEncontrado::new);
        barbeiro.setAtivo(false);
        barbeiroRepository.delete(barbeiro);
    }

    @Transactional
    public void atualizarBarbeiro(UUID id, BarbeiroDTO dto) {
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(BarbeiroNaoEncontrado::new);

        barbeiro.setNome(dto.nome());
        barbeiro.setEmail(dto.email());
        barbeiro.setTelefone(dto.telefone());
        barbeiroRepository.save(barbeiro);
    }
}
