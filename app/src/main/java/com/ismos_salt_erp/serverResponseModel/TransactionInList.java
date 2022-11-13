package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TransactionInList {
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("particulars")
    @Expose
    private String orderID;
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
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("FullName")
    @Expose
    private String fullName;

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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
