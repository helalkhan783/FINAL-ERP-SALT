package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PaymentInstruction {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("parties_with_limit")
    @Expose
    private List<PaymentInstructionResponse> paymentInstructionResponseList = null;
    @SerializedName("lists")
    @Expose
    private List<PaymentInstructionListResponse> paymentInstructionListResponseList = null;
    @SerializedName("instructions")
    @Expose
    private List<AddNewLimitInstructionResponse> addNewPaymentInstructionsList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PaymentInstructionResponse> getPaymentInstructionResponseList() {
        return paymentInstructionResponseList;
    }

    public void setPaymentInstructionResponseList(List<PaymentInstructionResponse> paymentInstructionResponseList) {
        this.paymentInstructionResponseList = paymentInstructionResponseList;
    }

    public List<PaymentInstructionListResponse> getPaymentInstructionListResponseList() {
        return paymentInstructionListResponseList;
    }

    public void setPaymentInstructionListResponseList(List<PaymentInstructionListResponse> paymentInstructionListResponseList) {
        this.paymentInstructionListResponseList = paymentInstructionListResponseList;
    }

    public List<AddNewLimitInstructionResponse> getAddNewPaymentInstructionsList() {
        return addNewPaymentInstructionsList;
    }

    public void setAddNewPaymentInstructionsList(List<AddNewLimitInstructionResponse> addNewPaymentInstructionsList) {
        this.addNewPaymentInstructionsList = addNewPaymentInstructionsList;
    }
}
