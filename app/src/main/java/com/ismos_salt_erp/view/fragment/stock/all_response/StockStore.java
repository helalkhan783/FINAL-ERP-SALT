package com.ismos_salt_erp.view.fragment.stock.all_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class StockStore {
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("store_name")
    @Expose
    private String  storeName;
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



}
