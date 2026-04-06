package com.gabriel_henrique.regua_barbier.exceptions;

public class DadosNaoDeletadosException extends RuntimeException {
    public DadosNaoDeletadosException(String mensagem) {
        super(mensagem);
    }
}
