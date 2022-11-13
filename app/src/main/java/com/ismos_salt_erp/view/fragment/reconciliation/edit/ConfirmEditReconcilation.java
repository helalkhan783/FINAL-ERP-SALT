package com.ismos_salt_erp.view.fragment.reconciliation.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.databinding.FragmentConfirmEditReconcilationBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SearchTransportList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.ReconcilationViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class ConfirmEditReconcilation extends AddUpDel {

    private FragmentConfirmEditReconcilationBinding binding;

    private ReconcilationViewModel reconcilationViewModel;

    public static boolean isConfirmSubmitData = false;
    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private DiscountViewModel discountViewModel;
    private MyDatabaseHelper myDatabaseHelper;


    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;

    /**
     * For customer search
     */
    private List<CustomerResponse> customerResponseList;
    private List<String> customerNameList;

    /**
     * for transport search
     */
    private List<SearchTransportList> searchTransportLists;
    private List<String> searchTransportNameList;

    private String selectedCustomer, selectedTransport;


    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId, sid, orderSerial, reconciliationType;

    /**
     * For Reconciliation type
     */
    private List<String> reconcilationTypeList;
    private List<String> reconcilationTypeList1;
    private List<String> reconciliationList;
    private String selectedreconcilationType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_edit_reconcilation, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Update Reconciliation");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        reconcilationViewModel = new ViewModelProvider(this).get(ReconcilationViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });
        /**
         * Handle Reconciliation Type
         */

        reconcilationTypeList = new ArrayList<>();
        reconcilationTypeList1 = new ArrayList<>();
        reconcilationTypeList.add("Damage");
        reconcilationTypeList.add("Increase");
        reconcilationTypeList.add("Process Lost");
        reconcilationTypeList.add("Expire");


        for (int i = 0; i < reconcilationTypeList.size(); i++) {
            reconcilationTypeList1.add(reconcilationTypeList.get(i));
            if (reconciliationType != null) {
                if (reconciliationType.equals(reconcilationTypeList1.get(i))) {
                    binding.reconciliationType.setSelection(i);
                }
                /*if (reconciliationType.equals("Increase")) {
                    binding.reconciliationType.setSelection(i);
                }
                if (reconciliationType.equals("Process Lost")) {
                    binding.reconciliationType.setSelection(i);
                }
                if (reconciliationType.equals("Expire")) {
                    binding.reconciliationType.setSelection(i);
                }*/
            }

        }

        binding.reconciliationType.setItem(reconcilationTypeList1);


        binding.reconciliationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedreconcilationType = reconcilationTypeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * For Save
         */
        binding.save.setOnClickListener(v -> {
            hideKeyboard(getActivity());


            if (binding.note.getText().toString().isEmpty()) {
                binding.note.setError("Empty");
                binding.note.requestFocus();
                return;
            }
            if (selectedreconcilationType == null) {
                infoMessage(getActivity().getApplication(), "Please select reconciliation type");
                return;
            }
            showDialog("Do you want to update reconciliation ?");
        });


        return binding.getRoot();
    }


    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(EditReconcilationData.updatedQuantityProductList);//get static data from (AddNewSale) Fragment
        /**
         * set set selected data to recyclerview
         */
        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        sid = getArguments().getString("id");
        orderSerial = getArguments().getString("orderSerial");
        reconciliationType = getArguments().getString("reconciliationType");


    }

    private void submit() {
       /* if (selectedTransport == null) {
            binding.searchVehicle.setError("Empty");
            binding.searchVehicle.requestFocus();
            return;
            //Snackbar.make(getView().findViewById(android.R.id.content), "Please Select Transport", Snackbar.LENGTH_SHORT).show();
        }*/
        /**
         * for set updated productIdList,unitList
         */
        Set<Integer> proDuctIdList = new HashSet<>();
        List<String> unitList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();
        List<String> previousQuantityList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<Double> quantitySet = new ArrayList<>();
        List<Double> sellingPriceList = new ArrayList<>();
        quantitySet.clear();
        sellingPriceList.clear();
        soldFromList.clear();
        previousQuantityList.clear();

        List<Double> buyingPriceSet = new ArrayList<>();
        buyingPriceSet.clear();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        for (int i = 0; i < EditReconcilationData.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(Integer.parseInt(EditReconcilationData.updatedQuantityProductList.get(i).getProductID()));
                unitList.add(EditReconcilationData.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(EditReconcilationData.updatedQuantityProductList.get(i).getProductTitle());
                buyingPriceSet.add(0.0);
                sellingPriceList.add(Double.parseDouble(EditReconcilationData.updatedQuantityProductList.get(i).getPrice()));
                soldFromList.add(EditReconcilationData.previousReconcilationInfoResponse.getItems().get(i).getSoldFrom());
                previousQuantityList.add(EditReconcilationData.previousReconcilationInfoResponse.getItems().get(i).getQuantity());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }
        for (int i = 0; i < EditReconcilationData.updatedQuantityProductList.size(); i++) {
            quantitySet.add(Double.parseDouble(EditReconcilationData.updatedQuantityProductList.get(i).getQuantity()));
        }
        Set<Integer> selectedStoreIdSet = new HashSet<>();

        selectedStoreIdSet.clear();
        selectedStoreIdSet.add(Integer.parseInt(selectedStoreId));
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        reconcilationViewModel.submitReconcilationEditData(
                getActivity(), sid,
                EditReconcilationData.previousReconcilationInfoResponse.getOrderInfo().getOrderID(),
                proDuctIdList, EditReconcilationData.updatedQuantityList, sellingPriceList, productTitleList,
                soldFromList, soldFromList, previousQuantityList, binding.note.getText().toString(),
                selectedreconcilationType
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                Log.d("TYPE", "" + response.getMessage());
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();

//                    *//**
//         * after successfully add new sale to server delete all data from local DB
//         *//*
            try {
                myDatabaseHelper.deleteAllData();//delete all data from local database
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            getActivity().onBackPressed();
        });
    }


    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            submit();
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}