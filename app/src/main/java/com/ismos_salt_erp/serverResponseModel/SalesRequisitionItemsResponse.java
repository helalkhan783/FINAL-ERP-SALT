package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SalesRequisitionItemsResponse {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("unit_name")
    @Expose
    private String unit_name;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
