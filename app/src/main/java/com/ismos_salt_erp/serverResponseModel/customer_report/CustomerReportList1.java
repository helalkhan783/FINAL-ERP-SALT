package com.ismos_salt_erp.serverResponseModel.customer_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerReportList1 {
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("invoice_no")
    @Expose
    public String invoiceNo;
    @SerializedName("sale_amount")
    @Expose
    public String saleAmount;
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
    public Object remarks;
    @SerializedName("balance")
    @Expose
    public String balance;
    @SerializedName("particular")
    @Expose
    public String particular;
    @SerializedName("trx_type")
    @Expose
    public String trx_type;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getTrx_type() {
        return trx_type;
    }

    public void setTrx_type(String trx_type) {
        this.trx_type = trx_type;
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

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
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

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
