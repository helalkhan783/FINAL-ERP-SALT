package com.ismos_salt_erp.utils;


import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public final class ManagementUtils {
    public static final String addNewItem = "New Item";
    public static final String itemLists = "Item lists";
    public static final String initiaItemLists = "Initial Item lists";
    public static final String itemCategory = "Item Categories";
    public static final String assignItemPacket = "Assign Item Packet";
    public static final String brands = "Brands";
    public static final String trash = "Trash";

    public ManagementUtils() {
    }

    public static List<String> getItemManagementNameList() {
        List<String> itemName = new ArrayList<>();
        itemName.add(addNewItem);
        itemName.add(itemLists);
        itemName.add(initiaItemLists);
        itemName.add(itemCategory);
        itemName.add(assignItemPacket);
        itemName.add(brands);
        itemName.add(trash);
        return itemName;
    }

    public static List<Integer> getItemManagementImageList() {
        List<Integer> itemImage = new ArrayList<>();
        itemImage.add(R.drawable.add_new_item);
        itemImage.add(R.drawable.ic_lists);
        itemImage.add(R.drawable.ic_lists);
        itemImage.add(R.drawable.ic_categories);
        itemImage.add(R.drawable.packating);
        itemImage.add(R.drawable.ic_brand);
        itemImage.add(R.drawable.ic_trash);
        return itemImage;
    }
}
