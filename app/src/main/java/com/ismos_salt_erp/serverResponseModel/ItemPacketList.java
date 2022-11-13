package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ItemPacketList {
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("base_unit")
    @Expose
    private String baseUnit;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("record_date_time")
    @Expose
    private String recordDateTime;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("product_priceID")
    @Expose
    private String productPriceID;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("name")
    @Expose
    private String name;
}
