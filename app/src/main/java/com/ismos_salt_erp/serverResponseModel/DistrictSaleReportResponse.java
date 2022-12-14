package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictSaleReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sale_list")
    @Expose
    private List<DistrictWiseSaleReport> saleList = null;

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

    public List<DistrictWiseSaleReport> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<DistrictWiseSaleReport> saleList) {
        this.saleList = saleList;
    }
}
