package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ConvertedItemInfo {
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("packet_id")
    @Expose
    private String packetId;
}
