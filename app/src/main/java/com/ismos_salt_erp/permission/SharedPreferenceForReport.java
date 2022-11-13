package com.ismos_salt_erp.permission;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class SharedPreferenceForReport {
    FragmentActivity context;

    public SharedPreferenceForReport(FragmentActivity context) {
        this.context = context;
    }

    public void saveCustomerId(String customerId) {//for save store Id
        SharedPreferences preferences = context.getSharedPreferences("CUSTOMER_ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("customerId", customerId);
        editor.apply();
    }

    public String getCustomerId() {
        SharedPreferences prfs = context.getSharedPreferences("CUSTOMER_ID", Context.MODE_PRIVATE);
        String storeId = prfs.getString("customerId", "");
        return storeId;
    }

    public void deleteCustomerData() {
        SharedPreferences preferences = context.getSharedPreferences("CUSTOMER_ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }



}
