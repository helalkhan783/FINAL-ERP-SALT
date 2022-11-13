package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SaleReportMillerResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<SaleReportMillerList> millerList = null;


}
