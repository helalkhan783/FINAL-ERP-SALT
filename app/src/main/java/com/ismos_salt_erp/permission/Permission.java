package com.ismos_salt_erp.permission;

import androidx.fragment.app.FragmentActivity;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;

import es.dmoral.toasty.Toasty;

public  class Permission {
    FragmentActivity context;
    public Permission(FragmentActivity context) {
        this.context = context;
    }
    public boolean checkPermission(Integer permissionId,String profileType){
        if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(permissionId)) {
            return true;
        }
        Toasty.info(context, "You don't  have permission for access this portion", Toasty.LENGTH_LONG).show();
        return false;
    }
}
