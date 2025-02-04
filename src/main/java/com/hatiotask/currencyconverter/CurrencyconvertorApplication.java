package com.hatiotask.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hatiotask.currencyconverter.config.ExchangeRateConfig;

@SpringBootApplication
@EnableConfigurationProperties(ExchangeRateConfig.class)
public class CurrencyconvertorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyconvertorApplication.class, args);
	}

}
