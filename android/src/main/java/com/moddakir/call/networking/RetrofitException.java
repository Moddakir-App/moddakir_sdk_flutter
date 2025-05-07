package com.moddakir.call.networking;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class RetrofitException extends RuntimeException {// class to collect all error data may be you did not use all but for future usage if needed
    String message;
    @StringRes
    int messageResourceId;
    private String responseBody;
    int responseCode;
    /**
     * The event kind which triggered this error.
     */
    NetworkErrorKind kind;
    Throwable exception;
    int errorCode;

    public RetrofitException(String message, int messageResourceId, String responseBody, int responseCode, NetworkErrorKind networkErrorKind, Throwable throwable, int errorCode) {
        this.message = message;
        this.messageResourceId = messageResourceId;
        this.responseBody = responseBody;
        this.kind = networkErrorKind;
        this.responseCode = responseCode;
        this.exception = throwable;
        this.errorCode = errorCode;
    }


    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageResourceId() {
        return messageResourceId;
    }

    public void setMessageResourceId(int messageResourceId) {
        this.messageResourceId = messageResourceId;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public NetworkErrorKind getKind() {
        return kind;
    }

    public void setKind(NetworkErrorKind kind) {
        this.kind = kind;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
