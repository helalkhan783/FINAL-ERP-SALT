package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVersionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_force_update")
    @Expose
    private Integer isForceUpdate;
    @SerializedName("android_version")
    @Expose
    private String androidVersion;
    @SerializedName("ios_version")
    @Expose
    private String iosVersion;
    @SerializedName("is_maintanance")
    @Expose
    private Integer isMaintanance;
    @SerializedName("current_version")
    @Expose
    private String currentVersion;
    @SerializedName("maintanance_note")
    @Expose
    private String maintananceNote;

    public GetVersionResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Integer isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public Integer getIsMaintanance() {
        return isMaintanance;
    }

    public void setIsMaintanance(Integer isMaintanance) {
        this.isMaintanance = isMaintanance;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getMaintananceNote() {
        return maintananceNote;
    }

    public void setMaintananceNote(String maintananceNote) {
        this.maintananceNote = maintananceNote;
    }
}
