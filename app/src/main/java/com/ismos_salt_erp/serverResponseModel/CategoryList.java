package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CategoryList {
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_code")
    @Expose
    private String categoryCode;
    @SerializedName("is_parent")
    @Expose
    private String isParent;
    @SerializedName("entryDateTime")
    @Expose
    private String entryDateTime;
    @SerializedName("status")
    @Expose
    private String status;


}
