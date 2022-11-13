package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayBookReportList {
    @SerializedName("paymentID")
    @Expose
    public String paymentID;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("paid_amount")
    @Expose
    public String paidAmount;
    @SerializedName("sales_type")
    @Expose
    public String salesType;
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("orderID")
    @Expose
    public String orderID;
    @SerializedName("store_name")
    @Expose
    public String storeName;
    @SerializedName("entry_userID")
    @Expose
    public String entryUserID;
    @SerializedName("particulars_name")
    @Expose
    public String particularsName;
    @SerializedName("receipt")
    @Expose
    public Double receipt;
    @SerializedName("payment")
    @Expose
    public String payment;
    @SerializedName("total")
    @Expose
    public Double total;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getParticularsName() {
        return particularsName;
    }

    public void setParticularsName(String particularsName) {
        this.particularsName = particularsName;
    }

    public Double getReceipt() {
        return receipt;
    }

    public void setReceipt(Double receipt) {
        this.receipt = receipt;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
