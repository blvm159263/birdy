package com.newbies.birdy.exceptions.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        EntityException exception = new EntityException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
        );
        return new ResponseEntity<>(exception, notFound);
    }

}

