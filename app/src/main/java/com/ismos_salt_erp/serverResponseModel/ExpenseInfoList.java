package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ExpenseInfoList {
    @SerializedName("expenseID")
    @Expose
    private String expenseID;
    @SerializedName("expense_typeID")
    @Expose
    private String expenseTypeID;
    @SerializedName("expense_amount")
    @Expose
    private String expenseAmount;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("entry_month")
    @Expose
    private Object entryMonth;
    @SerializedName("entry_year")
    @Expose
    private String entryYear;
    @SerializedName("entry_date_time")
    @Expose
    private String entryDateTime;
    @SerializedName("issued_date_time")
    @Expose
    private String issuedDateTime;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("expense_referenceNO")
    @Expose
    private String expenseReferenceNO;
    @SerializedName("approval_status")
    @Expose
    private String approvalStatus;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("expense_title")
    @Expose
    private String expenseTitle;
    @SerializedName("edit_attempt_time")
    @Expose
    private Object editAttemptTime;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("expense_category")
    @Expose
    private String expenseCategory;
    @SerializedName("store_name")
    @Expose
    private String storeName;
}
