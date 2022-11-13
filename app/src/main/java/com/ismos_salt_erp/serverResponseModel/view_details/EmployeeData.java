package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EmployeeData {
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
    @SerializedName("totalEmployeeMale")
    @Expose
    private Integer totalEmployeeMale;
    @SerializedName("totalEmployeeFemale")
    @Expose
    private Integer totalEmployeeFemale;
}
