package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class DebitorsAndCreditorsResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("list")
    @Expose
    public List<DevitorsList> list = null;
    @SerializedName("total_credit")
    @Expose
    public String totalCredit;
    @SerializedName("total_debit")
    @Expose
    public String totalDebit;
    @SerializedName("balance_summery")
    @Expose
    public String balanceSummery;

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

    public List<DevitorsList> getList() {
        return list;
    }

    public void setList(List<DevitorsList> list) {
        this.list = list;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getBalanceSummery() {
        return balanceSummery;
    }

    public void setBalanceSummery(String balanceSummery) {
        this.balanceSummery = balanceSummery;
    }
}
