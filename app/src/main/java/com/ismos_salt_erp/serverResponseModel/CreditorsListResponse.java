package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CreditorsListResponse {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("particulars")
    @Expose
    private String particulars;
    @SerializedName("amount")
    @Expose
    private String amount;
}
