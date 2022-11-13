package com.ismos_salt_erp.serverResponseModel.customer_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.serverResponseModel.CustomerReportList;

import java.util.List;

public class CustomerReportResponse1 {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("list")
    @Expose
    public List<CustomerReportList1> list = null;
    @SerializedName("total_sale")
    @Expose
    public String totalPurchase;
    @SerializedName("total_return")
    @Expose
    public String totalReturn;
    @SerializedName("total_receipt")
    @Expose
    public String totalReceipt;
    @SerializedName("total_payment")
    @Expose
    public String totalPayment;
    @SerializedName("total_balance")
    @Expose
    public String totalBalance;
    @SerializedName("opening_balance")
    @Expose
    public String openingBalance;
    @SerializedName("due_balance")
    @Expose
    public String dueBalance;
    @SerializedName("sale_total")
    @Expose
    public String sale_total;

    public String getSale_total() {
        return sale_total;
    }

    public void setSale_total(String sale_total) {
        this.sale_total = sale_total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomerReportList1> getList() {
        return list;
    }

    public void setList(List<CustomerReportList1> list) {
        this.list = list;
    }

    public String getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(String totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public String getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(String totalReturn) {
        this.totalReturn = totalReturn;
    }

    public String getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(String totalReceipt) {
        this.totalReceipt = totalReceipt;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getDueBalance() {
        return dueBalance;
    }

    public void setDueBalance(String dueBalance) {
        this.dueBalance = dueBalance;
    }
}
