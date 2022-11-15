package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TransactionInList {
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
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("particulars")
    @Expose
    private String particulars;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
