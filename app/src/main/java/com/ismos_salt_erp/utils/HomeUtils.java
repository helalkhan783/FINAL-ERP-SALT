package com.ismos_salt_erp.utils;


import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public final class HomeUtils {
    public HomeUtils() {
    }

    public static final String itemManagement = "Items";
    public static final String purchases = "Purchases";
    public static final String production = "Production";
    public static final String sales = "Sale";
    public static final String stock = "Transfer";
    public static final String store = "Store";
    public static final String customers = "Customers";
    public static final String suppliers = "Suppliers";
    public static final String qcQa = "QC/QA";
    public static final String monitoring = "Monitoring";
    //    public static final String expenses = "Expenses";
//    public static final String accounts = "Accounts";
    public static final String user = "Manage User";

    public static final String purchaseRequisition = "Purchase Req.";
    public static final String vendors = "Vendors";
    public static final String reconciliation = "Reconciliation";
    public static final String report = "Report";
    public static final String account = "Accounts";
    public static final String salesReq = "Sales Requisition";
    public static final String purchaseReq = "Purchase Requisition";


    public static final Integer itemManagementImage = R.drawable.items;
    public static final Integer salesImage = R.drawable.sales;
    public static final Integer purchasesImage = R.drawable.purchases;
    public static final Integer salesRequisitionImage = R.drawable.sales_req;
    public static final Integer expensesImage = R.drawable.expenses;
    public static final Integer purchaseRequisitionImage = R.drawable.purchase_req;
    public static final Integer quotationImage = R.drawable.vendors;
    public static final Integer customersImage = R.drawable.customers;
    public static final Integer suppliersImage = R.drawable.suppliers;
    public static final Integer stockImage = R.drawable.stock;
    public static final Integer reportImage = R.drawable.report;
    public static final Integer accountsImage = R.drawable.account;


    public static List<String> getHomeItemName() {
        List<String> itemName = new ArrayList<>();
        itemName.add(itemManagement);
        itemName.add(purchases);
        itemName.add(production);
        itemName.add(sales);
        itemName.add(stock);
//        itemName.add(monitoring);
        itemName.add(customers);
        itemName.add(suppliers);
//        itemName.add(qcQa);
//        itemName.add(expenses);
//        itemName.add(accounts);
        itemName.add(user);
        itemName.add(reconciliation);
        itemName.add(report);
        itemName.add(account);
        /*itemName.add(purchaseRequisition);
        itemName.add(vendors);
        itemName.add(miller);*/
        return itemName;
    }

    public static List<Integer> getHomePageImage() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.items2);
        imageList.add(R.drawable.purchase2);
        imageList.add(R.drawable.production);
        imageList.add(R.drawable.sales2);
        imageList.add(R.drawable.stock);
//        imageList.add(R.drawable.monitoring);
        imageList.add(R.drawable.customers);
        imageList.add(R.drawable.suppliers);
//        imageList.add(R.drawable.qc_qa);
//        imageList.add(R.drawable.expenses);
//        imageList.add(R.drawable.account);
        imageList.add(R.drawable.user);
        imageList.add(R.drawable.reconciliation_report);
        imageList.add(R.drawable.report);
        imageList.add(R.drawable.account);

      /*  imageList.add(R.drawable.purchase_req);
        imageList.add(R.drawable.vendors);
        imageList.add(R.drawable.customers);*/
        return imageList;
    }
}
