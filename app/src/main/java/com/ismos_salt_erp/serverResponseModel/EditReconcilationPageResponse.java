package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class EditReconcilationPageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("items")
    @Expose
    private List<EditSaleItemsResponse> items = null;
    @SerializedName("order_info")
    @Expose
    private OrderInfo orderInfo;
    @SerializedName("customer_info")
    @Expose
    private Object customerInfo;
    @SerializedName("reconciliation_type")
    @Expose
    private String reconciliationType;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<EditSaleItemsResponse> getItems() {
        return items;
    }

    public void setItems(List<EditSaleItemsResponse> items) {
        this.items = items;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Object getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Object customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getReconciliationType() {
        return reconciliationType;
    }

    public void setReconciliationType(String reconciliationType) {
        this.reconciliationType = reconciliationType;
    }
}
