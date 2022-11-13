package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ExpensePaymentDueList {
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("sales_type")
    @Expose
    private String salesType;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("payment_date")
    @Expose
    private String payment_date;
}
