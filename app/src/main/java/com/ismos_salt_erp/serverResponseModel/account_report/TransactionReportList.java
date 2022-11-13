package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionReportList {

    @SerializedName("paymentSummaryID")
    @Expose
    private String paymentSummaryID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_date_time")
    @Expose
    private String paymentDateTime;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("custom_discount")
    @Expose
    private String customDiscount;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("payment_type_name")
    @Expose
    private String paymentTypeName;
    @SerializedName("particular")
    @Expose
    private String particular;

    public String getPaymentSummaryID() {
        return paymentSummaryID;
    }

    public void setPaymentSummaryID(String paymentSummaryID) {
        this.paymentSummaryID = paymentSummaryID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }
}
