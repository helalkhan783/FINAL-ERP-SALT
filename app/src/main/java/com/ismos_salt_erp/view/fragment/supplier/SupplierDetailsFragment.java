package com.ismos_salt_erp.view.fragment.supplier;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentBlank2Binding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CustomerDetailsViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

public class SupplierDetailsFragment extends BaseFragment implements View.OnClickListener {
    private FragmentBlank2Binding binding;
    private CustomerDetailsViewModel customerDetailsViewModel;
    private MillerProfileInfoViewModel millerProfileInfoViewModel;

    private String typeKey, status, customerId, pageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank2, container, false);
        customerDetailsViewModel = new ViewModelProvider(this).get(CustomerDetailsViewModel.class);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        binding.toolbar.toolbarTitle.setText("Supplier Details");
        /**
         * get Previous data
         */
        getPreviousData();
        if (typeKey.equals("23")) {
            binding.divisionLayout.setVisibility(View.VISIBLE);
            binding.country.setVisibility(View.GONE);

        }
        if (typeKey.equals("24")) {
            binding.divisionLayout.setVisibility(View.GONE);
            binding.country.setVisibility(View.VISIBLE);
        }
        /**
         * click handle
         */
        click();
        getPageData();
        return binding.getRoot();
    }

    private void getPageData() {
        if (typeKey.equals("23")) {
            getLocalSupplierData("1");

        }
        if (typeKey.equals("24")) {
            getLocalSupplierData("5");

        }
    }

    private void getLocalSupplierData(String supplierType) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        customerDetailsViewModel.customerDetails(getActivity(), customerId, supplierType).observe(getViewLifecycleOwner(),
                response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        /**
                         * now set data to view
                         */
                        try {
                            binding.company.setText(":  " + response.getCustomerEditInfo().getCompanyName());
                            binding.ownerName.setText(":  " + response.getCustomerEditInfo().getCustomerFname());
                            binding.phone.setText(":  " + response.getCustomerEditInfo().getPhone());
                            binding.altPhone.setText(":  " + response.getCustomerEditInfo().getAltPhone());
                            binding.email.setText(":  " + response.getCustomerEditInfo().getEmail());
                            binding.country.setText(":  " + response.getCustomerEditInfo().getCountry());


                            /**
                             * For set Selected Division
                             */
                            millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                                    .observe(getViewLifecycleOwner(), divisionResponse -> {
                                        if (divisionResponse == null) {
                                            infoMessage(getActivity().getApplication(), "Something Wrong");
                                            return;
                                        }
                                        if (divisionResponse.getStatus() == 400) {
                                            infoMessage(getActivity().getApplication(), "" + divisionResponse.getMessage());
                                            return;
                                        }

                                        for (int i = 0; i < divisionResponse.getDivisions().size(); i++) {
                                            if (divisionResponse.getDivisions().get(i).getDivisionId().equals(response.getCustomerEditInfo().getDivision())){
                                                binding.division.setText(":  "+divisionResponse.getDivisions().get(i).getName());
                                                break;
                                            }
                                        }

                                    });

                            if (response.getCustomerEditInfo().getDivision() != null) {
                                millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), response.getCustomerEditInfo().getDivision())
                                        .observe(getViewLifecycleOwner(), districtResponse -> {
                                            if (districtResponse == null) {
                                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                                return;
                                            }
                                            if (districtResponse.getStatus() == 400) {
                                                infoMessage(getActivity().getApplication(), "" + districtResponse.getMessage());
                                                return;
                                            }
                                            /**
                                             * For set selected District based on division id
                                             */
                                            for (int i = 0; i < districtResponse.getLists().size(); i++) {
                                                if (districtResponse.getLists().get(i).getDistrictId().equals(response.getCustomerEditInfo().getDistrict())) {
                                                    binding.district.setText(":  " + districtResponse.getLists().get(i).getName());
                                                    break;
                                                }
                                            }
                                            /**
                                             *  For set selected thana based on district id
                                             */
                                            millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), response.getCustomerEditInfo().getDistrict())
                                                    .observe(getViewLifecycleOwner(), thanaResponse -> {
                                                        if (response == null) {
                                                            errorMessage(getActivity().getApplication(), "Something Wrong");
                                                            return;
                                                        }
                                                        if (response.getStatus() == 400) {
                                                            infoMessage(getActivity().getApplication(), ":  " + response.getMessage());
                                                            return;
                                                        }
                                                        for (int i = 0; i < thanaResponse.getLists().size(); i++) {
                                                            if (thanaResponse.getLists().get(i).getUpazilaId().equals(response.getCustomerEditInfo().getThana())) {
                                                                binding.thana.setText(":  " + thanaResponse.getLists().get(i).getName());
                                                                break;
                                                            }
                                                        }

                                                    });


                                        });
                            }
                            binding.country.setText(":  "+response.getCustomerEditInfo().getCountry());
                            binding.bazar.setText(":  " + response.getCustomerEditInfo().getBazar());
                            binding.nid.setText(":  " + response.getCustomerEditInfo().getNid());
                            binding.tin.setText(":  " + response.getCustomerEditInfo().getTin());
                            binding.customerType.setText(":  " + response.getCustomerEditInfo().getTypeID());
                            binding.address.setText(":  " + response.getCustomerEditInfo().getAddress());
                            binding.note.setText(":  " + response.getCustomerEditInfo().getCustomerNote());
                            if (response.getCustomerEditInfo().getImage() != null) {
                                binding.imageName.setText(String.valueOf(response.getCustomerEditInfo().getImage()));
                            }

                            try {
                                Glide.with(getContext()).load(ImageBaseUrl.image_base_url + response.getCustomerEditInfo().getImage()).centerCrop().
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

                    }
                    progressDialog.dismiss();


                });

    }

    private void getPreviousData() {

        if (!getArguments().isEmpty()) {
            typeKey = getArguments().getString("typeKey");
            status = getArguments().getString("status");
            customerId = getArguments().getString("customerId");//supplier id
            pageName = getArguments().getString("pageName");//supplier id
            binding.toolbar.toolbarTitle.setText(pageName);
        }

    }

    private void click() {
        binding.approve.setOnClickListener(this);
        binding.decline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.approve) {
            if (binding.noteEditTExt.getText().toString().isEmpty()) {
                binding.noteEditTExt.setError("Empty field");
                binding.noteEditTExt.requestFocus();
                return;
            }
            if (typeKey.equals("23")) {
                approveLocalSupplier("1");
            }
            if (typeKey.equals("24")) {
                approveLocalSupplier("5");
            }
            return;
        }

        if (v.getId() == R.id.decline) {

            if (binding.noteEditTExt.getText().toString().isEmpty()) {
                binding.noteEditTExt.setError("Empty field");
                binding.noteEditTExt.requestFocus();
                return;
            }
            if (typeKey.equals("23")) {
                declineSupplierEditDetails("1");
                return;
            }
            if (typeKey.equals("24")) {
                declineSupplierEditDetails("5");
            }
            return;
        }
    }

    private void declineSupplierEditDetails(String supplierType) {
        customerDetailsViewModel.declineCustomerEditDetails(getActivity(), customerId, supplierType, binding.noteEditTExt.getText().toString()).observe(getViewLifecycleOwner(),
                response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        return;
                    }
                    hideKeyboard(getActivity());
                    successMessage(getActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                });
    }

    private void approveLocalSupplier(String supplierType) {
        customerDetailsViewModel.customerDetailsApprove(getActivity(), customerId, supplierType, binding.noteEditTExt.getText().toString()).observe(getViewLifecycleOwner(),
                response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        return;
                    }
                    hideKeyboard(getActivity());
                    successMessage(getActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (status == null) {

            return;
        }
        if (getProfileTypeId(getActivity().getApplication()).equals("7") && status.equals("2")) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1445)) {
                binding.btnLayout.setVisibility(View.VISIBLE);
            }
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1)) {
                binding.btnLayout.setVisibility(View.VISIBLE);
            }
        }
    }

}