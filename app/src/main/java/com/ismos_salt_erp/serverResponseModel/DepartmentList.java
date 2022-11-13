package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DepartmentList {
    @SerializedName("departmentID")
    @Expose
    private String departmentID;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
}
