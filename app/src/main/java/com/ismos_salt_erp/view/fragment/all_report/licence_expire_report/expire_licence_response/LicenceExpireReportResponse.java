package com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.expire_licence_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.miller.LicenceExpireMillerList;

import java.util.List;

import lombok.Data;

@Data
public class LicenceExpireReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("asociation_list")
    @Expose
    private List<LicenceExpireAsociationList> asociationList = null;
    @SerializedName("certificate_types")
    @Expose
    private List<LicenceExpireCertificateType> certificateTypes = null;
    @SerializedName("miller_list")
    @Expose
    private List<LicenceExpireMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;


}
