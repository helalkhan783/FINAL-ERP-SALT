package com.ismos_salt_erp.navigatehelaper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.LicenceCheckAdapter;
import com.ismos_salt_erp.serverResponseModel.LicenceExpire;
import com.ismos_salt_erp.view.fragment.SplashScreen;
import com.ismos_salt_erp.view.fragment.auth.LoginFragment;

import java.util.List;
import java.util.Objects;

public class LicenceData {
    private FragmentActivity context;
    private List<LicenceExpire> licenceExpires;

    public LicenceData(FragmentActivity context, List<LicenceExpire> licenceExpires) {
        this.context = context;
        this.licenceExpires = licenceExpires;
    }


    public void showLicenceData() {
        if (!(licenceExpires == null || licenceExpires.isEmpty())) {
            String uniqueId = LoginFragment.getDeviceUniqueID(context);
            if (LoginFragment.showLicencePoup || SplashScreen.showPopupLicence) {
                show();
                LoginFragment.showLicencePoup = false;//
                SplashScreen.showPopupLicence = false;
            }


        }

    }

    private void show() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            @SuppressLint("InflateParams")
            View view = (context).getLayoutInflater().inflate(R.layout.layout_for_licence_check_dialog, null);
            //Set the view
            builder.setView(view);
            RecyclerView recyclerView = view.findViewById(R.id.checkRv);
            ImageButton df = view.findViewById(R.id.btn_neg);
            AlertDialog alertDialog = builder.create();
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            df.setOnClickListener(v -> alertDialog.dismiss());//for cancel
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            LicenceCheckAdapter adapter = new LicenceCheckAdapter(context, licenceExpires);
            recyclerView.setAdapter(adapter);
            alertDialog.show();

        } catch (Exception e) {
        }
    }

}
