package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.AgendamentoDTO;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.exceptions.AgendamentoNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions.BarbeiroNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import com.gabriel_henrique.regua_barbier.domain.servico.exceptions.ServicoNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.infra.AtualizacaoDadosException;
import com.gabriel_henrique.regua_barbier.infra.ConflitoDeHorarioException;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.repository.AgendamentoRepository;
import com.gabriel_henrique.regua_barbier.repository.BarbeiroRepository;
import com.gabriel_henrique.regua_barbier.repository.ClienteRepository;
import com.gabriel_henrique.regua_barbier.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


        LocalDateTime inicio_procedimento = dto.dataHoraInicio();
        LocalDateTime fim_procedimento = inicio_procedimento.plusMinutes(servico.getDuracaoMinutos());

        boolean ocupacao_horario = agendamentoRepository.existeConflito(barbeiro.getId(), inicio_procedimento, fim_procedimento);

        if (ocupacao_horario) {
            throw new ConflitoDeHorarioException("Este barbeiro já tem um cliente no horário de " + inicio_procedimento.format(DateTimeFormatter.ofPattern("HH:mm")) + " e " + fim_procedimento.format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        var agendamento = Agendamento.builder()
                .barbeiro(barbeiro)
                .cliente(cliente)
                .servico(servico)
                .dataInicio(inicio_procedimento)
                .dataTermino(fim_procedimento)
                .statusAgendamento(StatusAgendamento.PENDENTE_PAGAMENTO)
                .statusPagamento(PagamentoStatus.PENDENTE)
                .build();

        if (inicio_procedimento.isBefore(LocalDateTime.now().plusHours(3))) {
            throw new DadosInvalidosException("Agendamentos deem ser feitos no mínimo 3 horas de antecedência.");
        }

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void atualizarAgendamento(Long id, AgendamentoDTO dto) {
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AtualizacaoDadosException("Não foi possível encontrar o agendamento"));
        agendamento.setDataInicio(dto.dataHoraInicio());
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void removerAgendamento(Long id) {
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontrado("Agendamento não encontrado"));
        agendamentoRepository.delete(agendamento);
    }
}
