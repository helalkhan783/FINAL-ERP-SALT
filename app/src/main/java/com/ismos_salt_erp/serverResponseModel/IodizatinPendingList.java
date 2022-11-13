package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IodizatinPendingList {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_vendorID")
    @Expose
    private String orderVendorID;
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("customer_fname")
    @Expose
    private Object customerFname;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("is_confirmed")
    @Expose
    private String isConfirmed;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public String getOrderVendorID() {
        return orderVendorID;
    }

    public void setOrderVendorID(String orderVendorID) {
        this.orderVendorID = orderVendorID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Object getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(Object customerFname) {
        this.customerFname = customerFname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderSerial() {
        return orderSerial;
    }

    public void setOrderSerial(String orderSerial) {
        this.orderSerial = orderSerial;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
