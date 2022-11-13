package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SupplierListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("country_list")
    @Expose
    private  List<CountryListResponse> countryList = null;
    @SerializedName("district_list")
    @Expose
    private  List<DistrictListResponse> districtList = null;
    @SerializedName("type_list")
    @Expose
    private  List<TypeList> typeList = null;
    @SerializedName("lists")
    @Expose
    private List<SupplierList> lists = null;


}
