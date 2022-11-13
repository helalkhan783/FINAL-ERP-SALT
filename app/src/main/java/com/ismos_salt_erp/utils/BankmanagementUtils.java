package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class BankmanagementUtils {
    public static final String bankAccountlist = "Bank A/C List";




    public static List<String> bankItemNameList() {
        List<String> list = new ArrayList<>();
        list.add(bankAccountlist);

        return list;
    }

    public static List<Integer>bankImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.add_local_supplier);

        return imageList;
    }
}
