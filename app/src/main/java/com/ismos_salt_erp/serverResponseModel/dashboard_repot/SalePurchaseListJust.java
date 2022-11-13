package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalePurchaseListJust {
    @SerializedName("enterprise_name")
    @Expose
    public String enterpriseName;
    @SerializedName("quantity")
    @Expose
    public String quantity;
    @SerializedName("grand_total")
    @Expose
    public String grandTotal;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
