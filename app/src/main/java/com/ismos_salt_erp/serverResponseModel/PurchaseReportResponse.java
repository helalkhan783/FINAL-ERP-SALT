package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.PurchaseMillerList;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReportResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<PurchaseAssociationList> associationList = null;
    @SerializedName("category_list")
    @Expose
    private List<ReportPurchaseCategoryList> categoryList = null;
    @SerializedName("brand_list")
    @Expose
    private List<ReportPurchaseBrandList> brandList = null;
    @SerializedName("supplier_list")
    @Expose
    private List<ReportPurchaseSupplierList> supplierList = null;
    @SerializedName("customer_list")
    @Expose
    private List<ReportPurchaseSupplierList> customerList = null;
    @SerializedName("miller_list")
    @Expose
    private List<PurchaseMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;
    @SerializedName("district_list")
    @Expose
    private List<DistrictList> districtList = null;

    public List<DistrictList> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictList> districtList) {
        this.districtList = districtList;
    }

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

    public List<PurchaseAssociationList> getAssociationList() {
        return associationList;
    }

    public void setAssociationList(List<PurchaseAssociationList> associationList) {
        this.associationList = associationList;
    }

    public List<ReportPurchaseCategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ReportPurchaseCategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ReportPurchaseBrandList> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<ReportPurchaseBrandList> brandList) {
        this.brandList = brandList;
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

    public List<PurchaseMillerList> getMillerList() {
        return millerList;
    }

    public void setMillerList(List<PurchaseMillerList> millerList) {
        this.millerList = millerList;
    }

    public String getAssociationID() {
        return associationID;
    }

    public void setAssociationID(String associationID) {
        this.associationID = associationID;
    }
}
