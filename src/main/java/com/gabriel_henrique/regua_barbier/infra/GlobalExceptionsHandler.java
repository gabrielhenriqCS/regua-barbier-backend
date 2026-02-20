package com.gabriel_henrique.regua_barbier.infra;

import com.gabriel_henrique.regua_barbier.domain.agendamento.exceptions.AgendamentoNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.exceptions.BarbeiroNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.domain.servico.exceptions.ServicoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), mensagem));
    }

    @ExceptionHandler(ClienteNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> handlerClienteNaoEncontrado(RuntimeException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BarbeiroNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> handlerBarbeiroNaoEncontrado(RuntimeException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AgendamentoNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> handlerAgendamentoNaoEncontrado(RuntimeException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ServicoNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> handlerServicoNaoEncontrado(RuntimeException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AtualizacaoDadosException.class)
    public ResponseEntity<ErrorResponse> handlerDadosNaoAtualizados(AtualizacaoDadosException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, e.getMessage()));
    }
}
