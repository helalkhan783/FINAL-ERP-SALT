package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.miller_response.SaleReturnReportMillerList;

import java.util.List;

import lombok.Data;

@Data
public class SaleReturnReportResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("association_list")
    @Expose
    private List<SaleReturnReportAssociationList> associationList = null;
    @SerializedName("category_list")
    @Expose
    private List<SaleReturnReportCategoryList> categoryList = null;
    @SerializedName("brand_list")
    @Expose
    private List<SaleReturnReportBrandList> brandList = null;
    @SerializedName("supplier_list")
    @Expose
    private List<SaleReturnReportSupplierList> supplierList = null;
    @SerializedName("miller_list")
    @Expose
    private List<SaleReturnReportMillerList> millerList = null;
    @SerializedName("associationID")
    @Expose
    private String associationID;


}
