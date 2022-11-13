package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private  String message;
    @SerializedName("customer")
    @Expose
    private PurchaseDetailsCustomer customer;
    @SerializedName("orderinfo")
    @Expose
    private PurchaseDetailsOrderInfo orderinfo;
    @SerializedName("other_information")
    @Expose
    private Object otherInformation ;
    @SerializedName("delivery_address")
    @Expose
    private Object deliveryAddress;
    @SerializedName("payment_info")
    @Expose
    private PurchaseDetailsPaymentInfo paymentInfo;
    @SerializedName("order_details")
    @Expose
    private List<PurchaseOrderDetailList> orderDetails = null;

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

    public PurchaseDetailsCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(PurchaseDetailsCustomer customer) {
        this.customer = customer;
    }

    public PurchaseDetailsOrderInfo getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(PurchaseDetailsOrderInfo orderinfo) {
        this.orderinfo = orderinfo;
    }

    public Object getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(Object otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Object getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Object deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PurchaseDetailsPaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PurchaseDetailsPaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public List<PurchaseOrderDetailList> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<PurchaseOrderDetailList> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
