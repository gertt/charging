package com.example.charging.exeptions;

import java.util.*;
import java.util.stream.Collectors;

import com.example.charging.payload.response.RateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.charging.util.AppConstants.BED_REQUEST;
import static com.example.charging.util.AppConstants.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * This method handles errors for the validation of fields,
     * that can be a null field ora negative or empty value of the field.
     **/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        log.error(BED_REQUEST + "exception : {} fieldError : [{}] url : {}", exception, fieldErrors, ((ServletWebRequest) request).getRequest().getRequestURI());
        return buildErrorResponse(BED_REQUEST, HttpStatus.BAD_REQUEST, fieldErrors);
    }

    /**
     * This method handles exception errors ,that can be an Internal Server Error
     * when something unexpected has occurred.
     **/
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest request) {
        log.error(INTERNAL_SERVER_ERROR + "exception : {}  url : {}", exception, ((ServletWebRequest) request).getRequest().getRequestURI());
        return buildErrorResponse(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<Object> buildErrorResponse(String message,
                                                      HttpStatus httpStatus,
                                                      List<String> errors) {
        return ResponseEntity.status(httpStatus).body(new RateResponse(false, message, errors, null));
    }
}
