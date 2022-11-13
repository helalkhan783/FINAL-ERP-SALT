package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AddNewLimitInstructionResponse {
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("last_received_date")
    @Expose
    private String lastReceivedDate;
    @SerializedName("last_paid_amount")
    @Expose
    private Double lastPaidAmount;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("due")
    @Expose
    private Double due;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getLastReceivedDate() {
        return lastReceivedDate;
    }

    public void setLastReceivedDate(String lastReceivedDate) {
        this.lastReceivedDate = lastReceivedDate;
    }

    public Double getLastPaidAmount() {
        return lastPaidAmount;
    }

    public void setLastPaidAmount(Double lastPaidAmount) {
        this.lastPaidAmount = lastPaidAmount;
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

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
    }
}
