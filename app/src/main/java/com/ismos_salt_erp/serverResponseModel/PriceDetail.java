package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PriceDetail {
    @SerializedName("product_priceID")
    @Expose
    private String productPriceID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("vat")
    @Expose
    private Object vat;
    @SerializedName("promo_price")
    @Expose
    private Object promoPrice;
    @SerializedName("promo_start")
    @Expose
    private Object promoStart;
    @SerializedName("promo_end")
    @Expose
    private Object promoEnd;
    @SerializedName("promo_status")
    @Expose
    private String promoStatus;
    @SerializedName("status")
    @Expose
    private String status;
}
