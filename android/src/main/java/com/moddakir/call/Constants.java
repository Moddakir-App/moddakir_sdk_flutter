package com.moddakir.call;


public class Constants {
    public static final float DEFAULT_TEXT_SIZE = 13.0f;
    public static String AppID  ,AppKey ;
    public static String CALL_IDS = "callIds";

    public static String getAppID() {
        return AppID;
    }

    public static void setAppID(String appID) {
        AppID = appID;
    }

    public static String getAppKey() {
        return AppKey;
    }

    public static void setAppKey(String appKey) {
        AppKey = appKey;
    }
}
