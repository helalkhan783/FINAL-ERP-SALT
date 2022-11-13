package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PurchaseReturnListAdapter;
import com.ismos_salt_erp.databinding.FragmentPurchaseAndSaleReturnDetailsBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PurchaseReturnDetailsViewModel;
import com.ismos_salt_erp.viewModel.PurchaseReturnViewModel;


public class PurchaseAndSaleReturnDetailsFragment extends BaseFragment {
    private FragmentPurchaseAndSaleReturnDetailsBinding binding;
    private PurchaseReturnDetailsViewModel purchaseReturnDetailsViewModel;
    private PurchaseReturnViewModel purchaseReturnViewModel;

    private String id, orderVendorId, portion, pageName, enterprise, status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_and_sale_return_details, container, false);
        purchaseReturnDetailsViewModel = new ViewModelProvider(this).get(PurchaseReturnDetailsViewModel.class);
        purchaseReturnViewModel = new ViewModelProvider(this).get(PurchaseReturnViewModel.class);
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());
        getPreviousFragmentData();
        getPageData();


        binding.approveBtn.setOnClickListener(v -> {
            if (binding.NoteEt.getText().toString().isEmpty()) {
                binding.NoteEt.setError("Empty");
                binding.NoteEt.requestFocus();
                return;
            }
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            confirmApproveDialog();
        });
        binding.declineBtn.setOnClickListener(v -> {
            if (binding.NoteEt.getText().toString().isEmpty()) {
                binding.NoteEt.setError("Empty");
                binding.NoteEt.requestFocus();
                return;
            }
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            confirmDeclineDialog();

        });

        return binding.getRoot();
    }

    private void getPageData() {
        if (!isInternetOn(getActivity())) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }
        if (portion.equals("PurchaseReturnDetails")) {
            if (status != null) {
                if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||
                            PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1314)) {
                        binding.approveDeclineOption.setVisibility(View.VISIBLE);
                    } else {
                        binding.approveDeclineOption.setVisibility(View.GONE);
                    }
                } else {
                    binding.approveDeclineOption.setVisibility(View.GONE);
                }
            } else {
                binding.approveDeclineOption.setVisibility(View.GONE);
            }
            purchaseReturnDetails();
        }


    }

    private void purchaseReturnDetails() {

        String currentVendorId = null;
        if (orderVendorId != null) {
            currentVendorId = orderVendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        purchaseReturnDetailsViewModel.getPurchaseDetails(getActivity(), id, currentVendorId).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();

                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "something wrong");

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
                        binding.poNoTv.setText(":  #PORTN" + response.getOrderinfo().getOrderSerial());
                        binding.refPo.setText(":   #PO" + response.getOrderinfo().getRef_order_serial());
                        binding.orderSerial.setText(":  " + response.getOrderinfo().getOrderID());
                        binding.poDateTv.setText(":  " + response.getOrderinfo().getOrderDate());
                       // binding.paymentType.setText(":  " + response.getOrderinfo().get());
                        binding.discount.setText(":  " + response.getOrderinfo().getDiscountAmount());
                      //  binding.paid.setText(":  " + response.getOrderinfo().get());
                       // binding.total.setText(":  " + response.getPaymentInfo().getTotalAmount());
                        binding.total.setText(":  " + response.getOrderinfo().getTotal());
                        binding.grandTotal.setText(":  " + response.getOrderinfo().getGrandTotal());

                        binding.supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                        binding.companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                        binding.supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                        binding.addressTv.setText(":  " + response.getCustomer().getAddress());


                        binding.productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        PurchaseReturnListAdapter adapter = new PurchaseReturnListAdapter(getActivity(), response.getOrderDetails(), enterprise);
                        binding.productListRv.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                });


    }


    public void confirmApproveDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to approve ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();

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
                                hideKeyboard(getActivity());
                                successMessage(getActivity().getApplication(), "" + response.getMessage());
                                getActivity().onBackPressed();
                            });
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void confirmDeclineDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to decline ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
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
                                hideKeyboard(getActivity());
                                successMessage(getActivity().getApplication(), "" + response.getMessage());
                                getActivity().onBackPressed();
                            });
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
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
}