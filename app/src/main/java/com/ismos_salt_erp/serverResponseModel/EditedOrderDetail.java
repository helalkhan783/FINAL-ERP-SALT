
package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedOrderDetail {

    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("entry_month")
    @Expose
    private String entryMonth;
    @SerializedName("entry_year")
    @Expose
    private String entryYear;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("unitID")
    @Expose
    private String unitID;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("others")
    @Expose
    private Object others;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;

}
