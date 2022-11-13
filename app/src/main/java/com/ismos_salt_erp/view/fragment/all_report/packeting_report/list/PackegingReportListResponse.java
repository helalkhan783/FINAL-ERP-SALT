package com.ismos_salt_erp.view.fragment.all_report.packeting_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PackegingReportListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("packeging_list")
    @Expose
    private List<ReportPackegingList> packegingList = null;
}
