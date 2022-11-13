package com.ismos_salt_erp.view.fragment.stock.all_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class StockPendingReconciliationListResponse {
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
    private List<StockPendingReconciliationList> lists = null;


}
