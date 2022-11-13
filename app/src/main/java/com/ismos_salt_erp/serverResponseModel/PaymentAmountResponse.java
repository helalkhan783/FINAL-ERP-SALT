package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentAmountResponse {
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("sales_type")
    @Expose
    private String salesType;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
}
