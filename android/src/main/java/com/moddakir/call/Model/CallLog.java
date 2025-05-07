package com.moddakir.call.Model;

import java.io.Serializable;

public class CallLog implements Serializable {
    private String callId;
    private String studentId;
    private String studentName;
    private String studentCountry;
    private String teacherId;
    private String teacherName;
    private String status;
    private String startDateTime;
    private String endDateTime;
    private String duration;
    private String studentAvatarUrl;
    private String teacherAvatarUrl;
    private String createdAt;
    private String date;
    private String type;
    private String callSessionId;
    private float assignmentValue;
    boolean isStudentHasPlans;

    public String getCid() {
        return callId;
    }

    public void setCid(String cid) {
        this.callId = cid;
    }

    public String getSid() {
        return studentId;
    }

    public void setSid(String sid) {
        this.startDateTime = sid;
    }

    public String getSname() {
        return studentName;
    }

    public void setSname(String sname) {
        this.startDateTime = sname;
    }

    public String getsCountry() {
        return studentCountry;
    }

    public void setsCountry(String sCountry) {
        this.studentCountry = sCountry;
    }

    public String getTid() {
        return teacherId;
    }

    public void setTid(String tid) {
        this.teacherId = tid;
    }

    public String getTname() {
        return teacherName;
    }

    public void setTname(String tname) {
        this.teacherName = tname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartdate() {
        return startDateTime;
    }

    public void setStartdate(String startdate) {
        this.startDateTime = startdate;
    }

    public String getEnddate() {
        return endDateTime;
    }

    public void setEnddate(String enddate) {
        this.endDateTime = enddate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSavatarurl() {
        return studentAvatarUrl;
    }

    public void setSavatarurl(String savatarurl) {
        this.studentAvatarUrl = savatarurl;
    }

    public String getTavatarurl() {
        return teacherAvatarUrl;
    }

    public void setTavatarurl(String tavatarurl) {
        this.teacherAvatarUrl = tavatarurl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCallSessionId() {
        return callSessionId;
    }

    public void setCallSessionId(String callSessionId) {
        this.callSessionId = callSessionId;
    }

    public float getAssignmentValue() {
        return assignmentValue;
    }

    public void setAssignmentValue(float assignmentValue) {
        this.assignmentValue = assignmentValue;
    }


}
