
package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QuotationDetail {

    @SerializedName("SL")
    @Expose
    private String sL;
    @SerializedName("quotationID")
    @Expose
    private String quotationID;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ref")
    @Expose
    private Object ref;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("unit")
    @Expose
    private String unit;


}
