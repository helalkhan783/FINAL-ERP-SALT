package com.ismos_salt_erp.serverResponseModel.bankresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankAccountListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("lists")
    @Expose
    private List<BankAccountList> lists = null;

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

    public List<BankAccountList> getLists() {
        return lists;
    }

    public void setLists(List<BankAccountList> lists) {
        this.lists = lists;
    }
}
