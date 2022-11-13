package com.ismos_salt_erp.utils;


import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public final class SalesRequisitionUtil {
    /**
     * title
     */
    public static final String newSaleTitle = "New Sale Req";
    public static final String salesReqListTitle = "Sales Req. List";
    public static final String pendingReqListTitle = "Pending Req. List";
    public static final String declinedReqListTitle = "Declined Req. List";
    /**
     * icon
     */
    public static Integer newSaleImage = R.drawable.sales_requisitions;
    public static Integer salesReqListImage = R.drawable.ic_lists;
    public static Integer pendingReqListImage = R.drawable.ic_lists;
    public static Integer declinedReqListImage = R.drawable.ic_lists;


    public static List<String> getAllSaleRequisitionTitle() {
        List<String> saleRequisitionTitle = new ArrayList<>();
        saleRequisitionTitle.add(newSaleTitle);
        saleRequisitionTitle.add(salesReqListTitle);
        saleRequisitionTitle.add(pendingReqListTitle);
        saleRequisitionTitle.add(declinedReqListTitle);
        return saleRequisitionTitle;
    }

    public static List<Integer> getAllSaleRequisitionImage() {
        List<Integer> saleRequisitionImage = new ArrayList<>();
        saleRequisitionImage.add(newSaleImage);
        saleRequisitionImage.add(salesReqListImage);
        saleRequisitionImage.add(pendingReqListImage);
        saleRequisitionImage.add(declinedReqListImage);
        return saleRequisitionImage;
    }


}
