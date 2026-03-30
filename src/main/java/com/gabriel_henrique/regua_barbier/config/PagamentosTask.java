package com.gabriel_henrique.regua_barbier.config;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import com.gabriel_henrique.regua_barbier.repository.AgendamentoRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class PagamentosTask {

    private AgendamentoRepository agendamento;

    @Scheduled(cron = "0 30 18 * * *", zone = "America/Brasilia")
    public void limparPagementosTask() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(15);

        List<Agendamento> expirados = agendamento.findByStatusPagamentoAndDataCriacaoAntes(PagamentoStatus.PENDENTE, limite);

        if (!expirados.isEmpty()) {
            expirados.forEach(a -> {
                a.setStatusAgendamento(StatusAgendamento.CANCELADO);
            });
            agendamento.saveAll(expirados);
            System.out.println(expirados.size() + " agendamentos cancelados por expiração!");
        }
    }
}
