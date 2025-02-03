package com.hatiotask.currencyconverter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "exchange")
@Getter
@Setter
public class ExchangeRateConfig {
    private String apiUrl;
    private String apiKey;
}