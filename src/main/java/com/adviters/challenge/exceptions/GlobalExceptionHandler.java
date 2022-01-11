package com.adviters.challenge.exceptions;

import com.adviters.challenge.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runTimeExceptionHandler(RuntimeException exception) {
        ErrorDTO errorDTO = ErrorDTO.builder().code("450").message(exception.getMessage()).build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException exception) {
        ErrorDTO errorDTO = ErrorDTO.builder().code(exception.getCode()).message(exception.getMessage()).build();
        return new ResponseEntity<>(errorDTO, exception.getHttpStatus());
    }
}
