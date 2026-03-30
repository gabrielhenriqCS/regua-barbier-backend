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

    // 404 - Recursos não encontrados
    @ExceptionHandler({
            ClienteNaoEncontrado.class,
            BarbeiroNaoEncontrado.class,
            AgendamentoNaoEncontrado.class,
            ServicoNaoEncontrado.class
    })
    public ResponseEntity<ErrorResponse> handlerNotFound(RuntimeException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // 400 - Falha na atualização (Bad Request)
    @ExceptionHandler(AtualizacaoDadosException.class)
    public ResponseEntity<ErrorResponse> handlerAtualizacaoDados(AtualizacaoDadosException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 422 - Dados Duplicados ou Inválidos (Unprocessable Entity)
    @ExceptionHandler({
            DadosPreenchidosMultiplasVezesException.class,
            DadosInvalidosException.class,
            ConflitoDeHorarioException.class
    })
    public ResponseEntity<ErrorResponse> handlerDadosNaoProcessaveis(RuntimeException e) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    }

    // 409 - Conflito (Ex: Falha ao deletar algo que possui dependências)
    @ExceptionHandler(DadosNaoDeletadosException.class)
    public ResponseEntity<ErrorResponse> handlerConflito(DadosNaoDeletadosException e) {
        return buildResponse(HttpStatus.CONFLICT, e.getMessage());
    }
}