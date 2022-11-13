package com.ismos_salt_erp.view.fragment.user;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.EnterPriseAdapter;
import com.ismos_salt_erp.adapter.UserAccessibilityAdapter;
import com.ismos_salt_erp.adapter.UserListAdapter;
import com.ismos_salt_erp.adapter.UserPermissionAdapter;
import com.ismos_salt_erp.adapter.UserTrashListAdapter;
import com.ismos_salt_erp.clickHandle.UserAccessPageClickHandle;
import com.ismos_salt_erp.databinding.FragmentUserAllListBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.Department;
import com.ismos_salt_erp.serverResponseModel.Designation;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.ManageAccessibility;
import com.ismos_salt_erp.serverResponseModel.UserLists;
import com.ismos_salt_erp.utils.NoDataFoundCheckerShowBuddy;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.UserUtil;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.ManageUserViewModel;
import com.ismos_salt_erp.viewModel.UserManageAccessibilityViewModel;
import com.ismos_salt_erp.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class UserAllListFragment extends BaseFragment implements PermissionCheckedHandle, EnterPrisePermissionCheckedHandle, View.OnClickListener {
    private FragmentUserAllListBinding binding;
    private UserViewModel userViewModel;
    private UserManageAccessibilityViewModel userManageAccessibilityViewModel;
    private ManageUserViewModel manageUserViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;

    String portion, id, profileId;//store data from previous fragment
    /**
     * for designation And dept
     */
    private List<Designation> designationsList;
    private List<Department> departmentList;
    String deptNameId, designationId;
    /**
     * For store checked and unchecked Enterprise & permission
     */
    private Set<Integer> permissionsList;
    private Set<Integer> enterPrisePermissionsList;
    private final boolean click = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_all_list, container, false);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userManageAccessibilityViewModel = new ViewModelProvider(this).get(UserManageAccessibilityViewModel.class);
        manageUserViewModel = new ViewModelProvider(this).get(ManageUserViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);

        permissionsList = new HashSet<>();
        enterPrisePermissionsList = new HashSet<>();


        getDataFromPreviousFragment();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /** click */

        click();
/** get previous fragment data*/
        getAllListDataFromServer();
        /**
         * now update all permission information
         */
        binding.setClickHandle(new UserAccessPageClickHandle() {
            @Override
            public void submit() {
                validationAndSave();
            }

            @Override
            public void expendEnterpriseList() {//this method only for handle user permission list enterprise selection
                try {
                    if (binding.expendableEnterPriseList.isExpanded()) {
                        binding.expendableEnterPriseList.setExpanded(false);
                        return;
                    }
                    binding.expendableEnterPriseList.setExpanded(true);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getLocalizedMessage());
                }
            }
        });


        binding.filter.designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designationId = designationsList.get(position).getUserDesignationId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.filter.department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptNameId = departmentList.get(position).getDepartmentID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        return binding.getRoot();
    }

    private void validationAndSave() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        if (enterPrisePermissionsList.isEmpty()) {
            infoMessage(getActivity().getApplication(), "At least select one enterprise");
            return;
        }

        if (permissionsList.isEmpty()) {
            infoMessage(getActivity().getApplication(), "At least select one");
            return;
        }
        /**
         * all ok now send update to server
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        manageUserViewModel.submitPermissionUpdateInfo(getActivity(), id, profileId, permissionsList, enterPrisePermissionsList)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), " " + response.getMessage());
                        return;
                    }
                    Log.d("RESPONSE", "" + response.getMessage());
                    successMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                });
    }


    private void getAllListDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            binding.itemListRv.setVisibility(View.GONE);
            binding.noDataFound.setVisibility(View.VISIBLE);
            binding.noDataFound.setText("Please Check Your Internet Connection");
            return;
        }
        if (portion.equals(UserUtil.userList)) {
            getUserListFromServer();
            binding.toolbar.addBtn.setVisibility(View.VISIBLE);
            return;
        }
        if (portion.equals(UserUtil.userTrashList)) {
            getUserTrashListFromServer();
            return;
        }
        if (portion.equals(UserUtil.manageAccesability)) {
            getManageAccessibility();
            return;
        }
        /**
         * For permission
         */
        if (portion.equals("Manage User Permissions")) {
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            getAllManageUserPermissionList();
            return;
        }

    }

    private void getAllManageUserPermissionList() {
        binding.submit.setVisibility(View.VISIBLE);
        binding.message.setVisibility(View.VISIBLE);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        manageUserViewModel.getUsersPermissions(getActivity(), id)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), ""+response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getPermisssionLists() == null || response.getPermisssionLists().isEmpty()) {
                        binding.itemListRv.setVisibility(View.GONE);
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    /**
                     * set all enterprise list to enterprise recycler view
                     */
                    if (response.getEnterprises() != null) {
                        /**
                         * set all enterprise to recycler view
                         */
                        EnterPriseAdapter enterpriseAdapter = new EnterPriseAdapter(getActivity(), response.getEnterprises(), response.getUserEnterpriseAccess(), UserAllListFragment.this);
                        binding.enterpriseRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.enterpriseRv.setAdapter(enterpriseAdapter);
                    }


                    /**
                     * set All permission list to recycler view
                     */
                    UserPermissionAdapter adapter = new UserPermissionAdapter(getActivity(), response.getPermisssionLists(), response.getUserPermissions(), UserAllListFragment.this);
                    binding.itemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.itemListRv.setAdapter(adapter);
                    progressDialog.dismiss();

                    for (int i = 0; i < response.getUserPermissions().size(); i++) {
                        permissionsList.add(Integer.parseInt(response.getUserPermissions().get(i)));
                    }
                    /**
                     * now set previous selected enterprise
                     */
                    try {
                        List<Integer> enterprisePermissionList = PermissionUtil.currentUserPermissionList(response.getUserEnterpriseAccess().getStoreAccess());
                        enterPrisePermissionsList.addAll(enterprisePermissionList);
                    } catch (Exception e) {
                        Log.d("ERROR", "" + "enterprise" + e.getMessage());
                    }
                });

    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        portion = getArguments().getString("portion");
        id = getArguments().getString("id");
        profileId = getArguments().getString("profileId");
        binding.toolbar.toolbarTitle.setText(portion);
    }

    private void getUserListFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please check your internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userViewModel.getUserList(getActivity(), deptNameId, designationId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (new NoDataFoundCheckerShowBuddy(getActivity()).isEmptyObjectListWithRecyclerControl(response.getLists(), binding.noDataFound, binding.itemListRv)) {
                return;
            }

            /**
             * now set All User list to recyclerView
             */
// ekhane admin ke dekhte pabe na
            List<UserLists> userLists = new ArrayList<>();
            userLists.clear();
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.getLists().size(); i++) {
                if (!getVendorId(requireActivity().getApplication()).equals(response.getLists().get(i).getUserId())) {// mane admin na
                    userLists.add(response.getLists().get(i));
                }
            }


            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            UserListAdapter adapter = new UserListAdapter(getActivity(), userLists, getViewLifecycleOwner());
            binding.itemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.itemListRv.setAdapter(adapter);

            /** set designation list to spinner List */
            setInspiner(response.getDesignationList());

            /** for dept */
            setDept(response.getDepartmentList());
        });
    }

    private void setDept(List<Department> list) {
        List<String> deptNameList = new ArrayList<>();
        deptNameList.clear();

        departmentList = new ArrayList<>();
        departmentList.clear();
        departmentList.addAll(list);

        for (int i = 0; i < list.size(); i++) {
            deptNameList.add(list.get(i).getDepartmentName());
            if (deptNameId != null) {
                if (list.get(i).getDepartmentID().equals(deptNameId)) {
                    binding.filter.department.setSelection(i);
                }
            }

        }
        binding.filter.department.setItem(deptNameList);

    }

    private void setInspiner(List<Designation> list) {
        List<String> designationNameList = new ArrayList<>();
        designationNameList.clear();

        designationsList = new ArrayList<>();
        designationsList.clear();
        designationsList.addAll(list);


        for (int i = 0; i < list.size(); i++) {
            designationNameList.add("" + list.get(i).getDesignationName());

            if (designationId != null) {
                if (list.get(i).getUserDesignationId().equals(designationId)) {
                    binding.filter.designation.setSelection(i);
                }
            }
        }
        binding.filter.designation.setItem(designationNameList);

    }

    private void getUserTrashListFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userViewModel.getUserTrashList(getActivity()).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }

            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), " " + response.getMessage());
                return;
            }
            if (response.getLists().isEmpty()) {
                binding.itemListRv.setVisibility(View.GONE);
                binding.noDataFound.setVisibility(View.VISIBLE);
                return;
            }
            /**
             * no set All Trash list to recyclerView
             */
            UserTrashListAdapter adapter = new UserTrashListAdapter(getActivity(), response.getLists());
            binding.itemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.itemListRv.setAdapter(adapter);
        });
    }

    private void getManageAccessibility() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your internet Connection");
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userManageAccessibilityViewModel.getManageAccessibility(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null || response.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getLists().isEmpty()) {
                        binding.itemListRv.setVisibility(View.GONE);
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    /**
                     * now set All list to recyclerView
                     */

                    // ekhane login user nijeke dekhte pabena and admin ke dekhte pabena
                    List<ManageAccessibility> manageAccessibilityResponseList = new ArrayList<>();
                    manageAccessibilityResponseList.clear();
                    for (int i = 0; i < response.getLists().size(); i++) {
                        if (!getVendorId(getActivity().getApplication()).equals(response.getLists().get(i).getUserId())) {//mane admin na
                            if (!getUserId(getActivity().getApplication()).equals(response.getLists().get(i).getUserId())) {// mane user nije na
                                manageAccessibilityResponseList.add(response.getLists().get(i));
                            }
                        }

                    }

                    UserAccessibilityAdapter adapter = new UserAccessibilityAdapter(getActivity(), manageAccessibilityResponseList);
                    binding.itemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.itemListRv.setAdapter(adapter);

                });
    }

    @Override
    public void checkedPermission(int checkedPermissionId) {
        try {
            permissionsList.add(checkedPermissionId);
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    @Override
    public void unCheckedPermission(int unCheckedPermissionId) {
        try {
            permissionsList.remove(unCheckedPermissionId);
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    @Override
    public void checkedEnterPrise(Integer checkedEnterPriseId) {
        try {
            enterPrisePermissionsList.add(checkedEnterPriseId);
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    @Override
    public void unCheckedEnterPrise(Integer unCheckedEnterPriseId) {
        try {
            enterPrisePermissionsList.remove(unCheckedEnterPriseId);
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    private void click() {
        binding.toolbar.addBtn.setOnClickListener(this);
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.filter.filterSearchBtn.setOnClickListener(this);
        binding.filter.resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                Navigation.findNavController(getView()).navigate(R.id.action_userAllListFragment_to_addNewUser);
                break;
            case R.id.filterBtn:
                ExpandableView response = new ExpandableView(getActivity(), binding.expandableView);
                response.response();

                break;

            case R.id.filterSearchBtn:
                search();
                break;

            case R.id.resetBtn:

                deptNameId = null;
                designationId = null;
                if (deptNameId == null && designationId == null) {
                    binding.noDataFound.setVisibility(View.GONE);
                    binding.itemListRv.setVisibility(View.VISIBLE);
                    search();
                }
                break;
        }
    }

    private void search() {
        if (portion.equals(UserUtil.userList)) {
            getUserListFromServer();

        }
    }


    @Override
    public void onStart() {
        super.onStart();
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