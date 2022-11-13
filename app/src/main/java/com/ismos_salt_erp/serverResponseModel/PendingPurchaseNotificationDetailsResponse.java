package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingPurchaseNotificationDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprise_name")
    @Expose
    private String enterprise_name;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("customer")
    @Expose
    private PendingPurchaseNotificationCustomer customer;
    @SerializedName("other_info")
    @Expose
    private OtherInfoResponse otherInfo;
    @SerializedName("payment_info")
    @Expose
    private PaymentInfoForPurchase paymentInfo;
    @SerializedName("order_info")
    @Expose
    private OrderInfoResponse orderInfo;
    @SerializedName("processed_by")
    @Expose
    private ProcessedByResponse processedBy;
    @SerializedName("items")
    @Expose
    private List<ItemsResponse> items = null;

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

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public PendingPurchaseNotificationCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(PendingPurchaseNotificationCustomer customer) {
        this.customer = customer;
    }

    public OtherInfoResponse getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfoResponse otherInfo) {
        this.otherInfo = otherInfo;
    }

    public PaymentInfoForPurchase getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfoForPurchase paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public OrderInfoResponse getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoResponse orderInfo) {
        this.orderInfo = orderInfo;
    }

    public ProcessedByResponse getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(ProcessedByResponse processedBy) {
        this.processedBy = processedBy;
    }

    public List<ItemsResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemsResponse> items) {
        this.items = items;
    }
}
