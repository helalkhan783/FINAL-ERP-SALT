package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ProfileData {
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("processTypeName")
    @Expose
    private String processTypeName;
    @SerializedName("ownerTypeName")
    @Expose
    private String ownerTypeName;
    @SerializedName("millTypeName")
    @Expose
    private String millTypeName;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("zoneName")
    @Expose
    private String zoneName;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
    @SerializedName("millID")
    @Expose
    private String millID;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("is_submit")
    @Expose
    private Object isSubmit;
}
