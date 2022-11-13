package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DeliveryAddress {
    @SerializedName("customer_addressID")
    @Expose
    private String customerAddressID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("permanent_address")
    @Expose
    private Object permanentAddress;
    @SerializedName("present_address")
    @Expose
    private Object presentAddress;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("zip")
    @Expose
    private Object zip;
    @SerializedName("shop_location")
    @Expose
    private String shopLocation;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("upazila")
    @Expose
    private String upazila;
    @SerializedName("address_title")
    @Expose
    private String addressTitle;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("person_name")
    @Expose
    private String personName;
    @SerializedName("c_phone")
    @Expose
    private String cPhone;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sl_id")
    @Expose
    private String slId;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("updated")
    @Expose
    private String updated;
}
