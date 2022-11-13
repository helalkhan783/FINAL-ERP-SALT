package com.ismos_salt_erp.navigatehelaper;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;

import es.dmoral.toasty.Toasty;

public class ReportHelper {
    private FragmentActivity contex;
    private View root;
    Bundle bundle = new Bundle();

    public ReportHelper(FragmentActivity contex, View root) {
        this.contex = contex;
        this.root = root;
    }

    public void managementToReportLisFragment(String positionString,String matchString,int permission){
        if (positionString.equals(matchString)) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(contex).getUserCredentials().getPermissions()).contains(permission)) {
                bundle.putString("pageName", matchString);
                bundle.putString("portion", matchString);
                Navigation.findNavController(root).navigate(R.id.action_managementFragment_to_reportListFragment, bundle);
                return;
            } else {
                Toasty.info(contex, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }

        }

    }

    public  void managementReconciliation(String positionString,String matchString,int permission){
        if (positionString.equals(matchString)) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(contex).getUserCredentials().getPermissions()).contains(permission)) {
                bundle.putString("pageName", matchString);
                bundle.putString("portion", matchString);
                Navigation.findNavController(root).navigate(R.id.action_managementFragment_to_reconciliationReportFragment, bundle);
                return;
            } else {
                Toasty.info(contex, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }

        }
    }

    public  void managementToPacketingReport(String positionString,String matchString,int permission){
        if (positionString.equals(matchString)) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(contex).getUserCredentials().getPermissions()).contains(permission)) {
                bundle.putString("pageName", matchString);
                bundle.putString("portion", matchString);
                Navigation.findNavController(root).navigate(R.id.action_managementFragment_to_packetingReportFragment, bundle);
                return;
            } else {
                Toasty.info(contex, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }

        }
    }
}
