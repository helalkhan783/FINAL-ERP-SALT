package com.ismos_salt_erp.serverResponseModel;

public class AddNewLimitModel {
    String paymentLimitAmount, customerId;

    public AddNewLimitModel(String paymentLimitAmount, String customerId) {
        this.paymentLimitAmount = paymentLimitAmount;
        this.customerId = customerId;
    }

    public String getPaymentLimitAmount() {
        return paymentLimitAmount;
    }

    public void setPaymentLimitAmount(String paymentLimitAmount) {
        this.paymentLimitAmount = paymentLimitAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
