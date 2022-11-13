package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsUtil {
    public static final String storeList = "Manage Store";
    public static final String setIodine = "Set Iodine";


    public static final Integer newSaleImage = R.drawable.new_sale;
    public static final Integer salePendingImage = R.drawable.sale_pending;

    public static List<String> settingNameList() {
        List<String> list = new ArrayList<>();
        list.add(storeList);
        list.add(setIodine);

        return list;
    }

    public static List<Integer> settingImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(newSaleImage);
        imageList.add(salePendingImage);
        return imageList;
    }
}
