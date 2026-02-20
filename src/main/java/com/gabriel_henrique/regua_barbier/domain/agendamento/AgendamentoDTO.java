package com.gabriel_henrique.regua_barbier.domain.agendamento;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoDTO(
        @NotNull Barbeiro barbeiroId,
        @NotNull Cliente clienteId,
        @NotNull Servico servicoId,
        @NotNull @Future LocalDateTime dataHoraInicio,
        @NotNull @Future LocalDateTime dataHoraFim
        ) {
}
