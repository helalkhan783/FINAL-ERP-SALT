package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditIItemResponseObject {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("base_unit")
    @Expose
    private String baseUnit;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("product_details")
    @Expose
    private String productDetails;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("product_image")
    @Expose
    private Object productImage;
    @SerializedName("multiple_image")
    @Expose
    private Object multipleImage;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("brandID")
    @Expose
    private String brandID;
    @SerializedName("is_grouped")
    @Expose
    private String isGrouped;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("record_date_time")
    @Expose
    private String recordDateTime;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("storeID")
    @Expose
    private Object storeID;
    @SerializedName("is_ecommerce")
    @Expose
    private Object isEcommerce;
    @SerializedName("packet_id")
    @Expose
    private Object packetId;
    @SerializedName("refProductID")
    @Expose
    private Object refProductID;
}
