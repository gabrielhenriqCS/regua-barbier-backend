package com.gabriel_henrique.regua_barbier.repository;

import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    UserDetails findUserByEmail(String email);
}
