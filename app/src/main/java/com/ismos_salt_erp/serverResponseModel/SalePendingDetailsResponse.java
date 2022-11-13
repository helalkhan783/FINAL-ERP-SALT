package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SalePendingDetailsResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("details")
    @Expose
    private Details details;

}
