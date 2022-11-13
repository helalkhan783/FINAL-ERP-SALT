package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class ReconciliationUtils {

    public static final String addNewReconciliation = "Add New";
    public static final String reconciliationHistoryList = "History List";
    public static final String pendingReconciliationList = "Pending List";
    public static final String declinedReconciliationList = "Declined List";
    public static final String stockInfo = MtUtils.currentStockInfo;


    public static List<String> getReconciliationItemName() {
        List<String> reconciliationItemName = new ArrayList<>();
        reconciliationItemName.add(addNewReconciliation);
        reconciliationItemName.add(reconciliationHistoryList);
        reconciliationItemName.add(pendingReconciliationList);
        reconciliationItemName.add(declinedReconciliationList);
        reconciliationItemName.add(stockInfo);
        return reconciliationItemName;
    }



    public static List<Integer> getReconciliationItemImage() {
        List<Integer> imageList = new ArrayList<>();

        imageList.add(R.drawable.new_reconciliation);
        imageList.add(R.drawable.reconciliation_history_list);
        imageList.add(R.drawable.pending_reconciliation_list);
        imageList.add(R.drawable.decline_reconciliation);
        imageList.add(R.drawable.stock_input_output_repurt);

        return imageList;
    }
}
