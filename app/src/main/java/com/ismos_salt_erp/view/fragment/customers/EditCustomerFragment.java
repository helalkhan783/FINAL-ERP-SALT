package com.ismos_salt_erp.view.fragment.customers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentEditCustomerBinding;
import com.ismos_salt_erp.serverResponseModel.CustomerEditResponse;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;


public class EditCustomerFragment extends AddUpDel {
    private FragmentEditCustomerBinding binding;
    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;

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

    private String editable, dueLimit, country, oldImage, type, totalAmount, selectedDistrict, selectedDivision, selectedThana, customerId, selectedCustomerType;
    MultipartBody.Part image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_customer, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.toolbar.toolbarTitle.setText("Edit Customer Info");
        /**
         * get previous fragment data
         */
        previousfragmentData();
        /**
         * get page data
         */

        getPageDataFromServer();

        /**
         * Now handle division item click
         */
        binding.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDivision = divisionResponseList.get(position).getDivisionId();
                binding.division.setEnableErrorLabel(false);
                /**
                 * Now get district list based on division id
                 */
                getDistrictListByDivisionId(selectedDivision);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * Nw handle district item click
         */
        binding.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                binding.district.setEnableErrorLabel(false);
                /**
                 * Now get Thana list based on district
                 */
                getThanaListByDistrictId(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * now handle Thana item click
         */
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
        /**
         * now handle selected customer item click
         */

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

        binding.image.setOnClickListener(v -> {
            forImage();
        });
        binding.updateCustomer.setOnClickListener(v -> {
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please check your internet connection");
                return;
            }
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
            showDialog(getString(R.string.dialog_title_update_customer));
        });


        return binding.getRoot();
    }


    private void previousfragmentData() {
        if (!getArguments().isEmpty()) {
            customerId = getArguments().getString("customerId");
        }
    }

    private void getPageDataFromServer() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        /**
         * For get Division list from This Api
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
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
/*
                    binding.division.setItem(divisionNameList);
*/

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


        customerViewModel.customerEditResponse(getActivity(), customerId, "7").observe(getViewLifecycleOwner(),
                new Observer<CustomerEditResponse>() {
                    @Override
                    public void onChanged(CustomerEditResponse response) {
                        try {
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something wrong");
                                return;
                            }
                            if (response.getStatus() == 400) {
                                infoMessage(requireActivity().getApplication(), response.getMessage());
                                return;
                            }

                            editable = String.valueOf(response.getInitialAmountEditable());
                            if (editable.equals("2")) {//
                                binding.dueLimit.setFocusable(false);
                            }
                            totalAmount = response.getInitialPaymentInfo().getTotalAmount();
                            dueLimit = response.getCustomerInfo().getDueLimit();
                            country = response.getCustomerInfo().getCountry();
                            type = response.getCustomerInfo().getTypeID();


                            oldImage = String.valueOf(response.getCustomerInfo().getImage());

                            /**
                             * now set data to view
                             */
                            binding.companyName.setText(response.getCustomerInfo().getCompanyName());
                            binding.ownerName.setText(response.getCustomerInfo().getCustomerFname());
                            binding.phone.setText(response.getCustomerInfo().getPhone());
                            binding.altPhone.setText(response.getCustomerInfo().getAltPhone());
                            binding.email.setText(response.getCustomerInfo().getEmail());
                            binding.bazar.setText(response.getCustomerInfo().getBazar());
                            binding.nid.setText(response.getCustomerInfo().getNid());
                            binding.tin.setText(response.getCustomerInfo().getTin());
                            binding.address.setText(response.getCustomerInfo().getAddress());
                            binding.dueLimit.setText("" + response.getInitialPaymentInfo().getTotalAmount());
                            binding.note.setText(response.getCustomerInfo().getCustomerNote());
                            try {
                                Glide.with(getContext()).load(ImageBaseUrl.image_base_url + response.getCustomerInfo().getImage()).centerCrop().
                                        error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                                        into(binding.image);

                            } catch (NullPointerException e) {
                                Log.d("ERROR", e.getMessage());
                                Glide.with(getContext()).load(R.mipmap.ic_launcher).into(binding.image);
                            }


                        } catch (Exception e) {
                            Log.d("Error", e.getMessage());
                            progressDialog.dismiss();
                        }
                        if (selectedDivision == null) {
                            selectedDivision = response.getCustomerInfo().getDivision();
                            try {
                                for (int i = 0; i < divisionResponseList.size(); i++) {
                                    if (divisionResponseList.get(i).getDivisionId().equals(selectedDivision)) {
                                        binding.division.setSelection(i);
                                    }
                                    divisionNameList.add(divisionResponseList.get(i).getName());
                                }
                                binding.division.setItem(divisionNameList);
                            } catch (Exception e) {
                                Log.d("ERROR", "" + e.getMessage());
                            }
                        }
                        /**
                         * set previous division
                         */

                        if (selectedDistrict == null) {
                            selectedDistrict = response.getCustomerInfo().getDistrict();
                        }

                        if (selectedThana == null) {
                            selectedThana = response.getCustomerInfo().getThana();
                        }

                        getDistrictListByDivisionId(selectedDivision);
                        getThanaListByDistrictId(selectedDistrict);

                        try {
                            if (selectedCustomerType == null) {
                                selectedCustomerType = response.getCustomerInfo().getTypeID();
                                for (int i = 0; i < selectedCustomerTypeIdList.size(); i++) {
                                    if (response.getCustomerInfo().getTypeID().equals(selectedCustomerType)) {
                                        binding.customerType.setSelection(i);
                                    }
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            Log.d("Error", e.getMessage());
                            progressDialog.dismiss();
                        }

                    }
                });


        progressDialog.dismiss();
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
                        /**
                         * set previous district
                         */
                        if (selectedDistrict != null) {
                            if (response.getLists().get(i).getDistrictId().equals(selectedDistrict)) {
                                binding.district.setSelection(i);

                            }
                        }
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    binding.district.setItem(districtNameList);
                });
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

                        /**
                         * set previous thana
                         */
                        if (selectedThana != null) {
                            if (response.getLists().get(i).getUpazilaId().equals(selectedThana)) {
                                binding.thana.setSelection(i);


                            }
                        }
                        thanaNameList.add(response.getLists().get(i).getName());
                    }
                    binding.thana.setItem(thanaNameList);
                });
    }

    private void submit() {

        double editAmount = 0;
        if (!binding.dueLimit.getText().toString().isEmpty()) {
            editAmount = Double.parseDouble(binding.dueLimit.getText().toString());
        }
        if (editable.equals("0")) {
            double total = editAmount + Integer.parseInt(totalAmount);
            if (image == null) {
                image = null;
                updateData(String.valueOf(editAmount), String.valueOf(total), oldImage, image);
                return;
            }
            updateData(String.valueOf(editAmount), String.valueOf(total), "", image);

        }
        if (editable.equals("1")) {
            if (image == null) {
                image = null;
                updateData(String.valueOf(editAmount), totalAmount, oldImage, image);
                return;
            }

            updateData(String.valueOf(editAmount), totalAmount, "", image);

        }
        if (editable.equals("2")) {
            if (image == null) {
                image = null;
                updateData("", totalAmount, oldImage, image);
                return;
            }
            updateData("", totalAmount, "", image);
        }
    }

    private void updateData(String editAmount, String totalAmount, String oldImage, MultipartBody.Part newImage) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        customerViewModel.editCustomer(getActivity(), binding.companyName.getText().toString(),
                        binding.ownerName.getText().toString(), binding.phone.getText().toString(), binding.altPhone.getText().toString(),
                        binding.email.getText().toString(), selectedDivision, selectedDistrict, selectedThana, binding.bazar.getText().toString(),
                        binding.nid.getText().toString(), binding.tin.getText().toString(), dueLimit, country, type, binding.address.getText().toString(),
                        totalAmount, editAmount, newImage, oldImage, binding.note.getText().toString(), binding.editNote.getText().toString(), customerId)
                .observe(getViewLifecycleOwner(), response -> {

                    try {
                        if (response == null || response.getStatus() == 500) {
                            errorMes("");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        if (response.getStatus() == 200) {
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            progressDialog.dismiss();
                            getActivity().onBackPressed();
                            return;
                        }


                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Log.d("ERROR", e.getMessage());
                    }
                });
    }

    @Override
    public void save() {
        submit();
    }


    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");
    }


}