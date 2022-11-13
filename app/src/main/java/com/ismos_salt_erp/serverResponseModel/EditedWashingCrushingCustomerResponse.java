package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedWashingCrushingCustomerResponse {

    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
}
