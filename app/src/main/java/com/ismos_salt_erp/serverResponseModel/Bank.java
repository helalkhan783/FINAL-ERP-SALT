package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {
    @SerializedName("mainBankID")
    @Expose
    public String mainBankID;
    @SerializedName("bankID")
    @Expose
    public String bankID;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("bankBranch")
    @Expose
    public String bankBranch;
    @SerializedName("accountType")
    @Expose
    public String accountType;
    @SerializedName("accountant_name")
    @Expose
    public String accountantName;

    public String getMainBankID() {
        return mainBankID;
    }

    public void setMainBankID(String mainBankID) {
        this.mainBankID = mainBankID;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountantName() {
        return accountantName;
    }

    public void setAccountantName(String accountantName) {
        this.accountantName = accountantName;
    }
}
