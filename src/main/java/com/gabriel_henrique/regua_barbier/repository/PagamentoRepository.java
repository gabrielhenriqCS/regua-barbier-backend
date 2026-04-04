package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.pagamento.FormaDePagamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.Pagamento;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByAgendamentoClienteId(Long clienteId);
    List<Pagamento> findByFormaDePagamento(FormaDePagamento formaDePagamento);
    Optional<Pagamento> findByTransacaoIdExterno(String transacaoIdExterno);
    List<Pagamento> findByStatusEDataCriacaoAntes(PagamentoStatus status, LocalDateTime limite);
}
