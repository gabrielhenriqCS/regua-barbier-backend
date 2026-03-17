package com.gabriel_henrique.regua_barbier.domain.auth;

import jakarta.validation.constraints.NotNull;

public record Cadastrar(
        @NotNull(message = "Importante preencher seu nome para cadastro") String nome,
        @NotNull(message = "Importante preencher com seu email") String email,
        @NotNull(message = "Senha é obrigatório") String senha,
        @NotNull String telefone
) {
}
