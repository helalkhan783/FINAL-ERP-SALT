package com.ismos_salt_erp.view.fragment.miller.editmiller;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.MillerCompanyOwnerInfoEditClickHandle;
import com.ismos_salt_erp.databinding.FragmentMillerCompanyOwnerInfoEditBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.OwnerDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MillerOwnerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;

import java.util.ArrayList;
import java.util.List;


public class MillerCompanyOwnerInfoEdit extends BaseFragment {
    private FragmentMillerCompanyOwnerInfoEditBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;
    private MillerOwnerViewModel millerOwnerViewModel;


    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private ViewPager viewPager;
    /**
     * for division
     */
    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;
    /**
     * for district
     */
    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;
    /**
     * for Thana
     */
    private List<ThanaList> thanaListsResponse;
    private List<String> thanaNameResponse;


    private OwnerDetailsResponse previousMillerInfoResponse;


    private String selectedDivision, selectedDistrict, selectedThana;

    private String sid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_company_owner_info_edit, container, false);
        millerOwnerViewModel = new ViewModelProvider(this).get(MillerOwnerViewModel.class);
        viewPager = getActivity().findViewById(R.id.viewPager);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        binding.toolbar.toolbarTitle.setText("Miller Owner Update");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        getDataFromPreviousFragment();
        /**
         * get Page Data from server
         */
        getPageDataFromServer();

        binding.setClickHandle(new MillerCompanyOwnerInfoEditClickHandle() {
            @Override
            public void save() {
                hideKeyboard(getActivity());
                validationAndSave();
            }

            @Override
            public void addNew() {

            }
        });

        binding.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionResponseList.get(position).getDivisionId();
                binding.division.setEnableErrorLabel(false);
                /**
                 * now set district based on the current division
                 */
                if (selectedDivision != null) {
                    getDistrictListByDivisionId(selectedDivision);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                binding.district.setEnableErrorLabel(false);
                /**
                 * now set thana based on the current district
                 */
                if (selectedDistrict != null) {
                    getThanaListByDistrictId(selectedDistrict);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.upazila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThana = thanaListsResponse.get(position).getUpazilaId();
                binding.upazila.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        sid = getArguments().getString("sl_id");
    }

    private void getPageDataFromServer() {

        /**
         * Now set Previous selected  data
         */
        getPreviousSelectedData();


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    divisionResponseList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionNameList = new ArrayList<>();
                    divisionNameList.clear();
                    divisionResponseList.addAll(response.getDivisions());
                    for (int i = 0; i < response.getDivisions().size(); i++) {
                        divisionNameList.add(response.getDivisions().get(i).getName());
                    }
                    binding.division.setItem(divisionNameList);

                    /**
                     * now set Previous selected division
                     */
                    if (previousMillerInfoResponse != null) {
                        for (int i = 0; i < divisionResponseList.size(); i++) {
                            if (!previousMillerInfoResponse.getOwnerData().isEmpty()) {
                                if (divisionResponseList.get(i).getDivisionId().equals(previousMillerInfoResponse.getOwnerData().get(0).getDivision())) {
                                    selectedDivision = divisionResponseList.get(i).getDivisionId();//set previous selected division
                                    binding.division.setSelection(i);
                                    getDistrictListByDivisionId(selectedDivision);
                                }
                            }

                        }
                    }


                });
    }

    /**
     * For set previous selected data
     */
    private void getPreviousSelectedData() {

        millerOwnerViewModel.getOwnerDetailsResponse(getActivity(), sid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    previousMillerInfoResponse = response;


                    try {
                        if (!previousMillerInfoResponse.getOwnerData().isEmpty()) {
                            binding.name.setText("" + previousMillerInfoResponse.getOwnerData().get(0).getOwnerName());
                            binding.nid.setText("" + response.getOwnerData().get(0).getNid());
                            binding.mobile.setText("" + response.getOwnerData().get(0).getMobileNo());
                            binding.altMobile.setText("" + response.getOwnerData().get(0).getAltMobile());
                            binding.email.setText("" + response.getOwnerData().get(0).getEmail());
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getLocalizedMessage());
                    }


                });

       /* updateMillerViewModel.getPreviousMillerInfoBySid(getActivity(), sid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    previousMillerInfoResponse = response;
                    try {
                        if (!previousMillerInfoResponse.getOwnerInfo().isEmpty()) {
                            binding.name.setText(previousMillerInfoResponse.getOwnerInfo().get(0).getOwnerName());
                            binding.nid.setText(response.getOwnerInfo().get(0).getNid());
                            binding.mobile.setText(response.getOwnerInfo().get(0).getMobileNo());
                            binding.altMobile.setText(response.getOwnerInfo().get(0).getAltMobile());
                            binding.email.setText(response.getOwnerInfo().get(0).getEmail());
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getLocalizedMessage());
                    }
                });*/
    }

    /**
     * for set district Name
     */
    private void getDistrictListByDivisionId(String selectedDivision) {

        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), selectedDivision)
                .observe(getViewLifecycleOwner(), response -> {

                    districtListResponseList = new ArrayList<>();
                    districtListResponseList.clear();
                    districtNameList = new ArrayList<>();
                    districtNameList.clear();
                    districtListResponseList.addAll(response.getLists());

                    for (int i = 0; i < districtListResponseList.size(); i++) {
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    binding.district.setItem(districtNameList);

                    if (previousMillerInfoResponse != null) {

                        try {
                            for (int i = 0; i < districtListResponseList.size(); i++) {
                                if (districtListResponseList.get(i).getDistrictId().equals(previousMillerInfoResponse.getOwnerData().get(0).getDistrict())) {
                                    selectedDistrict = districtListResponseList.get(i).getDistrictId();
                                    binding.district.setSelection(i);
                                    /**
                                     * now set previous selected thana by current district
                                     */
                                    getThanaListByDistrictId(selectedDistrict);
                                }
                            }

                        } catch (Exception e) {
                            Log.d("ERROR", "Something Wrong in  = " + this.getClass().getName() + "\n" + e.getMessage());
                        }

                    }

                });
    }

    /**
     * for set thana Name
     */
    private void getThanaListByDistrictId(String selectedDistrict) {
        millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), selectedDistrict)
                .observe(getViewLifecycleOwner(), response -> {

                    thanaListsResponse = new ArrayList<>();
                    thanaListsResponse.clear();
                    thanaNameResponse = new ArrayList<>();
                    thanaNameResponse.clear();
                    thanaListsResponse.addAll(response.getLists());
                    for (int i = 0; i < response.getLists().size(); i++) {
                        thanaNameResponse.add(response.getLists().get(i).getName());
                    }
                    binding.upazila.setItem(thanaNameResponse);
                    if (previousMillerInfoResponse != null) {
                        try {
                            for (int i = 0; i < thanaListsResponse.size(); i++) {
                                if (thanaListsResponse.get(i).getUpazilaId().equals(previousMillerInfoResponse.getOwnerData().get(0).getUpazila())) {
                                    selectedThana = thanaListsResponse.get(i).getUpazilaId();
                                    binding.upazila.setSelection(i);
                                }
                            }
                        } catch (Exception e) {
                            Log.d("ERROR", "Something Wrong in  = " + this.getClass().getName() + "\n" + e.getMessage());
                        }
                    }


                });
    }


    private void validationAndSave() {

        if (binding.name.getText().toString().isEmpty()) {
            binding.name.setError("Empty Field");
            binding.name.requestFocus();
            return;
        }

        if (selectedDivision == null) {
           infoMessage(getActivity().getApplication(), "Please select division");
           return;
        }
        if (selectedDistrict == null) {
            infoMessage(getActivity().getApplication(), "Please select district");
            return;

        }
        if (selectedThana == null) {
            infoMessage(getActivity().getApplication(), "Please select upazila/thana");
            return;

        }

        if (binding.mobile.getText().toString().isEmpty()) {
            binding.mobile.setError("Empty Field");
            binding.mobile.requestFocus();
            return;
        }
        if (!binding.altMobile.getText().toString().isEmpty()) {
            if (!isValidPhone(binding.altMobile.getText().toString())) {
                binding.altMobile.setError("Invalid Mobile Number");
                binding.altMobile.requestFocus();
                return;
            }
        }
        if (binding.email.getText().toString().isEmpty()) {
            binding.email.setError("Empty Field");
            binding.email.requestFocus();
            return;
        }
        if (!isValidEmail(binding.email.getText().toString())) {
            binding.email.setError("Invalid Email");
            binding.email.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        updateMillerViewModel.updateMillerOwnerInfo(
                getActivity(), binding.name.getText().toString(),previousMillerInfoResponse.getOwnerData().get(0).getProfileID(),
                selectedDivision, selectedDistrict, selectedThana, binding.nid.getText().toString(),
                binding.mobile.getText().toString(),binding.altMobile.getText().toString(), binding.email.getText().toString(),
                previousMillerInfoResponse.getOwnerData().get(0).getSlID()
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
            if (response.getStatus() != 200) {
                Log.d("ERROR", "ERROR");
                return;
            }
            hideKeyboard(getActivity());
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();
        });

    }
}