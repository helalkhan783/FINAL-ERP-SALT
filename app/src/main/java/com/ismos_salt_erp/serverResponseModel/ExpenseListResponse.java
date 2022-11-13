package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ExpenseListResponse {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("expense_amount")
    @Expose
    private String expenseAmount;
    @SerializedName("remarks")
    @Expose
    private String remarks;
}
