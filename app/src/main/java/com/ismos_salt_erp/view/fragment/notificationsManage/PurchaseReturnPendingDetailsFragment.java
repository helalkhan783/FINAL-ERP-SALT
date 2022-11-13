package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentPurchaseReturnPendingDetailsBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingOrderDetail;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.adapter.PurchaseReturnOrderDetailsAdapter;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.PendingSalesReturnViewModel;
import com.ismos_salt_erp.viewModel.PurchaseReturnPendingDetailsViewModel;
import com.ismos_salt_erp.viewModel.PurchaseReturnViewModel;

import java.util.ArrayList;
import java.util.List;


public class PurchaseReturnPendingDetailsFragment extends AddUpDel {
    private FragmentPurchaseReturnPendingDetailsBinding binding;
    private PendingSalesReturnViewModel pendingSalesReturnViewModel;
    private String id, orderVendorId, portion, pageName, enterprise, status;
    private PurchaseReturnPendingDetailsViewModel purchaseReturnPendingDetailsViewModel;
    private PurchaseReturnViewModel purchaseReturnViewModel;
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_return_pending_details, container, false);
        pendingSalesReturnViewModel = new ViewModelProvider(this).get(PendingSalesReturnViewModel.class);
        getPreviousFragmentData();
        purchaseReturnPendingDetailsViewModel = new ViewModelProvider(this).get(PurchaseReturnPendingDetailsViewModel.class);
        purchaseReturnViewModel = new ViewModelProvider(this).get(PurchaseReturnViewModel.class);
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


        /**
         * Here check Permission for control/manage approve decline based on permission and portion
         */
        if (portion.equals("PENDING_PURCHASE")) {//for pending purchase return
            //check permission first
            checkPermission(1314);
            /**
             * now get purchase return details
             */
            getPageData();
        }
        if (portion.equals("SALES_RETURNS_DETAILS")) {
            //check permission first
            checkPermission(1311);
            /**
             * now get sale return details
             */
            getSaleReturnDetailsPageData();
        }


        /**
         * For Approve
         */
        binding.approveBtn.setOnClickListener(v -> {
            if (binding.NoteEt.getText().toString().isEmpty()) {
                binding.NoteEt.setError("Empty");
                binding.NoteEt.requestFocus();
                return;
            }
            approval = true;
            showDialog(getString(R.string.approve_dialog_title));

        });

        /**
         * For Decline pending purchase return
         */
        binding.declineBtn.setOnClickListener(v -> {

            if (binding.NoteEt.getText().toString().isEmpty()) {
                binding.NoteEt.setError("Empty");
                binding.NoteEt.requestFocus();
                return;
            }
            approval = false;
            showDialog(getString(R.string.decline_dialog_title));

        });


        return binding.getRoot();
    }

    private void getSaleReturnDetailsPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        String currentVendorId = null;
        if (orderVendorId != null) {
            currentVendorId = orderVendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        purchaseReturnPendingDetailsViewModel.getSaleReturnPendingDetails(getActivity(), currentVendorId, id)
                .observe(getViewLifecycleOwner(), response -> {
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(requireActivity().getApplication(), response.getMessage());
                            return;
                        }
                        /**
                         * now set data to view
                         */
                        binding.poNoTv.setText(":  " + response.getOrderinfo().getOrderSerial());
                        binding.orderSerial.setText(":  " + response.getOrderinfo().getOrderID());
                        binding.poDateTv.setText(":  " + response.getOrderinfo().getOrderDate());

                        binding.supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                        binding.companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                        binding.supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                        binding.addressTv.setText(":  " + response.getCustomer().getAddress());


                        List<PurchaseReturnPendingOrderDetail> currentReturnList = new ArrayList<>();
                        List<PurchaseReturnPendingOrderDetail> currentQuantityList = new ArrayList<>();
                        List<PurchaseReturnPendingOrderDetail> alreadyReturnList = new ArrayList<>();


                        for (int i = 0; i < response.getOrderDetails().size(); i++) {
                            //405 means current return quantity
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("404")/* for sale **/) {
                                currentReturnList.add(response.getOrderDetails().get(i));
                            }
                        }
                        if (currentReturnList.isEmpty() || currentReturnList == null) {
                            binding.currentReturnListRvPortion.setVisibility(View.GONE);
                            binding.currentReturnListRv.setVisibility(View.GONE);
                        }
                        if (!currentReturnList.isEmpty()) {
                            PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), currentReturnList);
                            binding.currentReturnListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.currentReturnListRv.setAdapter(adapter);
                        }


                        for (int i = 0; i < response.getOrderDetails().size(); i++) {//for purchase return
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("402")/* for sale **/) {
                                currentQuantityList.add(response.getOrderDetails().get(i));
                            }
                        }
                        if (currentQuantityList.isEmpty()) {
                            binding.currentQuantityPortion.setVisibility(View.GONE);
                            binding.currentQuantity.setVisibility(View.GONE);
                        }
                        if (!currentQuantityList.isEmpty()) {
                            PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), currentQuantityList);
                            binding.currentQuantity.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.currentQuantity.setAdapter(adapter);
                        }


                        for (int i = 0; i < response.getOrderDetails().size(); i++) {//for sales return
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("3")/* for sale **/) {
                                alreadyReturnList.add(response.getOrderDetails().get(i));
                            }
                        }
                        if (alreadyReturnList.isEmpty() || alreadyReturnList == null) {
                            binding.alreadyReturnListRvPortion.setVisibility(View.GONE);
                            binding.alreadyReturnListRv.setVisibility(View.GONE);
                        }
                        if (!alreadyReturnList.isEmpty()) {
                            PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), alreadyReturnList);
                            binding.alreadyReturnListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.alreadyReturnListRv.setAdapter(adapter);
                        }

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }


                });
    }

    private void checkPermission(int permissionKey) {
        if (status != null) {//status get from notification list adapter
            if (status.equals("2")) {
                try {
                    if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                        if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) |
                                PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(permissionKey)) {
                            binding.approveDeclineOption.setVisibility(View.VISIBLE);
                        } else {
                            binding.approveDeclineOption.setVisibility(View.GONE);
                        }
                    } else {
                        binding.approveDeclineOption.setVisibility(View.GONE);
                    }
                } catch (Exception E) {
                    Log.d("ERROR", "" + E.getMessage());
                }
            }
        } else {
            binding.approveDeclineOption.setVisibility(View.GONE);
        }
    }

    private void getPageData() {
        String currentVendorId = null;
        if (orderVendorId != null) {
            currentVendorId = orderVendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        purchaseReturnPendingDetailsViewModel.getPurchaseReturnPendingDetails(getActivity(), currentVendorId, id).observe(getViewLifecycleOwner(), new Observer<PurchaseReturnPendingDetailsResponse>() {
            @Override
            public void onChanged(PurchaseReturnPendingDetailsResponse response) {
                try {

                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    /**
                     * now set data to view
                     */
                    binding.poNoTv.setText(":  " + response.getOrderinfo().getOrderSerial());
                    binding.orderSerial.setText(":  " + response.getOrderinfo().getOrderID());
                    binding.poDateTv.setText(":  " + response.getOrderinfo().getOrderDate());

                    binding.supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                    binding.companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                    binding.supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                    binding.addressTv.setText(":  " + response.getCustomer().getAddress());


                    List<PurchaseReturnPendingOrderDetail> currentReturnList = new ArrayList<>();
                    List<PurchaseReturnPendingOrderDetail> currentQuantityList = new ArrayList<>();
                    List<PurchaseReturnPendingOrderDetail> alreadyReturnList = new ArrayList<>();
                    for (int i = 0; i < response.getOrderDetails().size(); i++) {
                        //405 means current return quantity
                        if (response.getOrderDetails().get(i).getSalesTypeID().equals("405")) {
                            currentReturnList.add(response.getOrderDetails().get(i));
                        }
                    }

                    if (currentReturnList.isEmpty() || currentReturnList == null) {
                        binding.currentReturnListRvPortion.setVisibility(View.GONE);
                        binding.currentReturnListRv.setVisibility(View.GONE);
                    }
                    if (!currentReturnList.isEmpty()) {
                        PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), currentReturnList);
                        binding.currentReturnListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.currentReturnListRv.setAdapter(adapter);
                    }


                    for (int i = 0; i < response.getOrderDetails().size(); i++) {
                        if (response.getOrderDetails().get(i).getSalesTypeID().equals("401")) {
                            currentQuantityList.add(response.getOrderDetails().get(i));
                        }
                    }
                    if (currentQuantityList.isEmpty()) {
                        binding.currentQuantityPortion.setVisibility(View.GONE);
                        binding.currentQuantity.setVisibility(View.GONE);
                    }
                    if (!currentQuantityList.isEmpty()) {
                        PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), currentQuantityList);
                        binding.currentQuantity.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.currentQuantity.setAdapter(adapter);
                    }


                    for (int i = 0; i < response.getOrderDetails().size(); i++) {
                        if (response.getOrderDetails().get(i).getSalesTypeID().equals("4")) {
                            alreadyReturnList.add(response.getOrderDetails().get(i));
                        }
                    }
                    if (alreadyReturnList.isEmpty() || alreadyReturnList == null) {
                        binding.alreadyReturnListRvPortion.setVisibility(View.GONE);
                        binding.alreadyReturnListRv.setVisibility(View.GONE);
                    }
                    if (alreadyReturnList.isEmpty()) {
                        PurchaseReturnOrderDetailsAdapter adapter = new PurchaseReturnOrderDetailsAdapter(getActivity(), alreadyReturnList);
                        binding.alreadyReturnListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.alreadyReturnListRv.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    private void getPreviousFragmentData() {
        id = getArguments().getString("RefOrderId");
        orderVendorId = getArguments().getString("orderVendorId");
        portion = getArguments().getString("portion");
        pageName = getArguments().getString("pageName");
        enterprise = getArguments().getString("enterprise");
        status = getArguments().getString("status");
        binding.toolbar.toolbarTitle.setText(pageName);
    }


    public void approveSubmit() {

        if (portion.equals("PENDING_PURCHASE")) {
            purchaseReturnViewModel.approvePurchaseReturn(getActivity(), id, binding.NoteEt.getText().toString())
                    .observe(getViewLifecycleOwner(), response -> {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                    });
        }
        if (portion.equals("SALES_RETURNS_DETAILS")) {
            pendingSalesReturnViewModel.approvePendingSalesReturnDetails(getActivity(), id, binding.NoteEt.getText().toString())
                    .observe(getViewLifecycleOwner(), response -> {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;

                        }
                        hideKeyboard(getActivity());
                        successMessage(requireActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();

                    });
        }

    }

    public void submitDecline() {
        if (portion.equals("PENDING_PURCHASE")) {

            purchaseReturnViewModel.declinePurchaseReturn(getActivity(), id, binding.NoteEt.getText().toString())
                    .observe(getViewLifecycleOwner(), response -> {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                    });

            return;
        }
        if (portion.equals("SALES_RETURNS_DETAILS")) {
            pendingSalesReturnViewModel.declinePendingSalesReturnDetails(getActivity(), id, binding.NoteEt.getText().toString())
                    .observe(getViewLifecycleOwner(), response -> {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;

                        }
                        hideKeyboard(getActivity());
                        successMessage(requireActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();

                    });
            return;
        }

    }


    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            if (approval == true) {
                approveSubmit();
            } else {
                submitDecline();
            }

        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}