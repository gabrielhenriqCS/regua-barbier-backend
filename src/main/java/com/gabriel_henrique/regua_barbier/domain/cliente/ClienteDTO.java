package com.gabriel_henrique.regua_barbier.domain.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotNull @Email String email
        ) {
}
