package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PacketingList {
    @SerializedName("refProductID")
    @Expose
    private String refProductID;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
}
