package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SaleUtil {
    public static final String newSale = "New Sale";
    public static final String salePending = "Pending Sales";
    public static final String saleHistory = "Sales History";
    public static final String declineSaleList = "Declined Sales";
    public static final String saleReturn = "Sales Return";
    public static final String salePendingReturn = "Pending Return";
    public static final String saleReturnHistory = "Return History";
    public static final String stockInfo = MtUtils.currentStockInfo;


    public static final Integer newSaleImage = R.drawable.new_sale;
    public static final Integer salePendingImage = R.drawable.sale_pending;
    public static final Integer saleHistoryImage = R.drawable.sale_history;
    public static final Integer draftSaleImage = R.drawable.draft_sale;
    public static final Integer declineSaleListImage = R.drawable.decline_purchase_list;
    public static final Integer saleReturnImage = R.drawable.sales_return;
    public static final Integer salePendingReturnImage = R.drawable.sale_pending;
    public static final Integer saleReturnHistoryImage = R.drawable.sales_return_history;
    public static final Integer stockImage = R.drawable.stock_input_output_repurt;




    public static List<String> saleNameList() {
        List<String> list = new ArrayList<>();
        list.add(newSale);
        list.add(salePending);
        list.add(saleHistory);
        list.add(declineSaleList);
        list.add(saleReturn);
      //  list.add(salePendingReturn);
        list.add(saleReturnHistory);
        list.add(stockInfo);
        return list;
    }

    public static List<Integer> saleImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(newSaleImage);
        imageList.add(salePendingImage);
        imageList.add(saleHistoryImage);
        imageList.add(draftSaleImage);
        imageList.add(declineSaleListImage);
     //   imageList.add(saleReturnImage);
        imageList.add(salePendingReturnImage);
        imageList.add(stockImage);
        return imageList;
    }
}
