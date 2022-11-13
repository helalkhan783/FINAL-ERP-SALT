package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DeclineList {
    @SerializedName("sl")
    @Expose
    private String sl;
    @SerializedName("entryTime")
    @Expose
    private String entryTime;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("processTypeName")
    @Expose
    private String processTypeName;
    @SerializedName("millTypeName")
    @Expose
    private String millTypeName;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
    @SerializedName("status")
    @Expose
    private String status;
}
