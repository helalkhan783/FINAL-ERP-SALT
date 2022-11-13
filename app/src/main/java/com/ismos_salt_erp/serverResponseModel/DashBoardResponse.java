package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DashBoardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("expense")
    @Expose
    private Integer expense;
    @SerializedName("sales")
    @Expose
    private String sales;
    @SerializedName("credit")
    @Expose
    private Integer credit;
    @SerializedName("debit")
    @Expose
    private Integer debit;
}
