package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CustomersUtil {
    public static final String addNewCustomer = "Add New Customer";
    public static final String customerList = "Customer List";
    public static final String customerTrashList = "Customer Trash List";

    public static List<String> customerNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.addAll(Arrays.asList(addNewCustomer, customerList, customerTrashList));
        return nameList;
    }

    public static List<Integer> customerImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.add_new_customer);
        imageList.add(R.drawable.customer_list);
        imageList.add(R.drawable.customer_trash_list);
        return imageList;
    }

}
