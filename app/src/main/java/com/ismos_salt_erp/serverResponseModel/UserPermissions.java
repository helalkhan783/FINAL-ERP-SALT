package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserPermissions {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprises")
    @Expose
    private List<Enterprise> enterprises = null;
    @SerializedName("permisssion_lists")
    @Expose
    private List<PermisssionList> permisssionLists = null;
    @SerializedName("user_permissions")
    @Expose
    private List<String> userPermissions = null;
    @SerializedName("user_enterprise_access")
    @Expose
    private UserEnterpriseAccess userEnterpriseAccess;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Enterprise> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }

    public List<PermisssionList> getPermisssionLists() {
        return permisssionLists;
    }

    public void setPermisssionLists(List<PermisssionList> permisssionLists) {
        this.permisssionLists = permisssionLists;
    }

    public List<String> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<String> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public UserEnterpriseAccess getUserEnterpriseAccess() {
        return userEnterpriseAccess;
    }

    public void setUserEnterpriseAccess(UserEnterpriseAccess userEnterpriseAccess) {
        this.userEnterpriseAccess = userEnterpriseAccess;
    }
}
