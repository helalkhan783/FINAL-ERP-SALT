package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_status_id")
    @Expose
    private String userStatusId;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("profile_type_id")
    @Expose
    private String profileTypeId;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("vendor_storeID")
    @Expose
    private String vendorStoreID;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profile_photo")
    @Expose
    private Object profilePhoto;
    @SerializedName("store_access")
    @Expose
    private String storeAccess;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("subscription_start_date")
    @Expose
    private String subscriptionStartDate;
    @SerializedName("subscription_end_date")
    @Expose
    private String subscriptionEndDate;
    @SerializedName("subscription_expiry_end_date")
    @Expose
    private String subscriptionExpiryEndDate;
    @SerializedName("account_status")
    @Expose
    private String accountStatus;
    @SerializedName("permissions")
    @Expose
    private String permissions;
    @SerializedName("company_name")
    @Expose
    private String company_name;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(String userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getProfileTypeId() {
        return profileTypeId;
    }

    public void setProfileTypeId(String profileTypeId) {
        this.profileTypeId = profileTypeId;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getVendorStoreID() {
        return vendorStoreID;
    }

    public void setVendorStoreID(String vendorStoreID) {
        this.vendorStoreID = vendorStoreID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Object profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getStoreAccess() {
        return storeAccess;
    }

    public void setStoreAccess(String storeAccess) {
        this.storeAccess = storeAccess;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(String subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public String getSubscriptionExpiryEndDate() {
        return subscriptionExpiryEndDate;
    }

    public void setSubscriptionExpiryEndDate(String subscriptionExpiryEndDate) {
        this.subscriptionExpiryEndDate = subscriptionExpiryEndDate;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
