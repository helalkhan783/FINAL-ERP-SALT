package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TestList {
    @SerializedName("SLID")
    @Expose
    private String sLID;
    @SerializedName("testID")
    @Expose
    private String testID;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("entryUserID")
    @Expose
    private String entryUserID;
    @SerializedName("entryTime")
    @Expose
    private String entryTime;
    @SerializedName("testStatus")
    @Expose
    private String testStatus;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("reviewTime")
    @Expose
    private String reviewTime;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("refSLID")
    @Expose
    private String refSLID;

    public String getsLID() {
        return sLID;
    }

    public void setsLID(String sLID) {
        this.sLID = sLID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRefSLID() {
        return refSLID;
    }

    public void setRefSLID(String refSLID) {
        this.refSLID = refSLID;
    }
}
