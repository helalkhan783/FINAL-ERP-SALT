package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDataResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("purchase_qty")
    @Expose
    private String purchaseQty;
    @SerializedName("production_qty")
    @Expose
    private String productionQty;
    @SerializedName("total_sale_qty")
    @Expose
    private String totalSaleQty;
    @SerializedName("iodine_purchase_qty")
    @Expose
    private String iodinePurchaseQty;
    @SerializedName("iodized_sale_qty")
    @Expose
    private String iodizedSaleQty;
    @SerializedName("today_sale_toal_amount")
    @Expose
    private Double todaySaleToalAmount;
    @SerializedName("today_purchase_total_amount")
    @Expose
    private Double todayPurchaseTotalAmount;
    @SerializedName("total_receipt")
    @Expose
    private String totalReceipt;
    @SerializedName("total_payment")
    @Expose
    private String totalPayment;
    @SerializedName("total_expense")
    @Expose
    private String totalExpense;
    @SerializedName("creditors")
    @Expose
    private Double creditors;
    @SerializedName("debitors")
    @Expose
    private Double debitors;

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

    public String getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(String purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public String getProductionQty() {
        return productionQty;
    }

    public void setProductionQty(String productionQty) {
        this.productionQty = productionQty;
    }

    public String getTotalSaleQty() {
        return totalSaleQty;
    }

    public void setTotalSaleQty(String totalSaleQty) {
        this.totalSaleQty = totalSaleQty;
    }

    public String getIodinePurchaseQty() {
        return iodinePurchaseQty;
    }

    public void setIodinePurchaseQty(String iodinePurchaseQty) {
        this.iodinePurchaseQty = iodinePurchaseQty;
    }

    public String getIodizedSaleQty() {
        return iodizedSaleQty;
    }

    public void setIodizedSaleQty(String iodizedSaleQty) {
        this.iodizedSaleQty = iodizedSaleQty;
    }

    public Double getTodaySaleToalAmount() {
        return todaySaleToalAmount;
    }

    public void setTodaySaleToalAmount(Double todaySaleToalAmount) {
        this.todaySaleToalAmount = todaySaleToalAmount;
    }

    public Double getTodayPurchaseTotalAmount() {
        return todayPurchaseTotalAmount;
    }

    public void setTodayPurchaseTotalAmount(Double todayPurchaseTotalAmount) {
        this.todayPurchaseTotalAmount = todayPurchaseTotalAmount;
    }

    public String getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(String totalReceipt) {
        this.totalReceipt = totalReceipt;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getCreditors() {
        return creditors;
    }

    public void setCreditors(Double creditors) {
        this.creditors = creditors;
    }

    public Double getDebitors() {
        return debitors;
    }

    public void setDebitors(Double debitors) {
        this.debitors = debitors;
    }
}
