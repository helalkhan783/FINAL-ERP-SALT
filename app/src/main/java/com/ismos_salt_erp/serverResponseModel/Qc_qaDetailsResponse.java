package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class Qc_qaDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("qc_info")
    @Expose
    private QcDetailsInfo qcInfo;
    @SerializedName("qc_details")
    @Expose
    private List<Qc_QaDetails> qcDetails = null;
    @SerializedName("test_list")
    @Expose
    private List<TestList> testList = null;
    @SerializedName("enterprize_list")
    @Expose
    private List<Enterprize> enterprizeList = null;
}
