package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Permisssions {
    @SerializedName("permissionID")
    @Expose
    private String permissionID;
    @SerializedName("permission_name")
    @Expose
    private String permissionName;
    @SerializedName("groupID")
    @Expose
    private String groupID;
}
