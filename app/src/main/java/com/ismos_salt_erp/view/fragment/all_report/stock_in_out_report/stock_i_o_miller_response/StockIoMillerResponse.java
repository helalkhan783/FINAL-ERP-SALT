package com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.stock_i_o_miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class StockIoMillerResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<StockIOReportMillerList> millerList = null;
}
