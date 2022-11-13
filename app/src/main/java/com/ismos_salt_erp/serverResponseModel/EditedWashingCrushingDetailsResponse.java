package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedWashingCrushingDetailsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("current_order")
    @Expose
    private CurrentOrder currentOrder;
    @SerializedName("current_order_details")
    @Expose
    private CurrentOrderDetails currentOrderDetails;
    @SerializedName("edited_order_details")
    @Expose
    private WashingAndCrushingEditedOrderDetails editedOrderDetails;
    @SerializedName("edited_order")
    @Expose
    private EditedOrder editedOrder;
    @SerializedName("edited_customer")
    @Expose
    private EditedCustomer editedCustomer;
    @SerializedName("edited_output_item")
    @Expose
    private String editedOutputItem;
}
