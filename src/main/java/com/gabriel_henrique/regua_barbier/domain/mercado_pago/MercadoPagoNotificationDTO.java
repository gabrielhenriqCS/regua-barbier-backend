package com.gabriel_henrique.regua_barbier.domain.mercado_pago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class MercadoPagoNotificationDTO {
    private Long id;

    @JsonProperty("live_mode")
    private boolean liveMode;

    private String type;

    @JsonProperty("date_created")
    private OffsetDateTime dateCreated;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("api_version")
    private String apiVersion;

    private String action;

    private Data data;

    public static class Data {
        private String id;
    }
}
