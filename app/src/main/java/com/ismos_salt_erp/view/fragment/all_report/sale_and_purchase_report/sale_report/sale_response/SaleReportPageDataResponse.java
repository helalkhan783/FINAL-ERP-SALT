package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_miller_response.SaleReportMillerList;


import java.util.List;

import lombok.Data;

@Data
public class SaleReportPageDataResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<SaleReportAssociationList> associationList = null;
    @SerializedName("category_list")
    @Expose
    private List<SaleReportCategoryList> categoryList = null;
    @SerializedName("brand_list")
    @Expose
    private List<SaleReportBrandList> brandList = null;
    @SerializedName("supplier_list")
    @Expose
    private List<SaleReportSupplierList> supplierList = null;
    @SerializedName("miller_list")
    @Expose
    private List<SaleReportMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;


}
