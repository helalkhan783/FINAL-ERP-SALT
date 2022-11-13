package com.ismos_salt_erp.view.fragment.all_report.employee_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EmployeeReportCertificateType {
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
