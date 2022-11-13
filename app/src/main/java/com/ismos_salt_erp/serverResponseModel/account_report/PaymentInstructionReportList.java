package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInstructionReportList {
    @SerializedName("payment_limitID")
    @Expose
    public String paymentLimitID;
    @SerializedName("customerID")
    @Expose
    public String customerID;
    @SerializedName("pay_limit_amount")
    @Expose
    public String payLimitAmount;
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("entryUserID")
    @Expose
    public String entryUserID;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("modifiedUserID")
    @Expose
    public String modifiedUserID;
    @SerializedName("modifiedDate")
    @Expose
    public String modifiedDate;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("storeID")
    @Expose
    public String storeID;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("expire_date")
    @Expose
    public String expireDate;
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("order_grand_total")
    @Expose
    public String orderGrandTotal;
    @SerializedName("order_total_paid")
    @Expose
    public String orderTotalPaid;
    @SerializedName("order_due")
    @Expose
    public Integer orderDue;

    public String getPaymentLimitID() {
        return paymentLimitID;
    }

    public void setPaymentLimitID(String paymentLimitID) {
        this.paymentLimitID = paymentLimitID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPayLimitAmount() {
        return payLimitAmount;
    }

    public void setPayLimitAmount(String payLimitAmount) {
        this.payLimitAmount = payLimitAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getModifiedUserID() {
        return modifiedUserID;
    }

    public void setModifiedUserID(String modifiedUserID) {
        this.modifiedUserID = modifiedUserID;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrderGrandTotal() {
        return orderGrandTotal;
    }

    public void setOrderGrandTotal(String orderGrandTotal) {
        this.orderGrandTotal = orderGrandTotal;
    }

    public String getOrderTotalPaid() {
        return orderTotalPaid;
    }

    public void setOrderTotalPaid(String orderTotalPaid) {
        this.orderTotalPaid = orderTotalPaid;
    }

    public Integer getOrderDue() {
        return orderDue;
    }

    public void setOrderDue(Integer orderDue) {
        this.orderDue = orderDue;
    }
}
