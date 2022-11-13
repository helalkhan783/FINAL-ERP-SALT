package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VehicleList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("person_name")
    @Expose
    private String personName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("transport_name")
    @Expose
    private String transportName;
    @SerializedName("vehicle_ship_no")
    @Expose
    private String vehicleShipNo;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("sl_id")
    @Expose
    private String slId;
    @SerializedName("updated")
    @Expose
    private String updated;
}
