package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OtherInfoDetails {
    @SerializedName("slId")
    @Expose
    private String slId;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("haveLaboratory")
    @Expose
    private String haveLaboratory;
    @SerializedName("standardProcedure")
    @Expose
    private String standardProcedure;
    @SerializedName("trainedLaboratoryPerson")
    @Expose
    private String trainedLaboratoryPerson;
    @SerializedName("useTestKit")
    @Expose
    private String useTestKit;
    @SerializedName("laboratoryPerson")
    @Expose
    private String laboratoryPerson;
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
    @SerializedName("labRemarks")
    @Expose
    private String labRemarks;
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
