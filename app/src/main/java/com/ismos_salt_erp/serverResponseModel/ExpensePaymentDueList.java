package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;


public class ExpensePaymentDueList {
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("paymentSummaryID")
    @Expose
    private String paymentSummaryID;
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
    private Double paidAmount;
    @SerializedName("custom_discount")
    @Expose
    private String customDiscount;
    @SerializedName("payment_remarks")
    @Expose
    private String paymentRemarks;
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
    private String requisitionRef;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("batch_ref")
    @Expose
    private String batchRef;
    @SerializedName("edited_by")
    @Expose
    private Object editedBy;
    @SerializedName("payment_sub_type")
    @Expose
    private String paymentSubType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_serial")
    @Expose
    private Object orderSerial;
    @SerializedName("serialID")
    @Expose
    private Object serialID;
    @SerializedName("particulars")
    @Expose
    private String particulars;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentSummaryID() {
        return paymentSummaryID;
    }

    public void setPaymentSummaryID(String paymentSummaryID) {
        this.paymentSummaryID = paymentSummaryID;
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

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getCustomDiscount() {
        return customDiscount;
    }

    public void setCustomDiscount(String customDiscount) {
        this.customDiscount = customDiscount;
    }

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
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

    public String getRequisitionRef() {
        return requisitionRef;
    }

    public void setRequisitionRef(String requisitionRef) {
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

    public Object getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Object editedBy) {
        this.editedBy = editedBy;
    }

    public String getPaymentSubType() {
        return paymentSubType;
    }

    public void setPaymentSubType(String paymentSubType) {
        this.paymentSubType = paymentSubType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getOrderSerial() {
        return orderSerial;
    }

    public void setOrderSerial(Object orderSerial) {
        this.orderSerial = orderSerial;
    }

    public Object getSerialID() {
        return serialID;
    }

    public void setSerialID(Object serialID) {
        this.serialID = serialID;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
