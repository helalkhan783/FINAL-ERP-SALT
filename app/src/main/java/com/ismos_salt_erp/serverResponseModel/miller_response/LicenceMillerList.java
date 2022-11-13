package com.ismos_salt_erp.serverResponseModel.miller_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LicenceMillerList {
    @SerializedName("storeID")
    @Expose
    public String storeID;
    @SerializedName("store_name")
    @Expose
    public String storeName;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("store_address")
    @Expose
    public String storeAddress;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("is_warehouse")
    @Expose
    public String isWarehouse;
    @SerializedName("is_vendor_manage")
    @Expose
    public String isVendorManage;
    @SerializedName("contact")
    @Expose
    public String contact;
    @SerializedName("landline")
    @Expose
    public Object landline;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("store_logo")
    @Expose
    public String storeLogo;
    @SerializedName("company_logo")
    @Expose
    public String companyLogo;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("owned_by")
    @Expose
    public String ownedBy;
    @SerializedName("store_no")
    @Expose
    public String storeNo;
    @SerializedName("header_image")
    @Expose
    public String headerImage;
    @SerializedName("show_header")
    @Expose
    public String showHeader;
    @SerializedName("margin_top")
    @Expose
    public Object marginTop;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("currencyID")
    @Expose
    public String currencyID;
    @SerializedName("navbar_image")
    @Expose
    public String navbarImage;
    @SerializedName("iodine_store")
    @Expose
    public String iodineStore;

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getIsWarehouse() {
        return isWarehouse;
    }

    public void setIsWarehouse(String isWarehouse) {
        this.isWarehouse = isWarehouse;
    }

    public String getIsVendorManage() {
        return isVendorManage;
    }

    public void setIsVendorManage(String isVendorManage) {
        this.isVendorManage = isVendorManage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Object getLandline() {
        return landline;
    }

    public void setLandline(Object landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getShowHeader() {
        return showHeader;
    }

    public void setShowHeader(String showHeader) {
        this.showHeader = showHeader;
    }

    public Object getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(Object marginTop) {
        this.marginTop = marginTop;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getNavbarImage() {
        return navbarImage;
    }

    public void setNavbarImage(String navbarImage) {
        this.navbarImage = navbarImage;
    }

    public String getIodineStore() {
        return iodineStore;
    }

    public void setIodineStore(String iodineStore) {
        this.iodineStore = iodineStore;
    }
}
