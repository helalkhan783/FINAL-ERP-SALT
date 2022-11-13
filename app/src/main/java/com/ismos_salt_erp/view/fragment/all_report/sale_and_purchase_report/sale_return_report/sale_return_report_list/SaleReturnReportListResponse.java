package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.sale_return_report_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SaleReturnReportListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("profuct_list")
    @Expose
    private List<SaleReturnReportProfuctList> profuctList = null;

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

    public List<SaleReturnReportProfuctList> getProfuctList() {
        return profuctList;
    }

    public void setProfuctList(List<SaleReturnReportProfuctList> profuctList) {
        this.profuctList = profuctList;
    }
}
