package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodaysSaleOrPurchaseList {
    @SerializedName("customer_fname")
    @Expose
    public String customerFname;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("grand_total")
    @Expose
    public String grandTotal;
    @SerializedName("total_quantity")
    @Expose
    public String totalQuantity;

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
