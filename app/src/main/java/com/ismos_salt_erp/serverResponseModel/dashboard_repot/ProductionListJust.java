package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductionListJust {
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("industrial_quantity")
    @Expose
    private String industrialQuantity;
    @SerializedName("iodized_quantity")
    @Expose
    private String iodizedQuantity;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIndustrialQuantity() {
        return industrialQuantity;
    }

    public void setIndustrialQuantity(String industrialQuantity) {
        this.industrialQuantity = industrialQuantity;
    }

    public String getIodizedQuantity() {
        return iodizedQuantity;
    }

    public void setIodizedQuantity(String iodizedQuantity) {
        this.iodizedQuantity = iodizedQuantity;
    }
}
