package com.moddakir.call.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public  class User  implements Serializable {
    boolean currentlyLogged = false;
    private String id;
    private String accessToken;
    private String teacherPreferenceLabel;
    private Double totalPreferanceScore;
    private String fullName;
    private String email;
    long lastTime;
    boolean isChildDependent=false;

    int orderForChild;

    public int getOrderForChild() {
        return orderForChild;
    }

    public void setOrderForChild(int orderForChild) {
        this.orderForChild = orderForChild;
    }

    public long getLast_items_time() {
        return lastTime;
    }

    public void setLast_items_time(long last_items_time) {
        this.lastTime = last_items_time;
    }

    public boolean isChildDependent() {
        return isChildDependent;
    }

    public void setChildDependent(boolean childDependent) {
        isChildDependent = childDependent;
    }

    public Double getTotalPreferanceScore() {
        return totalPreferanceScore;
    }

    public void setTotalPreferanceScore(Double totalPreferanceScore) {
        this.totalPreferanceScore = totalPreferanceScore;
    }

    private String phone;
    private String gender;
    private String username;
    private String pass;
    private String country;
    private String city;
    private String brief;
    private String certificate;
    private String address;
    private String status;
    private String avatarUrl;
    private boolean logged_in;
    private boolean isFav;
    private boolean isSubscribed;
    private String ejaza;
    private String languages;
    private String yearsOfExperience;
    private String totalStudents;
    private String availableTimePerWeek;
    private String sinchId;
    private String technicalExperience;
    private String parent;
    private String requested_date;
    private String search_type;
    private float rate;
    private float totalRate;
    private boolean isDependentManager;
    private boolean enableVoiceRecording;
    private boolean enableVideoRecording;
    private float assignmentValue;
    private List<TeacherCategoryModel> studentComments;
    private String videoUrl;
    @SerializedName("nearstSlotTime")
    private String nearestSlotTime;
    private String currency;
    private boolean isMailActivated = false;
    private boolean isMobileActivated = false;
    private Education education;
    private String childAgePreferanceForEdit="";
    private String childSessionsPerWeekId="";
    private String childPathId="";
    private String childEducationPlanId="";
    private int childLinesPerSession=0;



    public String getChildAgePreferanceForEdit() {
        return childAgePreferanceForEdit;
    }

    public void setChildAgePreferanceForEdit(String childAgePreferanceForEdit) {
        this.childAgePreferanceForEdit = childAgePreferanceForEdit;
    }

    public String getChildSessionsPerWeekId() {
        return childSessionsPerWeekId;
    }

    public void setChildSessionsPerWeekId(String childSessionsPerWeekId) {
        this.childSessionsPerWeekId = childSessionsPerWeekId;
    }

    public String getChildPathId() {
        return childPathId;
    }

    public void setChildPathId(String childPathId) {
        this.childPathId = childPathId;
    }

    public String getChildEducationPlanId() {
        return childEducationPlanId;
    }

    public void setChildEducationPlanId(String childEducationPlanId) {
        this.childEducationPlanId = childEducationPlanId;
    }

    public int getChildLinesPerSession() {
        return childLinesPerSession;
    }

    public void setChildLinesPerSession(int childLinesPerSession) {
        this.childLinesPerSession = childLinesPerSession;
    }

    public User() {
    }

    public String getTeacherPreferenceLabel() {
        return teacherPreferenceLabel;
    }

    public void setTeacherPreferenceLabel(String teacherPreferenceLabel) {
        this.teacherPreferenceLabel = teacherPreferenceLabel;
    }


    public String getNearestSlotTime() {
        return nearestSlotTime;
    }

    public void setNearestSlotTime(String nearestSlotTime) {
        this.nearestSlotTime = nearestSlotTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TeacherCategoryModel> getStudentComments() {
        return studentComments;
    }

    public void setStudentComments(List<TeacherCategoryModel> studentComments) {
        this.studentComments = studentComments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        if (country == null || country.isEmpty())
            return "SAR";
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConnstatus() {
        return status;
    }

    public void setConnstatus(String connstatus) {
        this.status = connstatus;
    }

    public String GetCalleeName() {
        return fullName != null && !fullName.trim().isEmpty() ? fullName : username;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public String getAvatarurl() {
        if (avatarUrl == null || avatarUrl.isEmpty()) return "";
        return avatarUrl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarUrl = avatarurl;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public String getCertificates() {
        return certificate;
    }

    public void setCertificates(String certificate) {
        this.certificate = certificate;
    }

    public String getEjaza() {
        return ejaza;
    }

    public void setEjaza(String ejaza) {
        this.ejaza = ejaza;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getAvailableTimePerWeek() {
        return availableTimePerWeek;
    }

    public void setAvailableTimePerWeek(String availableTimePerWeek) {
        this.availableTimePerWeek = availableTimePerWeek;
    }

    public String getTechnicalExperience() {
        return technicalExperience;
    }

    public void setTechnicalExperience(String technicalExperience) {
        this.technicalExperience = technicalExperience;
    }

    public String getSinchId() {
        return sinchId;
    }

    public void setSinchId(String sinchId) {
        this.sinchId = sinchId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(float totalRate) {
        this.totalRate = totalRate;
    }

    public String getParent() {
        if (parent != null && parent.trim().isEmpty()) parent = null;
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRequested_date() {
        return requested_date;
    }

    public void setRequested_date(String requested_date) {
        this.requested_date = requested_date;
    }

    public boolean isDependentManager() {
        return isDependentManager;
    }

    public void setDependentManager(boolean dependentManager) {
        isDependentManager = dependentManager;
    }

    public boolean isEnableVoiceRecording() {
        return enableVoiceRecording;
    }

    public void setEnableVoiceRecording(boolean enableVoiceRecording) {
        this.enableVoiceRecording = enableVoiceRecording;
    }

    public boolean isEnableVideoRecording() {
        return enableVideoRecording;
    }

    public void setEnableVideoRecording(boolean enableVideoRecording) {
        this.enableVideoRecording = enableVideoRecording;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isNotified() {
        return isSubscribed;
    }

    public void setNotified(boolean notified) {
        isSubscribed = notified;
    }

    public float getAssignmentValue() {
        return assignmentValue;
    }

    public void setAssignmentValue(float assignmentValue) {
        this.assignmentValue = assignmentValue;
    }

    public boolean isCurrentlyLogged() {
        return currentlyLogged;
    }

    public void setCurrentlyLogged(boolean currentlyLogged) {
        this.currentlyLogged = currentlyLogged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public boolean isMailActivated() {
        return isMailActivated;
    }

    public void setMailActivated(boolean mailActivated) {
        isMailActivated = mailActivated;
    }

    public boolean isMobileActivated() {
        return isMobileActivated;
    }

    public void setMobileActivated(boolean mobileActivated) {
        isMobileActivated = mobileActivated;
    }

    public boolean isMale() {
        return gender.toLowerCase().equals("male") || gender.toLowerCase().equals("boy") || gender.equals("رجل") || gender.equals("طفل");
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public boolean isEducationEmpty() {
        return education == null || education.getLevel() == null || education.getLevel().isEmpty() || education.getPaths() == null || education.getPaths().isEmpty();

    }

    public boolean canShowEducationDialog() {
        boolean canShowDialog = false;
        if (education != null) {
            canShowDialog = education.isEducationDialogDisplayed();
        }
        return isEducationEmpty() && !canShowDialog;
//        return isEducationEmpty() && !education.isEducationDialogDisplayed();
    }
}
