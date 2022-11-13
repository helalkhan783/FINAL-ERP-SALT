package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ReconciliationReportMillerResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<ReconciliationMillerList> millerList = null;


}
