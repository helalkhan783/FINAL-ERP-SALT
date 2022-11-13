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

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentAddNewLocalSupplierBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MultipartBody;


public class AddNewLocalSupplierFragment extends AddUpDel implements SmartMaterialSpinner.OnItemSelectedListener {
    private FragmentAddNewLocalSupplierBinding binding;
    MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    MultipartBody.Part image = null;

    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;


    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;

    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;
    /**
     * For Thana
     */
    private List<ThanaList> thanaListsResponse;
    private List<String> thanaNameList;


    private String selectedDivision, selectedDistrict, selectedThana;
    private String selectedCustomerType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_local_supplier, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        binding.toolbar.toolbarTitle.setText("Add Local Supplier");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();});

        getPageData();
        setClick();

        binding.division.setOnItemSelectedListener(this);
        binding.district.setOnItemSelectedListener(this);
        binding.thana.setOnItemSelectedListener(this);
        binding.customerType.setOnItemSelectedListener(this);

        return binding.getRoot();
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


    private void setClick() {
        binding.image.setOnClickListener(v -> {
            forImage();
        });

        binding.saveBtn.setOnClickListener(v -> {
            hideKeyboard(getActivity());
            showDialog(getString(R.string.local_supplier_title));//show dialog
            validation();
        });
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

                    divisionResponseList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionResponseList.addAll(response.getDivisions());

                    divisionNameList = new ArrayList<>();
                    divisionNameList.clear();

                    for (int i = 0; i < response.getDivisions().size(); i++) {
                        divisionNameList.add(response.getDivisions().get(i).getName());
                    }
                    binding.division.setItem(divisionNameList);

                }


                selectedCustomerTypeName = new ArrayList<>();
                selectedCustomerTypeName.clear();
                selectedCustomerTypeIdList = new ArrayList<>();
                selectedCustomerTypeIdList.clear();

                selectedCustomerTypeName.addAll(Arrays.asList("General"));
                selectedCustomerTypeIdList.addAll(Arrays.asList("1"));
                binding.customerType.setItem(selectedCustomerTypeName);
            }
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
            message(getString(R.string.supplier_mes));

            return;
        }
    }

    private void submit() {

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
                        "21",
                        selectedCustomerType,
                        binding.address.getText().toString(),
                        dueLimit,
                        image,
                        binding.note.getText().toString())
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
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                });
    }

    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");

    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            submit();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.division) {
            selectedDivision = divisionResponseList.get(position).getDivisionId();
            getDistrictListByDivisionId(selectedDivision);
        }
        if (parent.getId() == R.id.district) {
            selectedDistrict = districtListResponseList.get(position).getDistrictId();
            getThanaListByDistrictId(selectedDistrict);
        }
        if (parent.getId() == R.id.thana) {
            selectedThana = thanaListsResponse.get(position).getUpazilaId();
        }
        if (parent.getId() == R.id.customerType) {
            selectedCustomerType = selectedCustomerTypeIdList.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}