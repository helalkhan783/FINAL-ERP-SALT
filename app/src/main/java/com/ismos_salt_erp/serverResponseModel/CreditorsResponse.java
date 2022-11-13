package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class CreditorsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("lists")
    @Expose
    private List<CreditorsListResponse> lists = null;
}
