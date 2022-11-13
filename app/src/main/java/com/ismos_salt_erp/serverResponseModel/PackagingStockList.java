package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PackagingStockList {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("stock_qty")
    @Expose
    private Integer stockQty;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
}
