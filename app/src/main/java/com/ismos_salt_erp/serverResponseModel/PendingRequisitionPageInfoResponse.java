package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingRequisitionPageInfoResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("customer")
    @Expose
    private List<CompanyNameResponse> customer = null;
    @SerializedName("store")
    @Expose
    private List<StoreNameResponse> store = null;
}
