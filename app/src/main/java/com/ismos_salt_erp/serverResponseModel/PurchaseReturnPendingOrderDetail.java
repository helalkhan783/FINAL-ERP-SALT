package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Data
public class PurchaseReturnPendingOrderDetail {
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("reconciliation_type")
    @Expose
    private Object reconciliationType;
    @SerializedName("store_name")
    @Expose
    private String store_name;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("vat")
    @Expose
    private Object vat;
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
    @SerializedName("sales_typeID")
    @Expose
    private String salesTypeID;
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
    @SerializedName("sold_from_store_name")
    @Expose
    private String soldFromStoreName;
}
