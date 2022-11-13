package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LicenseInfoEdit {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("certificateTypeID")
    @Expose
    private String certificateTypeID;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("issuerName")
    @Expose
    private String issuerName;
    @SerializedName("issueDate")
    @Expose
    private String issueDate;
    @SerializedName("certificateDate")
    @Expose
    private String certificateDate;
    @SerializedName("certificateImage")
    @Expose
    private String certificateImage;
    @SerializedName("renewDate")
    @Expose
    private String renewDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remarks")
    @Expose
    private String remarks;
}
