package com.moddakir.call.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherCategoriesResponse implements Serializable {
    private String message;
    private String action;
    private int statusCode;
    private ArrayList<TeacherCategoryModel> teacherCategories;

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

    public ArrayList<TeacherCategoryModel> getTeacherCategories() {
        return teacherCategories;
    }

    public void setTeacherCategories(ArrayList<TeacherCategoryModel> teacherCategories) {
        this.teacherCategories = teacherCategories;
    }
}
