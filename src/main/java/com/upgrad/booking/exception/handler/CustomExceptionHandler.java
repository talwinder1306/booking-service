package com.upgrad.booking.exception.handler;

import com.upgrad.booking.exception.InvalidInputException;
import com.upgrad.booking.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(InvalidInputException e, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);

    }
}
