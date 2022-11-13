package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StoreList {
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("store_address")
    @Expose
    private String storeAddress;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("is_warehouse")
    @Expose
    private String isWarehouse;
    @SerializedName("is_vendor_manage")
    @Expose
    private String isVendorManage;
    @SerializedName("contact")
    @Expose
    private Object contact;
    @SerializedName("landline")
    @Expose
    private Object landline;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("store_logo")
    @Expose
    private Object storeLogo;
    @SerializedName("company_logo")
    @Expose
    private Object companyLogo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("owned_by")
    @Expose
    private String ownedBy;
    @SerializedName("store_no")
    @Expose
    private String storeNo;
    @SerializedName("header_image")
    @Expose
    private Object headerImage;
    @SerializedName("show_header")
    @Expose
    private String showHeader;
    @SerializedName("margin_top")
    @Expose
    private Object marginTop;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("currencyID")
    @Expose
    private Object currencyID;
    @SerializedName("navbar_image")
    @Expose
    private Object navbarImage;
    @SerializedName("iodine_store")
    @Expose
    private String iodineStore;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;

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

    public Object getContact() {
        return contact;
    }

    public void setContact(Object contact) {
        this.contact = contact;
    }

    public Object getLandline() {
        return landline;
    }

    public void setLandline(Object landline) {
        this.landline = landline;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(Object storeLogo) {
        this.storeLogo = storeLogo;
    }

    public Object getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Object companyLogo) {
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

    public Object getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Object headerImage) {
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

    public Object getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Object currencyID) {
        this.currencyID = currencyID;
    }

    public Object getNavbarImage() {
        return navbarImage;
    }

    public void setNavbarImage(Object navbarImage) {
        this.navbarImage = navbarImage;
    }

    public String getIodineStore() {
        return iodineStore;
    }

    public void setIodineStore(String iodineStore) {
        this.iodineStore = iodineStore;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
