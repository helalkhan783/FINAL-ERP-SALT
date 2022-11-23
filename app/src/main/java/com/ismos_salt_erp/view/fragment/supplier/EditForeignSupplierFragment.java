package com.ismos_salt_erp.view.fragment.supplier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
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
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentEditForeignSupplierBinding;
import com.ismos_salt_erp.serverResponseModel.CountryListResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditForeignSupplierFragment extends AddUpDel {
    FragmentEditForeignSupplierBinding binding;

    MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    MultipartBody.Part image = null;



    /**
     * For Selected Customer
     */
    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;


    /**
     * For Selected  country
     */

    private List<String> countryName;
    private List<CountryListResponse> countryResponsesList;

    private String editable, dueLimit, oldImage, countryId, type, totalAmount, customerId, selectedCustomerType;
    MultipartBody.Part logoBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_foreign_supplier, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        binding.toolbar.toolbarTitle.setText("Update Foreign Supplier");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        getPreviousFragmentData();

        getPageData();

        binding.updateBtn.setOnClickListener(v -> {
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
            if (countryId == null) {
                infoMessage(requireActivity().getApplication(), "Please select country");
                return;
            }
            if (selectedCustomerType == null) {
                infoMessage(requireActivity().getApplication(), "Please select supplier type");
                return;
            }
            hideKeyboard(getActivity());
             showDialog(getString(R.string.update_dialog_title));
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
        binding.country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryId = String.valueOf(countryResponsesList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.image.setOnClickListener(v -> forImage());


        return binding.getRoot();
    }

    private void getPreviousFragmentData() {
        customerId = getArguments().getString("customerId");
    }

    private void getPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity()).observe(getViewLifecycleOwner(), new Observer<MillerProfileInfoResponse>() {
            @Override
            public void onChanged(MillerProfileInfoResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something Wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {

                    countryResponsesList = new ArrayList<>();
                    countryResponsesList.clear();
                    countryResponsesList.addAll(response.getCountries());
                    countryName = new ArrayList<>();
                    countryName.clear();
                    //  binding.country.setItem(countryName);
                }


                /**
                 * now set selected customer
                 */
                selectedCustomerTypeName = new ArrayList<>();
                selectedCustomerTypeName.clear();
                selectedCustomerTypeIdList = new ArrayList<>();
                selectedCustomerTypeIdList.clear();
                countryResponsesList.addAll(response.getCountries());

                selectedCustomerTypeName.addAll(Arrays.asList("General"));
                selectedCustomerTypeIdList.addAll(Arrays.asList("5"));
                binding.customerType.setItem(selectedCustomerTypeName);
            }
        });

        ProgressDialog progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.show();
        customerViewModel.customerEditResponse(getActivity(), customerId, "5").observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog1.dismiss();
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
                        totalAmount = response.getInitialPaymentInfo().getTotalAmount();
                        dueLimit = response.getCustomerInfo().getDueLimit();
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
                        binding.tin.setText(response.getCustomerInfo().getTin());
                        binding.address.setText(response.getCustomerInfo().getAddress());
                        binding.note.setText(response.getCustomerInfo().getCustomerNote());
                        try {
                            Glide.with(getContext()).load(ImageBaseUrl.image_base_url + response.getCustomerInfo().getImage()).centerCrop().
                                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                                    into(binding.image);

                        } catch (NullPointerException e) {
                            Log.d("ERROR", e.getMessage());
                            Glide.with(getContext()).load(R.mipmap.ic_launcher).into(binding.image);
                        }

                        if (countryId == null) {
                            countryId = response.getCustomerInfo().getCountry();
                            for (int i = 0; i < countryResponsesList.size(); i++) {
                                /*if (countryResponsesList.get(i).getCountryID().equals(countryId)) {
                                    binding.country.setSelection(i);
                                }*/
                                countryName.add(countryResponsesList.get(i).getCountryName());
                            }
                            binding.country.setItem(countryName);

                            /**
                             * now show selected country
                             */
                            for (int i = 0; i < countryResponsesList.size(); i++) {
                                if (countryResponsesList.get(i).getCountryID().equals(countryId)) {
                                    binding.country.setSelection(i);
                                    break;
                                }
                            }

                        }

                        if (selectedCustomerType == null) {
                            selectedCustomerType = response.getCustomerInfo().getTypeID();
                            for (int i = 0; i < selectedCustomerTypeIdList.size(); i++) {
                                if (response.getCustomerInfo().getTypeID().equals(selectedCustomerType)) {
                                    binding.customerType.setSelection(i);
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        progressDialog.dismiss();
                    }

                });
        progressDialog1.dismiss();


    }


    private void ValidationAndSubmit() {
        double editAmount = 100;
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
                binding.email.getText().toString(), "0", "0", "0", "0",
                "0", binding.tin.getText().toString(), dueLimit, countryId, "5", binding.address.getText().toString(),
                totalAmount, editAmount, newImage, oldImage, binding.note.getText().toString(), "0", customerId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "ERROR");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        if (response.getStatus() == 200) {
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
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
        ValidationAndSubmit();
    }


    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");

    }


}