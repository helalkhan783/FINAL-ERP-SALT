package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CashBookList {
    @SerializedName("transaction_date")
    @Expose
    public String transactionDate;
    @SerializedName("enterprise")
    @Expose
    public String enterprise;
    @SerializedName("transaction_id")
    @Expose
    public String transactionId;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("in")
    @Expose
    public String in;
    @SerializedName("out")
    @Expose
    public String out;
    @SerializedName("balance")
    @Expose
    public String balance;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
