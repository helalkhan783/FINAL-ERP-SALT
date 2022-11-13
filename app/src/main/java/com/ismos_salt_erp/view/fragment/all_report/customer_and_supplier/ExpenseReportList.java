package com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseReportList {
    @SerializedName("exp_id")
    @Expose
    public String expId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("vendor")
    @Expose
    public String vendor;
    @SerializedName("particular")
    @Expose
    public String particular;
    @SerializedName("expense_type")
    @Expose
    public String expenseType;
    @SerializedName("total")
    @Expose
    public String total;

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
