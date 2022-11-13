package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class WashingAndCrushingEditedOrderDetails {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("items")
    @Expose
    private List<EditedItemsResponse> items = null;
    @SerializedName("customer")
    @Expose
    private EditedWashingCrushingCustomerResponse customer;
    @SerializedName("output_item")
    @Expose
    private String outputItem;
}
