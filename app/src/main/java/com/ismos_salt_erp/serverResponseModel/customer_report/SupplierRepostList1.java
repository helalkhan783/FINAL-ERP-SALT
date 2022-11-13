package com.ismos_salt_erp.serverResponseModel.customer_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierRepostList1 {
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("invoice_no")
    @Expose
    public String invoiceNo;
    @SerializedName("purchase_amount")
    @Expose
    public String purchaseAmount;
    @SerializedName("return_amount")
    @Expose
    public String returnAmount;
    @SerializedName("receipt_amount")
    @Expose
    public String receiptAmount;
    @SerializedName("payment_amount")
    @Expose
    public String paymentAmount;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("balance")
    @Expose
    public String balance;
    @SerializedName("trx_type")
    @Expose
    public String trx_type;
    @SerializedName("particular")
    @Expose
    public String particular;

    public String getTrx_type() {
        return trx_type;
    }

    public void setTrx_type(String trx_type) {
        this.trx_type = trx_type;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
