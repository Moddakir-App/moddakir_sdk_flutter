package com.moddakir.call.Model;

import java.io.Serializable;

public class ConfigModel implements Serializable {

    private String sinchAppKey;
    private String sinchSecretKey;


    public String getSinchAppKey() {
        return sinchAppKey;
    }

    public void setSinchAppKey(String sinchAppKey) {
        this.sinchAppKey = sinchAppKey;
    }

    public String getSinchSecretKey() {
        return sinchSecretKey;
    }

    public void setSinchSecretKey(String sinchSecretKey) {
        this.sinchSecretKey = sinchSecretKey;
    }

}
