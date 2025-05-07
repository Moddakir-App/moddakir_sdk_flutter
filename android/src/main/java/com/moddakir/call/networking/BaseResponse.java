package com.moddakir.call.networking;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class BaseResponse<T> implements Serializable {
    @SerializedName("result")
    BaseResponseResult result = null;

    @SerializedName("data")
    T data = null;


    void setData(T data) {
        this.data = data;
    }


    static class BaseResponseResult {
        @SerializedName("success")
        boolean isSuccess = false;

        @SerializedName("code")
        int code = 0;

        @SerializedName("message")
        String message = "";

    }
}