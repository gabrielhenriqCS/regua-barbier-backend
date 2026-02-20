package com.gabriel_henrique.regua_barbier.domain.cliente.exceptions;

public class ClienteNaoEncontrado extends RuntimeException {
    public ClienteNaoEncontrado() {
        super("Cliente não encontrado!");
    }

    public ClienteNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
