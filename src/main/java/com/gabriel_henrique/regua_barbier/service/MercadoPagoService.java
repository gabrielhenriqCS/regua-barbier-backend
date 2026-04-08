package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.agendamento.Agendamento;
import com.gabriel_henrique.regua_barbier.domain.mercado_pago.OrderDTO;
import com.gabriel_henrique.regua_barbier.exceptions.PagamentoException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MercadoPagoService {

    private final PaymentClient paymentClient;

    private final PagamentosService pagamentosService;

    @Value("${api.mercado-pago.access-token}")
    private String accessToken;

    @Value("${api.webhook.mercado-pago}")
    private String webhookUrl;

    public void processarNotificacao(String idPagamentoMP) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            Payment payment = paymentClient.get(Long.valueOf(idPagamentoMP));

            String statusReal = payment.getStatus();

            pagamentosService.verificarEAtualizarStatus(idPagamentoMP, statusReal);
        } catch (MPException | MPApiException e) {
            log.error("Erro ao consultar API do Mercado Pago para o ID: {}", idPagamentoMP);
        }
    }

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

            Payment payment = paymentClient.create(request);
            if (paymentMethodId.equals("pix")) {
                return payment.getPointOfInteraction().getTransactionData().getQrCode();
            }
            return payment.getId().toString();
        } catch (MPException | MPApiException e) {
            throw new PagamentoException("Erro ao processar pagamento.");
        }
    }

    public void gerarPedido(Agendamento agendamento) {
        String url = "https://api.mercadopago.com/v1/orders";

        OrderDTO order = new OrderDTO(
                "qr",
                agendamento.getServico().getPreco(),
                "Corte " + agendamento.getServico().getNome(),
                agendamento.getId().toString(),
                webhookUrl + "/v1/pagamentos/mercado-pago",
                new OrderDTO.OrderConfig(new OrderDTO.QrConfig("REGUABARBPOS001", "static")),
                new OrderDTO.TransationRequest(List.of(new OrderDTO.PaymentAmount(agendamento.getServico().getPreco())))
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("X-Idempotency-Key", "AGEND-" + agendamento.getId()); // Evita cobrança duplicada
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderDTO> entity = new HttpEntity<>(order, headers);

        try {
            restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Pedido enviado ao QR Code do REGUABARBPOS001!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar pedido: " + e.getMessage());
        }
    }

    public void reembolsarCliente() {

    }
 }
