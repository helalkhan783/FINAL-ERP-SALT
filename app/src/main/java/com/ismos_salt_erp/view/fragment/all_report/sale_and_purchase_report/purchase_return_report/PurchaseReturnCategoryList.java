package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseReturnCategoryList {
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
