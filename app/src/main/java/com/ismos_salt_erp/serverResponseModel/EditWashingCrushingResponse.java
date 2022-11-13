package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditWashingCrushingResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order")
    @Expose
    private EditWashingCrushingOrder order;
    @SerializedName("order_details")
    @Expose
    private EditWashingCrushingOrderDetails orderDetails;
    @SerializedName("payment_info")
    @Expose
    private Object paymentInfo;
    @SerializedName("destination_store")
    @Expose
    private String destination_store;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EditWashingCrushingOrder getOrder() {
        return order;
    }

    public void setOrder(EditWashingCrushingOrder order) {
        this.order = order;
    }

    public EditWashingCrushingOrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(EditWashingCrushingOrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Object getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(Object paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getDestination_store() {
        return destination_store;
    }

    public void setDestination_store(String destination_store) {
        this.destination_store = destination_store;
    }
}
