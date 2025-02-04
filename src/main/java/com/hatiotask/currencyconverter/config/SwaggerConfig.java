package com.hatiotask.currencyconverter.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Converter API")
                        .version("1.0")
                        .description("Real-time currency conversion API"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("currency-converter")
                .packagesToScan("com.hatiotask.currencyconverter.controller")
                .pathsToMatch("/api/currency/**")
                .build();
    }
}