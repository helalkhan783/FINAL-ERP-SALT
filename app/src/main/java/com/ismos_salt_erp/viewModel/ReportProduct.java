package com.ismos_salt_erp.viewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReportProduct {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("refProductID")
    @Expose
    private Object refProductID;
    @SerializedName("product_image")
    @Expose
    private Object productImage;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("name")
    @Expose
    private String name;
}
