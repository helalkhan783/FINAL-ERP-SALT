package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OwnerDetailsModel {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("upazila")
    @Expose
    private String upazila;
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
