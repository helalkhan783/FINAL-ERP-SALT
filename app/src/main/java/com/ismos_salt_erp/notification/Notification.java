package com.ismos_salt_erp.notification;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
    @SerializedName("image")
    private String image;
    @SerializedName("status")
    private String status;
    @SerializedName("notification_type")
    private String notificationType;
    @SerializedName("user_type")
    private String userType;
    @SerializedName("counter")
    private String counter;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;

    public Notification(String title, String message, String image, String status, String notificationType, String userType) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.status = status;
        this.notificationType = notificationType;
        this.userType = userType;
    }

    public Notification(String id, String title, String message, String image, String status, String notificationType, String userType) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.image = image;
        this.status = status;
        this.notificationType = notificationType;
        this.userType = userType;
    }

    public Notification(String id, String title, String message, String image, String status, String notificationType, String userType, String counter, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.image = image;
        this.status = status;
        this.notificationType = notificationType;
        this.userType = userType;
        this.counter = counter;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
