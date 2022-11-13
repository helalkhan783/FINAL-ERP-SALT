package com.ismos_salt_erp.serverResponseModel.dashboard_repot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class DataList {
    @SerializedName("production")
    @Expose
    private Production production;
    @SerializedName("sales")
    @Expose
    private Sales sales;
    @SerializedName("last_month_iodine")
    @Expose
    private LastMonthIodine lastMonthIodine;

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public LastMonthIodine getLastMonthIodine() {
        return lastMonthIodine;
    }

    public void setLastMonthIodine(LastMonthIodine lastMonthIodine) {
        this.lastMonthIodine = lastMonthIodine;
    }
}
