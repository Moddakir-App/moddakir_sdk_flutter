package com.moddakir.call.Model.Plan;


import java.io.Serializable;
import java.util.List;

public class afterCallResponse implements Serializable {
    String statusCode;
    String message;
    List<PlanPathPartModel> items;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<PlanPathPartModel> getItems() {
        return items;
    }

    public void setItems(List<PlanPathPartModel> items) {
        this.items = items;
    }
}
