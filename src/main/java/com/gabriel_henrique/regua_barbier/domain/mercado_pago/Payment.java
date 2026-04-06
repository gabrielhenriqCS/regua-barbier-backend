package com.gabriel_henrique.regua_barbier.domain.mercado_pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Payment {
    private String id;
    private String orderId;
    private String status;
    private String amount;
    private String statusDetail;
    private Payer payer;
    private String paymentMethodId;

    public enum StatusDetail {

    }

}
