package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.serverResponseModel.ReconcilationType;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response.ReconciliationMillerList;

import java.util.List;

import lombok.Data;

@Data
public class ReconciliationPageDataResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<ReconciliationReportAssociationList> associationList = null;
    @SerializedName("category_list")
    @Expose
    private List<ReconciliationReportCategoryList> categoryList = null;
    @SerializedName("brand_list")
    @Expose
    private List<ReconciliationReportBrandList> brandList = null;
    @SerializedName("item_list")
    @Expose
    private List<ReconciliationReportItemList> itemList = null;
    @SerializedName("reconcilation_type")
    @Expose
    private ReconcilationType reconcilationType;
    @SerializedName("miller_list")
    @Expose
    private List<ReconciliationMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;

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

    public List<ReconciliationReportAssociationList> getAssociationList() {
        return associationList;
    }

    public void setAssociationList(List<ReconciliationReportAssociationList> associationList) {
        this.associationList = associationList;
    }

    public List<ReconciliationReportCategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ReconciliationReportCategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ReconciliationReportBrandList> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<ReconciliationReportBrandList> brandList) {
        this.brandList = brandList;
    }

    public List<ReconciliationReportItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ReconciliationReportItemList> itemList) {
        this.itemList = itemList;
    }

    public ReconcilationType getReconcilationType() {
        return reconcilationType;
    }

    public void setReconcilationType(ReconcilationType reconcilationType) {
        this.reconcilationType = reconcilationType;
    }

    public List<ReconciliationMillerList> getMillerList() {
        return millerList;
    }

    public void setMillerList(List<ReconciliationMillerList> millerList) {
        this.millerList = millerList;
    }

    public String getAssociationID() {
        return associationID;
    }

    public void setAssociationID(String associationID) {
        this.associationID = associationID;
    }
}
