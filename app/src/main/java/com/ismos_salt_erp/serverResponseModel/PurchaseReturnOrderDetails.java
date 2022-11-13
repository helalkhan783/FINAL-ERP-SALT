package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReturnOrderDetails {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("requisition_date")
    @Expose
    private String requisitionDate;
    @SerializedName("requisition_end_date")
    @Expose
    private String requisitionEndDate;
    @SerializedName("items")
    @Expose
    private List<PurchaseItems> items = null;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("collected")
    @Expose
    private Integer collected;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getRequisitionEndDate() {
        return requisitionEndDate;
    }

    public void setRequisitionEndDate(String requisitionEndDate) {
        this.requisitionEndDate = requisitionEndDate;
    }

    public List<PurchaseItems> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItems> items) {
        this.items = items;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getCollected() {
        return collected;
    }

    public void setCollected(Integer collected) {
        this.collected = collected;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
