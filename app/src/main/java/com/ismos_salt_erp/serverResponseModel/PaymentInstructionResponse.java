package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentInstructionResponse {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customer_fname;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid;
    @SerializedName("payment_limit")
    @Expose
    private String paymentLimit;
    @SerializedName("due")
    @Expose
    private int due;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomer_fname() {
        return customer_fname;
    }

    public void setCustomer_fname(String customer_fname) {
        this.customer_fname = customer_fname;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getPaymentLimit() {
        return paymentLimit;
    }

    public void setPaymentLimit(String paymentLimit) {
        this.paymentLimit = paymentLimit;
    }

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
    }
}
