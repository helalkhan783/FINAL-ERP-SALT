package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GetEditQcQaResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("qc_info")
    @Expose
    private QcInfo qcInfo;
    @SerializedName("qc_details")
    @Expose
    private List<QcDetail> qcDetails = null;
    @SerializedName("test_list")
    @Expose
    private List<TestList> testList = null;
    @SerializedName("enterprize_list")
    @Expose
    private List<EnterprizeList> enterprizeList = null;
}
