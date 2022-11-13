package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentLimitResponse {
    @SerializedName("pay_limit_amount")
    @Expose
    private Integer payLimitAmount;

    public Integer getPayLimitAmount() {
        return payLimitAmount;
    }

    public void setPayLimitAmount(Integer payLimitAmount) {
        this.payLimitAmount = payLimitAmount;
    }
}
