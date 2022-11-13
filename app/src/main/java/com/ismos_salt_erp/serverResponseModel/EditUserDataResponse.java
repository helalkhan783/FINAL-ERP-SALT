package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditUserDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user_profile_infos")
    @Expose
    private UserProfileInfos userProfileInfos;
}
