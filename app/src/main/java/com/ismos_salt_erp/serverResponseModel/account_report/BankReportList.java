package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankReportList {
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
    public String referenceID;
    @SerializedName("notes")
    @Expose
    public Object notes;
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
    public String receiptNumber;
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
    public Object others;
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
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("accountType")
    @Expose
    public String accountType;
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
    @SerializedName("transection_type")
    @Expose
    public String transectionType;
    @SerializedName("deposite_amount_in")
    @Expose
    public Double depositeAmountIn;
    @SerializedName("deposite_amount_out")
    @Expose
    public String depositeAmountOut;
    @SerializedName("total_balance")
    @Expose
    public Double totalBalance;
    @SerializedName("particular")
    @Expose
    public Object particular;
    @SerializedName("entry_user_name")
    @Expose
    public String entryUserName;
    @SerializedName("cheque_no")
    @Expose
    public Object chequeNo;
    @SerializedName("party_name")
    @Expose
    public String party_name ;

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getEntryUserName() {
        return entryUserName;
    }

    public void setEntryUserName(String entryUserName) {
        this.entryUserName = entryUserName;
    }

    public Object getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(Object chequeNo) {
        this.chequeNo = chequeNo;
    }


    public Object getParticular() {
        return particular;
    }

    public void setParticular(Object particular) {
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

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
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

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
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

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
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

    public String getTransectionType() {
        return transectionType;
    }

    public void setTransectionType(String transectionType) {
        this.transectionType = transectionType;
    }

    public Double getDepositeAmountIn() {
        return depositeAmountIn;
    }

    public void setDepositeAmountIn(Double depositeAmountIn) {
        this.depositeAmountIn = depositeAmountIn;
    }

    public String getDepositeAmountOut() {
        return depositeAmountOut;
    }

    public void setDepositeAmountOut(String depositeAmountOut) {
        this.depositeAmountOut = depositeAmountOut;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
