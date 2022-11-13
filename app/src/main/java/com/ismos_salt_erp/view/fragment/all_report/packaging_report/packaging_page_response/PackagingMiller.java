package com.ismos_salt_erp.view.fragment.all_report.packaging_report.packaging_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PackagingMiller {
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
}
