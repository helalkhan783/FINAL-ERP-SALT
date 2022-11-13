package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class StockListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("store_list")
    @Expose
    private List<StockStoreList> storeList = null;
    @SerializedName("lists")
    @Expose
    private  List<StockList> lists = null;

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


    public List<StockStoreList> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StockStoreList> storeList) {
        this.storeList = storeList;
    }

    public List<StockList> getLists() {
        return lists;
    }

    public void setLists(List<StockList> lists) {
        this.lists = lists;
    }
}
