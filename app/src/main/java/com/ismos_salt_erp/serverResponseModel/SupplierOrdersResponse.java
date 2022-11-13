package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SupplierOrdersResponse {
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
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
    private SupplierResponse customer;
    @SerializedName("payment_limit")
    @Expose
    private PaymentLimitResponse paymentLimit;
    @SerializedName("payment_to")
    @Expose
    private List<PaymentToResponse> paymentTo = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<PaymentTypes> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentTypes> paymentTypes) {
        this.paymentTypes = paymentTypes;
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

    public SupplierResponse getCustomer() {
        return customer;
    }

    public void setCustomer(SupplierResponse customer) {
        this.customer = customer;
    }

    public PaymentLimitResponse getPaymentLimit() {
        return paymentLimit;
    }

    public void setPaymentLimit(PaymentLimitResponse paymentLimit) {
        this.paymentLimit = paymentLimit;
    }

    public List<PaymentToResponse> getPaymentTo() {
        return paymentTo;
    }

    public void setPaymentTo(List<PaymentToResponse> paymentTo) {
        this.paymentTo = paymentTo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
