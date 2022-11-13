package com.ismos_salt_erp.permission;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.view.fragment.store.StoreListFragment;

public class HelperClass {
FragmentActivity context;

    public HelperClass(FragmentActivity context) {
        this.context = context;
    }

    public static void navigate(String porson, View root, int destinationPath) {
        Bundle bundle = new Bundle();
        bundle.putString("porson", porson);
        bundle.putString("pageName", MtUtils.currentStockInfo);
        StoreListFragment.manage = 0;
        StoreListFragment.endScroll = true;
        StoreListFragment.pageNumber = 1;
        StoreListFragment.isFirstLoad = 0;
        Navigation.findNavController(root).navigate(destinationPath, bundle);
    }
}
