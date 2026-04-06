package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.agendamento.StatusAgendamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatusAgendamento(StatusAgendamento statusAgendamento);
    List<Agendamento> findByStatusPagamentoAndDataCriacaoBefore(PagamentoStatus statusPagamento, LocalDateTime limite);
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.barbeiro.id = :barbeiroId " +
            "AND a.statusAgendamento != 'CANCELADO' " +
            "AND (:inicio < a.dataTermino AND :fim > a.dataInicio)")
    boolean existeConflito(UUID barbeiroId, LocalDateTime inicio, LocalDateTime fim);

}
