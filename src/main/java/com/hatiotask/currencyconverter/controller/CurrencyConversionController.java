package com.hatiotask.currencyconverter.controller;

import com.hatiotask.currencyconverter.dto.CurrencyConversionDTO;
import com.hatiotask.currencyconverter.service.CurrencyConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionController {
    private final CurrencyConversionService conversionService;

    public CurrencyConversionController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/convert")
    public ResponseEntity<CurrencyConversionDTO> convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam BigDecimal amount) {
        CurrencyConversionDTO conversionResult = conversionService.convertCurrency(
                fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(conversionResult);
    }
}