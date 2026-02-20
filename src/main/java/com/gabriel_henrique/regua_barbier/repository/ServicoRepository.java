package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
