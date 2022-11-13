package com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class StockIOReportListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("stock_report")
    @Expose
    private List<StockIOReportList> stockReport = null;

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

    public List<StockIOReportList> getStockReport() {
        return stockReport;
    }

    public void setStockReport(List<StockIOReportList> stockReport) {
        this.stockReport = stockReport;
    }
}
