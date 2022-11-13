package com.ismos_salt_erp.view.fragment.all_report.reconcilation_report;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentReconciliationReportBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response.ReconciliationMillerList;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response.ReconciliationReportMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationPageDataResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationReportAssociationList;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationReportBrandList;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationReportItemList;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.store.ReconciliationStore;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.store.ReconciliationStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response.SaleReportCategoryList;
import com.ismos_salt_erp.viewModel.ReportProduct;
import com.ismos_salt_erp.viewModel.Store;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReconciliationReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class ReconciliationReportFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, SmartMaterialSpinner.OnItemSelectedListener {
    private FragmentReconciliationReportBinding binding;
    private ReconciliationReportViewModel reconciliationViewModel;
    private boolean isStartDate = false;

    /**
     * for store List
     */
    private List<String> storeNameList;
    private List<ReconciliationStore> storeLists;


    /**
     * for Association list
     */
    private List<String> associationName;
    private List<ReconciliationReportAssociationList> associationLists;

    /**
     * for item list
     */
    private List<String> itemNameList;
    private List<ReconciliationReportItemList> itemLists;

    /**
     * for Category List
     */
    private List<String> categoryNameList;
    private List<SaleReportCategoryList> purchaseCategoryLists;

    /**
     * for brand List
     */
    private List<String> brandNameList;
    private List<ReconciliationReportBrandList> brandLists;
    /**
     * for miller List
     */
    private List<String> millerNameList;
    private List<ReconciliationMillerList> millerLists;
    private List<Store> storeList;
    private List<ReportProduct> itemList;
    /**
     * for ReconcilationType
     */
    List<String> reconcilationType;
    private String selectAssociationId, millerProfileId, itemId, storeId, brandId, categoryId;
    private String fromStoreId, toStoreId, transferItemId; // for transfer report
    String startDate, endDate, associationId, portion, pageName;
    private String selectReconciliation = "";
    private List<String> reconcilationTypeList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reconciliation_report, container, false);
        reconciliationViewModel = new ViewModelProvider(this).get(ReconciliationReportViewModel.class);

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        getPreviousFragmentData();
        binding.toolbar.toolbarTitle.setText(pageName);

        getPageData();

        setonClick();


        /** for search button Click */
        binding.search.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("startDate", binding.startDate.getText().toString());
            bundle.putString("endDate", binding.EndDate.getText().toString());
            bundle.putString("millerProfileId", millerProfileId);
            bundle.putString("itemId", itemId);
            bundle.putString("brandId", brandId);
            bundle.putString("portion", getArguments().getString("portion"));
            bundle.putString("categoryId", categoryId);//item for reconciliation
            bundle.putString("storeId", storeId);
            bundle.putString("pageName", pageName);
            bundle.putString("selectReconciliation", selectReconciliation);
            //for transfer report data
            bundle.putString("fromStoreId", fromStoreId);
            bundle.putString("toStoreId", toStoreId);
            bundle.putString("transferItemId", transferItemId);
            bundle.putString("selectAssociationId", selectAssociationId);

            Navigation.findNavController(getView()).navigate(R.id.action_reconciliationReportFragment_to_purchaseReturnListFragment, bundle);

        });


        binding.selectAssociation.setOnItemSelectedListener(this);
        binding.miller.setOnItemSelectedListener(this);
        binding.selectItem.setOnItemSelectedListener(this);
        binding.selectBrand.setOnItemSelectedListener(this);
        binding.selectItem.setOnItemSelectedListener(this);
        binding.selectStore.setOnItemSelectedListener(this);
        binding.selectReconciliation.setOnItemSelectedListener(this);
        binding.accessForField.fromStore.setOnItemSelectedListener(this);
        binding.accessForField.toStore.setOnItemSelectedListener(this);
        binding.accessForField.item.setOnItemSelectedListener(this);

        return binding.getRoot();
    }

    private void getPageData() {
        if (portion.equals(ReportUtils.transferReport)) {
            binding.reconciliationAndStoctLayout.setVisibility(View.GONE);
            binding.transferReportLayout.setVisibility(View.VISIBLE);
            getTransferPageData();
            return;
        }
        if (portion.equals(ReportUtils.stockInOutReport)) {
            //if comes from stock in out fragment
            binding.reconciliationTypeLayout.setVisibility(View.GONE);
        }
        getPAgeDataFromViewModel();
    }

    private void getTransferPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        reconciliationViewModel.transferReportPageData(getActivity()).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        errorMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        //for store
                        storeList = new ArrayList<>();
                        storeList.clear();
                        storeList.addAll(response.getStoreList());
                        List<String> storeNameList = new ArrayList<>();
                        storeNameList.clear();
                        for (int i = 0; i < storeList.size(); i++) {
                            storeNameList.add(storeList.get(i).getStoreName());
                        }
                        binding.accessForField.fromStore.setItem(storeNameList);
                        binding.accessForField.toStore.setItem(storeNameList);


                        // item select
                        itemList = new ArrayList<>();
                        itemList.clear();
                        itemList.addAll(response.getProductList());
                        List<String> itemNameList = new ArrayList<>();
                        itemNameList.clear();
                        for (int l = 0; l < itemList.size(); l++) {
                            itemNameList.add(itemList.get(l).getProductTitle());
                        }
                        binding.accessForField.item.setItem(itemNameList);
                    }

                });
    }

    private void getPreviousFragmentData() {
        portion = getArguments().getString("portion");
        pageName = getArguments().getString("pageName");


    }

    private void getStoreDataByMillerStore() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        reconciliationViewModel.getSaleReturnReportStore(getActivity(), millerProfileId).observe(getViewLifecycleOwner(), new Observer<ReconciliationStoreResponse>() {
            @Override
            public void onChanged(ReconciliationStoreResponse response) {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    setDataStoreSpinner(response);
                }
            }
        });
    }

    private void setDataStoreSpinner(ReconciliationStoreResponse response) {
        storeNameList = new ArrayList<>();
        storeNameList.clear();
        storeLists = new ArrayList<>();
        storeLists.clear();
        storeLists.addAll(response.getMillerList());

        for (int i = 0; i < response.getMillerList().size(); i++) {
            storeNameList.add("" + response.getMillerList().get(i).getStoreName());
        }
        binding.selectStore.setItem(storeNameList);

    }


    private void getMillerPageData(String selectAssociationId) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        reconciliationViewModel.getReconciliationMiller(getActivity(), selectAssociationId).observe(getViewLifecycleOwner(), new Observer<ReconciliationReportMillerResponse>() {
            @Override
            public void onChanged(ReconciliationReportMillerResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    setDataMillerSpinner(response);
                }
            }
        });

    }

    private void setDataMillerSpinner(ReconciliationReportMillerResponse response) {
        millerNameList = new ArrayList<>();
        millerNameList.clear();
        millerLists = new ArrayList<>();
        millerLists.clear();

        millerLists.addAll(response.getMillerList());

        for (int i = 0; i < response.getMillerList().size(); i++) {
            millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
        }
        binding.miller.setItem(millerNameList);

    }


    private void getPAgeDataFromViewModel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        reconciliationViewModel.getReconciliationPageData(getActivity(), getProfileId(getActivity().getApplication())).observe(getViewLifecycleOwner(), new Observer<ReconciliationPageDataResponse>() {
            @Override
            public void onChanged(ReconciliationPageDataResponse response) {
                progressDialog.dismiss();
                try {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        errorMessage(getActivity().getApplication(), response.getMessage());
                        return;

                    }
                    if (response.getStatus() == 200) {
                        setDataInSpinner(response);

                    }

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    private void setDataInSpinner(ReconciliationPageDataResponse response) {
        reconcilationTypeList = new ArrayList<>();
        reconcilationTypeList.addAll(Arrays.asList("Damage", "Increase", "Process Lost", "Expire"));
        binding.selectReconciliation.setItem(reconcilationTypeList);


        /** for item  */

        itemNameList = new ArrayList<>();
        itemNameList.clear();
        itemLists = new ArrayList<>();
        itemLists.addAll(response.getItemList());


        for (int i = 0; i < response.getItemList().size(); i++) {
            itemNameList.add("" + response.getItemList().get(i).getProductTitle());
        }
        binding.selectItem.setItem(itemNameList);


        /** for brandList */
        brandNameList = new ArrayList<>();
        brandNameList.clear();

        brandLists = new ArrayList<>();
        brandLists.clear();

        brandLists.addAll(response.getBrandList());

        for (int i = 0; i < response.getBrandList().size(); i++) {
            brandNameList.add("" + response.getBrandList().get(i).getBrandName());
        }
        binding.selectBrand.setItem(brandNameList);


        millerNameList = new ArrayList<>();
        millerNameList.clear();
        millerLists = new ArrayList<>();
        millerLists.clear();

        millerLists.addAll(response.getMillerList());

        for (int i = 0; i < response.getMillerList().size(); i++) {
            millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
        }
        binding.miller.setItem(millerNameList);


    }

    @Override
    public void onStart() {
        super.onStart();
        selectAssociationId = null;
        millerProfileId = null;
        itemId = null;
        brandId = null;
        categoryId = null;
        startDate = null;
        endDate = null;
        associationId = null;

    }

    private void setonClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                isStartDate = true;
                timePicker();
                break;

            case R.id.EndDate:
                timePicker();
                break;
        }

    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);
        if (!isStartDate) {
            binding.EndDate.setText(selectedDate);
            binding.EndDate.setError(null);
        } else {
            binding.startDate.setText(selectedDate);
            binding.startDate.setError(null);
            isStartDate = false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.selectAssociation) {
            selectAssociationId = associationLists.get(position).getStoreID();
            getMillerPageData(selectAssociationId);
        }
        if (parent.getId() == R.id.miller) {
            millerProfileId = String.valueOf(millerLists.get(position).getStoreID());
            binding.miller.setEnableErrorLabel(false);
            getStoreDataByMillerStore();//store data select
        }
        if (parent.getId() == R.id.selectItem) {
            itemId = itemLists.get(position).getProductID();

        }
        if (parent.getId() == R.id.selectBrand) {
            brandId = brandLists.get(position).getBrandID();

        }
        if (parent.getId() == R.id.selectItem) {
            categoryId = itemLists.get(position).getProductID();

        }
        if (parent.getId() == R.id.selectStore) {
            storeId = storeLists.get(position).getStoreID();

        }
        if (parent.getId() == R.id.selectReconciliation) {
            selectReconciliation = reconcilationTypeList.get(position);

        }
        if (parent.getId() == R.id.fromStore) {
            fromStoreId = storeList.get(position).getStoreID();

        }
        if (parent.getId() == R.id.toStore) {
            toStoreId = storeList.get(position).getStoreID();

        }
        if (parent.getId() == R.id.item) {
            transferItemId = itemList.get(position).getProductID();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}