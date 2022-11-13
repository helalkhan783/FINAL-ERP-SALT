package com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockIOItemList {

    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
}

