package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MonitoringReportList {

    @SerializedName("monitoring_date")
    @Expose
    private String monitoringDate;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;
    @SerializedName("miller_name")
    @Expose
    private String millerName;
    @SerializedName("type")
    @Expose
    private String type;
}
