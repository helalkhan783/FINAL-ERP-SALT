package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class DueOrdersResponse{
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
    private CustomerInfoResponse customer;
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

    public CustomerInfoResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfoResponse customer) {
        this.customer = customer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
