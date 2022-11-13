package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseReturnReportAssociationList {
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("storeID")
    @Expose
    private String storeID;
}
