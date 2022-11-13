package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;

import java.util.List;

public class PermissionViewModel extends ViewModel {
    MutableLiveData<List<Integer>> accountPermissionList;

    public PermissionViewModel() {
        accountPermissionList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Integer>> getAccountPermission(FragmentActivity context) {
        String permission = PreferenceManager.getInstance(context).getUserPermissions();
        List<Integer> permissionList = PermissionUtil.currentUserPermissionList(permission);
        accountPermissionList.postValue(permissionList);
        return accountPermissionList;
    }
}
