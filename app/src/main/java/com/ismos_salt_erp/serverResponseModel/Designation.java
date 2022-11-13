package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Designation {
    @SerializedName("user_designation_id")
    @Expose
    private String userDesignationId;
    @SerializedName("designation_name")
    @Expose
    private String designationName;
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

    public String getUserDesignationId() {
        return userDesignationId;
    }

    public void setUserDesignationId(String userDesignationId) {
        this.userDesignationId = userDesignationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
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
