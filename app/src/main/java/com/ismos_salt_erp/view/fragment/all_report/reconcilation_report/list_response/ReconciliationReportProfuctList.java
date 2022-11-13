package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReconciliationReportProfuctList {
    @SerializedName("order_detailsID")
    @Expose
    private String orderDetailsID;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("transfer_type")
    @Expose
    private String transferType;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("brandID")
    @Expose
    private String brandID;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("reconciliation_type")
    @Expose
    private String reconciliationType;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("enterprize_name")
    @Expose
    private String enterprizeName;
    @SerializedName("customer_fname")
    @Expose
    private Object customerFname;


}
