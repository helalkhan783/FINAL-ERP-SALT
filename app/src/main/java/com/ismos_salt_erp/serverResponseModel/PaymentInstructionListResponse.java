package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentInstructionListResponse {
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
    private Double due;
    @SerializedName("instruction_id")
    @Expose
    private String instructionId;
    @SerializedName("lpa")
    @Expose
    private String lpa;

    public String getLpa() {
        return lpa;
    }

    public void setLpa(String lpa) {
        this.lpa = lpa;
    }

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

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

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
    }
}
