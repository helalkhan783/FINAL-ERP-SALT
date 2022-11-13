package com.ismos_salt_erp.serverResponseModel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.serverResponseModel.miller_response.LicenceMillerList;

import java.util.List;

import lombok.Data;

@Data
public class MillerLicenceReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("asociation_list")
    @Expose
    private List<MillerReportAsociationList> asociationList = null;
    @SerializedName("certificate_types")
    @Expose
    private List<MIllerLienceReportCertificateType> certificateTypes = null;
    @SerializedName("miller_list")
    @Expose
    private List<LicenceMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MillerReportAsociationList> getAsociationList() {
        return asociationList;
    }

    public void setAsociationList(List<MillerReportAsociationList> asociationList) {
        this.asociationList = asociationList;
    }

    public List<MIllerLienceReportCertificateType> getCertificateTypes() {
        return certificateTypes;
    }

    public void setCertificateTypes(List<MIllerLienceReportCertificateType> certificateTypes) {
        this.certificateTypes = certificateTypes;
    }

    public List<LicenceMillerList> getMillerList() {
        return millerList;
    }

    public void setMillerList(List<LicenceMillerList> millerList) {
        this.millerList = millerList;
    }

    public String getAssociationID() {
        return associationID;
    }

    public void setAssociationID(String associationID) {
        this.associationID = associationID;
    }
}
