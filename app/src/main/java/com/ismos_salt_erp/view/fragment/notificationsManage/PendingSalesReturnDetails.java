package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PendingSalesReturnItemAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PendingSalesReturnViewModel;
import com.ismos_salt_erp.viewModel.SalesReturnViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PendingSalesReturnDetails extends BaseFragment {
    private View view;

    private PendingSalesReturnViewModel pendingSalesReturnViewModel;
    private SalesReturnViewModel salesReturnViewModel;

    @BindView(R.id.toolbarTitle)
    TextView toolbar;


    @BindView(R.id.orderDate)
    TextView orderDate;
    @BindView(R.id.orderId)
    TextView orderIdTv;

    @BindView(R.id.invoiceId)
    TextView invoiceIdTv;
    @BindView(R.id.paymentType)
    TextView paymentType;

    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.driver)
    TextView driver;
    @BindView(R.id.phoneTransport)
    TextView phoneTransport;
    @BindView(R.id.transportName)
    TextView transportName;
    @BindView(R.id.shopNumber)
    TextView shipNumber;

    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.deliveryAddress)
    TextView deliveryAddress;

    @BindView(R.id.itemRv)
    RecyclerView itemRv;

    @BindView(R.id.paymentTypeFinal)
    TextView paymentTypeFinal;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.totalOrderAmount)
    TextView totalOrderAmount;
    @BindView(R.id.totalReturnAmount)
    TextView totalReturnAmount;
    @BindView(R.id.finalOrderAmount)
    TextView finalOrderAmount;
    @BindView(R.id.last)
    LinearLayout approveDeclineOptions;

    @BindView(R.id.noteEt)
    EditText noteEt;

    String orderId, type, status, orderVendorId;//here status comes from notificationList Fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pending_sales_return_details, container, false);
        ButterKnife.bind(this, view);
        pendingSalesReturnViewModel = ViewModelProviders.of(this).get(PendingSalesReturnViewModel.class);
        salesReturnViewModel = new ViewModelProvider(this).get(SalesReturnViewModel.class);

        getDataFromPreviousFragment();

        /**
         * now get pending Sales return details from server
         */
        if (status == null) {
            approveDeclineOptions.setVisibility(View.GONE);
        }

        if (type != null) {
            if (type.equals("21")) {//this is  sale RETURN cancel order details
                getCancelSalesReturnDetails();
            } else {
                getPendingSalesReturnDetail();
            }
        } else {
            getPendingSalesReturnDetail();
        }

        return view;
    }

    private void getCancelSalesReturnDetails() {
        if (status != null) {//status get from notification list adapter
            if (status.equals("2")) {
                try {
                    if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                        if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                                PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1091)) {//here 1431 is permission for approve decline Pending Iodization
                            approveDeclineOptions.setVisibility(View.VISIBLE);
                        } else {
                            approveDeclineOptions.setVisibility(View.GONE);
                        }
                    } else {
                        approveDeclineOptions.setVisibility(View.GONE);
                    }
                } catch (Exception E) {
                    Log.d("ERROR", "" + E.getMessage());
                }


            }
        } else {
            approveDeclineOptions.setVisibility(View.GONE);
        }

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        salesReturnViewModel.getSalesReturnDetailsDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "ERROR");
                        return;
                    }
                    try {
                        orderDate.setText(":  " + response.getOrderinfo().getOrderDate() + " " + response.getOrderinfo().getOrderTime());

                        invoiceIdTv.setText(":  " + response.getOrderinfo().getSerialID());

                        orderIdTv.setText(":  " + response.getOrderinfo().getOrderID());

                        if (response.getPaymentInfo().getPaymentType().equals("1")) {
                            paymentType.setText("Cash");
                        }
                        if (response.getPaymentInfo().getPaymentType().equals("2")) {
                            paymentType.setText("Cheque");
                        }

                        customerName.setText(":  " + response.getCustomer().getCompanyName() + "@" + response.getCustomer().getCustomerFname());
                        company.setText(":  " + response.getCustomer().getCompanyName());
                        phone.setText(":  " + response.getCustomer().getPhone());
                        address.setText(":  " + response.getCustomer().getThana() + "," + response.getCustomer().getDistrict() + "," + response.getCustomer().getDivision());

                        driver.setText(response.getOtherInformation().getDriverName());
                        phoneTransport.setText(response.getOtherInformation().getPhone());
                        transportName.setText(response.getOtherInformation().getCourrierName());
                        shipNumber.setText(response.getOtherInformation().getLicenseNo());

                        try {
                            if (response.getDeliveryAddress() != null) {
                                companyName.setText(" ");
                                area.setText(" ");
                                deliveryAddress.setText(" ");
                            } else {
                                companyName.setText(response.getDeliveryAddress().getCompanyName());
                                area.setText(response.getDeliveryAddress().getDivision() + "," + response.getDeliveryAddress().getDistrict() + "," + response.getDeliveryAddress().getUpazila());
                                deliveryAddress.setText(response.getDeliveryAddress().getShopLocation());
                            }
                        } catch (Exception e) {
                            Log.d("ERROR", e.getMessage());
                        }
                        /**
                         * now set data to recyclerView
                         */

                        itemRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        itemRv.setHasFixedSize(true);

                        PendingSalesReturnItemAdapter adapter = new PendingSalesReturnItemAdapter(getActivity(), response.getOrderDetails());
                        itemRv.setAdapter(adapter);

                    /*
                    now final portion
                     */
                        if (response.getPaymentInfo().getPaymentType().equals("1")) {
                            paymentTypeFinal.setText("Cash");
                        }
                        if (response.getPaymentInfo().getPaymentType().equals("2")) {
                            paymentTypeFinal.setText("Cheque");
                        }
                        discount.setText(response.getOrderinfo().getDiscountAmount());
                        totalOrderAmount.setText(response.getOrderinfo().getTotal());

                        double totalReturn = 0;
                        double finalOAmount = 0;
                        for (int i = 0; i < response.getOrderDetails().size(); i++) {
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("404")) {
                                double price = Double.parseDouble(response.getOrderDetails().get(i).getSellingPrice())
                                        * Double.parseDouble(response.getOrderDetails().get(i).getQuantity());
                                totalReturn += price;
                            }
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("402")) {
                                double finalPrice = Double.parseDouble(response.getOrderDetails().get(i).getSellingPrice())
                                        * Double.parseDouble(response.getOrderDetails().get(i).getQuantity());

                                finalOAmount += finalPrice;
                            }

                        }
                        totalReturnAmount.setText(String.valueOf(totalReturn));
                        finalOrderAmount.setText(String.valueOf(finalOAmount));

                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                });
    }

    private void getPendingSalesReturnDetail() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        String currentVendorId = null;
        if (orderVendorId != null) {
            currentVendorId = orderVendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        pendingSalesReturnViewModel.getPendingSalesReturnDetails(getActivity(), orderId, currentVendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(),  response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }


                    try {


                        orderDate.setText(":  "+response.getOrderinfo().getOrderDate() + " " + response.getOrderinfo().getOrderTime());

                        if (response.getPaymentInfo().getPaymentType().equals("1")) {
                            paymentType.setText("Cash");
                        }
                        if (response.getPaymentInfo().getPaymentType().equals("2")) {
                            paymentType.setText("Cheque");
                        }

                        invoiceIdTv.setText(":  " + response.getOrderinfo().getSerialID());

                        orderIdTv.setText(":  " + response.getOrderinfo().getOrderID());

                        customerName.setText(":  " + response.getCustomer().getCompanyName() + "@" + response.getCustomer().getCustomerFname());
                        company.setText(":  " + response.getCustomer().getCompanyName());
                        phone.setText(":  " + response.getCustomer().getPhone());
                        address.setText(":  " + response.getCustomer().getThana() + "," + response.getCustomer().getDistrict() + "," + response.getCustomer().getDivision());

                        driver.setText("" + response.getOtherInformation().getDriverName());
                        phoneTransport.setText("" + response.getOtherInformation().getPhone());
                        transportName.setText("" + response.getOtherInformation().getCourrierName());
                        shipNumber.setText("" + response.getOtherInformation().getLicenseNo());

                        if (response.getDeliveryAddress() != null) {
                       /* companyName.setText(" ");
                        area.setText(" ");
                        deliveryAddress.setText(" ");*/
                            companyName.setText(":  " + response.getDeliveryAddress().getCompanyName());
                            area.setText(":  " + response.getDeliveryAddress().getDivision() + "," + response.getDeliveryAddress().getDistrict() + "," + response.getDeliveryAddress().getUpazila());
                            deliveryAddress.setText(":  " + response.getDeliveryAddress().getShopLocation());
                        }
                        /**
                         * now set data to recyclerView
                         */

                        itemRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        itemRv.setHasFixedSize(true);

                        PendingSalesReturnItemAdapter adapter = new PendingSalesReturnItemAdapter(getActivity(), response.getOrderDetails());
                        itemRv.setAdapter(adapter);

                    /*
                    now final portion
                     */
                        if (response.getPaymentInfo().getPaymentType().equals("1")) {
                            paymentTypeFinal.setText("Cash");
                        }
                        if (response.getPaymentInfo().getPaymentType().equals("2")) {
                            paymentTypeFinal.setText("Cheque");
                        }
                        discount.setText(response.getOrderinfo().getDiscountAmount());
                        totalOrderAmount.setText(response.getOrderinfo().getTotal());
                        double totalReturn = 0;
                        double finalOAmount = 0;
                        for (int i = 0; i < response.getOrderDetails().size(); i++) {
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("404")) {
                                double price = Double.parseDouble(response.getOrderDetails().get(i).getSellingPrice())
                                        * Double.parseDouble(response.getOrderDetails().get(i).getQuantity());
                                totalReturn += price;
                            }
                            if (response.getOrderDetails().get(i).getSalesTypeID().equals("402")) {
                                double finalPrice = Double.parseDouble(response.getOrderDetails().get(i).getSellingPrice())
                                        * Double.parseDouble(response.getOrderDetails().get(i).getQuantity());

                                finalOAmount += finalPrice;
                            }

                        }
                        totalReturnAmount.setText(String.valueOf(totalReturn));
                        finalOrderAmount.setText(String.valueOf(finalOAmount));

                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }

                });
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        orderId = getArguments().getString("RefOrderId");
        type = getArguments().getString("TypeKey");
        toolbar.setText(getArguments().getString("pageName"));
        status = getArguments().getString("status");
        orderVendorId = getArguments().getString("orderVendorId");
    }


    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.approveBtn)
    public void approveDetails() {
        String noteVal = noteEt.getText().toString();
        if (noteVal.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        hideKeyboard(getActivity());

        if (type != null) {
            if (type.equals("21")) {//this is  sale RETURN cancel whole order details
                confirmApproveSalesReturnWholeOrderCancelDetailsDialog();
                return;
            }

            if (type.equals("17")) {//for sale return approve
                confirmApprovePendingSalesReturnDetailsDialog();
            }
            return;
        }

    }


    @OnClick(R.id.declineBtn)
    public void declineDetails() {
        String noteVal = noteEt.getText().toString();
        if (noteVal.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        hideKeyboard(getActivity());
        if (type != null) {
            if (type.equals("21")) {//this is  sale RETURN cancel whole order details

                confirmDeclineSalesReturnWholeOrderCancelDetailsDialog();
                return;
            }

            /**
             * For sales return approve details (For type = 17)
             */
            confirmDeclinePendingSalesReturnDetailsDialog();

            return;
        }


    }


    public void confirmApprovePendingSalesReturnDetailsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to approve ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    pendingSalesReturnViewModel.approvePendingSalesReturnDetails(getActivity(), orderId, noteEt.getText().toString())
                            .observe(getViewLifecycleOwner(), response -> {
                                if (response == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                    return;
                                }
                                Toasty.success(getContext(), "Approved", Toasty.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            });

                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void confirmDeclinePendingSalesReturnDetailsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to decline ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    pendingSalesReturnViewModel.declinePendingSalesReturnDetails(getActivity(), orderId, noteEt.getText().toString())
                            .observe(getViewLifecycleOwner(), response -> {
                                if (response == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                    return;
                                }
                                Toasty.success(getContext(), "Declined", Toasty.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            });
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    public void confirmDeclineSalesReturnWholeOrderCancelDetailsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to decline ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    pendingSalesReturnViewModel.declineSalesReturnWholeOrderCancel(getActivity(), orderId, noteEt.getText().toString())
                            .observe(getViewLifecycleOwner(), response -> {
                                progressDialog.dismiss();
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
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void confirmApproveSalesReturnWholeOrderCancelDetailsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to approve ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    pendingSalesReturnViewModel.approveSalesReturnWholeOrderCancel(getActivity(), orderId, noteEt.getText().toString())
                            .observe(getViewLifecycleOwner(), response -> {
                                progressDialog.dismiss();
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
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


}