package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.Pagamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import com.gabriel_henrique.regua_barbier.exceptions.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.repository.AgendamentoRepository;
import com.gabriel_henrique.regua_barbier.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentosService {
    private final PagamentoRepository pagamentosRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public void verificarEAtualizarStatus(String idTransacaoExterno, String novoStatus) {
        Pagamento pagamento = pagamentosRepository.findByIdTransacaoExterno(idTransacaoExterno)
                .orElseThrow(() -> new DadosInvalidosException("Pagamento não encontrado para o ID: " + idTransacaoExterno));

        if (pagamento.getStatus() == PagamentoStatus.APROVADO) {
            log.info("Pagamento {} já estava aprovado anteriormente.", idTransacaoExterno);
            return;
        }

        if ("approved".equalsIgnoreCase(novoStatus)) {
            pagamento.setStatus(PagamentoStatus.APROVADO);
            Agendamento agendamento = pagamento.getAgendamento();
            agendamento.setStatusAgendamento(StatusAgendamento.CONCLUIDO);

            pagamentosRepository.save(pagamento);
            agendamentoRepository.save(agendamento);

            log.info("Sucesso: Agendamento " + agendamento.getId() + "confirmado via Pagamento");

        }
    }

    private void aprovarTudo(Pagamento pagamento) {
        pagamento.setStatus(PagamentoStatus.APROVADO);
        pagamento.setDataConfirmacaoPagamento(LocalDateTime.now());

        Agendamento agendamento = pagamento.getAgendamento();
        agendamento.setStatusAgendamento(StatusAgendamento.CONFIRMADO);

        pagamentosRepository.save(pagamento);
        agendamentoRepository.save(agendamento);

        log.info("SUCESSO: Agendamento {} confirmado via cartão/PIX", agendamento.getId());
    }

    private void cancelarTudo(Pagamento pagamento) {
        pagamento.setStatus(PagamentoStatus.RECUSADO);
        pagamento.getAgendamento().setStatusAgendamento(StatusAgendamento.CANCELADO);

        pagamentosRepository.save(pagamento);
        agendamentoRepository.save(pagamento.getAgendamento());

        log.warn("CANCELADO: Agendamento {} cancelado por falha no pagamento", pagamento.getAgendamento().getId());
    }
}
