package com.hatiotask.currencyconverter.controller;

import com.hatiotask.currencyconverter.dto.CurrencyConversionDTO;
import com.hatiotask.currencyconverter.service.CurrencyConversionService;
import com.hatiotask.currencyconverter.util.CurrencyValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;

@Tag(name = "Currency Conversion API", description = "Endpoints for currency conversion")
@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionController {
    @Autowired
    private final CurrencyConversionService conversionService;

    public CurrencyConversionController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert")
    @Operation(summary = "Convert currency", description = "Convert amount between currencies")
    @ApiResponse(responseCode = "200", description = "Successful conversion")
    public ResponseEntity<CurrencyConversionDTO> convertCurrency(
            @RequestParam(name = "from") String fromCurrency,
            @RequestParam(name = "to") String toCurrency,
            @RequestParam BigDecimal amount) {
        CurrencyValidator.validate(fromCurrency);
        CurrencyValidator.validate(toCurrency);

        CurrencyConversionDTO result = conversionService.convertCurrency(
                fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(result);
    }
}