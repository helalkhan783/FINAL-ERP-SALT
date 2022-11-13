package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedPaymentDueResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("payment_info")
    @Expose
    private EditedPaymentDuePaymentInfo paymentInfo;
    @SerializedName("order_amount")
    @Expose
    private EditedPaymentDueOrderAmount orderAmount;
    @SerializedName("previous_payment_info")
    @Expose
    private Boolean previousPaymentInfo;
}
