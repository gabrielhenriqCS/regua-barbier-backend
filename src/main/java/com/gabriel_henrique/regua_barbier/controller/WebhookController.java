package com.gabriel_henrique.regua_barbier.controller;


import com.gabriel_henrique.regua_barbier.domain.mercado_pago.MercadoPagoNotificationDTO;
import com.gabriel_henrique.regua_barbier.service.MercadoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pagamentos/")
public class WebhookController {

    private final MercadoPagoService mercadoPagoService;

    @Value("${api.mercadoo-pago.client-secret}")
    private String secret;

    @PostMapping("/mercado-pago")
    public ResponseEntity<Void> receberNotificacaoMP(@RequestHeader("x-signature") String signature,
                                                     @RequestHeader("x-request-id") String requestId,
                                                     @RequestBody MercadoPagoNotificationDTO payload) {


        if ("payment".equals(payload.getType())) {
            mercadoPagoService.processarNotificacao(payload.getData().getId());
        }
        return ResponseEntity.ok().build();
    }
}
