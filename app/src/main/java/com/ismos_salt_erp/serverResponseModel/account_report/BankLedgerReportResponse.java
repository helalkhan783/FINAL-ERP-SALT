package com.ismos_salt_erp.serverResponseModel.account_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankLedgerReportResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("total_deposite_amount_in")
    @Expose
    public Double totalDepositeAmountIn;
    @SerializedName("total_deposite_amount_out")
    @Expose
    public Double totalDepositeAmountOut;
    @SerializedName("lists")
    @Expose
    public List<BankReportList> lists = null;

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

    public Double getTotalDepositeAmountIn() {
        return totalDepositeAmountIn;
    }

    public void setTotalDepositeAmountIn(Double totalDepositeAmountIn) {
        this.totalDepositeAmountIn = totalDepositeAmountIn;
    }

    public Double getTotalDepositeAmountOut() {
        return totalDepositeAmountOut;
    }

    public void setTotalDepositeAmountOut(Double totalDepositeAmountOut) {
        this.totalDepositeAmountOut = totalDepositeAmountOut;
    }

    public List<BankReportList> getLists() {
        return lists;
    }

    public void setLists(List<BankReportList> lists) {
        this.lists = lists;
    }
}
