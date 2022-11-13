package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class HomepageList {
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("processed_by")
    @Expose
    private String processedBy;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
}
