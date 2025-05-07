package com.moddakir.call.Model;

import java.io.Serializable;

public class ConnectingBanner implements Serializable {

    private String url;

    private String title;

    private int index;

    public ConnectingBanner(String url, String title, int index) {
        this.url = url;
        this.title = title;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}