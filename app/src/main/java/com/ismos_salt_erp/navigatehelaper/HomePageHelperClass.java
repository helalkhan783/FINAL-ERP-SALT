package com.ismos_salt_erp.navigatehelaper;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.view.fragment.BaseFragment;

import es.dmoral.toasty.Toasty;

public class HomePageHelperClass {
    private FragmentActivity contex;
    private View root;
    Bundle bundle = new Bundle();

    public HomePageHelperClass(FragmentActivity contex, View root) {
        this.contex = contex;
        this.root = root;
        if (!BaseFragment.isInternetOn(contex)) {
            Toasty.info(contex, "" + "Please Check Your Internet Connection", Toasty.LENGTH_SHORT).show();
            return;
        }
    }

    public void navigate(String porson) {
        bundle.putString("Item", porson);
        Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
    }

    public void homePageToAccountListFragment(String amount, String portion, int destinationaPath) {
        bundle.putString("From", "Homepage");
        bundle.putString("Total", amount);
        bundle.putString("portion", portion);
        bundle.putString("PageName", portion);
     try{
         Navigation.findNavController(root).navigate(destinationaPath, bundle);
     }catch (Exception e){}

    }

}
