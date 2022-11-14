package com.ismos_salt_erp.view.fragment.supplier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentAddNewForeignSupplierBinding;
import com.ismos_salt_erp.serverResponseModel.CountryListResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MultipartBody;

public class AddNewForeignSupplierFragment extends AddUpDel {
    private FragmentAddNewForeignSupplierBinding binding;

    MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    MultipartBody.Part image = null;



    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;


    private List<String> countryName;
    private List<CountryListResponse> countryResponsesList;

    String countryId;
    String selectedCustomerType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_foreign_supplier, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);


        binding.toolbar.toolbarTitle.setText("Add Foreign Supplier");

        /** back control */
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        getPageData();
        setClick();

        selectedCustomerTypeName = new ArrayList<>();
        selectedCustomerTypeName.clear();
        selectedCustomerTypeIdList = new ArrayList<>();
        selectedCustomerTypeIdList.clear();

        selectedCustomerTypeName.addAll(Arrays.asList("General"));
        selectedCustomerTypeIdList.addAll(Arrays.asList("5"));
        binding.customerType.setItem(selectedCustomerTypeName);

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
                countryId = String.valueOf(countryResponsesList.get(position).getCountryID());
                binding.country.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void setClick() {
        binding.image.setOnClickListener(v -> {
            forImage();
        });

        binding.saveBtn.setOnClickListener(v -> {
            validation();

            showDialog(getString(R.string.foreign_sup_title));

        });
    }

    private void validation() {
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
                binding.phone.setError("Invalid Mobile Number");
                binding.phone.requestFocus();
                return;
            }
        }
        if (!binding.altPhone.getText().toString().isEmpty()) {
            if (!isValidPhone(binding.altPhone.getText().toString())) {
                binding.altPhone.setError("Invalid Mobile Number");
                binding.altPhone.requestFocus();
                return;
            }
        }
        if (!binding.email.getText().toString().isEmpty()) {
            if (!isValidEmail(binding.email.getText().toString())) {
                binding.email.setError("Invalid EmailX");
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


    }


    private void getPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
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
                    /**
                     * now set division list
                     */
                    countryResponsesList = new ArrayList<>();
                    countryResponsesList.clear();
                    countryResponsesList.addAll(response.getCountries());

                    countryName = new ArrayList<>();
                    countryName.clear();

                    for (int i = 0; i < response.getCountries().size(); i++) {
                        countryName.add(response.getCountries().get(i).getCountryName());
                    }
                    binding.country.setItem(countryName);

                }

                selectedCustomerTypeName = new ArrayList<>();
                selectedCustomerTypeName.clear();
                selectedCustomerTypeIdList = new ArrayList<>();
                selectedCustomerTypeIdList.clear();

                selectedCustomerTypeName.addAll(Arrays.asList("General"));
                selectedCustomerTypeIdList.addAll(Arrays.asList("5"));
                binding.customerType.setItem(selectedCustomerTypeName);
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


    private void submit() {


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        customerViewModel.addNewForeignSupplier(
                        getActivity(), binding.companyName.getText().toString(),
                        binding.ownerName.getText().toString(),
                        binding.phone.getText().toString(),
                        binding.altPhone.getText().toString(),
                        binding.email.getText().toString(),
                        "0", "0", "0", "0",
                        "0",
                        binding.licence.getText().toString(),
                        "0",
                        countryId,
                        "5",//here type for detect this not a local supplier
                        binding.address.getText().toString(),
                        "0",
                        image,
                        binding.note.getText().toString(), binding.licence.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "ERROR");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            Log.d("RESPONSE", "" + response.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                });
    }


}