package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaleReturnPendingList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("order_vendorID")
    @Expose
    private String orderVendorID;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("return_amount")
    @Expose
    private ReturnAmount returnAmount;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("order_type")
    @Expose
    private String orderType;
}
