package com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MillerLicenceExpireReportListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("expire_list")
    @Expose
    private List<MillerLicenceExpireReportList> expireList = null;

}
