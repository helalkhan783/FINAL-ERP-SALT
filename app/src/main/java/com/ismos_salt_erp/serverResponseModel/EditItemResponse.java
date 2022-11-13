package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class EditItemResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_details")
    @Expose
    private EditIItemResponseObject productDetails;
    @SerializedName("price_details")
    @Expose
    private List<PriceDetail> priceDetails = null;


}
