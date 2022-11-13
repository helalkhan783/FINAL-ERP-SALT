package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class QcHistoryResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprize_list")
    @Expose
    private  List<Enterprize> enterprizeList = null;
    @SerializedName("lists")
    @Expose
    private List<QcHistoryList> lists = null;

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

    public List<Enterprize> getEnterprizeList() {
        return enterprizeList;
    }

    public void setEnterprizeList(List<Enterprize> enterprizeList) {
        this.enterprizeList = enterprizeList;
    }

    public List<QcHistoryList> getLists() {
        return lists;
    }

    public void setLists(List<QcHistoryList> lists) {
        this.lists = lists;
    }
}
