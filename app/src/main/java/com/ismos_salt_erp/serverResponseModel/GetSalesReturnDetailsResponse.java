package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GetSalesReturnDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("orderinfo")
    @Expose
    private OrderInfo orderinfo;
    @SerializedName("other_information")
    @Expose
    private OtherInformation otherInformation;
    @SerializedName("delivery_address")
    @Expose
    private Object deliveryAddress;
    @SerializedName("payment_info")
    @Expose
    private PaymentInfo paymentInfo;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;
}
