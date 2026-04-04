package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.service.PagamentosService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pagamentos/")
public class PagamentosController {

    private final PagamentosService pagamentosService;

    @PostMapping("/mercado-pago")
    public ResponseEntity<Void> receberNotificacaoMP(@RequestBody Map<String, Object> payload) {
        try {
            String idExterno = payload.get("data").toString();
            String status = "aprovado";
            pagamentosService.processarTransacaoDePagamento(idExterno, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).build();
        }
    }
}
