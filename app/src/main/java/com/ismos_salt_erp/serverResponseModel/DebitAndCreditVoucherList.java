package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitAndCreditVoucherList {
    @SerializedName("paymentID")
    @Expose
    public String paymentID;
    @SerializedName("orderID")
    @Expose
    public String orderID;
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("payment_type")
    @Expose
    public String paymentType;
    @SerializedName("payment_type_name")
    @Expose
    public String paymentTypeName;
    @SerializedName("payment_date_time")
    @Expose
    public String paymentDateTime;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("custom_discount")
    @Expose
    public String customDiscount;
    @SerializedName("batch_no")
    @Expose
    public String batchNo;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("payment_status")
    @Expose
    public String paymentStatus;
    @SerializedName("entry_userID")
    @Expose
    public String entryUserID;
    @SerializedName("entry_user")
    @Expose
    public String entryUser;

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomDiscount() {
        return customDiscount;
    }

    public void setCustomDiscount(String customDiscount) {
        this.customDiscount = customDiscount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }
}
