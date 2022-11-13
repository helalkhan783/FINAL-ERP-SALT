package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GetPurchaseReturnResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("order_details")
    @Expose
    private PurchaseReturnOrderDetails orderDetails;
    @SerializedName("payment_info")
    @Expose
    private PaymentInfo paymentInfo;
    @SerializedName("initial_payment_amount")
    @Expose
    private String initialPaymentAmount;
    @SerializedName("previous_return_amount")
    @Expose
    private Integer previousReturnAmount;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("has_returned_item")
    @Expose
    private Boolean has_returned_item ;

    public Boolean getHas_returned_item() {
        return has_returned_item;
    }

    public void setHas_returned_item(Boolean has_returned_item) {
        this.has_returned_item = has_returned_item;
    }

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PurchaseReturnOrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(PurchaseReturnOrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getInitialPaymentAmount() {
        return initialPaymentAmount;
    }

    public void setInitialPaymentAmount(String initialPaymentAmount) {
        this.initialPaymentAmount = initialPaymentAmount;
    }

    public Integer getPreviousReturnAmount() {
        return previousReturnAmount;
    }

    public void setPreviousReturnAmount(Integer previousReturnAmount) {
        this.previousReturnAmount = previousReturnAmount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
