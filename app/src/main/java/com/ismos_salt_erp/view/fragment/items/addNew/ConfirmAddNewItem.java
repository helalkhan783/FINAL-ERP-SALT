package com.ismos_salt_erp.view.fragment.items.addNew;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmAddNewItemAdapter;
import com.ismos_salt_erp.databinding.FragmentConfirmAddNewItemBinding;
import com.ismos_salt_erp.serverResponseModel.StoreList;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ConfirmAddNewItem extends BaseFragment {
    private FragmentConfirmAddNewItemBinding binding;
    private AddNewItemViewModel addNewItemViewModel;
    private List<StoreList> currentItemList;

    private String id, portion,itemName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_add_new_item, container, false);
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        getDataFromPreviousFragment();
        getPageInfoFromServer();


        if (portion != null) {
            binding.toolbar.toolbarTitle.setText(portion);
        } else {
            binding.toolbar.toolbarTitle.setText("Add New Item Initial Stock");
        }


        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * for submit add New item stock data
         */
        binding.setClickHandle(() -> {
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            updateNewItemDialog();
        });


        return binding.getRoot();
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        id = getArguments().getString("id");
        portion = getArguments().getString("portion");
        itemName = getArguments().getString("itemName");

        binding.itemName.setText("Item: "+itemName);
    }

    private void validationAndSave() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Check your Internet Connection");
            return;
        }

        hideKeyboard(getActivity());
        List<String> currentQuantityList = new ArrayList<>();
          // currentQuantityList.addAll(getAllQuantity());

        List<String> storeIdList = new ArrayList<>();
        for (int i = 0; i < currentItemList.size(); i++) {
            try {
                storeIdList.add(currentItemList.get(i).getStoreID());
              String currentQuantity = ((EditText) binding.itemRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantity)).getText().toString();
                if (currentQuantity == null) {
                    currentQuantity = "0";
                }
                currentQuantityList.add(currentQuantity);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }
        /**
         * will save data
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.submitConfirmAddNewItem(getActivity(), id, currentQuantityList, storeIdList)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    new AddNewItem(true);
                    successMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                });

    }

    private void getPageInfoFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.getItemList(getActivity(), id)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    currentItemList = new ArrayList<>();
                    currentItemList.clear();
                    currentItemList.addAll(response.getStoreLists());

                    binding.itemRv.setLayoutManager(new LinearLayoutManager(getContext()));

                    ConfirmAddNewItemAdapter adapter = new ConfirmAddNewItemAdapter(getActivity(), response.getStoreLists());
                    binding.itemRv.setAdapter(adapter);
                   /* CustomArrayAdapter my_adapter = new CustomArrayAdapter(  response.getStoreLists(),getActivity());
                    binding.itemRv.setAdapter(my_adapter);*/

                });



      /*
        addNewItemViewModel.getAddNewPageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    currentItemList = response;
                    binding.itemRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    *//**
         * set store list to recyclerview
         *//*
                    ConfirmAddNewItemAdapter adapter = new ConfirmAddNewItemAdapter(getActivity(), response.getStore());
                    binding.itemRv.setAdapter(adapter);
                });*/
    }


    public List<String> getAllQuantity() {
        List<String> quantityList = new ArrayList<>();
        try {
            for (int i = 0; i < currentItemList.size(); i++) {
                String currentQuantity = ((EditText) binding.itemRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantity)).getText().toString();
                if (currentQuantity == null) {
                    currentQuantity = "0";
                }
                quantityList.add(currentQuantity);
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getLocalizedMessage());
        }
        return quantityList;
    }

    private void updateNewItemDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to add these stock?");//set warning title
        tvMessage.setText("SALT ERP");
       // imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.unicef_main));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            validationAndSave();
        });
        /* if (alertDialog.getWindow() != null) {
         *//**
         * for show sliding animation in alert dialog
         *//*
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }*/
        alertDialog.show();
        /*______________________*/
   /*     AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
      *//*  alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);*//*
        alertDialog.setMessage("Do you want to add ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    validationAndSave();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();*/
    }


}