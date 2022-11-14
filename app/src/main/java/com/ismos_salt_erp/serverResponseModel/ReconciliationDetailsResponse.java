package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ReconciliationDetailsResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<ReconciliationListResponse> details = null;
    @SerializedName("payment_status")
    @Expose
    private ReconciliationPaymentStatus paymentStatus;
    @SerializedName("order_info")
    @Expose
    private ReconciliationOrderInfo orderInfo;
    @SerializedName("enterprise")
    @Expose
    private String enterprise;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ReconciliationListResponse> getDetails() {
        return details;
    }

    public void setDetails(List<ReconciliationListResponse> details) {
        this.details = details;
    }

    public ReconciliationPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(ReconciliationPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ReconciliationOrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(ReconciliationOrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
