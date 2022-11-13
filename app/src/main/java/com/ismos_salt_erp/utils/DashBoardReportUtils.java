package com.ismos_salt_erp.utils;


import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class DashBoardReportUtils {
    public static final String todaysPurchase = "Today's Purchase";
    public static final String todaysSale = "Today's Sale";
    public static final String todaysProduction = "Today's Production ";//
    public static final String todaysIndustrialIodization ="Industrial (%) & Iodization (%) ";//
    public static final String lastMonthQcQa = "Last Month QC/QA (%)";//
    public static final String todaysExpense = "Today's Expense";//
    public static final String currentAbailAbleBalance = "Current Available Balance";
    public static final String todaysSaleBasedOnCustomer = "Today's Sale (Based on Customer)";//
    public static final String todaysPurchaseBasedOnSupplier = "Today's Purchase (Based on Supplier)";//
    public static final String topTenSupplierBasedOnPurchase = "Top Ten Supplier (Based on Purchase)";
    public static final String topTenCustomerBasedOnSale = "Top Ten Customer (Based on Sale)";

    public static List<String> dashBoardReportNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.add(todaysPurchase);
        nameList.add(todaysSale);
        nameList.add(todaysProduction);
        nameList.add(todaysIndustrialIodization);//
        nameList.add(lastMonthQcQa);//
        nameList.add(todaysExpense);//
        nameList.add(currentAbailAbleBalance);//
        nameList.add(todaysSaleBasedOnCustomer);//
        nameList.add(todaysPurchaseBasedOnSupplier);//
        nameList.add(topTenSupplierBasedOnPurchase);//
        nameList.add(topTenCustomerBasedOnSale);//
        return nameList;
    }

    public static List<Integer> dashBoardReeportImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.report_phycal);
        imageList.add(R.drawable.purchase_report);
        imageList.add(R.drawable.purchase_return_report);
        imageList.add(R.drawable.packaging_report);
        imageList.add(R.drawable.packeting_report);
        imageList.add(R.drawable.stock_input_output_repurt);
        imageList.add(R.drawable.sale_history);
        imageList.add(R.drawable.sale_history);//Today's Sale (Based on Customer)
        imageList.add(R.drawable.sale_history);//Today's Purchase (Based on Supplier)
        imageList.add(R.drawable.sale_history);//top Ten Supplier (Based on Purchase)
        imageList.add(R.drawable.sale_history);//Top Ten Customer (Based on Sale)
        imageList.add(R.drawable.sale_history);//topTenCustomerBasedOnSale
        return imageList;
    }
}
