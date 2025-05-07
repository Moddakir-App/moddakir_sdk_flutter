package com.moddakir.call.Model;

import java.io.Serializable;

public class canMakeCallResponse implements Serializable {

    private String message;
    private String callProviderType;
    private int statusCode;
    private Boolean canJoinCall;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallProviderType() {
        return callProviderType;
    }

    public void setCallProviderType(String callProviderType) {
        this.callProviderType = callProviderType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getCanJoinCall() {
        return canJoinCall;
    }

    public void setCanJoinCall(Boolean canJoinCall) {
        this.canJoinCall = canJoinCall;
    }
}
