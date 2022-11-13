package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Product {
    @SerializedName("productID")
    @Expose
    public String productID;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("product_title")
    @Expose
    public String productTitle;
    @SerializedName("categoryId")
    @Expose
    public String categoryId;
    @SerializedName("pcode")
    @Expose
    public String pcode;
    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("product_dimensions")
    @Expose
    public String productDimensions;
    @SerializedName("is_deleted")
    @Expose
    public String isDeleted;
    @SerializedName("FullName")
    @Expose
    public String fullName;
    @SerializedName("storeID")
    @Expose
    public String storeID;
    @SerializedName("profile_photo")
    @Expose
    public String profilePhoto;
    @SerializedName("categoryID")
    @Expose
    public String categoryID;
    @SerializedName("can_add_initial")
    @Expose
    public Integer canAddInitial;
    @SerializedName("total_varient")
    @Expose
    public String totalVarient;

    public String getTotalVarient() {
        return totalVarient;
    }

    public void setTotalVarient(String totalVarient) {
        this.totalVarient = totalVarient;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getCanAddInitial() {
        return canAddInitial;
    }

    public void setCanAddInitial(Integer canAddInitial) {
        this.canAddInitial = canAddInitial;
    }
}
