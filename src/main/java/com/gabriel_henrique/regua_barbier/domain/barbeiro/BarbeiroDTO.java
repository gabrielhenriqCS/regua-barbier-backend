package com.gabriel_henrique.regua_barbier.domain.barbeiro;

import jakarta.validation.constraints.NotNull;

public record BarbeiroDTO(
        @NotNull String nome,
        @NotNull String especialidade,
        @NotNull Boolean ativo
        ) {
}
