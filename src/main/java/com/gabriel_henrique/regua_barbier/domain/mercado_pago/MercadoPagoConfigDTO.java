package com.gabriel_henrique.regua_barbier.domain.mercado_pago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

public class MercadoPagoConfigDTO {
    private String action;

    @JsonProperty("api_version")
    private String apiVersion;

    private Date data;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    private Long id;

    @JsonProperty("live_mode")
    private Boolean liveMode;

    private String type;

    @JsonProperty("user_id")
    private Long userId;

    @Data
    public static class Date {
        private String id;
    }
}
