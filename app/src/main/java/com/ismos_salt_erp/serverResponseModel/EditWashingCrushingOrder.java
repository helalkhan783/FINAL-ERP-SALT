package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EditWashingCrushingOrder {
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("serialID")
    @Expose
    private String serialID;
    @SerializedName("order_serial")
    @Expose
    private Object orderSerial;
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
    private Object isEcommerce;
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
    private Object referenceUser;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("profile_photo")
    @Expose
    private Object profilePhoto;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public Object getOrderSerial() {
        return orderSerial;
    }

    public void setOrderSerial(Object orderSerial) {
        this.orderSerial = orderSerial;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getEntryUserID() {
        return entryUserID;
    }

    public void setEntryUserID(String entryUserID) {
        this.entryUserID = entryUserID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(String orderMonth) {
        this.orderMonth = orderMonth;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(String orderYear) {
        this.orderYear = orderYear;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Object getIsEcommerce() {
        return isEcommerce;
    }

    public void setIsEcommerce(Object isEcommerce) {
        this.isEcommerce = isEcommerce;
    }

    public String getCarryCost() {
        return carryCost;
    }

    public void setCarryCost(String carryCost) {
        this.carryCost = carryCost;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getIndentNo() {
        return indentNo;
    }

    public void setIndentNo(String indentNo) {
        this.indentNo = indentNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getChallanNumber() {
        return challanNumber;
    }

    public void setChallanNumber(String challanNumber) {
        this.challanNumber = challanNumber;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Object getRequisitionRef() {
        return requisitionRef;
    }

    public void setRequisitionRef(Object requisitionRef) {
        this.requisitionRef = requisitionRef;
    }

    public Object getReferenceUser() {
        return referenceUser;
    }

    public void setReferenceUser(Object referenceUser) {
        this.referenceUser = referenceUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Object profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
