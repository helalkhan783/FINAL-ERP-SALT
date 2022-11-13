package com.ismos_salt_erp.view.fragment.stock.all_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockReconciliationHistoryList {
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("reconciliation_type")
    @Expose
    private String reconciliationType;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("others")
    @Expose
    private Object others;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("order_vendorID")
    @Expose
    private String order_vendorID;

}
