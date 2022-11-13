package com.ismos_salt_erp.serverResponseModel.order_sale_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Item {
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
}
