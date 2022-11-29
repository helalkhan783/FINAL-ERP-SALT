package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInformation {
    @SerializedName("date_time")
    @Expose
    private String date;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payment_type_name")
    @Expose
    private String paymentTypeName;
    @SerializedName("payment_remarks")
    @Expose
    private String paymentRemarks;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }
}
