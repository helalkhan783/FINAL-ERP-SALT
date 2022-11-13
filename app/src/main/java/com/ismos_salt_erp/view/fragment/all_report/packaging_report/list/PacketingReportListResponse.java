package com.ismos_salt_erp.view.fragment.all_report.packaging_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PacketingReportListResponse {



    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("packeting_list")
    @Expose
    private List<ReportPacketingList> packetingList = null;

}
