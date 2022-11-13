package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingTransferCustomerInfo {
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("customer_lname")
    @Expose
    private Object customerLname;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("typeID")
    @Expose
    private String typeID;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("alt_phone")
    @Expose
    private Object altPhone;
    @SerializedName("age")
    @Expose
    private Object age;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("thana")
    @Expose
    private Object thana;
    @SerializedName("division")
    @Expose
    private Object division;
    @SerializedName("district")
    @Expose
    private Object district;
    @SerializedName("nid")
    @Expose
    private Object nid;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("tin")
    @Expose
    private Object tin;
    @SerializedName("bazar")
    @Expose
    private Object bazar;
    @SerializedName("due_limit")
    @Expose
    private String dueLimit;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("customer_note")
    @Expose
    private Object customerNote;
}
