package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AccountReceiptListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("enterprize_list")
    @Expose
    private List<Enterprize> enterprizeList = null;
    @SerializedName("company_list")
    @Expose
    private List<Company> companyList = null;
    @SerializedName("payment_types")
    @Expose
    private List<PaymentTypes> paymentTypes;
    @SerializedName("lists")
    @Expose
    private  List<ReceiptPaymentHistoryList> lists = null;
    @SerializedName("total")
    @Expose
    private String total;

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

    public List<Enterprize> getEnterprizeList() {
        return enterprizeList;
    }

    public void setEnterprizeList(List<Enterprize> enterprizeList) {
        this.enterprizeList = enterprizeList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<PaymentTypes> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentTypes> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<ReceiptPaymentHistoryList> getLists() {
        return lists;
    }

    public void setLists(List<ReceiptPaymentHistoryList> lists) {
        this.lists = lists;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
