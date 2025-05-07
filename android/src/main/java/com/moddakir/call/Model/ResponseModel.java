package com.moddakir.call.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseModel implements Serializable {

    private String message;
    private String action;
    private Boolean forceValidatePhone ;
    private String accessToken;
    private String item;
    private int statusCode;
    private User teacher;

    private Boolean registrationInfoCompleted ;
    private User student;
    private String suggestedUsername;
    private String uploadURL;
    private String fileUrl;
    private boolean forceUpdate;
    private boolean isDependentManager;
    private boolean showRateAppMessage;
    private boolean showSurvey;

    public Boolean getForceValidatePhone() {
        return forceValidatePhone;
    }

    public void setForceValidatePhone(Boolean forceValidatePhone) {
        this.forceValidatePhone = forceValidatePhone;
    }

    public Boolean getRegistrationInfoCompleted() {
        return registrationInfoCompleted;
    }

    public void setRegistrationInfoCompleted(Boolean registrationInfoCompleted) {
        this.registrationInfoCompleted = registrationInfoCompleted;
    }

    public void setPhoneValidate(boolean phoneValidate) {
        isPhoneValidate = phoneValidate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isShowSurvey() {
        return showSurvey;
    }

    public void setShowSurvey(boolean showSurvey) {
        this.showSurvey = showSurvey;
    }


    private Survey survey;

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void setShowRateAppMessage(boolean showRateAppMessage) {
        this.showRateAppMessage = showRateAppMessage;
    }

    public boolean isShowRateAppMessage() {
        return showRateAppMessage;
    }

    private ConfigModel config;
    private int unreadnotification;
    private boolean isNewUser;
    private float freeMinutes;
    private StudentInfoResponse userInfo;
    @SerializedName("isemailValidate")
    private boolean isEmailValidate;
    private boolean isPhoneValidate;

    public StudentInfoResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(StudentInfoResponse userInfo) {
        this.userInfo = userInfo;
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

    public String getaccessToken() {
        return accessToken;
    }

    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public User getItems() {
        return teacher;
    }

    public void setItems(User items) {
        this.teacher = items;
    }

    public String getUploadURL() {
        return uploadURL;
    }

    public void setUploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
    }

    public String getSuggestedUsername() {
        return suggestedUsername;
    }

    public void setSuggestedUsername(String suggestedUsername) {
        this.suggestedUsername = suggestedUsername;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isDependentManager() {
        return isDependentManager;
    }

    public void setDependentManager(boolean dependentManager) {
        isDependentManager = dependentManager;
    }

    public ConfigModel getConfig() {
        return config;
    }

    public void setConfig(ConfigModel config) {
        this.config = config;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getUnreadnotification() {
        return unreadnotification;
    }

    public void setUnreadnotification(int unreadnotification) {
        this.unreadnotification = unreadnotification;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }

    public float getFreeMinutes() {
        return freeMinutes;
    }

    public void setFreeMinutes(float freeMinutes) {
        this.freeMinutes = freeMinutes;
    }

    public boolean isEmailValidate() {
        return isEmailValidate;
    }

    public boolean isPhoneValidate() {
        return isPhoneValidate;
    }


    public static class Survey implements Serializable{
        private String type;
        private String name;
        private String submitUrl;
        private boolean deleted;


        public String getIntroTitle() {
            return introTitle;
        }

        public void setIntroTitle(String introTitle) {
            this.introTitle = introTitle;
        }

        public String getIntroMessage() {
            return introMessage;
        }

        public void setIntroMessage(String introMessage) {
            this.introMessage = introMessage;
        }

        public String getDoneTitle() {
            return doneTitle;
        }

        public void setDoneTitle(String doneTitle) {
            this.doneTitle = doneTitle;
        }

        public String getDoneMessage() {
            return doneMessage;
        }

        public void setDoneMessage(String doneMessage) {
            this.doneMessage = doneMessage;
        }

        private String introTitle;
        private String introMessage;
        private String doneTitle;
        private String doneMessage;
        private List<Questions> questions;
        private String _id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubmitUrl() {
            return submitUrl;
        }

        public void setSubmitUrl(String submitUrl) {
            this.submitUrl = submitUrl;
        }

        public boolean getDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public List<Questions> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Questions> questions) {
            this.questions = questions;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

    public static class Questions implements Serializable{
        private boolean deleted;
        private String type;
        private String question;
        private List<Answers> answers;
        private String _id;
        private Boolean isMandatory;

        public Boolean getMandatory() {
            return isMandatory;
        }

        public void setMandatory(Boolean mandatory) {
            isMandatory = mandatory;
        }

        public boolean getDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<Answers> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Answers> answers) {
            this.answers = answers;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

    public static class Answers implements Serializable{
        private String answer;
        private String _id;
        private String emoji;

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

    }
}
