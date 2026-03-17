package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.AgendamentoDTO;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions.BarbeiroNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.servico.exceptions.ServicoNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.repository.AgendamentoRepository;
import com.gabriel_henrique.regua_barbier.repository.BarbeiroRepository;
import com.gabriel_henrique.regua_barbier.repository.ClienteRepository;
import com.gabriel_henrique.regua_barbier.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;


    private final BarbeiroRepository barbeiroRepository;


    private final ClienteRepository clienteRepository;


    private final ServicoRepository servicoRepository;

    public List<Agendamento> listarAgendamentos(StatusAgendamento status) {
        return agendamentoRepository.findByStatus(status);
    }

    @Transactional
    public Agendamento fazerAgendamento(AgendamentoDTO dto) {
        var barbeiro = barbeiroRepository.findById(dto.barbeiroId().getId())
                .orElseThrow(() -> new BarbeiroNaoEncontrado("Barbeiro não encontrado!"));

        var cliente = clienteRepository.findById(dto.clienteId().getId())
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente não encontrado!"));

        var servico = servicoRepository.findById(dto.servicoId().getId())
                .orElseThrow(() -> new ServicoNaoEncontrado("Serviço não encontrado!"));

        var agendamento = Agendamento.builder()
                .barbeiro(barbeiro)
                .cliente(cliente)
                .servico(servico)
                .dataHoraInicio(dto.dataHoraInicio())
                .dataHoraFim(dto.dataHoraInicio().plusMinutes(servico.getDuracaoMinutos()))
                .status(StatusAgendamento.AGENDADO)
                .build();

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void atualizarAgendamento(Long id, AgendamentoDTO dto) {
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar o agendamento"));
        agendamento.setDataHoraInicio(dto.dataHoraInicio());
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void removerAgendamento(Long id) {
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamentoRepository.delete(agendamento);
    }
}
