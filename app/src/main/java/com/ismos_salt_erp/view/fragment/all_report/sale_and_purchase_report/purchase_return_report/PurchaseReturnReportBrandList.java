package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseReturnReportBrandList {
    @SerializedName("brandID")
    @Expose
    private String brandID;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("distributor")
    @Expose
    private String distributor;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("isdeleted")
    @Expose
    private String isdeleted;
}
