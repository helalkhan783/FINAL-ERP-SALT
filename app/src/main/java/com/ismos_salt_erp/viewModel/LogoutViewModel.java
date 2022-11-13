package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;

/**
 * only for logout
 */
public class LogoutViewModel extends ViewModel {
    MutableLiveData<String> logoutMessage;

    public LogoutViewModel() {
        logoutMessage = new MutableLiveData<>();
    }

    public MutableLiveData<String> logout(FragmentActivity context) {

        PreferenceManager.getInstance(context).deleteUserPermission();
        PreferenceManager.getInstance(context).deleteUserCredentials();

        logoutMessage.postValue("Logout");
        return logoutMessage;
    }
}
