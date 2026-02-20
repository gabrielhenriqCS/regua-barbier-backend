package com.gabriel_henrique.regua_barbier.domain.agendamento.exceptions;


public class AgendamentoNaoEncontrado extends RuntimeException{
    public AgendamentoNaoEncontrado() {
        super("Agendamento não encontrado!");
    }

    public AgendamentoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
