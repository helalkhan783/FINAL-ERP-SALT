package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QcqaReportList {
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("test_name")
    @Expose
    private Object testName;
    @SerializedName("reference")
    @Expose
    private Object reference;
    @SerializedName("parameterValue")
    @Expose
    private String parameterValue;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("association")
    @Expose
    private String association;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("date")
    @Expose
    private String date;

}
