package com.ismos_salt_erp.view.fragment.all_report.packaging_report.packaging_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PackagingReportPageDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("asociation_list")
    @Expose
    private List<PackagingAsociation> asociationList = null;
    @SerializedName("miller_list")
    @Expose
    private List<PackagingMiller> millerList = null;
    @SerializedName("referer")
    @Expose
    private List<PackagingReferer> referer = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;
}
