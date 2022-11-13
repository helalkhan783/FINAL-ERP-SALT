package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PermisssionList {

    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("lists")
    @Expose
    private java.util.List<Permisssions> lists = null;
}
