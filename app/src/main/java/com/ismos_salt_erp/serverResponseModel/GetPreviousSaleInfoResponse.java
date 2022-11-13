package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.adapter.OrderResponse;

import lombok.Data;

@Data
public class GetPreviousSaleInfoResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order")
    @Expose
    private OrderResponse order;
    @SerializedName("order_details")
    @Expose
    private GetPreviousSaleOrderDetails orderDetails;
    @SerializedName("payment_info")
    @Expose
    private GetPreviousSalePaymentInfo paymentInfo;
    @SerializedName("payments_count")
    @Expose
    private Integer paymentsCount;
}
