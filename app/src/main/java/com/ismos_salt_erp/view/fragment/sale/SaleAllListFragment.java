package com.ismos_salt_erp.view.fragment.sale;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.SaleDeclinedListAdapter;
import com.ismos_salt_erp.adapter.SaleHistoryListAdapter;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.Company;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.SaleDeclinedList;
import com.ismos_salt_erp.serverResponseModel.SaleDeclinedResponse;
import com.ismos_salt_erp.serverResponseModel.SaleReturnHistoryList;
import com.ismos_salt_erp.serverResponseModel.SaleReturnPendingList;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.PermissionViewModel;
import com.ismos_salt_erp.viewModel.SaleHistoryViewModel;
import com.ismos_salt_erp.adapter.SalePendingListAdapter;
import com.ismos_salt_erp.databinding.FragmentSaleAllListBinding;
import com.ismos_salt_erp.serverResponseModel.SaleHistoryList;
import com.ismos_salt_erp.serverResponseModel.SalePendingList;
import com.ismos_salt_erp.serverResponseModel.SalePendingResponse;
import com.ismos_salt_erp.utils.SaleUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.SalePendingListViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class SaleAllListFragment extends BaseFragment
        implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, SaleViewDetailsTree {
    private ProgressDialog progressDialog;
    private FragmentSaleAllListBinding binding;
    private String previousSelectedId;
    private SalePendingListViewModel salePendingListViewModel;
    private SaleHistoryViewModel saleHistoryViewModel;
    private PermissionViewModel permissionViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;
    /**
     * for pagination
     */
    private boolean loading = true;

    private boolean isStartDate = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;
    boolean click = false;
    private boolean endScroll = false;
    LinearLayoutManager linearLayoutManager;


    private List<SalePendingList> salePendingLists = new ArrayList<>();
    List<SaleReturnPendingList> saleReturnPendingLists = new ArrayList<>();
    private List<SaleHistoryList> saleHistoryLists = new ArrayList<>();
    List<SaleDeclinedList> saleDeclinedLists = new ArrayList<>();
    List<SaleHistoryList> saleReturnHistoryLists = new ArrayList<>();


    /**
     * for company
     */
    List<String> companyNameList;
    List<Company> companyList;

    /**
     * for enterPrise
     */
    List<String> enterPriseNameList;
    List<Enterprize> enterprizeList;

    String companyId, enterPriseId, startDate, endDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sale_all_list, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        salePendingListViewModel = new ViewModelProvider(this).get(SalePendingListViewModel.class);
        saleHistoryViewModel = new ViewModelProvider(this).get(SaleHistoryViewModel.class);
        permissionViewModel = new ViewModelProvider(this).get(PermissionViewModel.class);
        linearLayoutManager = new LinearLayoutManager(getContext());
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);

        /** Visible filSaleUtil.saleHistory layout for all list toolbar*/
        binding.toolbar.filterBtn.setOnClickListener(v -> {
            ExpandableView response = new ExpandableView(getActivity(), binding.expandableView);
            response.response();
        });

        /** date Click*/
        setOnClick();
        /**
         control backpress
         **/

        /**
         get previous fragment data
         **/
        getFragmentData();


        /**
         * get All List data from server
         */

        getAllListDataFromServer();


        /** for pagination **/
        binding.saleListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    binding.expandableView.setExpanded(false);//collaps expandable view up scrolling time

                    try {
                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (endScroll) {
                                return;
                            }
                            loading = false;
                            pageNumber += 1;

                            getAllListDataFromServer();


                            loading = true;
                        }
                    }
                }
            }
        });

        binding.sale.selectCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                companyId = companyList.get(position).getCustomerID();
                binding.sale.selectCompany.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.sale.enterprise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enterPriseId = enterprizeList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void getAllListDataFromServer() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.saleListRv, binding.noDataFound)) {
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

         for Sale pending list
         **/
        if (previousSelectedId.equals(SaleUtil.salePending)) {
            binding.toolbar.toolbarTitle.setText(" Sale Pending List");
            getSalePendingFromViewModel();

        }
        /**for Sale pending list
         **/
        if (previousSelectedId.equals(SaleUtil.salePendingReturn)) {
            binding.toolbar.toolbarTitle.setText("Sale Return Pending List");
            getSaleReturnPendingFromViewModel();

        }

        /**
         for Sale History list
         **/
        if (previousSelectedId.equals(SaleUtil.saleHistory)) {
            binding.toolbar.toolbarTitle.setText("Sale History List");
            getSaleHistoryFromViewModel();
        }
/** for sale decline*/

        if (previousSelectedId.equals(SaleUtil.declineSaleList)) {
            binding.toolbar.toolbarTitle.setText("Sale Declined List");
            getDeclineListFromViewModel();
        }

        /** for return history */
        if (previousSelectedId.equals(SaleUtil.saleReturnHistory)) {
            binding.toolbar.toolbarTitle.setText("Sale Return history");
            getSaleReturnHistoryList();
        }


    }

    private void getSaleReturnPendingFromViewModel() {
        salePendingListViewModel.getSalePendingReturnList(getActivity(), String.valueOf(pageNumber),
                startDate, endDate, companyId, enterPriseId).observe(getViewLifecycleOwner(), response -> {

            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);

            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(requireActivity().getApplication(), "" + response.getMessage());
                return;
            }

            if (response.getLists() == null || response.getLists().isEmpty()) {
                managePaginationAndFilter();

            } else {

                manageFilterBtnAndRvAndDataNotFound();

                saleReturnPendingLists.addAll(response.getLists());
                SaleReturnPendingAdapter adapter = new SaleReturnPendingAdapter(getActivity(), saleReturnPendingLists);
                binding.saleListRv.setLayoutManager(linearLayoutManager);
                binding.saleListRv.setAdapter(adapter);


                /**Now set data to company select Spinner */
                setNameInSpinner(response.getCompanyList());

                /** set enterPrise */
                setDatainEnterPrise(response.getEnterprizeList());

            }
        });
    }


    private void getSaleReturnHistoryList() {
        salePendingListViewModel.getSaleReturnHistoryList(getActivity(), String.valueOf(pageNumber), startDate, endDate, companyId, enterPriseId).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    binding.progress.setVisibility(View.GONE);
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "something wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(requireActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        if (response.getStatus() == 200) {
                            if (response.getLists().isEmpty() || response.getLists() == null) {
                                managePaginationAndFilter();

                            } else {
                                manageFilterBtnAndRvAndDataNotFound();

                                saleReturnHistoryLists.addAll(response.getLists());
                                SaleReturnHistoryAdapter adapter = new SaleReturnHistoryAdapter(getActivity(), saleReturnHistoryLists);
                                binding.saleListRv.setLayoutManager(linearLayoutManager);
                                binding.saleListRv.setAdapter(adapter);

                                /**Now set data to company select Spinner */

                                setNameInSpinner(response.getCompanyList());

                                /** set enterPrise */
                                setDatainEnterPrise(response.getEnterprizeList());
                            }
                        }


                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());

                    }
                });
    }


    private void getDeclineListFromViewModel() {
        salePendingListViewModel.getSaleDeclinedList(getActivity(), String.valueOf(pageNumber), startDate, endDate, companyId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<SaleDeclinedResponse>() {
            @Override
            public void onChanged(SaleDeclinedResponse response) {
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
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();
                    } else {

                        manageFilterBtnAndRvAndDataNotFound();

                        saleDeclinedLists.addAll(response.getLists());

                        SaleDeclinedListAdapter adapter = new SaleDeclinedListAdapter(getActivity(), saleDeclinedLists);
                        binding.saleListRv.setLayoutManager(linearLayoutManager);
                        binding.saleListRv.setHasFixedSize(true);
                        binding.saleListRv.setAdapter(adapter);
                        /**Now set data to company select Spinner */

                        setNameInSpinner(response.getCompanyList());

                        /** set enterPrise */
                        setDatainEnterPrise(response.getEnterprizeList());
                    }
                }
            }
        });
    }


    private void getSaleHistoryFromViewModel() {
        saleHistoryViewModel.getSaleHistoryList(getActivity(), String.valueOf(pageNumber), startDate, endDate, companyId, enterPriseId).observe(getViewLifecycleOwner(), response -> {
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

                if (response.getLists() == null || response.getLists().isEmpty()) {
                    managePaginationAndFilter();

                } else {
                    manageFilterBtnAndRvAndDataNotFound();


                    saleHistoryLists.addAll(response.getLists());

                    SaleHistoryListAdapter adapter = new SaleHistoryListAdapter(getActivity(), saleHistoryLists);
                    binding.saleListRv.setLayoutManager(linearLayoutManager);
                    binding.saleListRv.setHasFixedSize(true);
                    binding.saleListRv.setAdapter(adapter);

                    /**Now set data to company select Spinner */

                    setNameInSpinner(response.getCompanyList());

                    /** set enterPrise */
                    setDatainEnterPrise(response.getEnterprizeList());
                }
            }
        });
    }

    private void setDatainEnterPrise(List<Enterprize> list) {
        enterPriseNameList = new ArrayList<>();
        enterPriseNameList.clear();
        enterprizeList = new ArrayList<>();
        enterprizeList.clear();
        enterprizeList.addAll(list);

        for (int i = 0; i < list.size(); i++) {
            enterPriseNameList.add("" + enterprizeList.get(i).getStoreName());
            if (enterPriseId != null) {
                if (enterprizeList.get(i).getStoreID().equals(enterPriseId)) {
                    binding.sale.enterprise.setSelection(i);
                }
            }
        }
        binding.sale.enterprise.setItem(enterPriseNameList);
    }

    private void setNameInSpinner(List<Company> list) {
        companyList = new ArrayList<>();
        companyList.clear();
        companyList.addAll(list);
        companyNameList = new ArrayList<>();
        companyNameList.clear();

        for (int i = 0; i < companyList.size(); i++) {
            companyNameList.add("" + companyList.get(i).getCompanyName() + "@" + companyList.get(i).getCustomerFname());
            if (companyId != null) {
                if (companyList.get(i).getCustomerID().equals(companyId)) {
                    binding.sale.selectCompany.setSelection(i);
                }
            }
        }

        binding.sale.selectCompany.setItem(companyNameList);


    }


    private void getSalePendingFromViewModel() {
        salePendingListViewModel.getSalePendinglist(getActivity(), String.valueOf(pageNumber), startDate, endDate, companyId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<SalePendingResponse>() {
            @Override
            public void onChanged(SalePendingResponse response) {
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
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();

                    }

                    /**
                     now set Data in RecyclerView
                     **/
                    else {

                        manageFilterBtnAndRvAndDataNotFound();

                        salePendingLists.addAll(response.getLists());
                        SalePendingListAdapter adapter = new SalePendingListAdapter(getActivity(), salePendingLists, getView(), SaleAllListFragment.this);
                        binding.saleListRv.setLayoutManager(linearLayoutManager);
                        binding.saleListRv.setHasFixedSize(true);
                        binding.saleListRv.setAdapter(adapter);

                        /**Now set data to company select Spinner */
                        setNameInSpinner(response.getCompanyList());

                        /** set enterPrise */
                        setDatainEnterPrise(response.getEnterprizeList());
                    }
                }

            }
        });
    }

    private void getFragmentData() {
        assert getArguments() != null;
        previousSelectedId = getArguments().getString("porson");
    }

    private void setOnClick() {
        binding.sale.startDate.setOnClickListener(this);
        binding.sale.EndDate.setOnClickListener(this);
        binding.sale.filterSearchBtn.setOnClickListener(this);
        binding.sale.resetBtn.setOnClickListener(this);
        binding.toolbar.backbtn.setOnClickListener(this);

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

            case R.id.backbtn:
                getActivity().onBackPressed();
                pageNumber = 1;
                break;
            case R.id.filterSearchBtn:
                pageNumber = 1;
                //for filter
                isFirstLoad = 0;
                salePendingLists.clear();
                saleReturnPendingLists.clear();
                saleDeclinedLists.clear();
                saleReturnHistoryLists.clear();
                saleHistoryLists.clear();
                hideKeyboard(getActivity());

                getAllListDataFromServer();

                break;
            case R.id.resetBtn:
                startDate = null;
                endDate = null;
                companyId = null;
                enterPriseId = null;
                binding.sale.startDate.setText("");
                binding.sale.EndDate.setText("");
                pageNumber = 1;
                hideKeyboard(getActivity());

                //for filter
                isFirstLoad = 0;
                salePendingLists.clear();
                saleReturnPendingLists.clear();
                saleDeclinedLists.clear();
                saleHistoryLists.clear();
                saleReturnHistoryLists.clear();


                if (nullChecked()) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.saleListRv.setVisibility(View.VISIBLE);
                    getAllListDataFromServer();
                }
                break;
        }
    }


    private void managePaginationAndFilter() {

        if (isFirstLoad == 0) { // if filter time list is null.  so then, data_not_found will be visible
            binding.saleListRv.setVisibility(View.GONE);
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
        binding.saleListRv.setVisibility(View.VISIBLE);
    }

    private boolean nullChecked() {

        if (startDate == null && endDate == null && companyId == null && enterPriseId == null) {
            return true;
        }
        return false;
    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);

        if (!isStartDate) {
            binding.sale.EndDate.setText(selectedDate);
            endDate = binding.sale.EndDate.getText().toString();
        } else {
            binding.sale.startDate.setText(selectedDate);
            startDate = binding.sale.startDate.getText().toString();
            isStartDate = false;
        }
    }

    @Override
    public void view(String refOrderID, String orderVendorId) {
        try {
            /**
             * First check Permission
             */
            permissionViewModel.getAccountPermission(getActivity())
                    .observe(getViewLifecycleOwner(), permissions -> {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("RefOrderId", refOrderID);
                            bundle.putString("orderVendorId", orderVendorId);
                            bundle.putString("portion", "PENDING_SALE");
                            bundle.putString("status", "2");
                            bundle.putString("pageName", "Pending Sales Details");
                            Navigation.findNavController(getView()).navigate(R.id.action_saleAllListFragment_to_pendingPurchaseDetailsFragment, bundle);
                        } catch (Exception e) {
                            Log.d("ERROR", "" + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        pageNumber = 1;
        isFirstLoad = 0;
        salePendingLists.clear();
        saleReturnPendingLists.clear();
        saleDeclinedLists.clear();
        saleReturnHistoryLists.clear();
        saleHistoryLists.clear();

        try {
            updateCurrentUserPermission(getActivity());
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


}