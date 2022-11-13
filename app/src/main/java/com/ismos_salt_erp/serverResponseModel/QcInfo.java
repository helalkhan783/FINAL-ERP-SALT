package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QcInfo {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("qcID")
    @Expose
    private String qcID;
    @SerializedName("testType")
    @Expose
    private String testType;
    @SerializedName("testedBy")
    @Expose
    private Object testedBy;
    @SerializedName("licenecIssuerID")
    @Expose
    private String licenecIssuerID;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("short_name")
    @Expose
    private Object shortName;
    @SerializedName("reference")
    @Expose
    private String reference;
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
    private String reviewTime;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("model")
    @Expose
    private Object model;
    @SerializedName("note")
    @Expose
    private Object note;
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
}
