package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaleReturnReportCategoryList {

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
