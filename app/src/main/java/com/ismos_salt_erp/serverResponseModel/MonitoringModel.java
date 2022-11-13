package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MonitoringModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("monitoring_type")
    @Expose
    private List<String> monitoringType = null;
    @SerializedName("lists")
    @Expose
    private List<ListMonitorModel> lists = null;
}
