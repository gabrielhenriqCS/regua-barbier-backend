package com.gabriel_henrique.regua_barbier.domain.servico.exceptions;


public class ServicoNaoEncontrado extends RuntimeException {
    public ServicoNaoEncontrado() {
        super("Serviço não encontrado!");
    }
    public ServicoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
