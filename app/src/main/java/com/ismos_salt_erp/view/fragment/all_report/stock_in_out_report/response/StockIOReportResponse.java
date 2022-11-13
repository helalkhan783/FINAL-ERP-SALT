package com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.stock_i_o_miller_response.StockIOReportMillerList;

import java.util.List;

import lombok.Data;

@Data
public class StockIOReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<StockIOAssociationList> associationList = null;
    @SerializedName("brand_list")
    @Expose
    private List<StockIOBrandList> brandList = null;
    @SerializedName("item_list")
    @Expose
    private List<StockIOItemList> itemList = null;
    @SerializedName("miller_list")
    @Expose
    private List<StockIOReportMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;

}
