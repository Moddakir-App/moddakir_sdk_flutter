package com.moddakir.call.Model;

import java.io.Serializable;

public class CreateCallResponseModel implements Serializable {
    public Boolean getEnableCallLogs() {
        return enableCallLogs;
    }

    public void setEnableCallLogs(Boolean enableCallLogs) {
        this.enableCallLogs = enableCallLogs;
    }

    private String message;
    private Boolean enableCallLogs = false;
    private String action;
    private int statusCode;
    private CallLog call;
    private String studentSignalingToken;
    private String teacherSignalingToken;
    private ConsumedPakageResponseModel studentDurations;
    private ConfigModel config;
    private int maxCallDuration;
    private String callApiKey;
    private String studentSessionToken;
    private String teacherSessionToken;
    private User student;
    private User teacher;
    private int ringingDuration;
    private int delayTimeAfterEndingCall;
    boolean hasPath;

    public String getStudentSignalingToken() {
        return studentSignalingToken;
    }

    public void setStudentSignalingToken(String studentSignalingToken) {
        this.studentSignalingToken = studentSignalingToken;
    }

    public String getTeacherSignalingToken() {
        return teacherSignalingToken;
    }

    public void setTeacherSignalingToken(String teacherSignalingToken) {
        this.teacherSignalingToken = teacherSignalingToken;
    }

    public boolean isHasPath() {
        return hasPath;
    }

    public void setHasPath(boolean hasPath) {
        this.hasPath = hasPath;
    }

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

    public CallLog getCall() {
        return call;
    }

    public void setCall(CallLog call) {
        this.call = call;
    }

    public ConsumedPakageResponseModel getStudentDurations() {
        return studentDurations;
    }

    public void setStudentDurations(ConsumedPakageResponseModel studentDurations) {
        this.studentDurations = studentDurations;
    }

    public ConfigModel getConfig() {
        return config;
    }

    public void setConfig(ConfigModel config) {
        this.config = config;
    }

    public int getMaxCallDuration() {
        return maxCallDuration;
    }

    public void setMaxCallDuration(int maxCallDuration) {
        this.maxCallDuration = maxCallDuration;
    }

    public String getCallApiKey() {
        return callApiKey;
    }

    public void setCallApiKey(String callApiKey) {
        this.callApiKey = callApiKey;
    }

    public String getStudentSessionToken() {
        return studentSessionToken;
    }

    public void setStudentSessionToken(String studentSessionToken) {
        this.studentSessionToken = studentSessionToken;
    }

    public String getTeacherSessionToken() {
        return teacherSessionToken;
    }

    public void setTeacherSessionToken(String teacherSessionToken) {
        this.teacherSessionToken = teacherSessionToken;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getRingingDuration() {
        return ringingDuration;
    }

    public void setRingingDuration(int ringingDuration) {
        this.ringingDuration = ringingDuration;
    }

    public int getDelayTimeAfterEndingCall() {
        return delayTimeAfterEndingCall;
    }

    public void setDelayTimeAfterEndingCall(int delayTimeAfterEndingCall) {
        this.delayTimeAfterEndingCall = delayTimeAfterEndingCall;
    }
}
