package com.ismos_salt_erp.view.fragment.all_report.packeting_report.miller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketReportMillerList;

import java.util.List;

import lombok.Data;

@Data
public class PacketMIllerReportResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<PacketReportMillerList> millerList = null;

}
