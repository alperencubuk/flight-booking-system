package com.iyzico.challenge.handler;

import com.iyzico.challenge.dto.ErrorResponse;
import com.iyzico.challenge.exception.GeneralException;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GeneralException.class})
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(StaleStateException.class)
    public ResponseEntity<ErrorResponse> handleStaleStateException(StaleStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Payment failed. Seat sold to another user."));
    }
}
