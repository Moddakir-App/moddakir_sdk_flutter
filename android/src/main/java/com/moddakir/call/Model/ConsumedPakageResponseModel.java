package com.moddakir.call.Model;

import com.moddakir.call.utils.Utils;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsumedPakageResponseModel implements Serializable {

    private int statusCode;
    private float totalConsumed;
    private float availableDuration;
    private float totalDuration;
    private String freezeEndDate;
    private boolean isFreezed;
    private String freezeMessage;

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean freezed) {
        isFreezed = freezed;
    }

    public String getFreezeMessage() {
        return freezeMessage;
    }

    public void setFreezeMessage(String freezeMessage) {
        this.freezeMessage = freezeMessage;
    }

    public String getFreezeEndDate() {
        return freezeEndDate;
    }

    public void setFreezeEndDate(String freezeEndDate) {
        this.freezeEndDate = freezeEndDate;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public float getConsumedMinitues() {
        BigDecimal bigDecimal = new BigDecimal(totalConsumed).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return bigDecimal.floatValue();
    }

    public void setConsumedMinitues(float consumedMinitues) {
        this.totalConsumed = consumedMinitues;
    }

    public String getConsumedFormateMinitues() {
        return Utils.parseMinutesToMMss(totalConsumed);
    }

    public float getRemainingMinitues() {
        BigDecimal bigDecimal = new BigDecimal(availableDuration).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return bigDecimal.floatValue();
    }

    public void setRemainingMinitues(float remainingMinitues) {
        this.availableDuration = remainingMinitues;
    }

    public String getRemainingFormateMinitues() {
        return Utils.parseMinutesToMMss(availableDuration);
    }

    public float getTotalMinitues() {
        BigDecimal bigDecimal = new BigDecimal(totalDuration).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return bigDecimal.floatValue();
    }

    public void setTotalMinitues(float totalMinitues) {
        this.totalDuration = totalMinitues;
    }

    public String getTotalFormateMinitues() {
        return Utils.parseMinutesToMMss(totalDuration);
    }

}
