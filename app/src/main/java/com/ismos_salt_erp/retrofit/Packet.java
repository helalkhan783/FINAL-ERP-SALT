package com.ismos_salt_erp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packet {
    @SerializedName("productID")
    @Expose
    public String productID;
    @SerializedName("product_title")
    @Expose
    public String productTitle;

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
}
