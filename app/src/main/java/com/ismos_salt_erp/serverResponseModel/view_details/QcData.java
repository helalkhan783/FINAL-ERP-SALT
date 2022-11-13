package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QcData {
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
}
