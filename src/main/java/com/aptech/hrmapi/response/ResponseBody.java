package com.aptech.hrmapi.response;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody {
    private String responseCode;
    private String responseMessage;
    private Object responseData;

    public ResponseBody(Response response, Object data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
        this.responseData = data;
    }

    public ResponseBody(CommonException e, Object data) {
        this.responseCode = e.getResponse().getResponseCode();
        this.responseMessage = e.getMessage();
        this.responseData = data;
    }

    public ResponseBody(Response response) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
    }

    public ResponseBody(Response response, String responseMessage) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = responseMessage;
    }

    public ResponseBody(Response response, String responseMessage, Object data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = responseMessage;
        this.responseData = data;
    }

    public ResponseBody(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ResponseBody(String responseCode, String responseMessage, Object data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseData = data;
    }
    public ResponseBody(CustomException customException){
        this.responseCode = String.valueOf(customException.getErrorCode());
        this.responseMessage = customException.getMessage();
    }

    public ResponseBody(Object obj){
        this.responseData = obj;
    }
}
