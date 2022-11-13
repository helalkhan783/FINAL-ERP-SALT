package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MonitoringDetails {
    @SerializedName("slID")
    @Expose
    private String slID;
    @SerializedName("monitor_id")
    @Expose
    private String monitorId;
    @SerializedName("monitoringDate")
    @Expose
    private String monitoringDate;
    @SerializedName("agencyName")
    @Expose
    private String agencyName;
    @SerializedName("zoneID")
    @Expose
    private String zoneID;
    @SerializedName("millID")
    @Expose
    private String millID;
    @SerializedName("monitoringType")
    @Expose
    private String monitoringType;
    @SerializedName("monitorBy")
    @Expose
    private String monitorBy;
    @SerializedName("otherMonitoringTypeName")
    @Expose
    private Object otherMonitoringTypeName;
    @SerializedName("monitoringSummery")
    @Expose
    private String monitoringSummery;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;
    @SerializedName("document")
    @Expose
    private String document;
    @SerializedName("entryDateTime")
    @Expose
    private String entryDateTime;
    @SerializedName("entryuserID")
    @Expose
    private String entryuserID;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("reviewTime")
    @Expose
    private String reviewTime;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("ref_slID")
    @Expose
    private String refSlID;
}
