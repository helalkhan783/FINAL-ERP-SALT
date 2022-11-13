package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PendingExpenseDetails {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("expense_lists")
    @Expose
    private List<ExpenseListResponse> expenseLists = null;
    @SerializedName("expense_info")
    @Expose
    private ExpenseInfoList expenseInfo;
    @SerializedName("payment_amount")
    @Expose
    private PaymentAmountResponse paymentAmount;
    @SerializedName("processed_by")
    @Expose
    private ProcessedBy processedBy;

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

    public List<ExpenseListResponse> getExpenseLists() {
        return expenseLists;
    }

    public void setExpenseLists(List<ExpenseListResponse> expenseLists) {
        this.expenseLists = expenseLists;
    }

    public ExpenseInfoList getExpenseInfo() {
        return expenseInfo;
    }

    public void setExpenseInfo(ExpenseInfoList expenseInfo) {
        this.expenseInfo = expenseInfo;
    }

    public PaymentAmountResponse getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(PaymentAmountResponse paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public ProcessedBy getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(ProcessedBy processedBy) {
        this.processedBy = processedBy;
    }
}
