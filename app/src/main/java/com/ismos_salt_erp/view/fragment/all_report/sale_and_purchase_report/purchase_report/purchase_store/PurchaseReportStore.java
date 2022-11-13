package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.purchase_store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseReportStore {
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("store_name")
    @Expose
    private String storeName;
}
