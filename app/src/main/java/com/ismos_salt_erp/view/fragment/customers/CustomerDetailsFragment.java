package com.ismos_salt_erp.view.fragment.customers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentCustomerDetailsBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.CustomerDetailsResponse;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CustomerDetailsViewModel;

public class CustomerDetailsFragment extends BaseFragment implements View.OnClickListener {
    private FragmentCustomerDetailsBinding binding;
    private CustomerDetailsViewModel customerDetailsViewModel;

    String typeKey, status, pageName, customerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false);
        customerDetailsViewModel = new ViewModelProvider(this).get(CustomerDetailsViewModel.class);

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        /**
         * getPrevious Fragment Data
         */
        getPreviousFramentData();

        getDataFromServer();

        click();


        return binding.getRoot();
    }


    private void getDataFromServer() {
        if (typeKey.equals("22")) {
            getCustomerDetail();
        }

    }

    private void getCustomerDetail() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        customerDetailsViewModel.customerDetails(getActivity(), customerId, "7").observe(getViewLifecycleOwner(),
                new Observer<CustomerDetailsResponse>() {
                    @Override
                    public void onChanged(CustomerDetailsResponse response) {
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
                                binding.division.setText(":  " + response.getCustomerEditInfo().getDivision());
                                binding.district.setText(":  " + response.getCustomerEditInfo().getDistrict());
                                binding.thana.setText(":  " + response.getCustomerEditInfo().getThana());
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


                    }
                });
    }

    private void getPreviousFramentData() {
        if (!getArguments().isEmpty()) {
            typeKey = getArguments().getString("typeKey");
            customerId = getArguments().getString("customerId");//refOrderId
            status = getArguments().getString("status");
            pageName = getArguments().getString("pageName");
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
            customerDetailsViewModel.customerDetailsApprove(getActivity(), customerId, "7", binding.noteEditTExt.getText().toString()).observe(getViewLifecycleOwner(),
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
            return;
        }
        if (v.getId() == R.id.decline) {
            if (binding.noteEditTExt.getText().toString().isEmpty()) {
                binding.noteEditTExt.setError("Empty field");
                binding.noteEditTExt.requestFocus();
                return;
            }
            customerDetailsViewModel.declineCustomerEditDetails(getActivity(), customerId, "7", binding.noteEditTExt.getText().toString()).observe(getViewLifecycleOwner(),
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
            return;
        }
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
