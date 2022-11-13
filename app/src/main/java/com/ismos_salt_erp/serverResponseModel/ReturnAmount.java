package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ReturnAmount {
    @SerializedName("selling_price")
    @Expose
    private Object sellingPrice;
    @SerializedName("buying_price")
    @Expose
    private Object buyingPrice;
}
