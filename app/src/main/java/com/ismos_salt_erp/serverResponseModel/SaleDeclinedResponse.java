package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SaleDeclinedResponse {
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
    private java.util.List<Enterprize> enterprizeList = null;
    @SerializedName("company_list")
    @Expose
    private java.util.List<Company> companyList = null;
    @SerializedName("lists")
    @Expose
    private List<SaleDeclinedList> lists = null;
}
