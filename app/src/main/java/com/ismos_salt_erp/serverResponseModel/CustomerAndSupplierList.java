package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAndSupplierList {
    @SerializedName("customerID")
    @Expose
    public String customerID;
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("customer_lname")
    @Expose
    public Object customerLname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("phone")
    @Expose
    public String phone;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public Object getCustomerLname() {
        return customerLname;
    }

    public void setCustomerLname(Object customerLname) {
        this.customerLname = customerLname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
