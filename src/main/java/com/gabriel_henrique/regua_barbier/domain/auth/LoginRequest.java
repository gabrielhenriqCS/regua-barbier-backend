package com.gabriel_henrique.regua_barbier.domain.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "E-mail deve ser preenchido!") String email,
        @NotEmpty(message = "Senha deve ser preenchida!") String senha
) {
}
