
package com.ismos_salt_erp.serverResponseModel;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedPurchaseOrderResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("current_order")
    @Expose
    private CurrentOrder currentOrder;
    @SerializedName("current_order_details")
    @Expose
    private CurrentOrderDetails currentOrderDetails;
    @SerializedName("current_payment_info")
    @Expose
    private Object currentPaymentInfo; // CurrentPaymentInfo
    @SerializedName("edited_order_details")
    @Expose
    private List<EditedOrderDetail> editedOrderDetails = null;
    @SerializedName("edited_order")
    @Expose
    private EditedOrder editedOrder;
    @SerializedName("edited_customer")
    @Expose
    private EditedCustomer editedCustomer;
    @SerializedName("edited_payment_info")
    @Expose
    private EditedPaymentInfo editedPaymentInfo;
    @SerializedName("edited_discount")
    @Expose
    private Integer editedDiscount;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CurrentOrder getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(CurrentOrder currentOrder) {
        this.currentOrder = currentOrder;
    }

    public CurrentOrderDetails getCurrentOrderDetails() {
        return currentOrderDetails;
    }

    public void setCurrentOrderDetails(CurrentOrderDetails currentOrderDetails) {
        this.currentOrderDetails = currentOrderDetails;
    }


    public List<EditedOrderDetail> getEditedOrderDetails() {
        return editedOrderDetails;
    }

    public void setEditedOrderDetails(List<EditedOrderDetail> editedOrderDetails) {
        this.editedOrderDetails = editedOrderDetails;
    }

    public EditedOrder getEditedOrder() {
        return editedOrder;
    }

    public void setEditedOrder(EditedOrder editedOrder) {
        this.editedOrder = editedOrder;
    }

    public EditedCustomer getEditedCustomer() {
        return editedCustomer;
    }

    public void setEditedCustomer(EditedCustomer editedCustomer) {
        this.editedCustomer = editedCustomer;
    }

    public EditedPaymentInfo getEditedPaymentInfo() {
        return editedPaymentInfo;
    }

    public void setEditedPaymentInfo(EditedPaymentInfo editedPaymentInfo) {
        this.editedPaymentInfo = editedPaymentInfo;
    }

    public Integer getEditedDiscount() {
        return editedDiscount;
    }

    public void setEditedDiscount(Integer editedDiscount) {
        this.editedDiscount = editedDiscount;
    }
}
