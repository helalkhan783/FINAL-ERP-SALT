package com.ismos_salt_erp.serverResponseModel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MIllerLienceReportCertificateType {
    @SerializedName("certificateTypeID")
    @Expose
    private String certificateTypeID;
    @SerializedName("certificateProvider")
    @Expose
    private String certificateProvider;
    @SerializedName("certificateTypeName")
    @Expose
    private String certificateTypeName;
    @SerializedName("certificateProviderName")
    @Expose
    private String certificateProviderName;

}
