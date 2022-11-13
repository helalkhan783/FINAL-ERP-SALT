package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseHistoryList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String order_serial;
    @SerializedName("order_vendorID")
    @Expose
    private String order_vendorID;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("end_date")
    @Expose
    private Object endDate;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("order_type")
    @Expose
    private String order_type;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public String getOrder_serial() {
        return order_serial;
    }

    public void setOrder_serial(String order_serial) {
        this.order_serial = order_serial;
    }

    public String getOrder_vendorID() {
        return order_vendorID;
    }

    public void setOrder_vendorID(String order_vendorID) {
        this.order_vendorID = order_vendorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
