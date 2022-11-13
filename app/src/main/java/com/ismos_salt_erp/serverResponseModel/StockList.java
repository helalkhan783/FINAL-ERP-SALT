package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockList {
    @SerializedName("quantity")
    @Expose
    private String quantity;
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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("avg_price")
    @Expose
    private Double avgPrice;
    @SerializedName("unit_buying_price")
    @Expose
    private Double unitBuyingPrice;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Object getProductIsbn() {
        return productIsbn;
    }

    public void setProductIsbn(Object productIsbn) {
        this.productIsbn = productIsbn;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getUnitBuyingPrice() {
        return unitBuyingPrice;
    }

    public void setUnitBuyingPrice(Double unitBuyingPrice) {
        this.unitBuyingPrice = unitBuyingPrice;
    }
}
