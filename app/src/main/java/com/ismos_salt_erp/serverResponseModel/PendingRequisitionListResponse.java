package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PendingRequisitionListResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("enterprise_name")
    @Expose
    private String enterpriseName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;}
