package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class MillerUtils {

    public static final String addnewMiller = "Add New Miller";
    public static final String millreProfileList = "Pending Lists";
    public static final String millerHistoryList = "Approved Profiles";
    public static final String millerDeclineList = "Declined List";



    public MillerUtils() {
    }

    public static List<String> getMillerNameList() {
        List<String> itemName = new ArrayList<>();
        itemName.add(addnewMiller);
        itemName.add(millerDeclineList);
        itemName.add(millerHistoryList);
        itemName.add(millreProfileList);
        return itemName;
    }

    public static List<Integer> getMillerImageList() {
        List<Integer> itemImage = new ArrayList<>();
        itemImage.add(R.drawable.add_new_miller);
        itemImage.add(R.drawable.miller_declined);
        itemImage.add(R.drawable.miller_pending);
        itemImage.add(R.drawable.miller_history);


        return itemImage;
    }

}
