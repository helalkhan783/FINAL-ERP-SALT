package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GetPreviousSaleOrderDetails {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_end_date")
    @Expose
    private String orderEndDate;
    @SerializedName("order_other_infos")
    @Expose
    private OrderOtherInfos orderOtherInfos;
    @SerializedName("items")
    @Expose
    private List<EditSaleItemsResponse> items = null;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("collected")
    @Expose
    private Integer collected;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
}
