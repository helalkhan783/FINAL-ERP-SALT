package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CustomerReportList {
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("invounce_no")
    @Expose
    public String invounceNo;
    @SerializedName("sales_type")
    @Expose
    public String salesType;
    @SerializedName("v")
    @Expose
    public Integer v;
    @SerializedName("sale_amount")
    @Expose
    public String saleAmount;
    @SerializedName("paid_amount")
    @Expose
    public String paidAmount;
    @SerializedName("balance")
    @Expose
    public String balance;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getInvounceNo() {
        return invounceNo;
    }

    public void setInvounceNo(String invounceNo) {
        this.invounceNo = invounceNo;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
