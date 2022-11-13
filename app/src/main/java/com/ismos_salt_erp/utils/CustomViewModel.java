package com.ismos_salt_erp.utils;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;

import es.dmoral.toasty.Toasty;

public class CustomViewModel extends ViewModel {

    public String getToken(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getToken();
    }

    public String getUserId(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getUserId();
    }

    public String getUserName(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getUserName();
    }

    public String getVendorId(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getVendorID());
    }

    public String getProfileTypeId(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getProfileTypeId();
    }
    public String getStoreAccessId(Application application) {
        return  String.valueOf( PreferenceManager.getInstance(application).getUserCredentials().getStoreAccess());
    }

    public String getProfilePhoto(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getProfilePhoto());
    }
    public String getStoreId(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getStoreID());
    }

    public String getProfileName(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getFullName();
    }

    public String getPhone(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getPhone();
    }

    public String getEmail(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getEmail());
    }

    public String getProfileId(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getProfileId();
    }

    public String getSubscriptionEndDate(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getSubscriptionEndDate();
    }


    public void successMessage(Application context, String message) {
        Toasty.success(context, "" + message, Toasty.LENGTH_LONG).show();
    }

    public void errorMessage(Application context, String error) {
        Toasty.error(context, "Something Wrong Contact to Support \n" + error, Toasty.LENGTH_LONG).show();
        Log.d("ERROR", error);
    }

    public void infoMessage(Application context, String message) {
        Toasty.info(context, "" + message, Toasty.LENGTH_LONG).show();
    }

    public void warningMessage(Application context, String message) {
        Toasty.warning(context, "" + message, Toasty.LENGTH_LONG).show();
    }



}
