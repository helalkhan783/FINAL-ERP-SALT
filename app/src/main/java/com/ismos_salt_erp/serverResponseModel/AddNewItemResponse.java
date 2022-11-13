package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AddNewItemResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("category")
    @Expose
    private List<AddNewItemCategory> category = null;
    @SerializedName("brand")
    @Expose
    private List<AddNewItemBrand> brand = null;
    @SerializedName("unit")
    @Expose
    private List<AddNewItemUnit> unit = null;
    @SerializedName("store")
    @Expose
    private List<AddNewItemStore> store = null;
}
