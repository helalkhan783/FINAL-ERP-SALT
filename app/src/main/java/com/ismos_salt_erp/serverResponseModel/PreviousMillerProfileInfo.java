package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PreviousMillerProfileInfo {
    @SerializedName("is_submit")
    @Expose
    private String isSubmit;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("associationID")
    @Expose
    private String associationID;
    @SerializedName("profile_details_id")
    @Expose
    private String profileDetailsId;
    @SerializedName("sl")
    @Expose
    private String sl;
    @SerializedName("ref_sl")
    @Expose
    private String refSl;
    @SerializedName("zoneID")
    @Expose
    private String zoneID;
    @SerializedName("processTypeID")
    @Expose
    private String processTypeID;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("millTypeIDs")
    @Expose
    private List<String> millTypeIDs = null;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("millID")
    @Expose
    private String millID;
    @SerializedName("ownerTypeID")
    @Expose
    private String ownerTypeID;
    @SerializedName("divisionID")
    @Expose
    private String divisionID;
    @SerializedName("districtID")
    @Expose
    private String districtID;
    @SerializedName("upazilaID")
    @Expose
    private String upazilaID;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("remarks")
    @Expose
    private String remarks;
}
