package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingQcQaResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprize_list")
    @Expose
    private  List<Enterprize> enterprizeList = null;
    @SerializedName("lists")
    @Expose
    private  List<PendingQcQaList> lists = null;
}
