package com.moddakir.call.Model;

import java.io.Serializable;
import java.util.List;

public class ConnectingBannersResponse implements Serializable {

    private String message;

    private int statusCode;
    private List<ConnectingBanner> data;

    public List<ConnectingBanner> getData() {
        return data;
    }

    public void setData(List<ConnectingBanner> data) {
        this.data = data;
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

}
