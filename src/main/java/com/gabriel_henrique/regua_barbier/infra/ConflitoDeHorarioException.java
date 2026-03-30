package com.gabriel_henrique.regua_barbier.infra;

public class ConflitoDeHorarioException extends RuntimeException {
    public ConflitoDeHorarioException(String message) {
        super(message);
    }
}
