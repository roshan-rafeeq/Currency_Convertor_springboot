package com.hatiotask.currencyconverter.util;

import com.hatiotask.currencyconverter.exception.CurrencyConversionException;
import java.util.Set;

public class CurrencyValidator {
    private static final Set<String> VALID_CURRENCIES = Set.of(
            "USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "INR");

    public static void validate(String currency) {
        if (!VALID_CURRENCIES.contains(currency.toUpperCase())) {
            throw new CurrencyConversionException("Invalid currency: " + currency);
        }
    }
}