package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class RequisitionListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;



    @SerializedName("lists")
    @Expose
    private List<SalesRequisitionListResponse> lists = null;

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
    public List<SalesRequisitionListResponse> getLists() {
        return lists;
    }

    public void setLists(List<SalesRequisitionListResponse> lists) {
        this.lists = lists;
    }
}
