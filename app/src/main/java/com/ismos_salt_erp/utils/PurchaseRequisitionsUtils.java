package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRequisitionsUtils {
    /**
     * title
     */
    public static final String newPurchase = "New Purchase Req";
    public static final String purchaseReqListTitle = "Purchase Req. List";
    public static final String pendingReqListTitle = "Pending Req. List";
    public static final String declinedReqListTitle = "Declined Req. List";
    /**
     * icon
     */
    public static Integer newSaleImage = R.drawable.purchase_req;
    public static Integer salesReqListImage = R.drawable.ic_lists;
    public static Integer pendingReqListImage = R.drawable.ic_lists;
    public static Integer declinedReqListImage = R.drawable.ic_lists;


    public static List<String> getAllPurchaseRequisitionTitle() {
        List<String> saleRequisitionTitle = new ArrayList<>();
        saleRequisitionTitle.add(newPurchase);
        saleRequisitionTitle.add(purchaseReqListTitle);
        saleRequisitionTitle.add(pendingReqListTitle);
        saleRequisitionTitle.add(declinedReqListTitle);
        return saleRequisitionTitle;
    }

    public static List<Integer> getAllPurchaseRequisitionImage() {
        List<Integer> saleRequisitionImage = new ArrayList<>();
        saleRequisitionImage.add(newSaleImage);
        saleRequisitionImage.add(salesReqListImage);
        saleRequisitionImage.add(pendingReqListImage);
        saleRequisitionImage.add(declinedReqListImage);
        return saleRequisitionImage;
    }
}
