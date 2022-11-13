package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MillerPendingResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("process_type")
    @Expose
    private  List<ProcessType> processType = null;
    @SerializedName("mill_type")
    @Expose
    private  List<MillType> millType = null;
    @SerializedName("lists")
    @Expose
    private List<MillerPendingList> lists = null;
}
