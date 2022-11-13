package com.ismos_salt_erp.localDatabase;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;

/**
 * This class is used to store user preferences
 * SINGLE TONE CLASS
 * ---------------------------------- USER SHARED PREFERENCES -----------------------------------
 * ---------------------------------- LOGIN CREDENTIALS -----------------------------------------
 * ---------------------------------- DIALOG NOTIFICATION ---------------------------------------
 * ---------------------------------- DEVICE TOKEN ----------------------------------------------
 */

public class PreferenceManager {

    private static final String SHARED_PREF_NAME = "GOTISharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    private static final String TAG_USER_RESPONSE = "tag_user_resp";
    private static final String TAG_NOTIFICATION = "tag_notific";
    private static final String TAG_DATE = "tag_date";
    private static final String TAG_COUNTER = "tag_counter";

    private static PreferenceManager mInstance;
    private static Context mCtx;

    private PreferenceManager(Context context) {
        mCtx = context;
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenceManager(context);
        }
        return mInstance;
    }


    public boolean saveUserPassword(String password) {//for save user password
        SharedPreferences preferences = mCtx.getSharedPreferences("USER_PASSWORD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.apply();
        return true;
    }

    public String getUserPassword() {
        SharedPreferences prfs = mCtx.getSharedPreferences("USER_PASSWORD", Context.MODE_PRIVATE);
        String password = prfs.getString("password", "");
        return password;
    }


    public boolean savePermissionList(String permissionList) {
        String permissions = String.valueOf(permissionList);
        SharedPreferences preferences = mCtx.getSharedPreferences("USER_PERMISSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("permission", permissions);
        editor.apply();
        return true;
    }

    public String getUserPermissions() {
        SharedPreferences preferences = mCtx.getSharedPreferences("USER_PERMISSION", Context.MODE_PRIVATE);
        String permission = preferences.getString("permission", "");
        return permission;
    }


    public boolean deleteUserPermission() {
        SharedPreferences preferences = mCtx.getSharedPreferences("USER_PERMISSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("permission");
        editor.apply();
        return true;
    }

    public boolean deletePassword() {
        SharedPreferences prfs = mCtx.getSharedPreferences("USER_PASSWORD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.remove("password");
        editor.apply();
        return true;
    }


    //this method will save the user login response to shared preferences
    public void saveUserCredentials(LoginResponse response) {

        String s = new Gson().toJson(response); // converting object to string

        // save to shared preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_USER_RESPONSE, s);
        editor.apply();
    }

    //this method will fetch the user credential from shared preferences
    public LoginResponse getUserCredentials() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString(TAG_USER_RESPONSE, null);
        return new Gson().fromJson(s, LoginResponse.class);
    }

    public void deleteUserCredentials() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TAG_USER_RESPONSE);
        editor.apply();
    }

/*    *//**
     * Save shown dialog notification
     *
     * @param notification - notification
     *//*
    public void saveDialogNotification(Notification notification) {
        String s = new Gson().toJson(notification);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_NOTIFICATION, s);
        editor.apply();
    }*/

   /* *//**
     * Get shown dialog notification data
     *
     * @return - dialog notification item
     *//*
    public Notification getDialogNotification() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString(TAG_NOTIFICATION, null);
        return new Gson().fromJson(s, Notification.class);
    }
*/
    /**
     * Save the pop up dialog notification shown date
     *
     * @param date - date in string format (yyyy-MM-dd)
     */
    public void saveDialogShownDate(String date) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_DATE, date);
        editor.apply();
    }

    /**
     * Get pop up dialog shown notification date
     *
     * @return - date (yyyy-MM-dd)
     */
    public String getSavedDialogShownDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_DATE, null);
    }

    /**
     * Save counter of the showing dialog popup
     *
     * @param counter - counter
     */
    public void saveDialogCounter(int counter) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TAG_COUNTER, counter);
        editor.apply();
    }

    /**
     * Get dialog counter
     */
    public int getDialogCounter() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TAG_COUNTER, 0);
    }

}