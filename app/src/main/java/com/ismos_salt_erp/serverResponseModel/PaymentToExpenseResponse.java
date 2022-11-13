package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentToExpenseResponse {
    @SerializedName("mainBankID")
    @Expose
    private String mainBankID;
    @SerializedName("mainBankName")
    @Expose
    private String mainBankName;
}
