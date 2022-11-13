package com.ismos_salt_erp.view.fragment.store.list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.serverResponseModel.Enterprize;

import java.util.List;

import lombok.Data;

@Data
public class StoreListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprize_list")
    @Expose
    private List<Enterprize> enterprizeList = null;
    @SerializedName("store_lst")
    @Expose
    private List<StoreLst> storeLst = null;

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

    public List<StoreLst> getStoreLst() {
        return storeLst;
    }

    public void setStoreLst(List<StoreLst> storeLst) {
        this.storeLst = storeLst;
    }
}
