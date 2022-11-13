package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockDeclineTransferredList {
    @SerializedName("inventoryID")
    @Expose
    private String inventoryID;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("from_store_name")
    @Expose
    private String fromStoreName;
    @SerializedName("from_store_no")
    @Expose
    private String fromStoreNo;
    @SerializedName("to_store_name")
    @Expose
    private String toStoreName;
    @SerializedName("to_store_no")
    @Expose
    private String toStoreNo;
    @SerializedName("transfer_to")
    @Expose
    private String transferTo;
    @SerializedName("transfer_from")
    @Expose
    private String transferFrom;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("from_enterprise_name")
    @Expose
    private String fromEnterpriseName;
    @SerializedName("to_enterprise_name")
    @Expose
    private String toEnterpriseName;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("order_vendorID")
    @Expose
    private String orderVendorID;

}
