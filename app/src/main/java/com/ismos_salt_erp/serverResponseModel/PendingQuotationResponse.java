
package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingQuotationResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("quotation_info")
    @Expose
    private QuotationInfo quotationInfo;
    @SerializedName("quotation_details")
    @Expose
    private List<QuotationDetail> quotationDetails = null;


}
