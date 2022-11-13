package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerAndSupplierResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("lists")
    @Expose
    public List<CustomerAndSupplierList> lists = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CustomerAndSupplierList> getLists() {
        return lists;
    }

    public void setLists(List<CustomerAndSupplierList> lists) {
        this.lists = lists;
    }
}
