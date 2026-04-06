package com.gabriel_henrique.regua_barbier.domain.mercado_pago;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MercadoPagoConfigDTO {
    private String action;

    @JsonProperty("api_version")
    private String apiVersion;

    private Data data;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    private Long id;

    @JsonProperty("live_mode")
    private Boolean liveMode;

    private String type;

    @JsonProperty("user_id")
    private Long userId;

    public static class Data {
        private String id;
    }
}
