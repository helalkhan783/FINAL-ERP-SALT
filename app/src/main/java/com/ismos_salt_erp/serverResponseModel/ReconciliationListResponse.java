package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReconciliationListResponse {

    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("others")
    @Expose
    private Object others;
    @SerializedName("reconciliation_type")
    @Expose
    String reconciliation_type;
    @SerializedName("store_name")
    @Expose
    String store_name;

}
