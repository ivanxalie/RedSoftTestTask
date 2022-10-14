package org.redsoft.test.accounting.controller;

import org.redsoft.test.accounting.entities.EntityCustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EntitiesAdviceController {
    @ExceptionHandler(EntityCustomException.class)
    public ResponseEntity<?> catchBossAlreadyExists(EntityCustomException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> catchConstraintValidationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n")));
    }
}
