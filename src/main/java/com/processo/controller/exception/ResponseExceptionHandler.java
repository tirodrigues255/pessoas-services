package com.processo.controller.exception;

import com.processo.model.response.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {
        return new ResponseEntity<>(buildErrorMessage(e), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private ErrorMessage buildErrorMessage(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDateTime(new Date());
        String message = e.getLocalizedMessage();
        if (message == null) {
            errorMessage.setMessage(e.toString());
        } else {
            errorMessage.setMessage(message);
        }
        return errorMessage;
    }
}
