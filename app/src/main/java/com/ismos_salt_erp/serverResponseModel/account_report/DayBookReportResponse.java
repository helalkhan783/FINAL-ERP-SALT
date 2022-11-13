package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayBookReportResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("total_amount")
    @Expose
    public Integer totalAmount;
    @SerializedName("lists")
    @Expose
    public List<DayBookReportList> lists = null;

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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DayBookReportList> getLists() {
        return lists;
    }

    public void setLists(List<DayBookReportList> lists) {
        this.lists = lists;
    }
}
