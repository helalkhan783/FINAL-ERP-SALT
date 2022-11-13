package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OtherInfoResponse {
   /* @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("order_file_no")
    @Expose
    private Object orderFileNo;
    @SerializedName("invoice_no")
    @Expose
    private Object invoiceNo;
    @SerializedName("invoice_date")
    @Expose
    private Object invoiceDate;
    @SerializedName("exp_no")
    @Expose
    private Object expNo;
    @SerializedName("exp_date")
    @Expose
    private Object expDate;
    @SerializedName("sc_no")
    @Expose
    private Object scNo;
    @SerializedName("sc_date")
    @Expose
    private Object scDate;
    @SerializedName("processing_date")
    @Expose
    private Object processingDate;
    @SerializedName("requistion_ref")
    @Expose
    private Object requistionRef;
    @SerializedName("courrier_name")
    @Expose
    private String courrierName;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;

*/
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private String orderSerial;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("storeID")
    @Expose
    private String storeID;
    @SerializedName("entry_userID")
    @Expose
    private String entryUserID;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("counterNo")
    @Expose
    private String counterNo;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("return_amount")
    @Expose
    private String returnAmount;
    @SerializedName("order_month")
    @Expose
    private String orderMonth;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("order_year")
    @Expose
    private String orderYear;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("is_confirmed")
    @Expose
    private String isConfirmed;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("is_ecommerce")
    @Expose
    private String isEcommerce;
    @SerializedName("carry_cost")
    @Expose
    private String carryCost;
    @SerializedName("po_no")
    @Expose
    private String poNo;
    @SerializedName("indent_no")
    @Expose
    private String indentNo;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_person_phone")
    @Expose
    private String contactPersonPhone;
    @SerializedName("challan_number")
    @Expose
    private String challanNumber;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("discount_rate")
    @Expose
    private String discountRate;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("requisition_ref")
    @Expose
    private Object requisitionRef;
    @SerializedName("reference_user")
    @Expose
    private String referenceUser;
}
