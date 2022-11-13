package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DesignationList {
    @SerializedName("user_designation_id")
    @Expose
    private String userDesignationId;
    @SerializedName("designation_name")
    @Expose
    private String designationName;
}
