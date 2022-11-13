package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReconciliationReportMillerList {
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("FullName")
    @Expose
    private Object fullName;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("storeID")
    @Expose
    private Object storeID;
}
