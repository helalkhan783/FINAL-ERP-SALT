package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OwnerInfo {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("divisionID")
    @Expose
    private String divisionID;
    @SerializedName("districtID")
    @Expose
    private String districtID;
    @SerializedName("upazilaID")
    @Expose
    private String upazilaID;
    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("alt_mobile")
    @Expose
    private String altMobile;
    @SerializedName("email")
    @Expose
    private String email;
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
}
