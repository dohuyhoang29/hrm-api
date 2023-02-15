package com.aptech.hrmapi.exception;


import com.aptech.hrmapi.common.Response;

public class CustomException extends RuntimeException{
    private String errorCode = "500";

    private String message;

    public CustomException(String msg) {
        super(msg);
        this.message = msg;
    }

    public CustomException(int errorCode, String msg) {
        super(msg);
        this.errorCode = String.valueOf(errorCode);
        this.message = msg;
    }

    public CustomException(String msg, Throwable ex) {
        super(msg, ex);
        this.message = msg;
    }

    public CustomException(Response response) {
        this.errorCode = response.getResponseCode();
        this.message = response.getResponseMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
