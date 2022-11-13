package com.ismos_salt_erp.serverResponseModel.account_report_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceiptReportResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("lists")
    @Expose
    public List<ReceiptList> lists = null;
    @SerializedName("grand_total")
    @Expose
    public String grandTotal;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReceiptList> getLists() {
        return lists;
    }

    public void setLists(List<ReceiptList> lists) {
        this.lists = lists;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
