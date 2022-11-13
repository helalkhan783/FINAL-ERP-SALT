package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PurchaseReturnSupplierList {
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private String companyName;
}
