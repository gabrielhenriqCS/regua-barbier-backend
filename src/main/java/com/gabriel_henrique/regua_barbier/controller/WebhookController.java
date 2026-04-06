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
public class WebhookController {

    private final PagamentosService pagamentosService;

    @PostMapping("/mercado-pago")
    public ResponseEntity<Void> receberNotificacaoMP(@RequestBody Map<String, Object> payload) {
        try {
            String action = (String) payload.get("action");
            if ("payment.updated".equals(action) || "payment.created".equals(action)) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String idExterno = data.get("id").toString();
                String statusMP = payload.get("action").toString();
                pagamentosService.verificarEAtualizarStatus(idExterno, statusMP);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableContent().build();
        }
    }
}
