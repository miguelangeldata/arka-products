package com.store.products.infraestructure.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class OpenApiConfig implements WebFluxConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Store Products Service API")
                        .version("1.0.0")
                        .description("API for store products management"));
    }

}