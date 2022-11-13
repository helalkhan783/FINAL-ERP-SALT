
package com.ismos_salt_erp.serverResponseModel;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditedPurchaseOrderResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("current_order")
    @Expose
    private CurrentOrder currentOrder;
    @SerializedName("current_order_details")
    @Expose
    private CurrentOrderDetails currentOrderDetails;
    @SerializedName("current_payment_info")
    @Expose
    private CurrentPaymentInfo currentPaymentInfo;
    @SerializedName("edited_order_details")
    @Expose
    private List<EditedOrderDetail> editedOrderDetails = null;
    @SerializedName("edited_order")
    @Expose
    private EditedOrder editedOrder;
    @SerializedName("edited_customer")
    @Expose
    private EditedCustomer editedCustomer;
    @SerializedName("edited_payment_info")
    @Expose
    private EditedPaymentInfo editedPaymentInfo;
    @SerializedName("edited_discount")
    @Expose
    private Integer editedDiscount;

}
