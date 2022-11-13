package com.ismos_salt_erp.view.fragment.stock.all_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

import lombok.Data;

@Data
public class StockReconciliationHistoryListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("enterprize_list")
    @Expose
    private  List<StockStore> enterprizeList = null;
    @SerializedName("store_list")
    @Expose
    private  List<StockStore> storeList = null;
    @SerializedName("item_list")
    @Expose
    private  List<StockItem> itemList = null;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("lists")
    @Expose
    private List<StockReconciliationHistoryList> lists = null;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<StockStore> getEnterprizeList() {
        return enterprizeList;
    }

    public void setEnterprizeList(List<StockStore> enterprizeList) {
        this.enterprizeList = enterprizeList;
    }

    public List<StockStore> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StockStore> storeList) {
        this.storeList = storeList;
    }

    public List<StockItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<StockItem> itemList) {
        this.itemList = itemList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<StockReconciliationHistoryList> getLists() {
        return lists;
    }

    public void setLists(List<StockReconciliationHistoryList> lists) {
        this.lists = lists;
    }
}
