package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CustomerEditResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("customer_info")
    @Expose
    private CustomerEditInfo customerInfo;
    @SerializedName("initial_amount_editable")
    @Expose
    private Integer initialAmountEditable;
    @SerializedName("initial_payment_info")
    @Expose
    private CustomerInitialPaymentInfo initialPaymentInfo;

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

    public CustomerEditInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerEditInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Integer getInitialAmountEditable() {
        return initialAmountEditable;
    }

    public void setInitialAmountEditable(Integer initialAmountEditable) {
        this.initialAmountEditable = initialAmountEditable;
    }

    public CustomerInitialPaymentInfo getInitialPaymentInfo() {
        return initialPaymentInfo;
    }

    public void setInitialPaymentInfo(CustomerInitialPaymentInfo initialPaymentInfo) {
        this.initialPaymentInfo = initialPaymentInfo;
    }
}
