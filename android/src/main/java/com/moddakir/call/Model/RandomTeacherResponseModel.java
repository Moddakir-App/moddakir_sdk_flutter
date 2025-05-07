package com.moddakir.call.Model;


import java.io.Serializable;

public class RandomTeacherResponseModel implements Serializable {
    private int statusCode;
    private String message;
    private String action;
    private String accessToken;
    private User teacher;
    private  int retryDuration;

    public int getRetryDuration() {
        return retryDuration;
    }

    public void setRetryDuration(int retryDuration) {
        this.retryDuration = retryDuration;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
