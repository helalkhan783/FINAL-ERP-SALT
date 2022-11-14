package com.ismos_salt_erp.view.fragment.transfer.edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.databinding.FragmentConfirmEditTransferBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.AddNewTransferViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConfirmEditTransfer extends AddUpDel {

    private FragmentConfirmEditTransferBinding binding;

    private SaleViewModel saleViewModel;
    private AddNewTransferViewModel addNewTransferViewModel;
    private SalesRequisitionViewModel salesRequisitionViewModel;

    private MyDatabaseHelper myDatabaseHelper;

    /**
     * For enterprise
     */
    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;

    /**
     * for store
     */
    List<EnterpriseList> storeResponseList;
    List<String> storeNameList;

    /**
     * Store previous fragment Data
     */
    private String previousEnterPrise, previousStore;
    private String currentSelectedEnterpriseId = null, currentStoreId = null;
    private String orderSerial, orderId, type;//from previous fragment


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_edit_transfer, container, false);


        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        addNewTransferViewModel = new ViewModelProvider(this).get(AddNewTransferViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        binding.toolbar.toolbarTitle.setText("Confirm Update Transfer");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();
        /**
         * now get current enterprise and store list from server
         */
        getEnterPriseListFromServer();
        //nowSetStoreListByEnterPriseId();

        binding.enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedEnterpriseId = enterpriseResponseList.get(position).getStoreID();
                nowSetStoreListByEnterPriseId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentStoreId = storeResponseList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * for submit
         */
        binding.save.setOnClickListener(v -> {

            if (currentSelectedEnterpriseId == null) {
                message(getString(R.string.enterprise_info));
                return;
            }
            if (currentStoreId == null) {
                message("Please select store");
                return;
            }
            if (binding.note.getText().toString().isEmpty()) {
                binding.note.setError("Please write some notes");
                binding.note.requestFocus();
                return;
            }
            hideKeyboard(getActivity());

            showDialog("Do you want to updated this transfer ?");
        });


        return binding.getRoot();
    }

    private void submit() {

        Set<Integer> proDuctIdList = new HashSet<>();
        List<String> unitList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();
        List<String> oldSoldFromList = new ArrayList<>();
        List<String> previousQuantityList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<Double> quantitySet = new ArrayList<>();
        List<Double> sellingPricelist = new ArrayList<>();
        quantitySet.clear();
        sellingPricelist.clear();
        soldFromList.clear();
        oldSoldFromList.clear();
        previousQuantityList.clear();

        List<Double> buyingPriceSet = new ArrayList<>();
        buyingPriceSet.clear();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();


        for (int i = 0; i < EditTransferData.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(Integer.parseInt(EditTransferData.updatedQuantityProductList.get(i).getProductID()));
                unitList.add(EditTransferData.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(EditTransferData.updatedQuantityProductList.get(i).getProductTitle());
                buyingPriceSet.add(Double.parseDouble(EditTransferData.updatedQuantityProductList.get(i).getPrice()));
                sellingPricelist.add(0.0);
                oldSoldFromList.add(EditTransferData.previousTransferInfoResponse.getItems().get(i).getSoldFrom());
                previousQuantityList.add(EditTransferData.previousTransferInfoResponse.getItems().get(i).getQuantity());
            } catch (Exception e) {

            }
        }
        for (int i = 0; i < EditTransferData.updatedQuantityProductList.size(); i++) {
            quantitySet.add(Double.parseDouble(EditTransferData.updatedQuantityProductList.get(i).getQuantity()));
        }


        Set<Integer> selectedStoreIdSet = new HashSet<>();

        selectedStoreIdSet.clear();
        selectedStoreIdSet.add(Integer.parseInt(currentStoreId));
        soldFromList.add(getArguments().getString("storeFrom"));

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        saleViewModel.submitEditTransferInfo(
                getActivity(), orderSerial, orderId, proDuctIdList, quantitySet, productTitleList, buyingPriceSet,
                soldFromList, oldSoldFromList, previousQuantityList, currentStoreId, binding.note.getText().toString(),
                previousEnterPrise, currentSelectedEnterpriseId
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();

            myDatabaseHelper.deleteAllData();//delete all data from local database

            getActivity().onBackPressed();
        });


    }

    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(EditTransferData.updatedQuantityProductList);//get static data from (AddNewSale) Fragment

        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;


        previousEnterPrise = getArguments().getString("enterpriseFrom");
        previousStore = getArguments().getString("storeFrom");

        orderSerial = getArguments().getString("id");
        orderId = getArguments().getString("orderId");
        type = getArguments().getString("type");

        if (currentSelectedEnterpriseId == null) {
            currentSelectedEnterpriseId = getArguments().getString("enterpriseTo");
        }
        if (currentStoreId == null) {
            currentStoreId = getArguments().getString("storeTo");
        }

    }

    private void getEnterPriseListFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        salesRequisitionViewModel.getEnterpriseResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    /**
                     * all Ok now set data to view
                     */
                    setPageData(response);
                });

    }

    private void setPageData(GetEnterpriseResponse response) {
        enterpriseResponseList = new ArrayList<>();
        enterpriseResponseList.clear();
        enterpriseNameList = new ArrayList<>();
        enterpriseNameList.clear();
        enterpriseResponseList.addAll(response.getEnterprise());

        try {
            for (int i = 0; i < response.getEnterprise().size(); i++) {
                enterpriseNameList.add("" + response.getEnterprise().get(i).getStoreName());
                if (currentSelectedEnterpriseId != null) {
                    if (currentSelectedEnterpriseId.equals(response.getEnterprise().get(i).getStoreID())) {
                        binding.enterPrice.setSelection(i);
                    }
                }
            }
        } catch (Exception e) {
        }
        binding.enterPrice.setItem(enterpriseNameList);

        if (enterpriseResponseList.size() == 1) {
            binding.enterPrice.setSelection(0);
            currentSelectedEnterpriseId = enterpriseResponseList.get(0).getStoreID();
        }
    }

    private void nowSetStoreListByEnterPriseId() {
        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), currentSelectedEnterpriseId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    storeResponseList = new ArrayList<>();
                    storeResponseList.clear();
                    storeNameList = new ArrayList<>();
                    storeNameList.clear();
                    storeResponseList.addAll(response.getEnterprise());

                    try {
                       /* for (int i = 0; i < response.getEnterprise().size(); i++) {
                            if (!(response.getEnterprise().get(i).getStoreID().equals(previousStore))) {//remove previous selected store from store list
                                storeResponseList.add(response.getEnterprise().get(i));
                                storeNameList.add("" + response.getEnterprise().get(i).getStoreName());
                                if (currentStoreId != null) {
                                    if ((storeResponseList.get(i).getStoreID().equals(currentStoreId))) {//remove previous selected store from store list
                                        binding.store.setSelection(i);
                                    }
                                }
                            }
                        }*/

                        for (int i = 0; i < response.getEnterprise().size(); i++) {
                            storeNameList.add("" + response.getEnterprise().get(i).getStoreName());
                            if (currentStoreId != null) {
                                if ((response.getEnterprise().get(i).getStoreID().equals(currentStoreId))) {//remove previous selected store from store list
                                    binding.store.setSelection(i);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    binding.store.setItem(storeNameList);
                    if (storeResponseList.size() == 1) {
                        binding.store.setSelection(0);
                        currentStoreId = storeResponseList.get(0).getStoreID();
                    }
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


