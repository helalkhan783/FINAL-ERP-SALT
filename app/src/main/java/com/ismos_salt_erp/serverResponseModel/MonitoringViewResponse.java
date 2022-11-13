package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringDetails;

import java.util.List;

import lombok.Data;

@Data
public class MonitoringViewResponse {
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
    private List<ZoneList> zoneList = null;
    @SerializedName("miller")
    @Expose
    private List<Miller> miller = null;
}
