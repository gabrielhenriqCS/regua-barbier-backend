package com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions;


public class BarbeiroNaoEncontrado extends RuntimeException {
    public BarbeiroNaoEncontrado() {
        super("Barbeiro não encontrado!");
    }

    public BarbeiroNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
