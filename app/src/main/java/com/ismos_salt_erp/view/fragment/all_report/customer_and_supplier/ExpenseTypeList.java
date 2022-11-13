package com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseTypeList {
    @SerializedName("expense_typeID")
    @Expose
    public String expenseTypeID;
    @SerializedName("expense_category")
    @Expose
    public String expenseCategory;
    @SerializedName("expense_category_status")
    @Expose
    public String expenseCategoryStatus;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("is_parent")
    @Expose
    public String isParent;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("entry_user")
    @Expose
    public String entryUser;

    public String getExpenseTypeID() {
        return expenseTypeID;
    }

    public void setExpenseTypeID(String expenseTypeID) {
        this.expenseTypeID = expenseTypeID;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseCategoryStatus() {
        return expenseCategoryStatus;
    }

    public void setExpenseCategoryStatus(String expenseCategoryStatus) {
        this.expenseCategoryStatus = expenseCategoryStatus;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }
}
