package com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseReportListReponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("lists")
    @Expose
    public List<ExpenseReportList> lists = null;
    @SerializedName("grand_total")
    @Expose
    public Integer grandTotal;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExpenseReportList> getLists() {
        return lists;
    }

    public void setLists(List<ExpenseReportList> lists) {
        this.lists = lists;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }
}
