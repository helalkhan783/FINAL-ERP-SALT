package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ExpensePaymentDueCustomerInfo {
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("typeID")
    @Expose
    private String typeID;
    @SerializedName("customer_lname")
    @Expose
    private Object customerLname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("due_limit")
    @Expose
    private Object dueLimit;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("bazar")
    @Expose
    private String bazar;
    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("tin")
    @Expose
    private String tin;
    @SerializedName("remaining_due")
    @Expose
    private Integer remainingDue;
    @SerializedName("status")
    @Expose
    private Integer status;
}
