package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.viewModel.Store;

import java.util.List;

import lombok.Data;

@Data
public class CustomerReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("supplier_list")
    @Expose
    private List<ReportPurchaseSupplierList> supplierList = null;
    @SerializedName("customer_list")
    @Expose
    private List<ReportPurchaseSupplierList> customerList = null;
    @SerializedName("store_list")
    @Expose
    private List<Store> storeList = null;
    @SerializedName("vendor_list")
    @Expose
    private List<ReportPurchaseSupplierList> vendorList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReportPurchaseSupplierList> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<ReportPurchaseSupplierList> supplierList) {
        this.supplierList = supplierList;
    }

    public List<ReportPurchaseSupplierList> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<ReportPurchaseSupplierList> customerList) {
        this.customerList = customerList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public List<ReportPurchaseSupplierList> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<ReportPurchaseSupplierList> vendorList) {
        this.vendorList = vendorList;
    }
}
