package com.gabriel_henrique.regua_barbier.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long clienteId, String email) {

}
