package com.moddakir.call.Model;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private String message;
    private String action;
    private int statusCode;

    public BaseResponse(String message, String action, int statusCode) {
        this.message = message;
        this.action = action;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
