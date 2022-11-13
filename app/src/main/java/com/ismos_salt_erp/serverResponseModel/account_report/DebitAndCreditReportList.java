package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitAndCreditReportList {
    @SerializedName("customerID")
    @Expose
    public String customerID;
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("paymentDate")
    @Expose
    public String paymentDate;
    @SerializedName("paidAmount")
    @Expose
    public String paidAmount;
    @SerializedName("balance_amount")
    @Expose
    public Object balanceAmount;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Object getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Object balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
