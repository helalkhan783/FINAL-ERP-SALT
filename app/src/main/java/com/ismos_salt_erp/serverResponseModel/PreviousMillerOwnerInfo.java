package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PreviousMillerOwnerInfo {
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
}
