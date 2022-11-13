package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QcQaDashBoardList {
    @SerializedName("enterprise_name")
    @Expose
    public String enterpriseName;
    @SerializedName("total_qc_percent")
    @Expose
    public String totalQcPercent;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getTotalQcPercent() {
        return totalQcPercent;
    }

    public void setTotalQcPercent(String totalQcPercent) {
        this.totalQcPercent = totalQcPercent;
    }
}
