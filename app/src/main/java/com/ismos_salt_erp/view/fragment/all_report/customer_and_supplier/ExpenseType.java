package com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseType {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("lists")
    @Expose
    public List<ExpenseTypeList> lists = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExpenseTypeList> getLists() {
        return lists;
    }

    public void setLists(List<ExpenseTypeList> lists) {
        this.lists = lists;
    }
}
