package com.example.translationservice.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<String> notEnoughMoneyExceptionHandler(FeignException e) {

        return switch (e.status()) {
            case 402 -> ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                    .body("You don't have enough money");
            case 404 -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Something went wrong");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");
        };
    }
}
