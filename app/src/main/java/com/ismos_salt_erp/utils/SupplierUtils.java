package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class SupplierUtils {
    public static final String addLocalSupplier = "Add local Supplier";
    public static final String addForeignSupplier = "Add Foreign Supplier";
    public static final String supplierList = "Suppliers Lists";
    public static final String supplierTrashList = "Trash List";

    public static final Integer newSaleImage = R.drawable.add_local_supplier;
    public static final Integer salePendingImage = R.drawable.add_new_foreign;
    public static final Integer saleHistoryImage = R.drawable.supplier_list;
    public static final Integer supplierTrash = R.drawable.supplier_trash_list;

    public static List<String> supplierNameList() {
        List<String> list = new ArrayList<>();
        list.add(addLocalSupplier);
        list.add(addForeignSupplier);
        list.add(supplierList);
        list.add(supplierTrashList);
        return list;
    }

    public static List<Integer>supplierImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(newSaleImage);
        imageList.add(salePendingImage);
        imageList.add(saleHistoryImage);
        imageList.add(supplierTrash);
        return imageList;
    }

}
