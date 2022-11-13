package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GetPreviousMillerInfoResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("profile_info")
    @Expose
    private PreviousMillerProfileInfo profileInfo;
    @SerializedName("owner_info")
    @Expose
    private List<PreviousMillerOwnerInfo> ownerInfo = null;
    @SerializedName("certificate_info")
    @Expose
    private List<PreviousMillerCertificateInfo> certificateInfo = null;
    @SerializedName("qc_info")
    @Expose
    private PreviousMillerQcInfo qcInfo;
    @SerializedName("employee_info")
    @Expose
    private PreviousMillerEmployeeInfo employeeInfo;
}
