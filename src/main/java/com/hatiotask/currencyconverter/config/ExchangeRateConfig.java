package com.hatiotask.currencyconverter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "exchange")
@Getter
@Setter
@Primary
public class ExchangeRateConfig {
    private String apiUrl;
    @Value("${exchange.api-key:${EXCHANGE_API_KEY}}")
    private String apiKey = System.getenv("EXCHANGE_API_KEY");

}