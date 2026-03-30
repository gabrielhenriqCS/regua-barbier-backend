package com.gabriel_henrique.regua_barbier.domain.pagamento;

import lombok.Getter;

@Getter
public enum FormaDePagamento {
    PIX("pix"), CARTAO_DE_CREDITO("cartão de crédito"), CARTAO_DE_DEBITO("cartão de débito"), DINHEIRO("dinheiro");

    private String formaDePagamento;

    FormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }
}
