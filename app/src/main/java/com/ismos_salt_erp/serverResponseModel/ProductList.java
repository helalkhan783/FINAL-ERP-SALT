package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {
    @SerializedName("productID")
    @Expose
    public String productID;
    @SerializedName("product_title")
    @Expose
    public String productTitle;
    @SerializedName("pcode")
    @Expose
    public String pcode;
    @SerializedName("product_isbn")
    @Expose
    public Object productIsbn;
    @SerializedName("product_dimensions")
    @Expose
    public String productDimensions;
    @SerializedName("product_image")
    @Expose
    public Object productImage;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("is_deleted")
    @Expose
    public String isDeleted;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("packet_id")
    @Expose
    public String packetId;
    @SerializedName("packet_name")
    @Expose
    public String packetName;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public String getPacketName() {
        return packetName;
    }

    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }
}
