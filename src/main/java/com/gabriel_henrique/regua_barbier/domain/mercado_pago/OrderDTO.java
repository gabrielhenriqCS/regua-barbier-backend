package com.gabriel_henrique.regua_barbier.domain.mercado_pago;


import java.math.BigDecimal;
import java.util.List;

public record OrderDTO(
        String type,
        BigDecimal total_amount,
        String description,
        String external_reference,
        String notification_url,
        OrderConfig config,
        TransationRequest transations
) {
    public record OrderConfig(
            QrConfig qrConfig
    ) {}
    public record QrConfig(
            String external_pos_id,
            String mode
    ) {}

    public record TransationRequest(
            List<PaymentAmount> payments
    ) {}

    public record PaymentAmount(
            BigDecimal amount
    ) {}
}
