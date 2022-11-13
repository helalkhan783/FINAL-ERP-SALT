package com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class IodineReportListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("iodine_report")
    @Expose
    private List<IodineReportList> iodineReport = null;
}
