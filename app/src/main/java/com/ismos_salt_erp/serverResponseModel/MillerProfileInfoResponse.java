package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MillerProfileInfoResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("zones")
    @Expose
    private List<ZoneResponse> zones = null;
    @SerializedName("owner_types")
    @Expose
    private List<OwnerTypeResponse> ownerTypes = null;
    @SerializedName("process_types")
    @Expose
    private List<ProcessTypeResponse> processTypes = null;
    @SerializedName("countries")
    @Expose
    private List<CountryListResponse> countries = null;
    @SerializedName("divisions")
    @Expose
    private List<DivisionResponse> divisions = null;
    @SerializedName("mill_types")
    @Expose
    private List<MillTypeResponse> millTypes = null;
    @SerializedName("count_profile")
    @Expose
    private String countProfile;
    @SerializedName("certificate")
    @Expose
    private List<CertificateResponse> certificate = null;

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

    public List<ZoneResponse> getZones() {
        return zones;
    }

    public void setZones(List<ZoneResponse> zones) {
        this.zones = zones;
    }

    public List<OwnerTypeResponse> getOwnerTypes() {
        return ownerTypes;
    }

    public void setOwnerTypes(List<OwnerTypeResponse> ownerTypes) {
        this.ownerTypes = ownerTypes;
    }

    public List<ProcessTypeResponse> getProcessTypes() {
        return processTypes;
    }

    public void setProcessTypes(List<ProcessTypeResponse> processTypes) {
        this.processTypes = processTypes;
    }

    public List<CountryListResponse> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryListResponse> countries) {
        this.countries = countries;
    }

    public List<DivisionResponse> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionResponse> divisions) {
        this.divisions = divisions;
    }

    public List<MillTypeResponse> getMillTypes() {
        return millTypes;
    }

    public void setMillTypes(List<MillTypeResponse> millTypes) {
        this.millTypes = millTypes;
    }

    public String getCountProfile() {
        return countProfile;
    }

    public void setCountProfile(String countProfile) {
        this.countProfile = countProfile;
    }

    public List<CertificateResponse> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<CertificateResponse> certificate) {
        this.certificate = certificate;
    }
}
