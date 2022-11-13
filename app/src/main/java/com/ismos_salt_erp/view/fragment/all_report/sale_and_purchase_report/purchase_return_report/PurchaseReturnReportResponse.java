package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnReportMillerList;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseReturnReportResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<PurchaseReturnReportAssociationList> associationList = null;
    @SerializedName("category_list")
    @Expose
    private List<PurchaseReturnCategoryList> categoryList = null;
    @SerializedName("brand_list")
    @Expose
    private List<PurchaseReturnReportBrandList> brandList = null;
    @SerializedName("supplier_list")
    @Expose
    private List<PurchaseReturnSupplierList> supplierList = null;
    @SerializedName("miller_list")
    @Expose
    private List<PurchaseReturnReportMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;



}
