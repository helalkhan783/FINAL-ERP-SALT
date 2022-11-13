package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ProcessType {
    @SerializedName("processTypeID")
    @Expose
    private String processTypeID;
    @SerializedName("processTypeName")
    @Expose
    private String processTypeName;



}
