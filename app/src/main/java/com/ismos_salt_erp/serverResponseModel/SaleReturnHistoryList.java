package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaleReturnHistoryList {
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("others")
    @Expose
    private String others;
    @SerializedName("sls_ttl")
    @Expose
    private String slsTtl;
    @SerializedName("pur_ttl")
    @Expose
    private String purTtl;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
}
