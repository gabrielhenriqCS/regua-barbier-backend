package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.servico.ServicoDTO;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import com.gabriel_henrique.regua_barbier.domain.servico.exceptions.ServicoNaoEncontrado;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public List<Servico> mostrarTodosServicos() {
        return servicoRepository.findAll();
    }

    @Transactional
    public Servico novoServico(ServicoDTO dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new DadosInvalidosException("Nome não preenchido. Por favor preencha o nome para prosseguir.");
        }
        var servico = Servico.builder().nome(dto.nome()).preco(dto.preco()).duracaoMinutos(dto.duracaoMinutos()).build();
        return servicoRepository.save(servico);
    }

    @Transactional
    public void removerServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(ServicoNaoEncontrado::new);
        servicoRepository.delete(servico);
    }

    @Transactional
    public void atualizarServico(Long id, ServicoDTO dto) {
        var servico = servicoRepository.findById(id)
                .orElseThrow(ServicoNaoEncontrado::new);
        servico.setNome(dto.nome());
        servico.setPreco(dto.preco());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        servicoRepository.save(servico);
    }
}
