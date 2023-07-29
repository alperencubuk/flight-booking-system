package com.iyzico.challenge.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends RuntimeException {
    final HttpStatus statusCode;

    public GeneralException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
