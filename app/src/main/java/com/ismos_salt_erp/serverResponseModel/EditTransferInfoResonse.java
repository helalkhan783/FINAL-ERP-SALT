package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class EditTransferInfoResonse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("items")
    @Expose
    private List<EditSaleItemsResponse> items = null;
    @SerializedName("transfer_info=")
    @Expose
    private TransferInfo transferInfo;
    @SerializedName("customer_info=")
    @Expose
    private Object customerInfo;
    @SerializedName("transfer_to_store")
    @Expose
    private String transferToStore;
    @SerializedName("transfer_to_enterprise")
    @Expose
    private String transferToEnterprise;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<EditSaleItemsResponse> getItems() {
        return items;
    }

    public void setItems(List<EditSaleItemsResponse> items) {
        this.items = items;
    }

    public TransferInfo getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(TransferInfo transferInfo) {
        this.transferInfo = transferInfo;
    }

    public Object getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Object customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getTransferToStore() {
        return transferToStore;
    }

    public void setTransferToStore(String transferToStore) {
        this.transferToStore = transferToStore;
    }

    public String getTransferToEnterprise() {
        return transferToEnterprise;
    }

    public void setTransferToEnterprise(String transferToEnterprise) {
        this.transferToEnterprise = transferToEnterprise;
    }
}
