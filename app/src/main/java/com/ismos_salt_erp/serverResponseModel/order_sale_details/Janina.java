package com.ismos_salt_erp.serverResponseModel.order_sale_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Janina {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("details")
    @Expose
    private  OrderDetails details;
}
