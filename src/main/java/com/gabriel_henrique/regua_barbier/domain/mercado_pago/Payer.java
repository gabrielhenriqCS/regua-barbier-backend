package com.gabriel_henrique.regua_barbier.domain.mercado_pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payer {
    private String firstName;
    private String lastName;
    private String email;
    private Identification identification;

    @Data
    @Builder
    public static class Identification {
        private String type;
        private String number;
    }
}
