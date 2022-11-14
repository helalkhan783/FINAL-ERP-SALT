package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class NotificationListResponse {
    @SerializedName("ref_orderID")
    @Expose
    private String refOrderID;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("processed_by")
    @Expose
    private ProcessedBy processedBy;
    @SerializedName("approved_date_time")
    @Expose
    private String approvedDateTime;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_approval")
    @Expose
    private String orderApproval;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("psid")
    @Expose
    private String batch;
    @SerializedName("customerID")
    @Expose
    private String customerID;

    public String getRefOrderID() {
        return refOrderID;
    }

    public void setRefOrderID(String refOrderID) {
        this.refOrderID = refOrderID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ProcessedBy getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(ProcessedBy processedBy) {
        this.processedBy = processedBy;
    }

    public String getApprovedDateTime() {
        return approvedDateTime;
    }

    public void setApprovedDateTime(String approvedDateTime) {
        this.approvedDateTime = approvedDateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderApproval() {
        return orderApproval;
    }

    public void setOrderApproval(String orderApproval) {
        this.orderApproval = orderApproval;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
