package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.servico.ServicoDTO;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
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
        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setPreco(dto.preco());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        return servicoRepository.save(servico);
    }

    @Transactional
    public void removerServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servicoRepository.delete(servico);
    }

    @Transactional
    public Servico atualizarServico(Long id, ServicoDTO dto) {
        var servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço informado não encontrado!"));
        servico.setNome(dto.nome());
        servico.setPreco(dto.preco());
        servico.setDuracaoMinutos(dto.duracaoMinutos());
        return servicoRepository.save(servico);
    }
}
