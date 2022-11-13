package com.ismos_salt_erp.serverResponseModel.view_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MIllerViewResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("employee_data")
    @Expose
    private EmployeeData employeeData;
    @SerializedName("qc_data")
    @Expose
    private QcData qcData;
    @SerializedName("certificate_data")
    @Expose
    private List<MillerCertificateDatum> certificateData = null;
    @SerializedName("owner_data")
    @Expose
    private List<MillerOwnerDatum> ownerData = null;
    @SerializedName("profile_data")
    @Expose
    private ProfileData profileData;
    @SerializedName("get_details")
    @Expose
    private GetDetails getDetails;
    @SerializedName("owner_info")
    @Expose
    private List<OwnerInfo> ownerInfo = null;
    @SerializedName("certificate_details")
    @Expose
    private List<CertificateDetail> certificateDetails = null;
    @SerializedName("otherInfoDetails")
    @Expose
    private OtherInfoDetails otherInfoDetails;

}
