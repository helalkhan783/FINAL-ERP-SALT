package com.ismos_salt_erp.utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.view.activity.MainActivity;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        try {

            boolean isVisible = MainActivity.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (networkInfo != null && networkInfo.isConnected()) {

                  //  Toast.makeText(context, "Internet was restored", Toast.LENGTH_SHORT).show();
                } else {
                  //  Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
