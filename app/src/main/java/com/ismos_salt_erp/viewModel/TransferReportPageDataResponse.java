package com.ismos_salt_erp.viewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class TransferReportPageDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("store_list")
    @Expose
    private List<Store> storeList = null;
    @SerializedName("product_list")
    @Expose
    private List<ReportProduct> productList = null;
}
