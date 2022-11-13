package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingSalesReturnDetailsResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("customer")
    @Expose
    private PendingSalesReturnCustomer customer;
    @SerializedName("orderinfo")
    @Expose
    private PendingSalesReturnDetailsOrderInfo orderinfo;
    @SerializedName("other_information")
    @Expose
    private PendingSalesReturnDetailsOtherInformation otherInformation;
    @SerializedName("delivery_address")
    @Expose
    private DeliveryAddress deliveryAddress;
    @SerializedName("payment_info")
    @Expose
    private PendingSalesReturnDetailsPaymentInfo paymentInfo;
    @SerializedName("order_details")
    @Expose
    private List<PendingSalesReturnOrderDetails> orderDetails = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PendingSalesReturnCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(PendingSalesReturnCustomer customer) {
        this.customer = customer;
    }

    public PendingSalesReturnDetailsOrderInfo getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(PendingSalesReturnDetailsOrderInfo orderinfo) {
        this.orderinfo = orderinfo;
    }

    public PendingSalesReturnDetailsOtherInformation getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(PendingSalesReturnDetailsOtherInformation otherInformation) {
        this.otherInformation = otherInformation;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PendingSalesReturnDetailsPaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PendingSalesReturnDetailsPaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public List<PendingSalesReturnOrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<PendingSalesReturnOrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
