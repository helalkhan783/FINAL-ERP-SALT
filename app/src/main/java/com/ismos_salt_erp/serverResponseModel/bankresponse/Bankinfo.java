package com.ismos_salt_erp.serverResponseModel.bankresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bankinfo {
    @SerializedName("bankID")
    @Expose
    public String bankID;
    @SerializedName("mainBankID")
    @Expose
    public String mainBankID;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("bankBranch")
    @Expose
    public String bankBranch;
    @SerializedName("bankAddress")
    @Expose
    public String bankAddress;
    @SerializedName("accountant_name")
    @Expose
    public String accountantName;
    @SerializedName("notes")
    @Expose
    public Object notes;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("accountType")
    @Expose
    public String accountType;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("storeID")
    @Expose
    public String storeID;
    @SerializedName("routing_no")
    @Expose
    public String routingNo;
    @SerializedName("contact_no")
    @Expose
    public Object contactNo;
    @SerializedName("keyperson_name")
    @Expose
    public String keypersonName;
    @SerializedName("keyperson_contact")
    @Expose
    public String keypersonContact;

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getMainBankID() {
        return mainBankID;
    }

    public void setMainBankID(String mainBankID) {
        this.mainBankID = mainBankID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getAccountantName() {
        return accountantName;
    }

    public void setAccountantName(String accountantName) {
        this.accountantName = accountantName;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getRoutingNo() {
        return routingNo;
    }

    public void setRoutingNo(String routingNo) {
        this.routingNo = routingNo;
    }

    public Object getContactNo() {
        return contactNo;
    }

    public void setContactNo(Object contactNo) {
        this.contactNo = contactNo;
    }

    public String getKeypersonName() {
        return keypersonName;
    }

    public void setKeypersonName(String keypersonName) {
        this.keypersonName = keypersonName;
    }

    public String getKeypersonContact() {
        return keypersonContact;
    }

    public void setKeypersonContact(String keypersonContact) {
        this.keypersonContact = keypersonContact;
    }
}
