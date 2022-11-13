package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IodizationPenDingResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("enterprize_list")
    @Expose
    private List<Enterprize> enterprizeList = null;
    @SerializedName("item_list")
    @Expose
    private List<ProductionItem> itemList = null;
    @SerializedName("lists")
    @Expose
    private List<IodizatinPendingList> lists = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Enterprize> getEnterprizeList() {
        return enterprizeList;
    }

    public void setEnterprizeList(List<Enterprize> enterprizeList) {
        this.enterprizeList = enterprizeList;
    }

    public List<ProductionItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ProductionItem> itemList) {
        this.itemList = itemList;
    }

    public List<IodizatinPendingList> getLists() {
        return lists;
    }

    public void setLists(List<IodizatinPendingList> lists) {
        this.lists = lists;
    }
}
