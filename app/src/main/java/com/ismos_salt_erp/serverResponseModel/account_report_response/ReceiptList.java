package com.ismos_salt_erp.serverResponseModel.account_report_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptList {
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("transaction_type")
    @Expose
    public String transactionType;
    @SerializedName("receipt_amout")
    @Expose
    public String receiptAmout;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReceiptAmout() {
        return receiptAmout;
    }

    public void setReceiptAmout(String receiptAmout) {
        this.receiptAmout = receiptAmout;
    }
}
