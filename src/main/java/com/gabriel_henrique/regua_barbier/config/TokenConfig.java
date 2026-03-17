package com.gabriel_henrique.regua_barbier.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;

@Component
public class TokenConfig {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Cliente cliente) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("cliente_id", Collections.singletonList(cliente.getId()))
                .withSubject(cliente.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(56000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
}
