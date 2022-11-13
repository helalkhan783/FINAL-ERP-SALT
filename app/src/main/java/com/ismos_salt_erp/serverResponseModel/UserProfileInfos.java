package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserProfileInfos {
    @SerializedName("EntryUserID")
    @Expose
    private String entryUserID;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("contact_category_type_id")
    @Expose
    private String contactCategoryTypeId;
    @SerializedName("profile_type_id")
    @Expose
    private String profileTypeId;
    @SerializedName("profile_Known_ID")
    @Expose
    private String profileKnownID;
    @SerializedName("address_id")
    @Expose
    private Object addressId;
    @SerializedName("user_designation_id")
    @Expose
    private String userDesignationId;
    @SerializedName("user_profession_id")
    @Expose
    private String userProfessionId;
    @SerializedName("CompanyID")
    @Expose
    private String companyID;
    @SerializedName("BranchID")
    @Expose
    private String branchID;
    @SerializedName("ApplicationID")
    @Expose
    private String applicationID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("About")
    @Expose
    private Object about;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DateOfBirth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("BloodGroup")
    @Expose
    private Object bloodGroup;
    @SerializedName("Nationality")
    @Expose
    private Object nationality;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("AlternativeEmail")
    @Expose
    private Object alternativeEmail;
    @SerializedName("PrimaryMobile")
    @Expose
    private String primaryMobile;
    @SerializedName("OtherContactNumbers")
    @Expose
    private Object otherContactNumbers;
    @SerializedName("Website")
    @Expose
    private Object website;
    @SerializedName("profile_photo")
    @Expose
    private Object profilePhoto;
    @SerializedName("CreatedDate")
    @Expose
    private Object createdDate;
    @SerializedName("EntryDate")
    @Expose
    private String entryDate;
    @SerializedName("LastUpdateUserID")
    @Expose
    private Object lastUpdateUserID;
    @SerializedName("LastUpdate")
    @Expose
    private Object lastUpdate;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("departmentID")
    @Expose
    private String departmentID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("store_access")
    @Expose
    private String storeAccess;
    @SerializedName("sl_id")
    @Expose
    private Object slId;
}
