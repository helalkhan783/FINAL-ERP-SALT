package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ProductionOutputResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("items")
    @Expose
    private List<ProductionOutputList> items = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ProductionOutputList> getItems() {
        return items;
    }

    public void setItems(List<ProductionOutputList> items) {
        this.items = items;
    }
}
