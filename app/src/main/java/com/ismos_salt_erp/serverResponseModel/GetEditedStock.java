package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GetEditedStock {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("storeID")
    @Expose
    private java.util.List<String> storeID = null;
    @SerializedName("stock_qty")
    @Expose
    private String stockQty;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
}
