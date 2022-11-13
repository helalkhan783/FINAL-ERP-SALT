package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentInstructionReportResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("lists")
    @Expose
    public List<PaymentInstructionReportList> lists = null;

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

    public List<PaymentInstructionReportList> getLists() {
        return lists;
    }

    public void setLists(List<PaymentInstructionReportList> lists) {
        this.lists = lists;
    }
}
