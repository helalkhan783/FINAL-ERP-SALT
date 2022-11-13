package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditSaleItemsResponse {
    @SerializedName("product_title")
    @Expose
    private String product_title;
    @SerializedName("productID")
    @Expose
    private String productID;
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
    @SerializedName("unitID")
    @Expose
    private String unitID;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSoldFrom() {
        return soldFrom;
    }

    public void setSoldFrom(String soldFrom) {
        this.soldFrom = soldFrom;
    }
}
