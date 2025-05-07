package com.moddakir.call.Model.Plan;


import java.io.Serializable;

public class PlanPathPartModel implements Serializable {
    Integer fromSurah;
    Integer toSurah;
    String fromAyah;
    String toAyah;
    String status;
    String lessonTitle;
    String date;
    slotsModel slot;
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public slotsModel getSlot() {
        return slot;
    }

    public void setSlot(slotsModel slot) {
        this.slot = slot;
    }

    public PlanPathPartModel(Integer fromSurah, Integer toSurah, String fromAyah, String toAyah, String status, String date, slotsModel slot) {
        this.fromSurah = fromSurah;
        this.toSurah = toSurah;
        this.fromAyah = fromAyah;
        this.toAyah = toAyah;
        this.status = status;
        this.date = date;
        this.slot = slot;
    }

    public Integer getFromSurah() {
        return fromSurah;
    }

    public void setFromSurah(Integer fromSurah) {
        this.fromSurah = fromSurah;
    }

    public Integer getToSurah() {
        return toSurah;
    }

    public void setToSurah(Integer toSurah) {
        this.toSurah = toSurah;
    }

    public String getFromAyah() {
        return fromAyah;
    }

    public void setFromAyah(String fromAyah) {
        this.fromAyah = fromAyah;
    }

    public String getToAyah() {
        return toAyah;
    }

    public void setToAyah(String toAyah) {
        this.toAyah = toAyah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
