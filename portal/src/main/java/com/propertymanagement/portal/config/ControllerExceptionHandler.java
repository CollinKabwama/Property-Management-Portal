package com.propertymanagement.portal.config;


import com.propertymanagement.portal.exception.InvalidInputException;
import com.propertymanagement.portal.exception.RecordAlreadyExistsException;
import com.propertymanagement.portal.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {RecordAlreadyExistsException.class, InvalidInputException.class, RecordNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse clientInputException(Exception ex, WebRequest request) {
        ErrorResponse message =
                ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return message;
    }
}



