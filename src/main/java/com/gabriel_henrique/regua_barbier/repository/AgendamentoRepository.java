package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatus(StatusAgendamento status);
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.status != 'CANCELADO' " +
            "AND (:inicio < a.dataHoraFim AND :fim > a.dataHoraInicio)")
    boolean existeConflito(UUID id, LocalDateTime inicio, LocalDateTime fim);
    List<Agendamento> findByStatusPagamentoAndDataCriacaoAntes(PagamentoStatus status, LocalDateTime limite);
}
