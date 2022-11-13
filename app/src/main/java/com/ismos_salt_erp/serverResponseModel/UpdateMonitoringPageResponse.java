package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UpdateMonitoringPageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("monitoring_details")
    @Expose
    private MonitoringDetails monitoringDetails;
    @SerializedName("zone_list")
    @Expose
    private List<ZoneListResponse> zoneList = null;
    @SerializedName("miller")
    @Expose
    private List<MillerListResponse> miller = null;
}
