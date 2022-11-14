package com.ismos_salt_erp.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AddStoreDialogLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.serverResponseModel.GetVersionResponse;
import com.ismos_salt_erp.view.fragment.store.list_response.StoreListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Response;

public final class MyApplication {
    private static String enterPriseId;
    private static AlertDialog alertDialog;

    public MyApplication() {
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void exitApp(FragmentActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.app_exit_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(context.getResources().getString(R.string.terminate));//set warning title
        tvMessage.append(context.getResources().getString(R.string.terminate_app_message));//set warning message details text
        imageIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            context.finish();
        });

        alertDialog.show();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public static void addStore(FragmentActivity context, List<Enterprize> enterPriseList, LifecycleOwner l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams")
        AddStoreDialogLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.add_store_dialog_layout, null, false);
        StoreListViewModel storeListViewModel = new ViewModelProvider(context).get(StoreListViewModel.class);

        if (enterPriseList != null) {
            List<String> nameList = new ArrayList<>();
            nameList.clear();
            for (int i = 0; i < enterPriseList.size(); i++) {
                nameList.add("" + enterPriseList.get(i).getStoreName());
            }
            binding.enterprise.setItem(nameList);
        }

        builder.setView(binding.getRoot());

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        binding.btnOk.setOnClickListener(v -> {
            if (binding.fullNameEt.getText().toString().isEmpty()) {
                binding.fullNameEt.setError("Empty");
                binding.fullNameEt.requestFocus();
                return;
            }
            if (binding.shortNameEt.getText().toString().isEmpty()) {
                binding.shortNameEt.setError("Empty");
                binding.shortNameEt.requestFocus();
                return;
            }

            storeListViewModel.addNewStore(context, enterPriseId, binding.fullNameEt.getText().toString(), binding.shortNameEt.getText()
                    .toString(), binding.addressEt.getText().toString()).observe(l, new Observer<DuePaymentResponse>() {
                @Override
                public void onChanged(DuePaymentResponse response) {
                    if (response == null) {
                        Toasty.error(context, "Something Wrong Contact to Support \n", Toasty.LENGTH_LONG).show();
                        return;
                    }
                    if (response.getStatus() == 400) {
                        Toasty.info(context, "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                        return;
                    }
                    Toasty.success(context, "Successful \n", Toasty.LENGTH_LONG).show();
                    try {


                    } catch (Exception e) {
                        Log.d("dfsaa", e.getMessage());
                    }
                }
            });

            alertDialog.dismiss();
            return;

        });
        binding.enterprise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enterPriseId = enterPriseList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.cancel.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();

    }



    @SuppressLint("UseCompatLoadingForDrawables")
    public static void updateAppDialog(FragmentActivity context, Response<GetVersionResponse> response, String packageName, String versionName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.update_dialog, null);
        //Set the view
        builder.setView(view);
        TextView   dialogName, dialogSpeech;
        Button noThanks, update;
        noThanks = view.findViewById(R.id.noThanks);
        update = view.findViewById(R.id.updateApp);
        dialogName = view.findViewById(R.id.dialogName);
        dialogSpeech = view.findViewById(R.id.dialogSpeech);
        ImageView imageIcon = view.findViewById(R.id.img_icon);

        imageIcon.setImageDrawable(ContextCompat.getDrawable(context,  R.drawable.app_sub_logo));//set warning image
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (response.body().getIsMaintanance() == 1) {
            update.setVisibility(View.GONE);
            noThanks.setText("Exit");
            dialogName.setVisibility(View.GONE);
            dialogSpeech.setText(response.body().getMaintananceNote());

        }
        noThanks.setOnClickListener(v -> context.finish());
        update.setOnClickListener(v -> {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        });

        alertDialog.show();
    }

}
