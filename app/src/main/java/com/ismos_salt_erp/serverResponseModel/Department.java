package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Department {
    @SerializedName("departmentID")
    @Expose
    private String departmentID;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("departmentRemarks")
    @Expose
    private String departmentRemarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("companyID")
    @Expose
    private String companyID;
    @SerializedName("branchID")
    @Expose
    private String branchID;
    @SerializedName("applicationID")
    @Expose
    private String applicationID;

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentRemarks() {
        return departmentRemarks;
    }

    public void setDepartmentRemarks(String departmentRemarks) {
        this.departmentRemarks = departmentRemarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }
}
