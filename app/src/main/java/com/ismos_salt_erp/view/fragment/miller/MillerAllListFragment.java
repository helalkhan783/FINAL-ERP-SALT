package com.ismos_salt_erp.view.fragment.miller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.DeclineListAdapter;
import com.ismos_salt_erp.adapter.MillerHistoryListAdapter;
import com.ismos_salt_erp.adapter.MillerPendingListAdapter;
import com.ismos_salt_erp.databinding.FragmentMillerAllListBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.DeclineList;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.HistoryList;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.MillType;
import com.ismos_salt_erp.serverResponseModel.MillerPendingList;
import com.ismos_salt_erp.serverResponseModel.ProcessType;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.utils.MillerUtils;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.MillerDeclineViewModel;
import com.ismos_salt_erp.viewModel.MillerHistoryViewModel;
import com.ismos_salt_erp.viewModel.MillerPendingViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MillerAllListFragment extends BaseFragment implements View.OnClickListener {
    private FragmentMillerAllListBinding binding;
    private String portion;
    private MillerPendingViewModel millerPendingViewModel;
    private MillerHistoryViewModel millerHistoryViewModel;
    private MillerDeclineViewModel millerDeclineViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;

    /**
     * for division
     */
    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    List<DivisionResponse> divisionResponseList;
    List<String> divisionNameList;


    /**
     * For District
     */
    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;
    /**
     * For Thana
     */
    private List<ThanaList> thanaListsResponse;
    private List<String> thanaNameList;

    /**
     * for process type and millerTYpe
     */
    List<ProcessType> processTypeList;
    List<MillType> millTypeList;

    private boolean click = false;//for filter layout visible and gone
    ProgressDialog progressDialog;

    String selectedDivision, selectedDistrict, selectedThana, processType, millerType;

    /**
     * for pagination
     */
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;
    private boolean endScroll = false;
    private LinearLayoutManager linearLayoutManager;

    List<MillerPendingList> millerPendingLists = new ArrayList<>();
    List<DeclineList> declineLists = new ArrayList<>();
    List<HistoryList> historyLists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_all_list, container, false);


        millerPendingViewModel = new ViewModelProvider(this).get(MillerPendingViewModel.class);
        millerHistoryViewModel = new ViewModelProvider(this).get(MillerHistoryViewModel.class);
        millerDeclineViewModel = new ViewModelProvider(this).get(MillerDeclineViewModel.class);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);

        progressDialog = new ProgressDialog(getContext());// for multiple use
        linearLayoutManager = new LinearLayoutManager(getContext());// for multiple use


        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        /** for division List */

        getDivisionData();
/**
 * click
 * */
        setClick();




        /**
         * Now handle division item click
         */
        binding.sale.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionResponseList.get(position).getDivisionId();

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
        binding.sale.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                binding.sale.district.setEnableErrorLabel(false);
                /**
                 * Now get Thana list based on district
                 */
                getThanaListByDistrictId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * now handle Thana item click
         */
        binding.sale.upazila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThana = thanaListsResponse.get(position).getUpazilaId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * now handle process type
         */
        binding.sale.processType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processType = processTypeList.get(position).getProcessTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**
         * now handle process type
         */
        binding.sale.millerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                millerType = millTypeList.get(position).getMillTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /** for pagination **/
        binding.millerRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    try {
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

                                allListData();

                                loading = true;
                            }
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());

                    }

                }
            }
        });


        return binding.getRoot();
    }

    private void allListData() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.millerRv, binding.noDataFound)) {
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
         * pending Miller Profile
         * */
        if (portion.equals(MillerUtils.millreProfileList)) {
            binding.toolbar.toolbarTitle.setText("Miller Profile Pending List");
            getDataFromProfilePendingViewModel();
        }

        /**
         *   Miller Decline List
         * */
        if (portion.equals(MillerUtils.millerDeclineList)) {
            binding.toolbar.toolbarTitle.setText("Miller  Declined List");
            getDataFromDeclineViewModel();
        }
        /**
         *   Miller History List
         * */
        if (portion.equals(MillerUtils.millerHistoryList)) {
            binding.toolbar.toolbarTitle.setText("Miller History List");
            getDataFromMillerHistoryViewModel();
        }
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
                        divisionNameList.add("" + response.getDivisions().get(i).getName());
                    }
                    binding.sale.division.setItem(divisionNameList);
                });


    }

    private void getDistrictListByDivisionId() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), selectedDivision)
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
                    }
                    binding.sale.district.setItem(districtNameList);
                });
    }

    private void getThanaListByDistrictId() {

        millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), selectedDistrict)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        return;
                    }
                    if (response.getStatus() != 200) {
                        return;
                    }
                    thanaListsResponse = new ArrayList<>();
                    thanaListsResponse.clear();
                    thanaNameList = new ArrayList<>();
                    thanaNameList.clear();

                    thanaListsResponse.addAll(response.getLists());
                    for (int i = 0; i < response.getLists().size(); i++) {
                        thanaNameList.add("" + response.getLists().get(i).getName());
                    }
                    binding.sale.upazila.setItem(thanaNameList);
                });
    }

    private void setMillerType(List<MillType> millType) {
        millTypeList = new ArrayList<>();
        millTypeList = new ArrayList<>();
        millTypeList.clear();
        millTypeList.addAll(millType);

        List<String> millerTypeName = new ArrayList<>();
        millerTypeName.clear();
        for (int i = 0; i < millType.size(); i++) {
            millerTypeName.add("" + millType.get(i).getMillTypeName());
        }
        binding.sale.millerType.setItem(millerTypeName);
    }

    private void setProcessType(List<ProcessType> processType) {
        processTypeList = new ArrayList<>();
        processTypeList.clear();
        processTypeList.addAll(processType);

        List<String> processTypeName = new ArrayList<>();
        processTypeName.clear();
        for (int i = 0; i < processType.size(); i++) {
            processTypeName.add("" + processType.get(i).getProcessTypeName());
        }
        binding.sale.processType.setItem(processTypeName);
    }

    private void getDataFromDeclineViewModel() {
        millerDeclineViewModel.getMillerDeclineHistory(getActivity(), String.valueOf(pageNumber), processType, millerType, selectedDivision, selectedDistrict, selectedThana).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            if (response == null) {
                errorMessage(requireActivity().getApplication(), "something wrong");
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

                    try {
                        declineLists.addAll(response.getLists());
                        binding.millerRv.setLayoutManager(linearLayoutManager);
                        DeclineListAdapter adapter = new DeclineListAdapter(getActivity(), declineLists);
                        binding.millerRv.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }

                    /** now set processType*/
                    setProcessType(response.getProcessType());
                    /** now set miller type*/

                    setMillerType(response.getMillType());
                }
            }


        });
    }

    private void getDataFromMillerHistoryViewModel() {
        millerHistoryViewModel.getMillerHistory(getActivity(), String.valueOf(pageNumber), processType, millerType, selectedDivision, selectedDistrict, selectedThana).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
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


                    try {
                        historyLists.addAll(response.getLists());
                        binding.millerRv.setLayoutManager(linearLayoutManager);
                        MillerHistoryListAdapter adapter = new MillerHistoryListAdapter(getActivity(), historyLists);
                        binding.millerRv.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                    /** now set processType*/
                    setProcessType(response.getProcessType());
                    /** now set miller type*/

                    setMillerType(response.getMillType());
                }
            }
        });
    }

    private void getDataFromProfilePendingViewModel() {
        millerPendingViewModel.getPendingMillerList(getActivity(), String.valueOf(pageNumber), processType, millerType, selectedDivision, selectedDistrict, selectedThana).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            if (response.getLists().isEmpty() || response.getLists() == null) {
                managePaginationAndFilter();
            }

            manageFilterBtnAndRvAndDataNotFound();
            /**
             * set data in recycler view
             */
            try {
                millerPendingLists.addAll(response.getLists());
                MillerPendingListAdapter adapter = new MillerPendingListAdapter(getActivity(), millerPendingLists, getView());
                binding.millerRv.setAdapter(adapter);
                binding.millerRv.setLayoutManager(linearLayoutManager);//for multiple use
                binding.millerRv.setHasFixedSize(true);// for multiple use
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }


            /** now set processType*/
            setProcessType(response.getProcessType());

            /** now set miller type*/
            setMillerType(response.getMillType());

        });

    }

    /**
     *
     **/
    private void getPeriviousFragmentData() {
        assert getArguments() != null;
        portion = getArguments().getString("porson");
    }

    private void setClick() {
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.sale.filterSearchBtn.setOnClickListener(this);
        binding.sale.resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterBtn:
                ExpandableView response = new ExpandableView(getActivity(), binding.expandableView);
                response.response();
                break;
            case R.id.filterSearchBtn:
                pageNumber = 1;
                //for filter
                isFirstLoad = 0;
                millerPendingLists.clear();
                declineLists.clear();
                historyLists.clear();
                allListData();

                break;

            case R.id.resetBtn:
                selectedDivision = null;
                selectedDistrict = null;
                selectedThana = null;
                processType = null;
                millerType = null;
                binding.sale.division.clearSelection();
                binding.sale.district.clearSelection();
                binding.sale.upazila.clearSelection();

                //for filter
                isFirstLoad = 0;
                millerPendingLists.clear();
                declineLists.clear();
                historyLists.clear();
                if (nullChecked()) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.millerRv.setVisibility(View.VISIBLE);
                    allListData();
                    progressDialog.dismiss();
                }
                break;
        }
    }


    private void managePaginationAndFilter() {
        if (isFirstLoad == 0) { // if filter time list data is  null.  so then, data_not_found will be visible
            binding.millerRv.setVisibility(View.GONE);
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
        binding.millerRv.setVisibility(View.VISIBLE);
    }

    private boolean nullChecked() {
        if (selectedDivision == null &&
                selectedDistrict == null &&
                selectedThana == null &&
                processType == null &&
                millerType == null) {
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        getPeriviousFragmentData();

        /**
         * get all list data from server
         */
        allListData();
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