package com.ismos_salt_erp.view.fragment.monitoring;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CustomerListAdapter;
import com.ismos_salt_erp.adapter.CustomerTrashListAdapter;
import com.ismos_salt_erp.adapter.MonitoringListAdapter;
import com.ismos_salt_erp.adapter.SupplierListAdapter;
import com.ismos_salt_erp.adapter.SupplierTrashListAdapter;
import com.ismos_salt_erp.databinding.AddStoreDialogLayoutBinding;
import com.ismos_salt_erp.databinding.DueLimitLayoutBinding;
import com.ismos_salt_erp.databinding.FragmentMonitoringListBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.CallListMethodFromAdapter;
import com.ismos_salt_erp.retrofit.GetMethod;
import com.ismos_salt_erp.serverResponseModel.CountryListResponse;
import com.ismos_salt_erp.serverResponseModel.CustoerTrashList;
import com.ismos_salt_erp.serverResponseModel.CustomerListModel;
import com.ismos_salt_erp.serverResponseModel.CustomerListResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerTrashListResponse;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.ListMonitorModel;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.MonitoringModel;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.serverResponseModel.SupplierList;
import com.ismos_salt_erp.serverResponseModel.SupplierListResponse;
import com.ismos_salt_erp.serverResponseModel.SupplierTrashList;
import com.ismos_salt_erp.serverResponseModel.SupplierTrashListResponse;
import com.ismos_salt_erp.serverResponseModel.TypeList;
import com.ismos_salt_erp.utils.CustomersUtil;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.utils.MonitoringUtil;
import com.ismos_salt_erp.utils.SupplierUtils;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.view.GenericClass;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;
import com.ismos_salt_erp.viewModel.MonitoringViewModel;
import com.ismos_salt_erp.viewModel.SupplierViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class MonitoringListFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, GetMethod, CallListMethodFromAdapter {
    private FragmentMonitoringListBinding binding;
    private MonitoringViewModel monitoringViewModel;
    private CustomerViewModel customerViewModel;
    private SupplierViewModel supplierViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;

    /**
     * for  customer filter division and District
     */
    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;

    /**
     * fro supplier
     */
    private List<String> supplierTypeList;
    private List<TypeList> typeLists;

    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;

    private String portion;
    private ProgressDialog progressDialog;
    /**
     * country for supplier list
     */
    private List<CountryListResponse> countryListResponseList;
    /**
     * district for supplier list
     */
    List<DistrictListResponse> supplierDistrictList;

    /**
     * for pagination
     */

    private List<Product> productList;
    private List<ListMonitorModel> monitorLists = new ArrayList<>();

    private List<CustomerListModel> customerLists = new ArrayList<>();

    private List<CustoerTrashList> custoerTrashLists = new ArrayList<>();

    private List<SupplierList> supplierLists = new ArrayList<>();
    private List<SupplierTrashList> supplierTrashLists = new ArrayList<>();


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;
    private boolean endScroll = false;
    private LinearLayoutManager linearLayoutManager;
    private boolean isStartDate = false;

    /**
     * for monitoring type
     */
    List<String> monitoringType;

    private boolean click = false;
    private boolean isEndDate = false;
    private String startDate, endDate, publishedDate, monitorTypeId; //  for monitoring filter variable
    private String customerDistrictId, customerDivisionId, supplierType, supplierCountryId, supplierDistrictId;//for supplier and customer
    public MonitoringModel monitoringModelObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monitoring_list, container, false);

        monitoringViewModel = new ViewModelProvider(this).get(MonitoringViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        supplierViewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);


        progressDialog = new ProgressDialog(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());

/**
 * get Data From Previous Fragment
 */
        getDataFromPreviousFragment();

        /** click handle */
        onClick();

        /** get Division*/
        getDivisionData();

        /**
         * get all list data
         */
        getAllListFromServer();

        binding.toolbar.setClickHandle(() -> {
            pageNumber = 1;
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


/** for pagination */
        binding.monitoringRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    binding.expandableView.collapse();//collaps expandable view up scrolling time
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (endScroll) {
                                return;
                            }
                            loading = false;
                            pageNumber += 1;

                            getAllListFromServer();

                            loading = true;
                        }
                    }
                }
            }
        });


        /**
         * Now handle division item click
         */
        binding.customerFilter.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerDivisionId = divisionResponseList.get(position).getDivisionId();

                /**
                 * Now get district list based on division id
                 */
                getDistrictListByDivisionId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * Nw handle district item click
         */
        binding.customerFilter.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerDistrictId = districtListResponseList.get(position).getDistrictId();

                /**
                 * Now get Thana list based on district
                 */

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.monitorFilter.monitoringType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monitorTypeId = monitoringType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** for supplier */

        binding.customerFilter.selectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierType = String.valueOf(typeLists.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.customerFilter.trashCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierCountryId = countryListResponseList.get(position).getCountryID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.customerFilter.trashDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierDistrictId = supplierDistrictList.get(position).getDistrictId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void getAllListFromServer() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.monitoringRv, binding.noDataFound)) {
            return;
        }
        if (pageNumber == 1) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
        }

        if (pageNumber > 1) {
            binding.progress.setVisibility(View.VISIBLE);
            binding.progress.setProgress(20);
            binding.progress.setMax(100);
        }


        /**
         * For show Monitoring list
         */
        if (portion.equals(MonitoringUtil.monitoringHistory)) {
            binding.toolbar.toolbarTitle.setText("Monitoring List");
            getMonitoringList();
        }


/**
 * For show Customer list from server
 */
        if (portion.equals(CustomersUtil.customerList)) {
            binding.toolbar.toolbarTitle.setText("Customer List");

            getCustomerListFromServer();
        }

/**
 * For show Customer trash list from server
 */
        if (portion.equals(CustomersUtil.customerTrashList)) {

            binding.toolbar.toolbarTitle.setText("Customer Trash List");
            getCustomerTrashListFromServer();
        }

/**
 * supplier list
 */
        if (portion.equals(SupplierUtils.supplierList)) {
            binding.customerFilter.supplierSelect.setVisibility(View.VISIBLE);
            binding.customerFilter.trashLayout.setVisibility(View.VISIBLE);
            binding.customerFilter.customerLayout.setVisibility(View.GONE);
            binding.toolbar.toolbarTitle.setText("Supplier List");

            hideKeyboard(getActivity());
            getData();
        }
        /**
         * supplier list
         */
        if (portion.equals(SupplierUtils.supplierTrashList)) {
            binding.customerFilter.supplierSelect.setVisibility(View.VISIBLE);
            binding.toolbar.toolbarTitle.setText("Supplier Trash List");

            getTrashData();

        }
    }

    private void getTrashData() {
        supplierViewModel.getSupplierTrashList(getActivity(), String.valueOf(pageNumber), supplierType, customerDivisionId, customerDistrictId,binding.customerFilter.searchByName.getText().toString()).observe(getViewLifecycleOwner(), new Observer<SupplierTrashListResponse>() {
            @Override
            public void onChanged(SupplierTrashListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                try {

                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getLists() == null || response.getLists().isEmpty()) {
                            managePaginationAndFilter();

                        } else {

                            manageFilterBtnAndRvAndDataNotFound();

                            supplierTrashLists.addAll(response.getLists());
                            SupplierTrashListAdapter adapter = new SupplierTrashListAdapter(getActivity(), supplierTrashLists);
                            binding.monitoringRv.setLayoutManager(linearLayoutManager);
                            binding.monitoringRv.setHasFixedSize(true);
                            binding.monitoringRv.setAdapter(adapter);


                            /** set supplier type */
                            setTypeInSpinner(response.getTypeList());

                        }
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }

            }
        });
    }

    private void getData() {
        supplierViewModel.getSupplierList(getActivity(), String.valueOf(pageNumber), supplierType, supplierCountryId, supplierDistrictId,binding.customerFilter.searchByName.getText().toString()).observe(getViewLifecycleOwner(), new Observer<SupplierListResponse>() {
            @Override
            public void onChanged(SupplierListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                try {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getLists() == null || response.getLists().isEmpty()) {
                            managePaginationAndFilter();


                        } else {
                            manageFilterBtnAndRvAndDataNotFound();


                            supplierLists.addAll(response.getLists());
                            SupplierListAdapter adapter = new SupplierListAdapter(getActivity(), supplierLists, getView());
                            binding.monitoringRv.setLayoutManager(linearLayoutManager);
                            binding.monitoringRv.setHasFixedSize(true);
                            binding.monitoringRv.setAdapter(adapter);

                            /** set supplier type */
                            setTypeInSpinner(response.getTypeList());
                            /** for supplier country */
                            setSupplierCountry(response.getCountryList());
                            /** for supplier district */
                            setSupplierDistrict(response.getDistrictList());
                        }
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
        });
    }

    private void setTypeInSpinner(List<TypeList> list) {
        supplierTypeList = new ArrayList<>();
        supplierTypeList.clear();
        typeLists = new ArrayList<>();
        typeLists.clear();
        typeLists.addAll(list);
        for (int i = 0; i < list.size(); i++) {
            supplierTypeList.add("" + typeLists.get(i).getName());
            if (supplierType != null) {
                if (supplierType.equals(String.valueOf(typeLists.get(i).getId()))) {
                    binding.customerFilter.selectType.setSelection(i);
                }
            }
        }
        binding.customerFilter.selectType.setItem(supplierTypeList);

    }

    private void setSupplierDistrict(List<DistrictListResponse> districtList) {
        List<String> supplierDistrictName = new ArrayList<>();
        supplierDistrictName.clear();
        supplierDistrictList = new ArrayList<>();
        supplierDistrictList.clear();
        supplierDistrictList.addAll(districtList);
        for (int i = 0; i < districtList.size(); i++) {
            supplierDistrictName.add("" + districtList.get(i).getName());
            if (supplierDistrictId !=null){
                if (supplierDistrictId.equals(districtList.get(i).getDistrictId())){
                    binding.customerFilter.trashDistrict.setSelection(i);
                }
            }
        }
        binding.customerFilter.trashDistrict.setItem(supplierDistrictName);
    }

    private void setSupplierCountry(List<CountryListResponse> countryList) {
        List<String> countryNameList = new ArrayList<>();
        countryNameList.clear();
        countryListResponseList = new ArrayList<>();
        countryListResponseList.clear();

        countryListResponseList.addAll(countryList);

        for (int i = 0; i < countryList.size(); i++) {
            countryNameList.add("" + countryList.get(i).getCountryName());
            if (supplierCountryId != null) {
                if (countryListResponseList.get(i).getCountryID().equals(supplierCountryId)) {
                    binding.customerFilter.trashCountry.setSelection(i);
                }
            }
        }
        binding.customerFilter.trashCountry.setItem(countryNameList);
    }


    private void getCustomerTrashListFromServer() {
        customerViewModel.getCustomerTrashList(getActivity(), String.valueOf(pageNumber), customerDivisionId, customerDistrictId,binding.customerFilter.searchByName.getText().toString()).observe(getViewLifecycleOwner(), new Observer<CustomerTrashListResponse>() {
            @Override
            public void onChanged(CustomerTrashListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists().isEmpty() || response.getLists() == null) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();
                        custoerTrashLists.addAll(response.getLists());
                        CustomerTrashListAdapter adapter = new CustomerTrashListAdapter(getActivity(), custoerTrashLists);
                        binding.monitoringRv.setLayoutManager(linearLayoutManager);
                        binding.monitoringRv.setHasFixedSize(true);
                        binding.monitoringRv.setAdapter(adapter);
                    }
                }

            }
        });
    }


    private void getCustomerListFromServer() {
        customerViewModel.getCustomerList(getActivity(), String.valueOf(pageNumber), binding.customerFilter.searchByName.getText().toString(), customerDivisionId, customerDistrictId).observe(getViewLifecycleOwner(), new Observer<CustomerListResponse>() {
            @Override
            public void onChanged(CustomerListResponse customerListResponse) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);

                if (customerListResponse == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (customerListResponse.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), customerListResponse.getMessage());
                    return;
                }
                if (customerListResponse.getStatus() == 200) {
                    if (customerListResponse.getCustomerLists() == null || customerListResponse.getCustomerLists().isEmpty()) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();

                        customerLists.addAll(customerListResponse.getCustomerLists());
                        CustomerListAdapter adapter = new CustomerListAdapter(getActivity(), customerLists, getView(), MonitoringListFragment.this);
                        binding.monitoringRv.setLayoutManager(linearLayoutManager);
                        binding.monitoringRv.setHasFixedSize(true);
                        binding.monitoringRv.setAdapter(adapter);
                    }
                }


            }
        });


    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        portion = getArguments().getString("portion");
    }

    private void getMonitoringList() {

        monitoringViewModel.getMonitoringList(getActivity(), String.valueOf(pageNumber), startDate, endDate, publishedDate, monitorTypeId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        return;
                    }

                    if (response.getStatus() == 200) {
                        if (response.getLists() == null || response.getLists().isEmpty()) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();

                            monitorLists.addAll(response.getLists());
                            MonitoringListAdapter adapter = new MonitoringListAdapter(getActivity(), monitorLists, response);
                            binding.monitoringRv.setLayoutManager(linearLayoutManager);
                            binding.monitoringRv.setHasFixedSize(true);
                            binding.monitoringRv.setAdapter(adapter);

                            /** for monitoring type */
                            monitoringType = new ArrayList<>();
                            monitoringType.clear();
                            for (int i = 0; i < response.getMonitoringType().size(); i++) {
                                monitoringType.add("" + response.getMonitoringType().get(i));
                                monitoringType.add("" + response.getMonitoringType().get(i));
                                monitoringType.add("" + response.getMonitoringType().get(i));
                                monitoringType.add("" + response.getMonitoringType().get(i));
                            }
                            binding.monitorFilter.monitoringType.setItem(monitoringType);
                        }
                    }

                });
    }


    private void onClick() {
        binding.toolbar.filterBtn.setOnClickListener(this);
        /** for monitor*/
        binding.monitorFilter.monitorSearchBtn.setOnClickListener(this);
        binding.monitorFilter.monitorResetBtn.setOnClickListener(this);
        binding.monitorFilter.startDate.setOnClickListener(this);
        binding.monitorFilter.EndDate.setOnClickListener(this);
        binding.monitorFilter.publishedDate.setOnClickListener(this);
        /** for customer */
        binding.customerFilter.customerSearchBtn.setOnClickListener(this);
        binding.customerFilter.customerResetBtn.setOnClickListener(this);
        binding.customerFilter.searchByNameBtn.setOnClickListener(this);
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
            case R.id.publishDate:

                timePicker();
                break;
            case R.id.filterBtn:

                if (portion.equals(MonitoringUtil.monitoringHistory)) {
                    showFilterLayout(binding.expandableView);
                }

                if (portion.equals(CustomersUtil.customerList) || portion.equals(CustomersUtil.customerTrashList)) {
                    showFilterLayout(binding.expandableView1);
                }

                if (portion.equals(SupplierUtils.supplierTrashList) || portion.equals(SupplierUtils.supplierList)) {
                    showFilterLayout(binding.expandableView1);
                    //  binding.localSupplierFilterLayout.setVisibility(View.VISIBLE);

                }

                break;
            /** for customer */

            case R.id.searchByNameBtn:
                pageNumber = 1;
                //for filter
                clearAllList();
                getAllListFromServer();
                break;
            case R.id.customerSearchBtn:
                pageNumber = 1;
                //for filter
                clearAllList();

                getAllListFromServer();

                break;

            case R.id.customerResetBtn:
                pageNumber = 1;
                customerDivisionId = null;
                customerDistrictId = null;
                binding.customerFilter.searchByName.setText("");
                binding.customerFilter.division.clearSelection();
                binding.customerFilter.district.clearSelection();
                /** for supplier list */
                supplierType = null;
                supplierCountryId = null;
                supplierDistrictId = null;


                //for filter
                clearAllList();

                if (supplierType == null && supplierCountryId == null && supplierDistrictId == null) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.monitoringRv.setVisibility(View.VISIBLE);
                    getAllListFromServer();
                    return;
                }

                if (supplierType == null && customerDivisionId == null && customerDistrictId == null) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.monitoringRv.setVisibility(View.VISIBLE);
                    getAllListFromServer();
                    return;
                }

                if (customerDivisionId == null && customerDistrictId == null) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.monitoringRv.setVisibility(View.VISIBLE);
                    getAllListFromServer();
                    return;
                }
                break;


            /** for monitor */
            case R.id.monitorSearchBtn:
                pageNumber = 1;

                //for filter
                isFirstLoad = 0;
                monitorLists.clear();

                getAllListFromServer();
                break;
            case R.id.monitorResetBtn:
                pageNumber = 1;
                startDate = null;
                endDate = null;
                publishedDate = null;
                binding.monitorFilter.startDate.setText("");
                binding.monitorFilter.EndDate.setText("");
                binding.monitorFilter.publishedDate.setText("");
                monitorTypeId = null;

                // for filter
                isFirstLoad = 0;
                monitorLists.clear();

                if (startDate == null && endDate == null && publishedDate == null && monitorTypeId == null) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.monitoringRv.setVisibility(View.VISIBLE);
                    getAllListFromServer();
                }
                break;
        }
    }

    private void showFilterLayout(ExpandableLayout expandableLayout) {
        ExpandableView response = new ExpandableView(getActivity(), expandableLayout);
        response.response();
    }

    private void clearAllList() {
        pageNumber = 1;
        isFirstLoad = 0;
        customerLists.clear();
        custoerTrashLists.clear();
        supplierLists.clear();
        supplierTrashLists.clear();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }

        String mainMonth, mainDay;


        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date


        if (!isStartDate) {
            binding.monitorFilter.EndDate.setText(selectedDate);
            endDate = binding.monitorFilter.EndDate.getText().toString();
        } else {
            binding.monitorFilter.startDate.setText(selectedDate);
            startDate = binding.monitorFilter.startDate.getText().toString();
            isStartDate = false;
        }
    }

    private void managePaginationAndFilter() {
        if (isFirstLoad == 0) { // if filter time list is null.  so then, data_not_found will be visible
            binding.monitoringRv.setVisibility(View.GONE);
            binding.noDataFound.setVisibility(View.VISIBLE);
            return;
        }
        if (isFirstLoad > 0) {//for scrolling off
            endScroll = true;//means scroll off
            pageNumber -= 1;
            return;
        }
        return;
    }

    private void manageFilterBtnAndRvAndDataNotFound() {
        isFirstLoad += 1;
        binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
        //for filter
        // sometime filter list data came null when, data_not_found have visible,
        // And again search comes data in list by the others filter parameter.that for recycler view visible
        binding.noDataFound.setVisibility(View.GONE);
        binding.monitoringRv.setVisibility(View.VISIBLE);
    }

    private void timePicker() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        assert getFragmentManager() != null;
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    private void getDivisionData() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        /**
         * For get Division list from This Api
         */
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    /**
                     * now set division list
                     */
                    divisionResponseList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionResponseList.addAll(response.getDivisions());

                    divisionNameList = new ArrayList<>();
                    divisionNameList.clear();

                    for (int i = 0; i < response.getDivisions().size(); i++) {
                        divisionNameList.add(response.getDivisions().get(i).getName());
                    }
                    binding.customerFilter.division.setItem(divisionNameList);
                });


    }

    private void getDistrictListByDivisionId() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), customerDivisionId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        return;
                    }
                    if (response.getStatus() != 200) {
                        return;
                    }
                    districtListResponseList = new ArrayList<>();
                    districtListResponseList.clear();
                    districtNameList = new ArrayList<>();
                    districtNameList.clear();

                    districtListResponseList.addAll(response.getLists());

                    for (int i = 0; i < response.getLists().size(); i++) {
                        districtNameList.add("" + response.getLists().get(i).getName());
                        if (customerDistrictId != null) {
                            if (customerDistrictId.equals(response.getLists().get(i).getDistrictId())) {
                                binding.customerFilter.district.setSelection(i);
                            }
                        }

                    }
                    binding.customerFilter.district.setItem(districtNameList);
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        clearAllList();
        try {
            updateCurrentUserPermission(getActivity());
            clearAllList();
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }

    }

    public void updateCurrentUserPermission(FragmentActivity activity) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        currentPermissionViewModel.getCurrentUserRealtimePermissions(
                PreferenceManager.getInstance(activity).getUserCredentials().getToken(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserId(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserName()
        ).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                Toasty.error(activity, "Something Wrong", Toasty.LENGTH_LONG).show();
                return;
            }
            if (response.getStatus() == 400) {
                Toasty.info(activity, "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                return;
            }
            try {
                LoginResponse loginResponse = PreferenceManager.getInstance(activity).getUserCredentials();
                if (loginResponse != null) {
                    loginResponse.setPermissions(response.getMessage());
                    loginResponse.setToken(response.getToken());
                    PreferenceManager.getInstance(activity).saveUserCredentials(loginResponse);
                }
            } catch (Exception e) {
                infoMessage(getActivity().getApplication(), "" + e.getMessage());
                Log.d("ERROR", "" + e.getMessage());
            }
        });
    }

    @Override
    public void fetch() {
        try {
            getCustomerListFromServer();
        } catch (Exception e) {
        }
    }

    @Override
    public void getPositionFromAdapter(int Position, String Id, String du,String customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DueLimitLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.due_limit_layout, null, false);
        builder.setView(binding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        binding.dueLimitEt.setText("" + du);
        binding.company.setText(""+customer);
        binding.btnOk.setOnClickListener(v1 -> {
            try {
                if (binding.dueLimitEt.getText().toString().isEmpty()) {
                    binding.dueLimitEt.setError("Empty field");
                    binding.dueLimitEt.requestFocus();
                    return;
                }

                customerViewModel.updateDueLimit(getActivity(), Id, binding.dueLimitEt.getText().toString()).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
                    @Override
                    public void onChanged(DuePaymentResponse duePaymentResponse) {
                        if (duePaymentResponse == null || duePaymentResponse.getStatus() == 500) {
                            errorMessage(getActivity().getApplication(), "Wrong " + duePaymentResponse.getMessage());

                        }

                        if(duePaymentResponse.getStatus() == 400 ){
                           infoMessage(getActivity().getApplication(),""+duePaymentResponse.getMessage());
                            return;
                        }
                        if (duePaymentResponse != null && duePaymentResponse.getStatus() == 200) {
                            successMessage(getActivity().getApplication(), duePaymentResponse.getMessage());
                            pageNumber = 1;
                            isFirstLoad = 0;
                            customerLists.clear();
                            getCustomerListFromServer();
                        }
                    }
                });
                alertDialog.dismiss();
            } catch (Exception e) {
            }
        });
        binding.cancel.setOnClickListener(v12 -> {
            alertDialog.dismiss();
        });

    }
}