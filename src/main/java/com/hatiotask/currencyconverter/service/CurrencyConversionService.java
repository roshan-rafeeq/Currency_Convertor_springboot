package com.hatiotask.currencyconverter.service;

import com.hatiotask.currencyconverter.config.ExchangeRateConfig;
import com.hatiotask.currencyconverter.dto.CurrencyConversionDTO;
import com.hatiotask.currencyconverter.exception.CurrencyConversionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class CurrencyConversionService {
    private final ExchangeRateConfig config;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public CurrencyConversionService(ExchangeRateConfig config) {
        this.config = config;
        this.restClient = RestClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public CurrencyConversionDTO convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
        try {
            log.info("Converting {} {} to {}", amount, fromCurrency, toCurrency);
            String fullApiUrl = buildApiUrl(fromCurrency);
            String response = fetchExchangeRates(fullApiUrl);
            JsonNode rootNode = parseResponse(response);

            BigDecimal exchangeRate = extractExchangeRate(rootNode, toCurrency);
            BigDecimal convertedAmount = calculateConvertedAmount(amount, exchangeRate);
            log.info("Conversion successful: {} {} = {} {}",
                    amount, fromCurrency, convertedAmount, toCurrency);
            return createConversionDTO(fromCurrency, toCurrency, amount, convertedAmount, exchangeRate);

        } catch (Exception e) {
            log.error("Conversion error: {}", e.getMessage());
            throw new CurrencyConversionException("Currency conversion failed: " + e.getMessage());
        }
    }

    private String buildApiUrl(String fromCurrency) {
        return String.format("%s/%s/latest/%s",
                config.getApiUrl(),
                config.getApiKey(),
                fromCurrency);
    }

    private String fetchExchangeRates(String url) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }

    private JsonNode parseResponse(String response) throws IOException {
        return objectMapper.readTree(response);
    }

    private BigDecimal extractExchangeRate(JsonNode rootNode, String toCurrency) {
        JsonNode conversionRates = rootNode.path("conversion_rates");

        if (conversionRates.isMissingNode() || !conversionRates.has(toCurrency)) {
            throw new CurrencyConversionException("Invalid currency: " + toCurrency);
        }

        return BigDecimal.valueOf(conversionRates.get(toCurrency).asDouble());
    }

    private BigDecimal calculateConvertedAmount(BigDecimal amount, BigDecimal exchangeRate) {
        return amount.multiply(exchangeRate)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private CurrencyConversionDTO createConversionDTO(
            String fromCurrency,
            String toCurrency,
            BigDecimal amount,
            BigDecimal convertedAmount,
            BigDecimal exchangeRate) {
        return new CurrencyConversionDTO(
                fromCurrency,
                toCurrency,
                amount,
                convertedAmount,
                exchangeRate);
    }
}