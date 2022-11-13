package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Miller {
    @SerializedName("sl")
    @Expose
    private String sl;
    @SerializedName("profile_details_id")
    @Expose
    private String profileDetailsId;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("processTypeID")
    @Expose
    private String processTypeID;
    @SerializedName("millTypeID")
    @Expose
    private String millTypeID;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("zoneID")
    @Expose
    private String zoneID;
    @SerializedName("millID")
    @Expose
    private String millID;
    @SerializedName("ownerTypeID")
    @Expose
    private String ownerTypeID;
    @SerializedName("countryID")
    @Expose
    private String countryID;
    @SerializedName("divisionID")
    @Expose
    private String divisionID;
    @SerializedName("districtID")
    @Expose
    private String districtID;
    @SerializedName("upazilaID")
    @Expose
    private String upazilaID;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("entryTime")
    @Expose
    private String entryTime;
    @SerializedName("entryUserID")
    @Expose
    private String entryUserID;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("reviewTime")
    @Expose
    private String reviewTime;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("address_1")
    @Expose
    private Object address1;
    @SerializedName("address_2")
    @Expose
    private Object address2;
    @SerializedName("ref_sl")
    @Expose
    private String refSl;
    @SerializedName("submit_by")
    @Expose
    private Object submitBy;
    @SerializedName("submit_time")
    @Expose
    private Object submitTime;
    @SerializedName("is_submit")
    @Expose
    private String isSubmit;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("associationID")
    @Expose
    private String associationID;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("profile_type_id")
    @Expose
    private String profileTypeId;
    @SerializedName("processTypeName")
    @Expose
    private String processTypeName;
    @SerializedName("millTypeName")
    @Expose
    private String millTypeName;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("upazila_name")
    @Expose
    private String upazilaName;
}
