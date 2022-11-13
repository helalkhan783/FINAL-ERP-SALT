package com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MillerLicenceExpireReportList {

    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("certificateTypeID")
    @Expose
    private String certificateTypeID;
    @SerializedName("issuerName")
    @Expose
    private String issuerName;
    @SerializedName("issueDate")
    @Expose
    private String issueDate;
    @SerializedName("certificateDate")
    @Expose
    private String certificateDate;
    @SerializedName("certificateImage")
    @Expose
    private String certificateImage;
    @SerializedName("renewDate")
    @Expose
    private String renewDate;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("reviewTime")
    @Expose
    private String reviewTime;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("ref_slId")
    @Expose
    private String refSlId;
    @SerializedName("miller")
    @Expose
    private String miller;
}
