package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PreviousMillerQcInfo {
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
    @SerializedName("labRemarks")
    @Expose
    private String labRemarks;
    @SerializedName("status")
    @Expose
    private String status;
}
