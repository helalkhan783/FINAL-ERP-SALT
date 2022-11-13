package com.ismos_salt_erp.view.fragment.production;

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
import com.ismos_salt_erp.adapter.IodizationHistoryAdapter;
import com.ismos_salt_erp.adapter.PackagingListAdapter;
import com.ismos_salt_erp.adapter.PacketingListAdapter;
import com.ismos_salt_erp.adapter.PendingListAdapter;
import com.ismos_salt_erp.databinding.FragmentProductionAllListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.serverResponseModel.IodizatinPendingList;
import com.ismos_salt_erp.serverResponseModel.IodizationPenDingResponse;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.PackagingList;
import com.ismos_salt_erp.serverResponseModel.PackagingListResponse;
import com.ismos_salt_erp.serverResponseModel.PacketingListResponse;
import com.ismos_salt_erp.serverResponseModel.PacketingProductionListList;
import com.ismos_salt_erp.serverResponseModel.ProductionItem;
import com.ismos_salt_erp.utils.ProductionUtils;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.serverResponseModel.IodizationHistoryList;
import com.ismos_salt_erp.serverResponseModel.IodizationHistoryResponse;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.IodizationHostoryViewModel;
import com.ismos_salt_erp.adapter.WashingAdapter;
import com.ismos_salt_erp.serverResponseModel.WashingList;
import com.ismos_salt_erp.viewModel.PackagingListViewModel;
import com.ismos_salt_erp.viewModel.WashingViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ProductionAllListFragment extends BaseFragment
        implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener {
    private FragmentProductionAllListBinding binding;
    private ProgressDialog progressDialog;
    private String portion;
    private WashingViewModel washingViewModel;
    private IodizationHostoryViewModel iodizationHostoryViewModel;
    PackagingListViewModel packagingListViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;


    /**
     * for pagination
     */
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;

    LinearLayoutManager linearLayoutManager;
    List<WashingList> washingList = new ArrayList<>();
    List<WashingList> washingLists = new ArrayList<>();
    List<IodizationHistoryList> iodizationHistoryLists = new ArrayList<>();
    List<PackagingList> packagingLists = new ArrayList<>();
    List<PacketingProductionListList> packetingProductionListLists = new ArrayList<>();
    List<IodizatinPendingList> iodizatinPendingLists = new ArrayList<>();


    boolean click = false;
    /**
     * for companyitem
     */
    List<String> companyNameList;
    List<ProductionItem> productionItems;

    /**
     * for enterPrise
     */
    List<String> enterPriseNameList;
    List<Enterprize> enterprizeList;
    private boolean isStartDate = false;
    private boolean endScroll = false;
    String itemId, enterPriseId, startDate, endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_production_all_list, container, false);

        washingViewModel = new ViewModelProvider(this).get(WashingViewModel.class);
        iodizationHostoryViewModel = new ViewModelProvider(this).get(IodizationHostoryViewModel.class);
        packagingListViewModel = new ViewModelProvider(this).get(PackagingListViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);

        binding.sale.spinner.setText("Select Item");

        binding.toolbar.filterBtn.setOnClickListener(v -> {
            ExpandableView response = new ExpandableView(getActivity(), binding.expandableView);
            response.response();

        });

        setOnClick();

        progressDialog = new ProgressDialog(getContext());

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
            pageNumber = 1;
        });
        /*     getPeriviousFragmentData();
         *//**
         * getAll List  from server
         *//*
        getAllListFromServer();*/


        binding.sale.selectCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemId = productionItems.get(position).getProductID();
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

/** for pagination */
        binding.productionRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    binding.expandableView.setExpanded(false);//collaps expandable view up scrolling time
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

        return binding.getRoot();
    }


    private void getAllListFromServer() {

        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.productionRv, binding.dataNotFound)) {
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
         this for washing & crushing list
         */
        if (portion.equals(ProductionUtils.washingCrushingList)) {
            getDataFromWashingViewModel();
            binding.toolbar.toolbarTitle.setText("Washing Crushing List");
        }

        /**
         this for washing & crushing list
         */
        if (portion.equals(ProductionUtils.pendingWashingCrushing)) {
            getWashingCrushingPendingList();
            binding.toolbar.toolbarTitle.setText("Washing Crushing Pending");
        }
        /**
         this for iodization list
         */
        if (portion.equals(ProductionUtils.iodizationList)) {
            binding.toolbar.toolbarTitle.setText("Iodization History");
            getDataFromIodizationViewModel();
        }
        if (portion.equals(ProductionUtils.pendingIodization)) {
            binding.toolbar.toolbarTitle.setText("Iodization Pending");
            getIOdizationPending();
        }
        /**
         this for packaging  list
         */
        if (portion.equals(ProductionUtils.packagingList)) {
            binding.toolbar.toolbarTitle.setText("Packaging List");
            geDataFromPackagingViewMOdel();
        }

        /**
         this for packeting  list
         */
        if (portion.equals(ProductionUtils.packatingList)) {
            binding.toolbar.toolbarTitle.setText("Cartoning List");
            getPacketingList();
        }

    }

    private void getIOdizationPending() {
        iodizationHostoryViewModel.getPendingList(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<IodizationPenDingResponse>() {
            @Override
            public void onChanged(IodizationPenDingResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);

                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "Something wrong");
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();
                        /**
                         * now set data in Recyclerview
                         */

                        iodizatinPendingLists.addAll(response.getLists());

                        PendingListAdapter adapter = new PendingListAdapter(getActivity(), iodizatinPendingLists);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);
                        /** set enterPrise */
                        setDatainEnterPrise(response.getEnterprizeList());
                        /**Now set data to company select Spinner */

                        setNameInSpinner(response.getItemList());


                    }
                }
            }
        });

    }

    private void getWashingCrushingPendingList() {
        washingViewModel.getWashingPendingList(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            try {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();


                    } else {
                        manageFilterBtnAndRvAndDataNotFound();   /**
                         * set washing crushing list data to recycler view
                         */

                        washingLists.addAll(response.getLists());

                        WashingPendingAdapter adapter = new WashingPendingAdapter(getActivity(), washingLists);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);

                        /**Now set data to company select Spinner */
                        setDatainEnterPrise(response.getEnterprizeList());
                        if (response.getItemList() == null) {
                            return;
                        }
                        setNameInSpinner(response.getItemList());

                        /** set enterPrise */

                    }
                }

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }

        });

    }

    private void getPacketingList() {

        packagingListViewModel.getPacketingList(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<PacketingListResponse>() {
            @Override
            public void onChanged(PacketingListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists().isEmpty() || response.getLists() == null) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();


                        /**
                         * now set data in Recyclerview
                         */

                        packetingProductionListLists.addAll(response.getLists());
                        PacketingListAdapter adapter = new PacketingListAdapter(getActivity(), packetingProductionListLists);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);
                        /**
                         * set enterprise data to enterprise spinner
                         */
                        setDatainEnterPrise(response.getEnterprizeList());
                        /**
                         * Now set data to company select Spinner
                         * */
                        setNameInSpinner(response.getItemList());

                    }
                }
            }
        });


    }

    private void geDataFromPackagingViewMOdel() {
        packagingListViewModel.getPackagingList(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<PackagingListResponse>() {
            @Override
            public void onChanged(PackagingListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists().isEmpty() || response.getLists() == null) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();

                        /**
                         * now set data in Recyclerview
                         */
                        packagingLists.addAll(response.getLists());
                        PackagingListAdapter adapter = new PackagingListAdapter(getActivity(), packagingLists);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);

                        /**
                         * set enterprise data to enterprise spinner
                         */
                        setDatainEnterPrise(response.getEnterprizeList());
                        /**
                         * Now set data to company select Spinner
                         * */
                        setNameInSpinner(response.getItemList());

                    }
                }
            }
        });
    }

    private void getDataFromIodizationViewModel() {
        iodizationHostoryViewModel.getIodizationHistory(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), new Observer<IodizationHistoryResponse>() {
            @Override
            public void onChanged(IodizationHistoryResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);

                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();
                        /**
                         * now set data in Recyclerview
                         */

                        iodizationHistoryLists.addAll(response.getLists());
                        IodizationHistoryAdapter adapter = new IodizationHistoryAdapter(getActivity(), iodizationHistoryLists);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);
                        /** set enterPrise */
                        setDatainEnterPrise(response.getEnterprizeList());
                        /**Now set data to company select Spinner */

                        setNameInSpinner(response.getItemList());


                    }
                }
            }
        });
    }

    private void getDataFromWashingViewModel() {
        washingViewModel.getWashing(getActivity(), String.valueOf(pageNumber), startDate, endDate, itemId, enterPriseId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            try {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();
                        /**
                         * set washing crushing list data to recycler view
                         */

                        washingList.addAll(response.getLists());

                        WashingAdapter adapter = new WashingAdapter(getActivity(), washingList);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        binding.productionRv.setLayoutManager(linearLayoutManager);
                        binding.productionRv.setHasFixedSize(true);
                        binding.productionRv.setAdapter(adapter);


                        /**Now set data to company select Spinner */
                        setDatainEnterPrise(response.getEnterprizeList());
                        if (response.getItemList() == null) {
                            return;
                        }
                        setNameInSpinner(response.getItemList());

                        /** set enterPrise */

                    }
                }

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
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

    private void setNameInSpinner(List<ProductionItem> list) {
        productionItems = new ArrayList<>();
        productionItems.clear();
        productionItems.addAll(list);
        companyNameList = new ArrayList<>();
        companyNameList.clear();

        for (int i = 0; i < productionItems.size(); i++) {
            companyNameList.add("" + productionItems.get(i).getProductTitle());
            if (itemId != null) {
                if (productionItems.get(i).getProductID().equals(itemId)) {
                    binding.sale.selectCompany.setSelection(i);
                }
            }
        }

        binding.sale.selectCompany.setItem(companyNameList);


    }


    private void managePaginationAndFilter() {

        if (isFirstLoad == 0) { // if filter time list is null.  so then, data_not_found will be visible
            binding.productionRv.setVisibility(View.GONE);
            binding.dataNotFound.setVisibility(View.VISIBLE);
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
        binding.dataNotFound.setVisibility(View.GONE);
        binding.productionRv.setVisibility(View.VISIBLE);
    }


    private void getPeriviousFragmentData() {
        assert getArguments() != null;
        portion = getArguments().getString("porson");
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
                break;
            case R.id.filterSearchBtn:
hideKeyboard(getActivity());
                /**
                 * for filter
                 */
                isFirstLoad = 0;
                washingList.clear();
                washingLists.clear();
                iodizationHistoryLists.clear();
                packagingLists.clear();
                packetingProductionListLists.clear();
                iodizatinPendingLists.clear();

                getAllListFromServer();
                break;
            case R.id.resetBtn:
                hideKeyboard(getActivity());
                startDate = null;
                endDate = null;
                itemId = null;
                enterPriseId = null;
                binding.sale.startDate.setText("");
                binding.sale.EndDate.setText("");
                pageNumber = 1;
                /**
                 * for filter
                 */
                isFirstLoad = 0;
                washingList.clear();
                washingLists.clear();
                iodizationHistoryLists.clear();
                packagingLists.clear();
                packetingProductionListLists.clear();
                iodizatinPendingLists.clear();

                if (nullChecked()) {
                    binding.dataNotFound.setVisibility(View.GONE);
                    binding.productionRv.setVisibility(View.VISIBLE);
                    getAllListFromServer();
                }

                break;
        }
    }

    private boolean nullChecked() {

        if (startDate == null && endDate == null && itemId == null && enterPriseId == null) {
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
    public void onStart() {
        super.onStart();
        washingList.clear();
        washingLists.clear();
        iodizationHistoryLists.clear();
        packagingLists.clear();
        packetingProductionListLists.clear();
        iodizatinPendingLists.clear();



        getPeriviousFragmentData();
        /**
         * getAll List  from server
         */
        getAllListFromServer();


        try {
            updateCurrentUserPermission(getActivity());
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
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