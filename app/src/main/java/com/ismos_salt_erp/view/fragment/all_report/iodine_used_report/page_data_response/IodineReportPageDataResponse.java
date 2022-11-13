package com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public  class IodineReportPageDataResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("asociation_list")
    @Expose
    private List<IodineAsociation> asociationList = null;
    @SerializedName("miller_list")
    @Expose
    private List<IodineMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;




}
