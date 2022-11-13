package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditAndDebitVoucherResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("lists")
    @Expose
    public List<DebitAndCreditVoucherList> lists = null;

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

    public List<DebitAndCreditVoucherList> getLists() {
        return lists;
    }

    public void setLists(List<DebitAndCreditVoucherList> lists) {
        this.lists = lists;
    }
}
