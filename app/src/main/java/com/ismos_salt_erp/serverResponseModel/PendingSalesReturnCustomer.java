package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingSalesReturnCustomer {
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("customer_lname")
    @Expose
    private String customerLname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("present_address")
    @Expose
    private Object presentAddress;
    @SerializedName("typeID")
    @Expose
    private String typeID;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("bazar")
    @Expose
    private String bazar;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("thana")
    @Expose
    private String thana;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("country")
    @Expose
    private Object country;
}
