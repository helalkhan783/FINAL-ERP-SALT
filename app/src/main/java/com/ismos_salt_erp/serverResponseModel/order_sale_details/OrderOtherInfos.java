package com.ismos_salt_erp.serverResponseModel.order_sale_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OrderOtherInfos {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("order_file_no")
    @Expose
    private Object orderFileNo;
    @SerializedName("invoice_no")
    @Expose
    private Object invoiceNo;
    @SerializedName("invoice_date")
    @Expose
    private Object invoiceDate;
    @SerializedName("exp_no")
    @Expose
    private Object expNo;
    @SerializedName("exp_date")
    @Expose
    private Object expDate;
    @SerializedName("sc_no")
    @Expose
    private Object scNo;
    @SerializedName("sc_date")
    @Expose
    private Object scDate;
    @SerializedName("processing_date")
    @Expose
    private Object processingDate;
    @SerializedName("requistion_ref")
    @Expose
    private Object requistionRef;
    @SerializedName("courrier_name")
    @Expose
    private String courrierName;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
}
