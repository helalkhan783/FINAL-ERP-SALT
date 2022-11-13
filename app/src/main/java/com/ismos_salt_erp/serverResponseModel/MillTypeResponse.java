package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MillTypeResponse {
    @SerializedName("millTypeID")
    @Expose
    private String millTypeID;
    @SerializedName("millTypeName")
    @Expose
    private String millTypeName;
    @SerializedName("remarks")
    @Expose
    private String remarks;
}
