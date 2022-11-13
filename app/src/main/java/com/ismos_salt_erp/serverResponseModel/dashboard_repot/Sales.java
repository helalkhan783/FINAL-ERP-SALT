package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sales {
    @SerializedName("industrial")
    @Expose
    private String industrial;
    @SerializedName("iodized")
    @Expose
    private String iodized;

    public String getIndustrial() {
        return industrial;
    }

    public void setIndustrial(String industrial) {
        this.industrial = industrial;
    }

    public String getIodized() {
        return iodized;
    }

    public void setIodized(String iodized) {
        this.iodized = iodized;
    }
}
