package com.moddakir.call.Model;

import java.io.Serializable;

public class FreezeAccountResponseModel implements Serializable {
    private String message;
    private int statusCode;
    private String freezeEndDate;

    public FreezeAccountResponseModel(String message, int statusCode, String freezeEndDate) {
        this.message = message;
        this.statusCode = statusCode;
        this.freezeEndDate = freezeEndDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getFreezeEndDate() {
        return freezeEndDate;
    }

    public void setFreezeEndDate(String freezeEndDate) {
        this.freezeEndDate = freezeEndDate;
    }
}
