package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReturnHistoryResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private  String message;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("enterprize_list")
    @Expose
    private List<Enterprize> enterprizeList = null;
    @SerializedName("company_list")
    @Expose
    private List<Company> companyList = null;
    @SerializedName("lists")
    @Expose
    private List<PurchaseReturnHistoryList> lists = null;

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

    public List<Enterprize> getEnterprizeList() {
        return enterprizeList;
    }

    public void setEnterprizeList(List<Enterprize> enterprizeList) {
        this.enterprizeList = enterprizeList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<PurchaseReturnHistoryList> getLists() {
        return lists;
    }

    public void setLists(List<PurchaseReturnHistoryList> lists) {
        this.lists = lists;
    }
}
