package com.ismos_salt_erp.serverResponseModel.bankresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankAccountDetailsResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("bankinfo")
    @Expose
    public Bankinfo bankinfo;
    @SerializedName("initial")
    @Expose
    public String initial;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    @SerializedName("list")
    @Expose
    public List<AccountList> list = null;

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

    public Bankinfo getBankinfo() {
        return bankinfo;
    }

    public void setBankinfo(Bankinfo bankinfo) {
        this.bankinfo = bankinfo;
    }

    public List<AccountList> getList() {
        return list;
    }

    public void setList(List<AccountList> list) {
        this.list = list;
    }
}
