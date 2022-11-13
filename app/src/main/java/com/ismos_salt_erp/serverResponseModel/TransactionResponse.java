package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class TransactionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_list")
    @Expose
    private List<UserLists> userList = null;
    @SerializedName("customer_list")
    @Expose
    private  List<TransactionCustomer> customerList = null;
    @SerializedName("list")
    @Expose
    private  List<TransactionInList> list = null;

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

    public List<UserLists> getUserList() {
        return userList;
    }

    public void setUserList(List<UserLists> userList) {
        this.userList = userList;
    }

    public List<TransactionCustomer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<TransactionCustomer> customerList) {
        this.customerList = customerList;
    }

    public List<TransactionInList> getList() {
        return list;
    }

    public void setList(List<TransactionInList> list) {
        this.list = list;
    }
}
