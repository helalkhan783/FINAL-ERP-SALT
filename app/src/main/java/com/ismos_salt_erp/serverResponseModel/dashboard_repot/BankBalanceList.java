package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankBalanceList {
    @SerializedName("account_name")
    @Expose
    public String accountName;
    @SerializedName("balance")
    @Expose
    public Double balance;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("bankBranch")
    @Expose
    public String bankBranch;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("bankID")
    @Expose
    public String bankID;
    @SerializedName("initial")
    @Expose
    public Object initial;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public Object getInitial() {
        return initial;
    }

    public void setInitial(Object initial) {
        this.initial = initial;
    }
}
