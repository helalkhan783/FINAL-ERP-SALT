package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AddNewUserResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("department_lists")
    @Expose
    private List<DepartmentList> departmentLists = null;
    @SerializedName("designation_lists")
    @Expose
    private List<DesignationList> designationLists = null;
    @SerializedName("blood_groups")
    @Expose
    private List<BloodGroup> bloodGroups = null;
}
