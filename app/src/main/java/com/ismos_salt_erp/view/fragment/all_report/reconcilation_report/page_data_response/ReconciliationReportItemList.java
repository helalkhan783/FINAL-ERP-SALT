package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReconciliationReportItemList {
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
}
