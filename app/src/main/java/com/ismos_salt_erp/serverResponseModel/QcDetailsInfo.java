package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QcDetailsInfo {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("qcID")
    @Expose
    private String qcID;
    @SerializedName("testType")
    @Expose
    private Object testType;
    @SerializedName("testedBy")
    @Expose
    private Object testedBy;
    @SerializedName("licenecIssuerID")
    @Expose
    private Object licenecIssuerID;
    @SerializedName("testName")
    @Expose
    private Object testName;
    @SerializedName("short_name")
    @Expose
    private Object shortName;
    @SerializedName("reference")
    @Expose
    private Object reference;
    @SerializedName("testDate")
    @Expose
    private String testDate;
    @SerializedName("entryDateTime")
    @Expose
    private String entryDateTime;
    @SerializedName("entryUserID")
    @Expose
    private String entryUserID;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("reviewTime")
    @Expose
    private Object reviewTime;
    @SerializedName("reviewBy")
    @Expose
    private Object reviewBy;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("declined_remarks")
    @Expose
    private Object declinedRemarks;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("ref_slID")
    @Expose
    private String refSlID;
    @SerializedName("entryUserName")
    @Expose
    private String entryUserName;
    @SerializedName("reviewUserName")
    @Expose
    private Object reviewUserName;
}