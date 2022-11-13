
package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QuotationInfo {

    @SerializedName("quotationID")
    @Expose
    private String quotationID;
    @SerializedName("quotation_to")
    @Expose
    private String quotationTo;
    @SerializedName("quotation_create_date")
    @Expose
    private String quotationCreateDate;
    @SerializedName("quotation_send_date")
    @Expose
    private Object quotationSendDate;
    @SerializedName("is_send")
    @Expose
    private String isSend;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("approve_decline_by")
    @Expose
    private Object approveDeclineBy;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("sl")
    @Expose
    private Object sl;
    @SerializedName("requisition_added")
    @Expose
    private String requisitionAdded;
    @SerializedName("requisition_ref")
    @Expose
    private String requisitionRef;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("store_name")
    @Expose
    private String storeName;

}
