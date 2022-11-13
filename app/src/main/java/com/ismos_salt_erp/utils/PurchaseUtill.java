package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class PurchaseUtill {
    public static final String addNewPurchase = "New Purchase";
    public static final String purchaseHistory = "Purchase History";
    public static final String pendingPurchaseList = "Pending Purchases ";
    public static final String declinePurchaseList = "Declined Purchases ";
    public static final String purchaseReturn = "Purchase Return";
    public static final String pendingPurchaseReturn = "Pending Purchase Return";
    public static final String purchaseReturnHistory = "Purchase Return History";
    public static final String stockInfo = MtUtils.currentStockInfo;


    public static List<String> getPurchaseName() {
        List<String> list = new ArrayList<>();
        list.add(addNewPurchase);
        list.add(purchaseHistory);
        list.add(pendingPurchaseList);
        list.add(declinePurchaseList);
        list.add(purchaseReturn);
        //  list.add(pendingPurchaseReturn);
        list.add(purchaseReturnHistory);
        list.add(stockInfo);
        return list;
    }


    public static List<Integer> getPurchaseImage() {
        List<Integer> image = new ArrayList<>();
        image.add(R.drawable.purchase2);
        image.add(R.drawable.tracking_icon_two);
        image.add(R.drawable.pending_purchase_list);
        image.add(R.drawable.decline_purchase_list);
        image.add(R.drawable.purchase_return);
      //  image.add(R.drawable.pending_sales_return);
        image.add(R.drawable.purchase_return_history);
        image.add(R.drawable.stock_input_output_repurt);
        return image;
    }


}
