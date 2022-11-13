package com.ismos_salt_erp.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;


import com.ismos_salt_erp.R;

import es.dmoral.toasty.Toasty;
import im.delight.android.webview.AdvancedWebView;

public class LoadUrlActivity extends AppCompatActivity {
    TextView toolBar;
    AdvancedWebView webView;
    ImageButton backbtn;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    ProgressDialog progressBar;


    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_url);
        intit();
        toolBar.setText("Invoice");
        getDataFromPreviousFragment();

        webView.setMixedContentAllowed(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setTitle("web");
        progressDialog.setCanceledOnTouchOutside(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);


        if (!(isInternetOn(this))) {
            Toasty.info(LoadUrlActivity.this, "Please Check Your Internet Connection", Toasty.LENGTH_LONG).show();
        } else {
            webView.loadUrl(url);
            webView.setScaleX(1.3f);
            webView.setDesktopMode(true);
        }
        progressDialog.dismiss();
        backbtn.setOnClickListener(v -> onBackPressed());
    }

    private void getDataFromPreviousFragment() {
        url = getIntent().getStringExtra("QR_URL");
    }

    private void intit() {
        webView = findViewById(R.id.webView);
        toolBar = findViewById(R.id.toolbarTitle);
        backbtn = findViewById(R.id.backbtn);
    }

    /**
     * Check Internet Connection
     */
    public boolean isInternetOn(Context context) {
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

}