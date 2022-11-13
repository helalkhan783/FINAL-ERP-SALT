package com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class IodineReportList {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("sales_typeID")
    @Expose
    private String salesTypeID;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("brand_name")
    @Expose
    private Object brandName;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("order_detailsID")
    @Expose
    private String orderDetailsID;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("name")
    @Expose
    private String name;
}
