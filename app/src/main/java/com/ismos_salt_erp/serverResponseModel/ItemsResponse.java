package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ItemsResponse {

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
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("others")
    @Expose
    private Object others;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("discount")
    @Expose
    private String discount;

}
