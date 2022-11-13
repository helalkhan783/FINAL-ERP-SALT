package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;
import java.util.ArrayList;
import java.util.List;

public class

AccountReportUtils {
    public static final String customerLedgerReport = "Customer Ledger";
    public static final String supplierLedgerReport = "Supplier Ledger";//
    public static final String vendorLedgerReport = "Vendor Ledger";//
    public static final String bankLedger = "Bank Ledger";//
    public static final String receipt = "Receipt";//
    public static final String paymentInstructions = "Payment Instructions";//
    public static final String payment = "Payment";//
    public static final String expense = "Expense";//
    public static final String transactionIn = "Transection In";//
    public static final String transactionOut = "Transection Out";//
    public static final String creditors = "Creditors";//
    public static final String debitors = "Debitors";//
    public static final String dayBook = "Day Book";//
    public static final String cashBook = "Cash Book";//

    public static List<String> accountReportNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.add(customerLedgerReport);
        nameList.add(supplierLedgerReport);
        nameList.add(vendorLedgerReport);
        nameList.add(bankLedger);//
        nameList.add(receipt);//
        nameList.add(paymentInstructions);//
        nameList.add(payment);//
        nameList.add(expense);//
        nameList.add(transactionIn);//
        nameList.add(transactionOut);//
        nameList.add(creditors);//
        nameList.add(debitors);//
        nameList.add(cashBook);//
        nameList.add(dayBook);//
        return nameList;
    }

    public static List<Integer> accountReeportImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.custome2);
        imageList.add(R.drawable.supplier2);
        imageList.add(R.drawable.vendors);
        imageList.add(R.drawable.packaging_report);
        imageList.add(R.drawable.receipt);
        imageList.add(R.drawable.instruction_list);
        imageList.add(R.drawable.payment);
        imageList.add(R.drawable.expenses);
        imageList.add(R.drawable.transfer_history_in);
        imageList.add(R.drawable.transaction_out);
        imageList.add(R.drawable.creditors);
        imageList.add(R.drawable.debitors);
        imageList.add(R.drawable.ic_cashbook);
        imageList.add(R.drawable.ic_daybook);
        return imageList;
    }
}
