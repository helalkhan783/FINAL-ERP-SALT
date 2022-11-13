package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditWashingCrushingItem {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unitID")
    @Expose
    private String unitID;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSoldFrom() {
        return soldFrom;
    }

    public void setSoldFrom(String soldFrom) {
        this.soldFrom = soldFrom;
    }
}
