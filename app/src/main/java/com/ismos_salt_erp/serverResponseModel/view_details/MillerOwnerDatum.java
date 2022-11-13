package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MillerOwnerDatum {
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
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
