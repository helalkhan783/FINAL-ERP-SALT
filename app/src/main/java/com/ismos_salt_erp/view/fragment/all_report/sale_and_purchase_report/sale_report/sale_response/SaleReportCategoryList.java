package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaleReportCategoryList {
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_code")
    @Expose
    private String categoryCode;
}
