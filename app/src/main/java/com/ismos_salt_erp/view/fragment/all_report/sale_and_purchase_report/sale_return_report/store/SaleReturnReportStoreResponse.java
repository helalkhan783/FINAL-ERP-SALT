package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SaleReturnReportStoreResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<SaleReturnReportStoreList> millerList = null;
}
