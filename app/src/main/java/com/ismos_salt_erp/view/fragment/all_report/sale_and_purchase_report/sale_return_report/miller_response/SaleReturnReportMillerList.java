package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaleReturnReportMillerList {
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private Object storeID;
}
