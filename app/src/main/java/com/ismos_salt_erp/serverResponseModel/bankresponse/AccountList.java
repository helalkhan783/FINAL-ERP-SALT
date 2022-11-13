package com.ismos_salt_erp.serverResponseModel.bankresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountList {
    @SerializedName("bank_amountID")
    @Expose
    public String bankAmountID;
    @SerializedName("bankID")
    @Expose
    public String bankID;
    @SerializedName("paymentID")
    @Expose
    public String paymentID;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("transactionType")
    @Expose
    public String transactionType;
    @SerializedName("transactionDate")
    @Expose
    public String transactionDate;
    @SerializedName("transactionDateTime")
    @Expose
    public String transactionDateTime;
    @SerializedName("referenceID")
    @Expose
    public Object referenceID;
    @SerializedName("notes")
    @Expose
    public String notes;
    @SerializedName("storeID")
    @Expose
    public String storeID;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("transactionMonth")
    @Expose
    public String transactionMonth;
    @SerializedName("transactionYear")
    @Expose
    public String transactionYear;
    @SerializedName("receipt_number")
    @Expose
    public Object receiptNumber;
    @SerializedName("entry_userID")
    @Expose
    public String entryUserID;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("sl_id")
    @Expose
    public Object slId;
    @SerializedName("updated_by")
    @Expose
    public Object updatedBy;
    @SerializedName("updated_at")
    @Expose
    public Object updatedAt;
    @SerializedName("others")
    @Expose
    public String others;
    @SerializedName("entry_user")
    @Expose
    public String entryUser;
    @SerializedName("entry_user_name")
    @Expose
    public String entryUserName;
    @SerializedName("in")
    @Expose
    public String in;
    @SerializedName("out")
    @Expose
    public String out;
    @SerializedName("particular")
    @Expose
    public String particular;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getBankAmountID() {
        return bankAmountID;
    }

    public void setBankAmountID(String bankAmountID) {
        this.bankAmountID = bankAmountID;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public Object getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Object referenceID) {
        this.referenceID = referenceID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(String transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public String getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(String transactionYear) {
        this.transactionYear = transactionYear;
    }

    public Object getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Object receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getSlId() {
        return slId;
    }

    public void setSlId(Object slId) {
        this.slId = slId;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getEntryUserName() {
        return entryUserName;
    }

    public void setEntryUserName(String entryUserName) {
        this.entryUserName = entryUserName;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
