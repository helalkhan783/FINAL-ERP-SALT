package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ExpenseDueResponse {
    @SerializedName("orders")
    @Expose
    private List<ExpenseOrdersResponse> orders = null;
    @SerializedName("payment_types")
    @Expose
    private List<PaymentTypes> paymentTypes;
    @SerializedName("payment_sub_types")
    @Expose
    private PaymentSubTypes paymentSubTypes;
    @SerializedName("main_banks")
    @Expose
    private List<MainBank> mainBanks = null;
    @SerializedName("customer")
    @Expose
    private ExpenseCustomerResponse customer;
    @SerializedName("payment_to")
    @Expose
    private List<PaymentToExpenseResponse> paymentTo = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<PaymentTypes> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentTypes> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<ExpenseOrdersResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<ExpenseOrdersResponse> orders) {
        this.orders = orders;
    }


    public PaymentSubTypes getPaymentSubTypes() {
        return paymentSubTypes;
    }

    public void setPaymentSubTypes(PaymentSubTypes paymentSubTypes) {
        this.paymentSubTypes = paymentSubTypes;
    }

    public List<MainBank> getMainBanks() {
        return mainBanks;
    }

    public void setMainBanks(List<MainBank> mainBanks) {
        this.mainBanks = mainBanks;
    }

    public ExpenseCustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(ExpenseCustomerResponse customer) {
        this.customer = customer;
    }

    public List<PaymentToExpenseResponse> getPaymentTo() {
        return paymentTo;
    }

    public void setPaymentTo(List<PaymentToExpenseResponse> paymentTo) {
        this.paymentTo = paymentTo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
