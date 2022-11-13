package com.ismos_salt_erp.permission;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.fragment.app.FragmentActivity;

public class VersionName {
    public static String getVersionName(FragmentActivity context) {
        String version="1.0.0";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
              version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;}
 }
