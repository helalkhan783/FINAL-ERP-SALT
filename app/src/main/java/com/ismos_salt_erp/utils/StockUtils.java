package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class StockUtils {

    public static final String addNewTransferred = "New Transferred";
    public static final String transferredInHistoryList = "Transferred History List (In)";
    public static final String transferredOutHistoryList = "Transferred History List (Out)";
    public static final String pendingTransferredList = "Pending Lists";
    public static final String declineTransferredList = "Declined Lists";
    public static final String stockInfo = MtUtils.currentStockInfo;


    public static List<String> getStockItemName() {
        List<String> stockItemName = new ArrayList<>();
        stockItemName.add(addNewTransferred);

        stockItemName.add(transferredInHistoryList);
        stockItemName.add(transferredOutHistoryList);
        stockItemName.add(pendingTransferredList);
        stockItemName.add(declineTransferredList);
        stockItemName.add(stockInfo);


        return stockItemName;
    }

    public static List<Integer> getStockItemImage() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.stock);
        imageList.add(R.drawable.transfer_history_in);
        imageList.add(R.drawable.transfer_history_out);
        imageList.add(R.drawable.pending_transferred);
        imageList.add(R.drawable.decline_transferred_list);
        imageList.add(R.drawable.stock_input_output_repurt);
        return imageList;
    }
}