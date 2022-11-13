package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ReconciliationDetailsResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("details")
    @Expose
    private List<ReconciliationListResponse> details = null;
    @SerializedName("payment_status")
    @Expose
    private ReconciliationPaymentStatus paymentStatus;
    @SerializedName("order_info")
    @Expose
    private ReconciliationOrderInfo orderInfo;
    @SerializedName("enterprise")
    @Expose
    private String enterprise;
}
