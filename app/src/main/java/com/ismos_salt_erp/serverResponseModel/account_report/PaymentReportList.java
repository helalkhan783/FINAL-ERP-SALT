package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReportList {
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("customerID")
    @Expose
    public String customerID;
    @SerializedName("paymentID")
    @Expose
    public String paymentID;
    @SerializedName("payment_type")
    @Expose
    public String paymentType;
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("batch_no")
    @Expose
    public String batchNo;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("entry_userID")
    @Expose
    public String entryUserID;
    @SerializedName("payment_type_name")
    @Expose
    public String paymentTypeName;
    @SerializedName("paid_amount")
    @Expose
    public String paid_amount;

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }
}
