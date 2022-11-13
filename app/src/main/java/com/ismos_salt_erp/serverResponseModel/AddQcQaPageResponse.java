package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AddQcQaPageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("test_list")
    @Expose
    private List<TestList> testList = null;
    @SerializedName("enterprize_list")
    @Expose
    private List<EnterprizeList> enterprizeList = null;
    @SerializedName("qcID")
    @Expose
    private Integer qcID;
}
