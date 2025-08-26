package com.NextOne.Challenge3.Services.exceptions;


import org.springframework.validation.FieldError;

public record ValidationErrorData(String campo, String mensaje) {
    public ValidationErrorData(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}