package com.gabriel_henrique.regua_barbier.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Régua Barbier API")
                        .description("Sistema de agendamento - Spring Boot 4.0.2")
                        .version("v1"));
    }
}