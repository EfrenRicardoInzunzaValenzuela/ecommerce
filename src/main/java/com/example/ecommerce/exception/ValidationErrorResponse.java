package com.example.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {
    private String message;
    private List<String> errors;

    public ValidationErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

}