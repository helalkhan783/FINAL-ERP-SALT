package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReceiptDetailsBatchList {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_type_name")
    @Expose
    private String paymentTypeName;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("order_serialID")
    @Expose
    private String order_serialID;

    @SerializedName("particulars")
    @Expose
    private String particular;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getOrder_serialID() {
        return order_serialID;
    }

    public void setOrder_serialID(String order_serialID) {
        this.order_serialID = order_serialID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
}
