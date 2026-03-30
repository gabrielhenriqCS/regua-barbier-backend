package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.pagamento.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByClienteId(Long clienteId);
}
