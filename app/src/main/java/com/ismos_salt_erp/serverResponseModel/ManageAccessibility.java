package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ManageAccessibility {
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("user_status_id")
    @Expose
    private String userStatusId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
}
