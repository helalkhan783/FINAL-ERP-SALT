package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiaItemList {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("transfer_to")
    @Expose
    private String transferTo;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("inventoryID")
    @Expose
    private String inventoryID;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("product_isbn")
    @Expose
    private Object productIsbn;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("product_image")
    @Expose
    private Object productImage;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("enterprise_name")
    @Expose
    private String enterprise_name;

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Object getProductIsbn() {
        return productIsbn;
    }

    public void setProductIsbn(Object productIsbn) {
        this.productIsbn = productIsbn;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public Object getProductImage() {
        return productImage;
    }

    public void setProductImage(Object productImage) {
        this.productImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
