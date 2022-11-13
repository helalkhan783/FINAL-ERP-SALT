package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DevitorsList {
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("supplier")
    @Expose
    public String supplier;
    @SerializedName("last_payment_date")
    @Expose
    public String lastPaymentDate;
    @SerializedName("last_payment_amount")
    @Expose
    public String lastPaymentAmount;
    @SerializedName("balance")
    @Expose
    public String balance;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    public void setLastPaymentAmount(String lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
