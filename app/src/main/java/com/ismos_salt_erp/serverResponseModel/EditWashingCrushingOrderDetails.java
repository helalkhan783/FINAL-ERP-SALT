package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class EditWashingCrushingOrderDetails {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("items")
    @Expose
    private List<EditWashingCrushingItem> items = null;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("output_item")
    @Expose
    private String outputItem;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EditWashingCrushingItem> getItems() {
        return items;
    }

    public void setItems(List<EditWashingCrushingItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOutputItem() {
        return outputItem;
    }

    public void setOutputItem(String outputItem) {
        this.outputItem = outputItem;
    }
}
