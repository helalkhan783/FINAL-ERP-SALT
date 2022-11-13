package com.ismos_salt_erp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VendorReportList {
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("reference_no")
    @Expose
    public String referenceNo;
    @SerializedName("expense")
    @Expose
    public String expense;
    @SerializedName("receipt")
    @Expose
    public String receipt;
    @SerializedName("payment")
    @Expose
    public String payment;
    @SerializedName("remarks")
    @Expose
    public Object remarks;
    @SerializedName("balance")
    @Expose
    public Double balance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
