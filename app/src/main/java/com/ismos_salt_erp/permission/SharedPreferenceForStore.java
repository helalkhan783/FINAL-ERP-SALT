package com.ismos_salt_erp.permission;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class SharedPreferenceForStore {
    FragmentActivity context;


    public SharedPreferenceForStore(FragmentActivity context ) {
        this.context = context;

    }

    public void saveStoreId(String enterpriseId,String storeId) {//for save store Id
        SharedPreferences preferences =context.getSharedPreferences("STORE_ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("enterpriseId", enterpriseId);
        editor.putString("storeId", storeId);
        editor.apply();
    }

    public String getStoreId() {
        SharedPreferences prfs = context.getSharedPreferences("STORE_ID", Context.MODE_PRIVATE);
        String storeId = prfs.getString("storeId", "");
        return storeId;
    }

    public  void  deleteData(){
        SharedPreferences preferences =context.getSharedPreferences("STORE_ID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getEnterpriseId() {
        SharedPreferences prfs = context.getSharedPreferences("STORE_ID", Context.MODE_PRIVATE);
        String enterpriseId = prfs.getString("enterpriseId", "");
        return enterpriseId;
    }


}
