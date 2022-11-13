package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class IodizationEditedOrderResponse {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("items")
    @Expose
    private List<IodizationItemRespons> items = null;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("output_item")
    @Expose
    private String outputItem;
}
