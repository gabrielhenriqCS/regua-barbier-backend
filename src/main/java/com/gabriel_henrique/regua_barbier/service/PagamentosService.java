package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.Pagamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.repository.AgendamentoRepository;
import com.gabriel_henrique.regua_barbier.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentosService {
    private final PagamentoRepository pagamentosRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public void processarTransacaoDePagamento(String idTransacaoExterno, String novoStatus) {
        Pagamento pagamento = pagamentosRepository.findByTransacaoIdExterno(idTransacaoExterno)
                .orElseThrow(() -> new DadosInvalidosException("Pagamento não encontrado para o ID: " + idTransacaoExterno));

        if (pagamento.getStatus() == PagamentoStatus.APROVADO) {
            return;
        }

        if ("aprovado".equalsIgnoreCase(novoStatus) || "pago".equalsIgnoreCase(novoStatus)) {
            pagamento.setStatus(PagamentoStatus.APROVADO);
            Agendamento agendamento = pagamento.getAgendamento();
            agendamento.setStatusAgendamento(StatusAgendamento.CONCLUIDO);

            pagamentosRepository.save(pagamento);
            agendamentoRepository.save(agendamento);

            System.out.println("Sucesso: Agendamento " + agendamento.getId() + "confirmado via Pagamento");

        }
    }

    @Transactional
    public void confirmarPagamento(String idExterno) {
        Pagamento pagamento = pagamentosRepository.findByTransacaoIdExterno(idExterno)
                .orElseThrow(() -> new DadosInvalidosException("Pagamento não existe"));

        pagamento.setStatus(PagamentoStatus.APROVADO);
        pagamento.setDataConfirmacaoPagamento(LocalDateTime.now());

        pagamento.getAgendamento().setStatusAgendamento(StatusAgendamento.CONFIRMADO);

        pagamentosRepository.save(pagamento);
    }
}
