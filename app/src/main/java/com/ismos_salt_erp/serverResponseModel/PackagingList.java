package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PackagingList {
    @SerializedName("packagingID")
    @Expose
    private String packagingID;
    @SerializedName("packagingSLID")
    @Expose
    private String packagingSLID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("packedName")
    @Expose
    private String packedName;
    @SerializedName("originItemQty")
    @Expose
    private String originItemQty;
    @SerializedName("entryDateTime")
    @Expose
    private String entryDateTime;
    @SerializedName("convertedItemQty")
    @Expose
    private String convertedItemQty;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("enterprise")
    @Expose
    private String enterprise;
    @SerializedName("packaging_vendorID")
    @Expose
    private String packagingVendorID;


}
