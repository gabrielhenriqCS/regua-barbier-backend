package com.gabriel_henrique.regua_barbier.domain.agendamento;

import lombok.Getter;

@Getter
public enum StatusAgendamento {
    PENDENTE_PAGAMENTO("pagamento_pendente"),
    CONFIRMADO("confirmado"),
    CONCLUIDO("concluído"),
    CANCELADO("cancelado"),
    FALTOU("faltou")
    ;

    private String statusAgendamento;

    StatusAgendamento(String statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

}
