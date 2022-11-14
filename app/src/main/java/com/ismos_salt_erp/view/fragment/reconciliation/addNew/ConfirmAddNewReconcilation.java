package com.ismos_salt_erp.view.fragment.reconciliation.addNew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.databinding.FragmentConfirmAddNewReconcilationBinding;
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
import java.util.Arrays;
import java.util.List;


public class ConfirmAddNewReconcilation extends AddUpDel {

    private FragmentConfirmAddNewReconcilationBinding binding;


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
    private String selectedEnterpriseId, selectedStoreId;

    /**
     * For Reconciliation type
     */
    private List<String> reconcilationTypeList;
    private String selectedreconcilationType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_add_new_reconcilation, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Add New Reconciliation");


        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        reconcilationViewModel = new ViewModelProvider(this).get(ReconcilationViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        try {
            showSelectedDataToRecyclerView();

        } catch (Exception e) {
        }
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });
        /**
         * Handle Reconciliation Type
         */
        binding.reconciliationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedreconcilationType = reconcilationTypeList.get(position);
                binding.reconciliationType.setEnableErrorLabel(false);
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
            if (selectedreconcilationType == null) {
                infoMessage(getActivity().getApplication(), "Please select reconciliation type");
                return;
            }
            hideKeyboard(getActivity());

            showDialog("Do you want to add this Reconciliation ?");
        });


        return binding.getRoot();
    }


    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(AddNewReconcilation.updatedQuantityProductList);//get static data from (AddNewSale) Fragment
        /**
         * set set selected data to recyclerview
         */
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        binding.selectedProductsRv.setAdapter(adapter);
    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        /**
         * set reconcilationType
         */
        reconcilationTypeList = new ArrayList<>();
        reconcilationTypeList.addAll(Arrays.asList("Damage", "Increase", "Process Lost", "Expire"));
        binding.reconciliationType.setItem(reconcilationTypeList);
    }

    private void submit() {

        List<Integer> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<Double> sellingPriseList = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        for (int i = 0; i < AddNewReconcilation.updatedQuantityProductList.size(); i++) {
            proDuctIdList.add(Integer.parseInt(AddNewReconcilation.updatedQuantityProductList.get(i).getProductID()));
            unitList.add(AddNewReconcilation.updatedQuantityProductList.get(i).getUnit());
            productTitleList.add(AddNewReconcilation.updatedQuantityProductList.get(i).getProductTitle());
            sellingPriseList.add(Double.parseDouble(AddNewReconcilation.updatedQuantityProductList.get(i).getPrice()));
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        reconcilationViewModel.addNewReconcilation(
                getActivity(), selectedEnterpriseId, proDuctIdList, AddNewReconcilation.updatedQuantityList,
                sellingPriseList, unitList, productTitleList, selectedStoreId, selectedreconcilationType,
                binding.note.getText().toString()).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();

            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            if (response.getStatus() == 200) {
                successMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();

                /**
                 * after successfully add new sale to server delete all data from local DB
                 */
                AddNewReconcilation.sharedPreferenceForStore.deleteData();
                myDatabaseHelper.deleteAllData();//delete all data from local database
                isConfirmSubmitData = true;
                return;
            }
            infoMessage(getActivity().getApplication(), "Something Wrong");
        });


    }


    @Override
    public void save() {

            submit();

    }

    @Override
    public void imageUri(Intent uri) {

    }
}