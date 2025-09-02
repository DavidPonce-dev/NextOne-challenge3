package com.NextOne.Challenge3.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RequestExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> error400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream()
                .map(ValidationErrorData::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<DuplicateError>> error409(DataIntegrityViolationException ex) {
        String msg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        var m = java.util.regex.Pattern
                .compile("Duplicate entry '(.+?)' for key '(.+?)'")
                .matcher(msg != null ? msg : "");

        String value = null;
        String key = null;
        String field = null;
        String message = "Valor duplicado";
        if (m.find()) {
            value = m.group(1);
            key   = m.group(2);
            field = key.contains(".") ? key.substring(key.indexOf('.') + 1) : key;
        }
        List<DuplicateError> errores = List.of(
                new DuplicateError("duplicate_key", field, value, message, 1062)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errores);
    }
}
