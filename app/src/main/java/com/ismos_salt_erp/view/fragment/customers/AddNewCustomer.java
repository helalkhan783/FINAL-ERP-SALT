package com.ismos_salt_erp.view.fragment.customers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.Common;
import com.ismos_salt_erp.CommonNormal;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.AddNewCustomerClickHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewCustomerBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MultipartBody;

public class AddNewCustomer extends AddUpDel {
    private FragmentAddNewCustomerBinding binding;
    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    MultipartBody.Part image = null;
    /**
     * For Division
     */
    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;
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
     * For Selected Customer
     */
    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;

    private String selectedDivision, selectedDistrict, selectedThana, selectedCustomerType;
    private DueCollectionViewModel dueCollectionViewModel;
    public String name, id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_customer, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        binding.toolbar.toolbarTitle.setText("Add New Customer");
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);

        getPageDataFromServer();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        binding.setClickHandle(new AddNewCustomerClickHandle() {
            @Override
            public void getImage() {
                forImage();
            }

            @Override
            public void submit() {
                if (binding.companyName.getText().toString().isEmpty()) {
                    binding.companyName.setError("Empty");
                    binding.companyName.requestFocus();
                    return;
                }
                if (binding.ownerName.getText().toString().isEmpty()) {
                    binding.ownerName.setError("Empty");
                    binding.ownerName.requestFocus();
                    return;
                }
                if (binding.phone.getText().toString().isEmpty()) {
                    binding.phone.setError("Empty");
                    binding.phone.requestFocus();
                    return;
                }
                if (!binding.phone.getText().toString().isEmpty()) {
                    if (!isValidPhone(binding.phone.getText().toString())) {
                        binding.phone.setError("Invalid Contact Number");
                        binding.phone.requestFocus();
                        return;
                    }
                }

                if (!binding.altPhone.getText().toString().isEmpty()) {
                    if (!isValidPhone(binding.altPhone.getText().toString())) {
                        binding.altPhone.setError("Invalid Number");
                        binding.altPhone.requestFocus();
                        return;
                    }
                }

                if (!binding.email.getText().toString().isEmpty()) {
                    if (!isValidEmail(binding.email.getText().toString())) {
                        binding.email.setError("Invalid Email");
                        binding.email.requestFocus();
                        return;
                    }
                }

                if (selectedDivision == null) {
                    message(getString(R.string.division_mes));
                    return;
                }
                if (selectedDistrict == null) {
                    message(getString(R.string.district_mes));
                    return;
                }
                if (selectedThana == null) {
                    message(getString(R.string.thana_mes));

                    return;
                }
                if (selectedCustomerType == null) {
                    message(getString(R.string.customer_mes));

                    return;
                }
                showDialog(getString(R.string.customer_add)); // show
            }
        });

        binding.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionResponseList.get(position).getDivisionId();
                binding.division.setEnableErrorLabel(false);

                getDistrictListByDivisionId(selectedDivision);
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

                getThanaListByDistrictId(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.thana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThana = thanaListsResponse.get(position).getUpazilaId();
                binding.thana.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.customerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomerType = selectedCustomerTypeIdList.get(position);
                binding.customerType.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }





    private void getCustomerList() {
        dueCollectionViewModel
                .apiCallForGetCustomers(
                        getActivity(),
                        getToken(getActivity().getApplication()),
                        getVendorId(getActivity().getApplication()),
                        binding.companyName.getText().toString().trim()
                );

        dueCollectionViewModel.getCustomerList()
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }


                    for (int i = 0; i < response.getLists().size(); i++) {
                        name = response.getLists().get(i).getCompanyName() + response.getLists().get(i).getCustomerFname();
                        id = response.getLists().get(i).getCustomerID();

                        Common ref = new CommonNormal();
                        ref.customerKey(name, id);


                        getActivity().onBackPressed();
                    }


                });

    }

    private void getPageDataFromServer() {
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
                    binding.division.setItem(divisionNameList);
                });


        /**
         * now set selected customer
         */
        selectedCustomerTypeName = new ArrayList<>();
        selectedCustomerTypeName.clear();
        selectedCustomerTypeIdList = new ArrayList<>();
        selectedCustomerTypeIdList.clear();

        selectedCustomerTypeName.addAll(Arrays.asList("General"));
        selectedCustomerTypeIdList.addAll(Arrays.asList("7"));
        binding.customerType.setItem(selectedCustomerTypeName);

    }

    private void getThanaListByDistrictId(String selectedDistrict) {
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
                        thanaNameList.add(response.getLists().get(i).getName());
                    }
                    binding.thana.setItem(thanaNameList);
                });
    }

    private void getDistrictListByDivisionId(String selectedDivision) {

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
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    binding.district.setItem(districtNameList);
                });
    }


    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");

    }

    @Override
    public void save() {
        String dueLimit = "0";
        if (!binding.dueLimit.getText().toString().isEmpty()) {
            dueLimit = binding.dueLimit.getText().toString();
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        customerViewModel.addNewCustomer(
                        getActivity(), binding.companyName.getText().toString(),
                        binding.ownerName.getText().toString(),
                        binding.phone.getText().toString(),
                        binding.altPhone.getText().toString(),
                        binding.email.getText().toString(),
                        selectedDivision, selectedDistrict, selectedThana, binding.bazar.getText().toString(),
                        binding.nid.getText().toString(),
                        binding.tin.getText().toString(),
                        "0",
                        "1",
                        selectedCustomerType,
                        binding.address.getText().toString(),
                        dueLimit,
                        image,
                        binding.note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    hideKeyboard(getActivity());
                    successMessage(getActivity().getApplication(), "" + response.getMessage());

                    getCustomerList();

                });
    }


}