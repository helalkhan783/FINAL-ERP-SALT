package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AccountResponse {
    @SerializedName("bankID")
    @Expose
    private String bankID;
    @SerializedName("mainBankID")
    @Expose
    private String mainBankID;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("bankBranch")
    @Expose
    private String bankBranch;
    @SerializedName("bankAddress")
    @Expose
    private String bankAddress;
    @SerializedName("accountant_name")
    @Expose
    private String accountantName;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountType")
    @Expose
    private String accountType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("routing_no")
    @Expose
    private String routingNo;
    @SerializedName("contact_no")
    @Expose
    private Object contactNo;
    @SerializedName("keyperson_name")
    @Expose
    private String keypersonName;
    @SerializedName("keyperson_contact")
    @Expose
    private String keypersonContact;

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
