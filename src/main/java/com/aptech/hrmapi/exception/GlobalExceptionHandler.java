package com.aptech.hrmapi.exception;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.response.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * * @author : VinhTQ
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //Get all errors
        List<String> errors = new ArrayList<>();
        for (FieldError x : ex.getBindingResult()
                .getFieldErrors()) {
            String defaultMessage = x.getDefaultMessage();
            errors.add(defaultMessage);
        }

        return new ResponseEntity<>(new ResponseBody(Response.DATA_INVALID, errors), HttpStatus.OK);
    }
}
