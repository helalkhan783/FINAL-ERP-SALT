package com.ismos_salt_erp.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.dialog.MyApplication;
import com.ismos_salt_erp.localDatabase.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public abstract class BaseFragment extends Fragment {
    public boolean clickEvent;

    /**
     * For hide KeyBoard
     */
    public void hideKeyboard(FragmentActivity activity) {
        try {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }

    public boolean isValidEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public boolean isValidPhone(String phone) {
        if (!(phone.matches("(^([+]{1}[8]{2}|0088)?(01){1}[2-9]{1}\\d{8})$"))) {
            return false;
        }
        return true;
    }

    /**
     * For Handle conditional backPress
     */
    public void handleBackPressWithDialog(FragmentActivity activity) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                MyApplication.exitApp(getActivity());//for show exit app dialog
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    public static String getCustomCurrentDateTime(String pattern) {//like yyy/MM/dd
        DateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        String currentDateAndTime = df.format(new Date());
        return currentDateAndTime;
    }

    /**
     * Check Internet Connection
     */
    public static boolean isInternetOn(FragmentActivity context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }


    public String getToken(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getToken();
    }

    public String getUserId(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getUserId();
    }

    public String getVendorId(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getVendorID());
    }

    public String getProfileTypeId(Application application) {
        return PreferenceManager.getInstance(application).getUserCredentials().getProfileTypeId();
    }

    public String getProfilePhoto(Application application) {
        return String.valueOf(PreferenceManager.getInstance(application).getUserCredentials().getProfilePhoto());
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


    public boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public void requestStoragePermission(int STORAGE_PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void getLogoImageFromFile(Application application, int PICK_LOGO_PHOTO) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        // startActivityForResult(intent, PICK_LOGO_PHOTO);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_LOGO_PHOTO);
    }


    public boolean checkProfileType() {
        String profileTypeId = getProfileTypeId(getActivity().getApplication());
        String profileId = getProfileId(getActivity().getApplication());
        if (profileTypeId.equals("6") || profileTypeId.equals("7")) {
            return true;
        } else {
            return false;
        }
    }


    public void openFrrdBackActivity() {
        if (!isInternetOn((FragmentActivity) getContext())) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");

            return;
        }
        try {

            String[] recipients = new String[]{"support@usi.net.bd"};// Replace your email id here
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
            emailIntent.setType("text/plain");
            final PackageManager pm = getContext().getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
            if (best != null)
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            startActivity(emailIntent);
        } catch (Exception e) {
        }
    }

    public void share() {
        if (!isInternetOn((FragmentActivity) getContext())) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Studio Pro");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + "com.ismos_salt_erp" + "&hl=en");
            intent.setType("text/plain");
            startActivity(intent);
        } catch (Exception e) {
        }

    }

    public void checkStore(String storeId, FragmentActivity context, String sms) {
        if (storeId == null) {
            this.infoMessage(context.getApplication(), sms);
            return;
        }

    }




}
