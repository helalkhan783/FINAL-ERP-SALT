package com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.miller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.page_data_response.IodineMillerList;

import java.util.List;

import lombok.Data;

@Data
public class IodineReportMillerResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<IodineMillerList> millerList = null;
}
