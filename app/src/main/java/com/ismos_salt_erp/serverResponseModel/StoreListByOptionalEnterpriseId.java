package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class StoreListByOptionalEnterpriseId {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("enterprise")
    @Expose
    private List<EnterpriseList> enterprise = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<EnterpriseList> getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(List<EnterpriseList> enterprise) {
        this.enterprise = enterprise;
    }
}
