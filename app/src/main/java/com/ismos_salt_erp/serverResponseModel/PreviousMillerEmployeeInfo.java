package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PreviousMillerEmployeeInfo {
    @SerializedName("slId")
    @Expose
    private String slId;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("fullTimeMale")
    @Expose
    private String fullTimeMale;
    @SerializedName("fullTimeFemale")
    @Expose
    private String fullTimeFemale;
    @SerializedName("partTimeMale")
    @Expose
    private String partTimeMale;
    @SerializedName("partTimeFemail")
    @Expose
    private String partTimeFemail;
    @SerializedName("totalTechMale")
    @Expose
    private String totalTechMale;
    @SerializedName("totalTechFemale")
    @Expose
    private String totalTechFemale;
    @SerializedName("totalEmployeeNum")
    @Expose
    private Integer totalEmployeeNum;
    @SerializedName("totalFemaleNum")
    @Expose
    private Integer totalFemaleNum;
    @SerializedName("count_profile")
    @Expose
    private String countProfile;

}
