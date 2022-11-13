package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReturnPendingDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("customer")
    @Expose
    private PurchaseReturnPendingCustomer customer;
    @SerializedName("orderinfo")
    @Expose
    private PurchaseReturnPendingOrderInfo orderinfo;
    @SerializedName("other_information")
    @Expose
    private PurchaseReturnPendingOtherInformation otherInformation;
    @SerializedName("delivery_address")
    @Expose
    private Object deliveryAddress;
    @SerializedName("payment_info")
    @Expose
    private PurchaseReturnPendingPaymentInfo paymentInfo;
    @SerializedName("order_details")
    @Expose
    private List<PurchaseReturnPendingOrderDetail> orderDetails = null;
}
