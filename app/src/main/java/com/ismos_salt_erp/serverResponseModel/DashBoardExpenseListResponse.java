package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DashBoardExpenseListResponse {
    @SerializedName("expenseID")
    @Expose
    private String expenseID;
    @SerializedName("issued_date_time")
    @Expose
    private String issuedDateTime;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("expense_head")
    @Expose
    private String expenseHead;
    @SerializedName("expense_group")
    @Expose
    private String expenseGroup;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("expense_amount")
    @Expose
    private String expense_amount;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
}
