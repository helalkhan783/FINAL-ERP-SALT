package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingSalesReturnDetailsPaymentInfo {
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("custom_discount")
    @Expose
    private String customDiscount;
    @SerializedName("payment_remarks")
    @Expose
    private String paymentRemarks;
    @SerializedName("payment_month")
    @Expose
    private String paymentMonth;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("payment_year")
    @Expose
    private String paymentYear;
    @SerializedName("payment_date_time")
    @Expose
    private String paymentDateTime;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("counter_no")
    @Expose
    private String counterNo;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("sales_type")
    @Expose
    private String salesType;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;
    @SerializedName("requisition_ref")
    @Expose
    private Object requisitionRef;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("batch_ref")
    @Expose
    private String batchRef;
    @SerializedName("payment_sub_type")
    @Expose
    private Object paymentSubType;
}
