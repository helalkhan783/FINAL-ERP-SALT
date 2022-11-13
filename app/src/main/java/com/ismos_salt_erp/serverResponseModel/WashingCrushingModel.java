package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class WashingCrushingModel {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("unit_name")
    @Expose
    private String unit_name;
    @SerializedName("quantity")
    @Expose
    private String quantity = null;
}
