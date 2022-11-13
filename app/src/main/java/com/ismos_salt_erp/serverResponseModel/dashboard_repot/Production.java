package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Production {
    @SerializedName("industrial")
    @Expose
    private String industrial;
    @SerializedName("iodized")
    @Expose
    private Integer iodized;

    public String getIndustrial() {
        return industrial;
    }

    public void setIndustrial(String industrial) {
        this.industrial = industrial;
    }

    public Integer getIodized() {
        return iodized;
    }

    public void setIodized(Integer iodized) {
        this.iodized = iodized;
    }
}
