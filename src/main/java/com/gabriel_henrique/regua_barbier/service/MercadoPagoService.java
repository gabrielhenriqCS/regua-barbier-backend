package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoService {

    @Value("${mercado_pago_access_token}")
    private String accessToken;

    public String criarPagamentoPix(Agendamento agendamento) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .transactionAmount(agendamento.getServico().getPreco())
                .description("Corte de cabelo - Régua Barbier")
                .paymentMethodId("pix")
                .notificationUrl("https://regua-barbier.com/v1/pagamentos/mercado-pago")
                .payer(PaymentPayerRequest.builder()
                        .email(agendamento.getCliente().getEmail())
                        .firstName(agendamento.getCliente().getNome())
                        .build())
                .build();

        Payment payment = client.create(request);
        return payment.getPointOfInteraction().getTransactionData().getQrCode();
    }
}
