package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    public static final String addNewUser = "Add New Employee";
    public static final String userList = "Employee List";
    public static final String manageAccesability = "Manage Accessibility";
    public static final String userTrashList = "Employee Trash Lists";




    public static List<String> userNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.add(addNewUser);
        nameList.add(userList);
        nameList.add(userTrashList);
        nameList.add(manageAccesability);

        return nameList;
    }

    public static List<Integer> userImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.add_new_user);
        imageList.add(R.drawable.user_list);
        imageList.add(R.drawable.customer_trash);
        imageList.add(R.drawable.manage_accesability);

        return imageList;
    }
}
