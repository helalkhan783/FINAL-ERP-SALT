package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SupplierResponse {
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
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public Object getCustomerLname() {
        return customerLname;
    }

    public void setCustomerLname(Object customerLname) {
        this.customerLname = customerLname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getDueLimit() {
        return dueLimit;
    }

    public void setDueLimit(Object dueLimit) {
        this.dueLimit = dueLimit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBazar() {
        return bazar;
    }

    public void setBazar(String bazar) {
        this.bazar = bazar;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
