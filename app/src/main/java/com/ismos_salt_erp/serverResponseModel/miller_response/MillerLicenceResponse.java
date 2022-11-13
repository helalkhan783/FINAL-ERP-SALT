package com.ismos_salt_erp.serverResponseModel.miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class MillerLicenceResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("miller_list")
    @Expose
    private List<LicenceMillerList> millerList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LicenceMillerList> getMillerList() {
        return millerList;
    }

    public void setMillerList(List<LicenceMillerList> millerList) {
        this.millerList = millerList;
    }
}
