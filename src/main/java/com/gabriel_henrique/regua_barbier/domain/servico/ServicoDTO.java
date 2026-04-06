package com.gabriel_henrique.regua_barbier.domain.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicoDTO(
        @NotBlank String nome,
        @NotNull BigDecimal preco,
        @NotNull Integer duracaoMinutos
) {
}
