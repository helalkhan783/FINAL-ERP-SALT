package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VarientList {
    @SerializedName("product_title")
    @Expose
    public String productTitle;
    @SerializedName("base_unit")
    @Expose
    public String baseUnit;
    @SerializedName("product_dimensions")
    @Expose
    public String productDimensions;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("productID")
    @Expose
    public String productID;
    @SerializedName("is_deleted")
    @Expose
    public String isDeleted;
    @SerializedName("record_date_time")
    @Expose
    public String recordDateTime;
    @SerializedName("selling_price")
    @Expose
    public String sellingPrice;
    @SerializedName("product_priceID")
    @Expose
    public String productPriceID;
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("name")
    @Expose
    public String name;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(String recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProductPriceID() {
        return productPriceID;
    }

    public void setProductPriceID(String productPriceID) {
        this.productPriceID = productPriceID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
