package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserInfoForHome {
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
    private String about;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("BloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("AlternativeEmail")
    @Expose
    private String alternativeEmail;
    @SerializedName("PrimaryMobile")
    @Expose
    private String primaryMobile;
    @SerializedName("OtherContactNumbers")
    @Expose
    private String otherContactNumbers;
    @SerializedName("Website")
    @Expose
    private Object website;
    @SerializedName("profile_photo")
    @Expose
    private Object profilePhoto;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("EntryDate")
    @Expose
    private String entryDate;
    @SerializedName("LastUpdateUserID")
    @Expose
    private String lastUpdateUserID;
    @SerializedName("LastUpdate")
    @Expose
    private String lastUpdate;
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
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getContactCategoryTypeId() {
        return contactCategoryTypeId;
    }

    public void setContactCategoryTypeId(String contactCategoryTypeId) {
        this.contactCategoryTypeId = contactCategoryTypeId;
    }

    public String getProfileTypeId() {
        return profileTypeId;
    }

    public void setProfileTypeId(String profileTypeId) {
        this.profileTypeId = profileTypeId;
    }

    public String getProfileKnownID() {
        return profileKnownID;
    }

    public void setProfileKnownID(String profileKnownID) {
        this.profileKnownID = profileKnownID;
    }

    public Object getAddressId() {
        return addressId;
    }

    public void setAddressId(Object addressId) {
        this.addressId = addressId;
    }

    public String getUserDesignationId() {
        return userDesignationId;
    }

    public void setUserDesignationId(String userDesignationId) {
        this.userDesignationId = userDesignationId;
    }

    public String getUserProfessionId() {
        return userProfessionId;
    }

    public void setUserProfessionId(String userProfessionId) {
        this.userProfessionId = userProfessionId;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternativeEmail() {
        return alternativeEmail;
    }

    public void setAlternativeEmail(String alternativeEmail) {
        this.alternativeEmail = alternativeEmail;
    }

    public String getPrimaryMobile() {
        return primaryMobile;
    }

    public void setPrimaryMobile(String primaryMobile) {
        this.primaryMobile = primaryMobile;
    }

    public String getOtherContactNumbers() {
        return otherContactNumbers;
    }

    public void setOtherContactNumbers(String otherContactNumbers) {
        this.otherContactNumbers = otherContactNumbers;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Object profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getLastUpdateUserID() {
        return lastUpdateUserID;
    }

    public void setLastUpdateUserID(String lastUpdateUserID) {
        this.lastUpdateUserID = lastUpdateUserID;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreAccess() {
        return storeAccess;
    }

    public void setStoreAccess(String storeAccess) {
        this.storeAccess = storeAccess;
    }

    public Object getSlId() {
        return slId;
    }

    public void setSlId(Object slId) {
        this.slId = slId;
    }
}
