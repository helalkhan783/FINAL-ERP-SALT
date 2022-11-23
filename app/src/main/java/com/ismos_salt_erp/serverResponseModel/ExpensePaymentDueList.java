package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;


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

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}
