package com.ram.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApiException extends RuntimeException{
    private String message;
    public ApiException(String message) {
        super(message);
        this.message = message;
    }
}
