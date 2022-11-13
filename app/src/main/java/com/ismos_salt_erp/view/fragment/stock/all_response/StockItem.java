package com.ismos_salt_erp.view.fragment.stock.all_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockItem {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("product_image")
    @Expose
    private Object productImage;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand_name")
    @Expose
    private String brandName;

}
