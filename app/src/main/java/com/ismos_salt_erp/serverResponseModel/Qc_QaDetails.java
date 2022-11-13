package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Qc_QaDetails {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("refqc_slID")
    @Expose
    private String refqcSlID;
    @SerializedName("qcID")
    @Expose
    private String qcID;
    @SerializedName("parameterName")
    @Expose
    private Object parameterName;
    @SerializedName("testID")
    @Expose
    private String testID;
    @SerializedName("parameterValue")
    @Expose
    private String parameterValue;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("reference")
    @Expose
    private String reference;
}
