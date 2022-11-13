package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("designation_list")
    @Expose
    private  List<Designation> designationList = null;
    @SerializedName("department_list")
    @Expose
    private  List<Department> departmentList = null;
    @SerializedName("lists")
    @Expose
    private List<UserLists> lists = null;
}
