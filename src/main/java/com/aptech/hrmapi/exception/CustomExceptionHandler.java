package com.aptech.hrmapi.exception;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.response.ResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2(topic = "ErrorLogger")
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(methodArgumentNotValidException.getMessage(),methodArgumentNotValidException);
        return new ResponseEntity(methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException accessDeniedException) {
        log.error(accessDeniedException.getMessage(),accessDeniedException);
        return new ResponseEntity(accessDeniedException.getMessage(),HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseBody handleCustomException(CustomException customException) {
        log.error("Custom Exception : ",customException.getMessage());
            return new ResponseBody(customException);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBody handleCustomException(Exception customException) {
        log.error(customException.getMessage(),customException);
        return new ResponseBody(new CustomException(Response.SYSTEM_ERROR));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseBody handleCommonException(CommonException commonException) {
        log.error(commonException.getMessage(),commonException);
        return new ResponseBody(commonException.getResponse());
    }
}
