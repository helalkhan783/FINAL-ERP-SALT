package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseOrderDetailList {
    @SerializedName("product_title")
    @Expose
    private String productTitle;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("reconciliation_type")
    @Expose
    private Object reconciliationType;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("vat")
    @Expose
    private Object vat;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("entry_month")
    @Expose
    private String entryMonth;
    @SerializedName("entry_year")
    @Expose
    private String entryYear;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("unitID")
    @Expose
    private String unitID;
    @SerializedName("sales_typeID")
    @Expose
    private String salesTypeID;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("sold_from")
    @Expose
    private String soldFrom;
    @SerializedName("others")
    @Expose
    private Object others;
    @SerializedName("sold_from_store_name")
    @Expose
    private String soldFromStoreName;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Object getReconciliationType() {
        return reconciliationType;
    }

    public void setReconciliationType(Object reconciliationType) {
        this.reconciliationType = reconciliationType;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public Object getVat() {
        return vat;
    }

    public void setVat(Object vat) {
        this.vat = vat;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryMonth() {
        return entryMonth;
    }

    public void setEntryMonth(String entryMonth) {
        this.entryMonth = entryMonth;
    }

    public String getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(String entryYear) {
        this.entryYear = entryYear;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getSalesTypeID() {
        return salesTypeID;
    }

    public void setSalesTypeID(String salesTypeID) {
        this.salesTypeID = salesTypeID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSoldFrom() {
        return soldFrom;
    }

    public void setSoldFrom(String soldFrom) {
        this.soldFrom = soldFrom;
    }

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
    }

    public String getSoldFromStoreName() {
        return soldFromStoreName;
    }

    public void setSoldFromStoreName(String soldFromStoreName) {
        this.soldFromStoreName = soldFromStoreName;
    }
}
