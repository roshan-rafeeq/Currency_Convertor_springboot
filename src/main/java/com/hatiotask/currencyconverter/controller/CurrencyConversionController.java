package com.hatiotask.currencyconverter.controller;

import com.hatiotask.currencyconverter.dto.CurrencyConversionDTO;
import com.hatiotask.currencyconverter.service.CurrencyConversionService;
import com.hatiotask.currencyconverter.util.CurrencyValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    @Operation(summary = "Convert currency", description = "Convert amount between currencies")
    @ApiResponse(responseCode = "200", description = "Successful conversion")
    public ResponseEntity<CurrencyConversionDTO> convertCurrency(
            @Valid @RequestParam String fromCurrency,
            @Valid @RequestParam String toCurrency,
            @Valid @RequestParam BigDecimal amount) {
        // Validate currencies before conversion
        CurrencyValidator.validate(fromCurrency);
        CurrencyValidator.validate(toCurrency);

        CurrencyConversionDTO result = conversionService.convertCurrency(
                fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(result);
    }
}