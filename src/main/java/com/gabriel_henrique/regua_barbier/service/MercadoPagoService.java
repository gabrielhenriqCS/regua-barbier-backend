package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.exceptions.PagamentoException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final PaymentClient paymentClient;

    @Value("${api.mercado-pago.access-token}")
    private String accessToken;

    @Value("${api.webhook.mercado-pago}")
    private String webhookUrl;

    public String criarPagamento(Agendamento agendamento, String tokenCartao, String paymentMethodId, Integer parcelas) throws Exception {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PaymentCreateRequest request = PaymentCreateRequest.builder()
                    .transactionAmount(agendamento.getServico().getPreco())
                    .token(tokenCartao)
                    .description("Corte de Cabelo - Régua Barbier")
                    .installments(parcelas != null ? parcelas : 1)
                    .paymentMethodId(paymentMethodId)
                    .notificationUrl(webhookUrl + "/v1/pagamentos/mercado-pago")
                    .payer(PaymentPayerRequest.builder()
                            .email(agendamento.getCliente().getEmail())
                            .build())
                    .build();
            if (!paymentMethodId.equals("pix")) {
                return request.getToken();
            }
            Payment payment = paymentClient.create(request);
            if (paymentMethodId.equals("pix")) {
                return payment.getPointOfInteraction().getTransactionData().getQrCode();
            }
            return payment.getId().toString();
        } catch (MPException | MPApiException e) {
            throw new PagamentoException("Erro ao processar pagamento");
        }
    }
 }
