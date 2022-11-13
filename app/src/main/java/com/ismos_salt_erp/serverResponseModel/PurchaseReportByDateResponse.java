package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReportByDateResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("profuct_list")
    @Expose
    private List<PurchaseReportProductList> profuctList = null;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

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

    public List<PurchaseReportProductList> getProfuctList() {
        return profuctList;
    }

    public void setProfuctList(List<PurchaseReportProductList> profuctList) {
        this.profuctList = profuctList;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
