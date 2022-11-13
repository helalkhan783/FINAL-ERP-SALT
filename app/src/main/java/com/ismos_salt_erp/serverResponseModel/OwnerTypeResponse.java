package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OwnerTypeResponse {
    @SerializedName("ownerTypeID")
    @Expose
    private String ownerTypeID;
    @SerializedName("ownerTypeName")
    @Expose
    private String ownerTypeName;
}
