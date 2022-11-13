package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GetPendingWashingCrushingDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("items")
    @Expose
    private PendingWashingCrushingItems items;
    @SerializedName("referrer")
    @Expose
    private PendingWashingCrushingReferrer referrer;
    @SerializedName("payment_info")
    @Expose
    private Boolean paymentInfo;
    @SerializedName("order_info")
    @Expose
    private GetPendingWashingCrushingDetailsOrderInfo orderInfo;
}
