package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionReportList {
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("brandID")
    @Expose
    private String brandID;
    @SerializedName("product_dimensions")
    @Expose
    private String productDimensions;
    @SerializedName("orderID")
    @Expose
    private Object orderID;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("enterprize_name")
    @Expose
    private String enterprizeName;
    @SerializedName("from_store")
    @Expose
    private String fromStore;
    @SerializedName("customer_fname")
    @Expose
    private Object customerFname;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public Object getOrderID() {
        return orderID;
    }

    public void setOrderID(Object orderID) {
        this.orderID = orderID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEnterprizeName() {
        return enterprizeName;
    }

    public void setEnterprizeName(String enterprizeName) {
        this.enterprizeName = enterprizeName;
    }

    public String getFromStore() {
        return fromStore;
    }

    public void setFromStore(String fromStore) {
        this.fromStore = fromStore;
    }

    public Object getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(Object customerFname) {
        this.customerFname = customerFname;
    }
}
