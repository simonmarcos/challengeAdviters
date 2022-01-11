package com.adviters.challenge.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestException extends RuntimeException {

    private String code;
    private HttpStatus httpStatus;

    public RequestException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
