package com.ismos_salt_erp.view.fragment.all_report.employee_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.miller.EmployeeReportMillerList;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeReportPageDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("asociation_list")
    @Expose
    private List<EmployeeReportAsociationList> asociationList = null;
    @SerializedName("certificate_types")
    @Expose
    private List<EmployeeReportCertificateType> certificateTypes = null;
    @SerializedName("miller_list")
    @Expose
    private List<EmployeeReportMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;

}
