package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingIodizationDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("items")
    @Expose
    private IodizationItems items;
    @SerializedName("referrer")
    @Expose
    private IodizationRefferResponse referrer;
    @SerializedName("payment_info")
    @Expose
    private Boolean paymentInfo;
    @SerializedName("order_info")
    @Expose
    private IodizationOrderInfoResponse orderInfo;

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

    public IodizationItems getItems() {
        return items;
    }

    public void setItems(IodizationItems items) {
        this.items = items;
    }

    public IodizationRefferResponse getReferrer() {
        return referrer;
    }

    public void setReferrer(IodizationRefferResponse referrer) {
        this.referrer = referrer;
    }

    public Boolean getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(Boolean paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public IodizationOrderInfoResponse getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(IodizationOrderInfoResponse orderInfo) {
        this.orderInfo = orderInfo;
    }
}
