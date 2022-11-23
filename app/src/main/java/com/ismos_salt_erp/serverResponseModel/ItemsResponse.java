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

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

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

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSoldFrom() {
        return soldFrom;
    }

    public void setSoldFrom(String soldFrom) {
        this.soldFrom = soldFrom;
    }

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
