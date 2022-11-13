package com.ismos_salt_erp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utility.NetworkChangeListener;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper myDatabaseHelper;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    public static boolean isCreate = true;
    public static boolean activityVisible; // Variable that will check the
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
     //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // }
       /*
        *//**
         * create a background Thread For handle token validation
         *//*
        MyThread myTask = new MyThread();
        Timer myTimer = new Timer();
        int time = (1000 * 60) * 58;
        myTimer.schedule(myTask, time, time);//start scheduler

        */


    }

    /**
     * create a task for delete user credentials after a limited time (Sequentially)
     */
    class MyThread extends TimerTask {

        @Override
        public void run() {
            try {
                PreferenceManager.getInstance(MainActivity.this).deleteUserCredentials();
                PreferenceManager.getInstance(MainActivity.this).deleteUserPermission();
                PreferenceManager.getInstance(MainActivity.this).deletePassword();
                PreferenceManager.getInstance(MainActivity.this).deletePassword();
            } catch (Exception e) {
                Log.d("ERROR", e.getLocalizedMessage());
            }
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDatabaseHelper.deleteAllData();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
//        myDatabaseHelper.deleteAllData();

    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
//        myDatabaseHelper.deleteAllData();
    }



    @Override
    protected void onStop() {

      //  unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPaused();// On Pause notify the Application
    }


    @Override
    protected void onResume() {
        super.onResume();
        activityResumed();
    }

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }
    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }
}