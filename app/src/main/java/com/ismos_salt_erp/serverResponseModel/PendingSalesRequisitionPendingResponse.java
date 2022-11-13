package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingSalesRequisitionPendingResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("details")
    @Expose
    private PendingSalesRequisitionDetails details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PendingSalesRequisitionDetails getDetails() {
        return details;
    }

    public void setDetails(PendingSalesRequisitionDetails details) {
        this.details = details;
    }
}
