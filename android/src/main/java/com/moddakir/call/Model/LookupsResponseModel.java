package com.moddakir.call.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class LookupsResponseModel implements Serializable {

    private String message;
    private String action;
    private int statusCode;
    private String totalTime;
    private ArrayList<CheckBoxModel> items;

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

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public ArrayList<CheckBoxModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CheckBoxModel> items) {
        this.items = items;
    }
}
