package com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.expire_licence_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LicenceExpireCertificateType {

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
