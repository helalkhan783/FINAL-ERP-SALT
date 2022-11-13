package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QcDetail {
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
}
