package com.gabriel_henrique.regua_barbier.domain.pagamento;


import lombok.Getter;

@Getter
public enum PagamentoStatus {
    PENDENTE("pendente"), APROVADO("aprovado"), RECUSADO("recusado"), PAGO("pago");

    private String status;

    PagamentoStatus(String status) {
        this.status = status;
    }
}
