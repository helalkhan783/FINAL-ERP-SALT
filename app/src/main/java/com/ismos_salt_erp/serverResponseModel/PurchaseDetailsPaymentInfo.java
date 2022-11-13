package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseDetailsPaymentInfo {
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("custom_discount")
    @Expose
    private String customDiscount;
    @SerializedName("payment_remarks")
    @Expose
    private Object paymentRemarks;
    @SerializedName("payment_month")
    @Expose
    private String paymentMonth;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("payment_year")
    @Expose
    private String paymentYear;
    @SerializedName("payment_date_time")
    @Expose
    private String paymentDateTime;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("counter_no")
    @Expose
    private String counterNo;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("sales_type")
    @Expose
    private String salesType;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;
    @SerializedName("requisition_ref")
    @Expose
    private Object requisitionRef;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("batch_ref")
    @Expose
    private String batchRef;
    @SerializedName("payment_sub_type")
    @Expose
    private Object paymentSubType;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getCustomDiscount() {
        return customDiscount;
    }

    public void setCustomDiscount(String customDiscount) {
        this.customDiscount = customDiscount;
    }

    public Object getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(Object paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(String paymentYear) {
        this.paymentYear = paymentYear;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
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

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Object getRequisitionRef() {
        return requisitionRef;
    }

    public void setRequisitionRef(Object requisitionRef) {
        this.requisitionRef = requisitionRef;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchRef() {
        return batchRef;
    }

    public void setBatchRef(String batchRef) {
        this.batchRef = batchRef;
    }

    public Object getPaymentSubType() {
        return paymentSubType;
    }

    public void setPaymentSubType(Object paymentSubType) {
        this.paymentSubType = paymentSubType;
    }
}
