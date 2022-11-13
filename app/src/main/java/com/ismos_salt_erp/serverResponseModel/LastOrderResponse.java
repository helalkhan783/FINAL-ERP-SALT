package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LastOrderResponse {
    @SerializedName("orderID")
    @Expose
    private Integer orderID;
}
