package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report;

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
import com.ismos_salt_erp.databinding.FragmentReportListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.permission.SharedPreferenceForReport;
import com.ismos_salt_erp.serverResponseModel.DistrictList;
import com.ismos_salt_erp.serverResponseModel.PurchaseAssociationList;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportResponse;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseBrandList;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseCategoryList;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseSupplierList;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.PurchaseMillerList;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.purchase_store.PurchaseReportStore;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.purchase_store.PurchaseReportStoreResponse;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, SmartMaterialSpinner.OnItemSelectedListener {
    private FragmentReportListBinding binding;
    private String portion;//get previous fragment
    private ProgressDialog progressDialog;
    private boolean isStartDate = false;

    private ReportViewModel reportViewModel;
    /**
     * for Association list
     */
    private List<String> associationName;
    private List<PurchaseAssociationList> associationLists;

    /**
     * for supplier list
     */
    private List<String> supplierNameList;
    private List<ReportPurchaseSupplierList> supplierResponseLists;
    List<DistrictList> districtLists;
    List<String> districtNameList;

    /**
     * for Category List
     */
    private List<String> categoryNameList;
    private List<ReportPurchaseCategoryList> purchaseCategoryLists;

    /**
     * for brand List
     */
    private List<String> brandNameList;
    private List<ReportPurchaseBrandList> brandLists;
    /**
     * for miller List
     */
    private List<String> millerNameList;
    private List<PurchaseMillerList> millerLists;

    /**
     * for store List
     */
    private List<String> storeNameList;
    private List<PurchaseReportStore> storeLists;

    private String selectAssociationId, millerProfileId, supplierId, customerName, brandId, districId, categoryId;
    String startDate, endDate, storeId, associaionId, pageName;

    SharedPreferenceForReport sharedPreferenceForReport;
    private CustomerReportViewModel customerReportViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_list, container, false);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        sharedPreferenceForReport = new SharedPreferenceForReport(getActivity());
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);
        progressDialog = new ProgressDialog(getContext());
        getPreviousFragmentData();

        /** for customerList */
        if (portion.equals(ReportUtils.saleReport) || portion.equals(ReportUtils.saleReturnReport)) {
            binding.supplierTv.setText("Customer");
        }


        if (portion.equals(ReportUtils.districtSaleReport)) {
            binding.districtWiseSaleLayout.setVisibility(View.VISIBLE);
            binding.supplierlayout.setVisibility(View.GONE);
            binding.categoryAndBrandLayout.setVisibility(View.GONE);
        }
        /** get page data from ReportViewModel */
        getPageDataFromViewModel();

        // customer list
        if (portion.equals(ReportUtils.saleReport) || portion.equals(ReportUtils.saleReturnReport)) {
            customerList();
        }


        /** set click on startDate TextView & EndDate TextView */
        setOnClick();

        /** back Control */
        binding.toolbar.setClickHandle(() -> {
            sharedPreferenceForReport.deleteCustomerData();
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.miller.setOnItemSelectedListener(this);
        binding.selectSupplier.setOnItemSelectedListener(this);
        binding.selectBrand.setOnItemSelectedListener(this);
        binding.selectCategory.setOnItemSelectedListener(this);
        binding.selectStore.setOnItemSelectedListener(this);
        binding.districtSpinner.setOnItemSelectedListener(this);


        binding.search.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("startDate", binding.startDate.getText().toString());
            bundle.putString("endDate", binding.EndDate.getText().toString());
            bundle.putString("portion", portion);
            bundle.putString("millerProfileId", millerProfileId);
            bundle.putString("supplierId", sharedPreferenceForReport.getCustomerId());
            bundle.putString("brandId", brandId);
            bundle.putString("districId", districId);
            bundle.putString("categoryId", categoryId);
            bundle.putString("storeId", storeId);
            bundle.putString("portion", portion);
            bundle.putString("customerName", customerName);
            bundle.putString("pageName", pageName);
            Navigation.findNavController(getView()).navigate(R.id.action_reportListFragment_to_purchaseReturnListFragment, bundle);
        });

        return binding.getRoot();
    }

    private void customerList() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        customerReportViewModel.getCustomerReportPageData(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(requireActivity().getApplication(), response.getMessage());
                return;
            }

            if (response.getStatus() == 200) {

                List<String> supplierNameList = new ArrayList<>();
                supplierNameList.clear();
                supplierResponseLists = new ArrayList<>();
                supplierResponseLists.addAll(response.getCustomerList());

                for (int i = 0; i < response.getCustomerList().size(); i++) {
                    supplierNameList.add("" + response.getCustomerList().get(i).getCompanyName() + "@" + response.getCustomerList().get(i).getCustomerFname());

                    if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                        if (sharedPreferenceForReport.getCustomerId().equals(response.getCustomerList().get(i).getCustomerID())) {
                            binding.selectSupplier.setSelection(i);
                        }
                    }

                }

                binding.selectSupplier.setItem(supplierNameList);
            }
        });


    }

    private void getPreviousFragmentData() {
        try {
            portion = getArguments().getString("portion");
            pageName = getArguments().getString("pageName");
            binding.toolbar.toolbarTitle.setText(pageName);

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getStoreDataByMillerStore() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        reportViewModel.getPurchaseReportStore(getActivity(), millerProfileId).observe(getViewLifecycleOwner(), new Observer<PurchaseReportStoreResponse>() {
            @Override
            public void onChanged(PurchaseReportStoreResponse response) {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong ");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    setInStoreSpinner(response);
                }
            }
        });


    }

    private void setInStoreSpinner(PurchaseReportStoreResponse response) {
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


    private void getPageDataFromViewModel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        reportViewModel.getPurchaseReportPageData(getActivity(), getProfileId(requireActivity().getApplication())).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(requireActivity().getApplication(), " " + response.getMessage());
                return;
            }
            if (response.getStatus() == 200) {

                setDataInAssociationSpinner(response);

            }
        });
    }

    private void setDataInAssociationSpinner(PurchaseReportResponse response) {

        /** for category List */
        categoryNameList = new ArrayList<>();
        categoryNameList.clear();
        purchaseCategoryLists = new ArrayList<>();
        purchaseCategoryLists.clear();
        purchaseCategoryLists.addAll(response.getCategoryList());

        for (int i = 0; i < response.getCategoryList().size(); i++) {
            categoryNameList.add("" + response.getCategoryList().get(i).getCategory());
        }
        binding.selectCategory.setItem(categoryNameList);


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

        try {
            if (response.getMillerList() != null) {
                millerLists.addAll(response.getMillerList());

                for (int i = 0; i < response.getMillerList().size(); i++) {
                    millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
                }
                binding.miller.setItem(millerNameList);
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


        /** for supplierList for purchase */

        if (portion.equals(ReportUtils.purchaseReport) || portion.equals(ReportUtils.purchaseReturnReport)) {
            supplierNameList = new ArrayList<>();
            supplierNameList.clear();
            supplierResponseLists = new ArrayList<>();
            supplierResponseLists.addAll(response.getSupplierList());


            for (int i = 0; i < response.getSupplierList().size(); i++) {
                supplierNameList.add("" + response.getSupplierList().get(i).getCompanyName() + "@" + response.getSupplierList().get(i).getCustomerFname());
                if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                    if (sharedPreferenceForReport.getCustomerId().equals(response.getSupplierList().get(i).getCustomerID())) {
                        binding.selectSupplier.setSelection(i);

                    }
                }
            }
            binding.selectSupplier.setItem(supplierNameList);
        }


        /** for customerList  fo sale */
      /*  if (portion.equals(ReportUtils.saleReport) || portion.equals(ReportUtils.saleReturnReport)) {
            supplierNameList = new ArrayList<>();
            supplierNameList.clear();
            supplierResponseLists = new ArrayList<>();
            if (response.getCustomerList() == null) {
                return;
            }
            supplierResponseLists.addAll(response.getCustomerList());
            for (int i = 0; i < supplierResponseLists.size(); i++) {
                supplierNameList.add("" + supplierResponseLists.get(i).getCompanyName() + "@" + supplierResponseLists.get(i).getCustomerFname());
                if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                    if (sharedPreferenceForReport.getCustomerId().equals(response.getSupplierList().get(i).getCustomerID())) {
                        binding.selectSupplier.setSelection(i);

                    }
                }
            }

            binding.selectSupplier.setItem(supplierNameList);

            return;
        }*/
        districtLists = new ArrayList<>();
        districtLists.clear();
        districtLists.addAll(response.getDistrictList());
        districtNameList = new ArrayList<>();
        districtNameList.clear();
        for (int i = 0; i < districtLists.size(); i++) {
            districtNameList.add("" + districtLists.get(i).getName());
        }
        binding.districtSpinner.setItem(districtNameList);


    }


    /**
     * for click
     */

    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
        binding.supplierlayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                timePicker();
                isStartDate = true;
                break;

            case R.id.EndDate:
                timePicker();
                break;
            case R.id.supplierlayout:
                binding.selectSupplier.clearSelection();
                sharedPreferenceForReport.deleteCustomerData();
                customerName = null;
                break;
        }
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

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        selectAssociationId = null;
        millerProfileId = null;
        supplierId = null;
        brandId = null;
        districId = null;
        categoryId = null;
        startDate = null;
        endDate = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.miller) {
            millerProfileId = millerLists.get(position).getStoreID();
            getStoreDataByMillerStore();//store data select
        }

        if (parent.getId() == R.id.selectSupplier) {
            supplierId = supplierResponseLists.get(position).getCustomerID();
            sharedPreferenceForReport.saveCustomerId(supplierResponseLists.get(position).getCustomerID());
            customerName = supplierResponseLists.get(position).getCustomerFname();
        }

        if (parent.getId() == R.id.selectBrand) {
            brandId = brandLists.get(position).getBrandID();
         }

        if (parent.getId() == R.id.selectCategory) {
            categoryId = purchaseCategoryLists.get(position).getCategoryID();
        }

        if (parent.getId() == R.id.selectStore) {
            storeId = storeLists.get(position).getStoreID();
        }

        if (parent.getId() == R.id.districtSpinner) {
            districId = districtLists.get(position).getDistrictId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}