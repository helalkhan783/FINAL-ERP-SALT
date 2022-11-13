package com.ismos_salt_erp.view.fragment.all_report.packaging_report.packaging_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PackagingReferer {
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("customer_lname")
    @Expose
    private Object customerLname;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("phone")
    @Expose
    private String phone;
}
