package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PackageWeightAndDimensions {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("converted_item_info")
    @Expose
    private ConvertedItemInfo convertedItemInfo;
    @SerializedName("packet_info")
    @Expose
    private PacketInfo packetInfo;
}
