package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingExpensePaymentDueResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payment_info")
    @Expose
    private PaymentInformation paymentInfo;
    @SerializedName("lists")
    @Expose
    private List<ExpensePaymentDueList> lists = null;
    @SerializedName("customer_info")
    @Expose
    private ExpensePaymentDueCustomerInfo customerInfo;

    public PaymentInformation getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInformation paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExpensePaymentDueList> getLists() {
        return lists;
    }

    public void setLists(List<ExpensePaymentDueList> lists) {
        this.lists = lists;
    }

    public ExpensePaymentDueCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(ExpensePaymentDueCustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
