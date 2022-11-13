package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class HomePageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("slider_lists")
    @Expose
    private List<SliderList> sliderLists = null;
    @SerializedName("raw_salt_buy")
    @Expose
    private String rawSaltBuy;
    @SerializedName("edible_salt_sale")
    @Expose
    private Double edibleSaltSale;
    @SerializedName("industrial_salt_sale")
    @Expose
    private Double industrialSaltSale;
    @SerializedName("total_sale")
    @Expose
    private String totalSale;
    @SerializedName("enterprise_info")
    @Expose
    private EnterpriseInfo enterpriseInfo;
    @SerializedName("store_added")
    @Expose
    private Double storeAdded;
    @SerializedName("store_approved")
    @Expose
    private Double storeApproved;
    @SerializedName("mill_id")
    @Expose
    private String millId;
    @SerializedName("customer_total_paid")
    @Expose
    private Double customerTotalPaid;
    @SerializedName("purchase_toal_amount")
    @Expose
    private Double purchaseToalAmount;
    @SerializedName("purchase_toal_amount_this_month")
    @Expose
    private Double purchaseToalAmountThisMonth;
    @SerializedName("all_supplier_total_paid")
    @Expose
    private Double allSupplierTotalPaid;
    @SerializedName("all_sale_toal_amount")
    @Expose
    private Double allSaleToalAmount;
    @SerializedName("this_month_sale_toal_amount")
    @Expose
    private Double thisMonthSaleToalAmount;
    @SerializedName("total_receipt")
    @Expose
    private String totalReceipt;
    @SerializedName("totapayment")
    @Expose
    private String totapayment;
    @SerializedName("total_expense_paid")
    @Expose
    private String totalExpensePaid;
    @SerializedName("total_expense")
    @Expose
    private String totalExpense;
    @SerializedName("licence_expire")
    @Expose
    private List<LicenceExpire> licenceExpire = null;

    @SerializedName("user_info")
    @Expose
    private UserInfoForHome userInfo;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SliderList> getSliderLists() {
        return sliderLists;
    }

    public void setSliderLists(List<SliderList> sliderLists) {
        this.sliderLists = sliderLists;
    }

    public String getRawSaltBuy() {
        return rawSaltBuy;
    }

    public void setRawSaltBuy(String rawSaltBuy) {
        this.rawSaltBuy = rawSaltBuy;
    }

    public Double getEdibleSaltSale() {
        return edibleSaltSale;
    }

    public void setEdibleSaltSale(Double edibleSaltSale) {
        this.edibleSaltSale = edibleSaltSale;
    }

    public Double getIndustrialSaltSale() {
        return industrialSaltSale;
    }

    public void setIndustrialSaltSale(Double industrialSaltSale) {
        this.industrialSaltSale = industrialSaltSale;
    }

    public String getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(String totalSale) {
        this.totalSale = totalSale;
    }

    public EnterpriseInfo getEnterpriseInfo() {
        return enterpriseInfo;
    }

    public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        this.enterpriseInfo = enterpriseInfo;
    }

    public Double getStoreAdded() {
        return storeAdded;
    }

    public void setStoreAdded(Double storeAdded) {
        this.storeAdded = storeAdded;
    }

    public Double getStoreApproved() {
        return storeApproved;
    }

    public void setStoreApproved(Double storeApproved) {
        this.storeApproved = storeApproved;
    }

    public String getMillId() {
        return millId;
    }

    public void setMillId(String millId) {
        this.millId = millId;
    }

    public Double getCustomerTotalPaid() {
        return customerTotalPaid;
    }

    public void setCustomerTotalPaid(Double customerTotalPaid) {
        this.customerTotalPaid = customerTotalPaid;
    }

    public Double getPurchaseToalAmount() {
        return purchaseToalAmount;
    }

    public void setPurchaseToalAmount(Double purchaseToalAmount) {
        this.purchaseToalAmount = purchaseToalAmount;
    }

    public Double getPurchaseToalAmountThisMonth() {
        return purchaseToalAmountThisMonth;
    }

    public void setPurchaseToalAmountThisMonth(Double purchaseToalAmountThisMonth) {
        this.purchaseToalAmountThisMonth = purchaseToalAmountThisMonth;
    }

    public Double getAllSupplierTotalPaid() {
        return allSupplierTotalPaid;
    }

    public void setAllSupplierTotalPaid(Double allSupplierTotalPaid) {
        this.allSupplierTotalPaid = allSupplierTotalPaid;
    }

    public Double getAllSaleToalAmount() {
        return allSaleToalAmount;
    }

    public void setAllSaleToalAmount(Double allSaleToalAmount) {
        this.allSaleToalAmount = allSaleToalAmount;
    }

    public Double getThisMonthSaleToalAmount() {
        return thisMonthSaleToalAmount;
    }

    public void setThisMonthSaleToalAmount(Double thisMonthSaleToalAmount) {
        this.thisMonthSaleToalAmount = thisMonthSaleToalAmount;
    }

    public String getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(String totalReceipt) {
        this.totalReceipt = totalReceipt;
    }

    public String getTotapayment() {
        return totapayment;
    }

    public void setTotapayment(String totapayment) {
        this.totapayment = totapayment;
    }

    public String getTotalExpensePaid() {
        return totalExpensePaid;
    }

    public void setTotalExpensePaid(String totalExpensePaid) {
        this.totalExpensePaid = totalExpensePaid;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<LicenceExpire> getLicenceExpire() {
        return licenceExpire;
    }

    public void setLicenceExpire(List<LicenceExpire> licenceExpire) {
        this.licenceExpire = licenceExpire;
    }

    public UserInfoForHome getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoForHome userInfo) {
        this.userInfo = userInfo;
    }
}
