package com.gabriel_henrique.regua_barbier.domain.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicoDTO(
        @NotBlank String nome,
        @NotNull Float preco,
        @NotNull Integer duracaoMinutos
) {
}
