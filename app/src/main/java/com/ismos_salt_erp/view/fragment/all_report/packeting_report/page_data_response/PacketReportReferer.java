package com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PacketReportReferer {
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
