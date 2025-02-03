package com.hatiotask.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CurrencyConversionException.class)
    public ResponseEntity<ErrorResponse> handleCurrencyConversionException(
            CurrencyConversionException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}