package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.bumptech.glide.Glide;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.NotificationPendingPurchaseAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.UrlUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.ApproveDeclinePendingPurchaseViewModel;
import com.ismos_salt_erp.viewModel.PendingPurchaseApproveDeclineViewModel;
import com.ismos_salt_erp.viewModel.PendingSaleApproveDeclineViewModel;
import com.ismos_salt_erp.viewModel.PermissionViewModel;
import com.ismos_salt_erp.viewModel.SalesReturnViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PendingPurchaseDetailsFragment extends AddUpDel {
    private View view;
    private PendingPurchaseApproveDeclineViewModel pendingPurchaseApproveDeclineViewModel;
    private ApproveDeclinePendingPurchaseViewModel approveDeclinePendingPurchaseViewModel;
    private PendingSaleApproveDeclineViewModel pendingSaleApproveDeclineViewModel;
    private SalesReturnViewModel salesReturnViewModel;
    private PermissionViewModel permissionViewModel;
    @BindView(R.id.poNoTv)
    TextView poNoTv;
    @BindView(R.id.orderSerial)
    TextView orderSerial;
    @BindView(R.id.poDateTv)
    TextView poDateTv;
    @BindView(R.id.processByIcon)
    CircularImageView processByIcon;
    @BindView(R.id.processBynameTv)
    TextView processBynameTv;
    @BindView(R.id.uniquePortion)
    TextView uniquePortion;
    //
    @BindView(R.id.supplierNameTv)
    TextView supplierNameTv;
    @BindView(R.id.companyNameTv)
    TextView companyNameTv;
    @BindView(R.id.supplierPhoneTv)
    TextView supplierPhoneTv;
    @BindView(R.id.addressTv)
    TextView addressTv;
    @BindView(R.id.productListRv)
    RecyclerView productListRv;
    @BindView(R.id.NoteEt)
    EditText noteEt;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.paymentTypeTv)
    TextView paymentTypeTv;
    @BindView(R.id.discount)
    TextView discountTv;
    @BindView(R.id.dueTv)
    TextView dueTv;
    @BindView(R.id.paidAmount)
    TextView paidAmount;
    @BindView(R.id.totalAmountTv)
    TextView totalAmountTv;
    @BindView(R.id.carryCost)
    TextView carryCost;
    @BindView(R.id.vat)
    TextView vat;
    @BindView(R.id.collectedAmountTv)
    TextView collectedAmountTv;
    @BindView(R.id.grandTotal)
    TextView grandTotal;
    @BindView(R.id.paid)
    TextView paid;

    @BindView(R.id.procedureLAyout)
    LinearLayout procedureLAyout;
    @BindView(R.id.optionalStatus)
    LinearLayout optionalStatus;
    @BindView(R.id.carryingCostLayout)
    LinearLayout carryingCostLayout;
    @BindView(R.id.paidLayout)
    LinearLayout paidLayout;
    @BindView(R.id.grandTotalLayout)
    LinearLayout grandTotalLayout;
    @BindView(R.id.vatLayout)
    LinearLayout vatLayout;
    @BindView(R.id.collectedLayot1)
    LinearLayout collectedLayot1;
    @BindView(R.id.collectedLayot)
    LinearLayout collectedLayot;//for handle approve and decline functionality
    TextView textView;
    String typeKey, refOrderId, OrderSerialId, pageName, portion, porson, status, orderVendorId;
    public static String total;
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pending_purchase_details, container, false);
        ButterKnife.bind(this, view);
        pendingPurchaseApproveDeclineViewModel = ViewModelProviders.of(this).get(PendingPurchaseApproveDeclineViewModel.class);
        approveDeclinePendingPurchaseViewModel = ViewModelProviders.of(this).get(ApproveDeclinePendingPurchaseViewModel.class);
        pendingSaleApproveDeclineViewModel = ViewModelProviders.of(this).get(PendingSaleApproveDeclineViewModel.class);
        salesReturnViewModel = new ViewModelProvider(this).get(SalesReturnViewModel.class);
        getDataFromPreviousFragment();

        textView = view.findViewById(R.id.uniqueOrderId);

        /**
         * approve and decline option will be VISIBLE only for profile id 7
         */
        if (!getProfileTypeId(getActivity().getApplication()).equals("7")) {
            view.findViewById(R.id.approveBtn).setVisibility(View.GONE);
            view.findViewById(R.id.declineBtn).setVisibility(View.GONE);
        }


        /**
         * now load data to recyclerView
         */
        if (portion.equals("PENDING_PURCHASE")) {
            vatLayout.setVisibility(View.GONE);
            carryingCostLayout.setVisibility(View.GONE);
            grandTotalLayout.setVisibility(View.VISIBLE);
            paidLayout.setVisibility(View.VISIBLE);
            collectedLayot1.setVisibility(View.GONE);
            collectedLayot.setVisibility(View.GONE);
            if (status == null) {
                optionalStatus.setVisibility(View.GONE);
            }

            if (status != null) {
                if (status.equals("2")) {
                    try {
                        if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1429)) {//here 1429 is permission for approve decline pending Purchase
                                optionalStatus.setVisibility(View.VISIBLE);
                            } else {
                                optionalStatus.setVisibility(View.GONE);
                            }
                        } else {
                            optionalStatus.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                }
            }

            if (porson != null) {
                if (porson.equals("PurchaseHistoryDetails")) {
                    optionalStatus.setVisibility(View.GONE);
                    if (status != null) {
                        if (status.equals("2")) {
                            optionalStatus.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            loadPurchaseDataToView();

        }
        if (portion.equals("PENDING_SALE")) {
            textView.setText("S.O ID");
            uniquePortion.setText("Customer Details");

            if (status == null) {// here status for handle pending or details view from notificationListAdapter
                optionalStatus.setVisibility(View.GONE);
            }

            if (status != null) {
                if (status.equals("2")) {
                    try {
                        if (getProfileTypeId(getActivity().getApplication()).equals("7")) {//here 1428 is permission for approve decline pending sale
                            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                                    PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1428)) {
                                optionalStatus.setVisibility(View.VISIBLE);
                            } else {
                                optionalStatus.setVisibility(View.GONE);
                            }
                        } else {
                            optionalStatus.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                }
            }

            if (porson != null) {
                if (porson.equals("SalePendingDetails")) {
                    textView.setText("S.O ID");
                    toolbar.setText("Sale Pending Details");
                    optionalStatus.setVisibility(View.GONE);
                    if (status != null) {
                        if (status.equals("2")) {
                            if (getProfileTypeId(getActivity().getApplication()).equals("7")) {//here 1428 is permission for approve decline pending sale
                                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                                        PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1428)) {
                                    optionalStatus.setVisibility(View.VISIBLE);
                                } else {
                                    optionalStatus.setVisibility(View.GONE);
                                }
                            } else {
                                optionalStatus.setVisibility(View.GONE);
                            }
                        } else {
                            optionalStatus.setVisibility(View.GONE);
                        }
                    }
                }

                if (porson.equals("SaleHistoryDetails")) {
                    textView.setText("S.O ID");
                    toolbar.setText("Sale History Details");
                    optionalStatus.setVisibility(View.GONE);
                    if (status != null) {
                        if (status.equals("2")) {
                            optionalStatus.setVisibility(View.VISIBLE);
                        }
                    }
                }
                if (porson.equals("Sale_Declined_Details")) {
                    textView.setText("S.O ID");
                    procedureLAyout.setVisibility(View.GONE);
                    toolbar.setText("Sale Declined Details");
                    optionalStatus.setVisibility(View.GONE);
                    if (status != null) {
                        if (status.equals("2")) {
                            optionalStatus.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            loadSalesDataToView();

        }

        if (portion.equals("SALES_WHOLE_ORDER_CANCEL")) {
            if (status != null) {
                if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||
                            PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1311)) {
                        optionalStatus.setVisibility(View.VISIBLE);
                    } else {
                        optionalStatus.setVisibility(View.GONE);
                    }
                } else {
                    optionalStatus.setVisibility(View.GONE);
                }
                loadSalesWholeOrderCancelDetails();
            } else {
                loadSalesWholeOrderCancelDetailsNew();
            }
        }

        return view;
    }

    private void loadSalesWholeOrderCancelDetailsNew() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        String vendorId = null;
        if (orderVendorId != null) {
            vendorId = orderVendorId;
        } else {
            vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        pendingSaleApproveDeclineViewModel.getPendingSalesReturnNotificationDetailsNew(getActivity(), refOrderId, vendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    try {

                        poNoTv.setText(":  " + refOrderId + " (Order serial ID)");

                        orderSerial.setText(":  " + response.getOrderInfo().getOrderSerial() + " (Sale order ID)");


                        poDateTv.setText(":  " + response.getOrderInfo().getOrderDate());

                        Glide.with(getContext())
                                .load(UrlUtil.profileBaseUrl + "" + response.getProcessedBy().getProfilePhoto())

                                .into(processByIcon);
                        processBynameTv.setText(response.getProcessedBy().getFullName());

                        supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                        companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                        supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                        addressTv.setText(":  " + response.getCustomer().getAddress());


                        String enterPriseName = response.getEnterprise_name();

                        NotificationPendingPurchaseAdapter adapter = new NotificationPendingPurchaseAdapter(getActivity(), response.getItems(), enterPriseName);
                        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRv.setAdapter(adapter);

                        // double total1 = Double.parseDouble(response.getOrderInfo().getDiscountAmount()) + Double.parseDouble(response.getOrderInfo().getTotal());
                        discountTv.setText("" + DataModify.addFourDigit(response.getOrderInfo().getDiscountAmount()) + MtUtils.priceUnit);
                        totalAmountTv.setText("" + DataModify.addFourDigit(response.getPaymentInfo().getTotalAmount()) + MtUtils.priceUnit);
                        paymentTypeTv.setText("" + response.getPayment_type());
                        double total = Double.parseDouble(response.getPaymentInfo().getTotalAmount());
                        double paid = Double.parseDouble(response.getPaymentInfo().getPaidAmount());
                        double due = total - paid;


                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                        orderSerial.setText(":  ");
                        poDateTv.setText(":  ");
                    }
                });
    }


    private void loadSalesWholeOrderCancelDetails() {
        loadSalesDataToView();
    }

    private void loadSalesDataToView() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }

        if (orderVendorId == null) {
            orderVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        pendingSaleApproveDeclineViewModel.getPendingNotificationDetails(getActivity(), refOrderId, orderVendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    try {
                        poNoTv.setText(":  " + refOrderId + " (Order serial ID)");
                        orderSerial.setText(":  " + response.getOrderInfo().getSerialID() + " (Sale order ID)");
                        poDateTv.setText(":  " + response.getOrderInfo().getOrderDate());

                        Glide.with(getContext())
                                .load(UrlUtil.profileBaseUrl + "" + response.getProcessedBy().getProfilePhoto())

                                .into(processByIcon);
                        processBynameTv.setText(response.getProcessedBy().getFullName());

                        supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                        companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                        supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                        addressTv.setText(":  " + response.getCustomer().getAddress());
                        paidAmount.setText("" + DataModify.addFourDigit(String.valueOf(response.getPaymentInfo().getBill_time_paid())) + MtUtils.priceUnit);
                        dueTv.setText("" + DataModify.addFourDigit(String.valueOf(response.getPaymentInfo().getTotal_paid())) + MtUtils.priceUnit);
                        totalAmountTv.setText("" + DataModify.addFourDigit(response.getOrderInfo().getTotal()) + MtUtils.priceUnit);
                        setDataToView(response.getOrderInfo().getTotal(), response.getOrderInfo().getVat(), String.valueOf(response.getPaymentInfo().getTotal_discount()));


                        discountTv.setText("" + DataModify.addFourDigit(String.valueOf(response.getPaymentInfo().getTotal_discount())) + MtUtils.priceUnit);
                        vat.setText("" + DataModify.addFourDigit(response.getOrderInfo().getVat()) + MtUtils.priceUnit);
                        carryCost.setText("" + DataModify.addFourDigit(response.getOrderInfo().getCarryCost()) + MtUtils.priceUnit);
                        paymentTypeTv.setText("" + response.getPayment_type());

                        String enterPriseName = response.getEnterprise_name();


                        NotificationPendingPurchaseAdapter adapter = new NotificationPendingPurchaseAdapter(getActivity(), response.getItems(), enterPriseName);
                        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRv.setAdapter(adapter);


                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());


                    }
                });
    }

    private void setDataToView(String total, String vat, String total_discount) {
        double totalAmount = 0.0;
        totalAmount = Double.parseDouble(total) + Double.parseDouble(total_discount) + Double.parseDouble(vat);
        totalAmountTv.setText("" + DataModify.addFourDigit(String.valueOf(totalAmount)) + MtUtils.priceUnit);

    }


    private void getDataFromPreviousFragment() {
        //typeKey = getArguments().getString("TypeKey");
        refOrderId = getArguments().getString("RefOrderId");
        OrderSerialId = getArguments().getString("OrderSerialId");
        pageName = getArguments().getString("pageName");
        porson = getArguments().getString("porson");///this porson value get from dash bord all needed view details
        portion = getArguments().getString("portion");//here portion means action come from.....
        status = getArguments().getString("status");
        orderVendorId = getArguments().getString("orderVendorId");
        toolbar.setText(pageName);


    }

    private void loadPurchaseDataToView() {
        String vendorId = null;
        if (orderVendorId != null) {
            vendorId = orderVendorId;
        } else {
            vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        pendingPurchaseApproveDeclineViewModel.getPendingPurchaseNotificationDetails(getActivity(), refOrderId, vendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();//if
                            return;
                        }

                        poNoTv.setText(":  " + response.getOrderInfo().getOrderSerial() + " (Purchase order ID)");
                        orderSerial.setText(":  " + refOrderId + " (Order serial ID)");
                        poDateTv.setText(":  " + response.getOrderInfo().getOrderDate());
                        Glide.with(getContext())
                                .load(UrlUtil.profileBaseUrl + "" + response.getProcessedBy().getProfilePhoto())

                                .into(processByIcon);
                        processBynameTv.setText(response.getProcessedBy().getFullName());

                        supplierNameTv.setText(":  " + response.getCustomer().getCustomerFname());
                        companyNameTv.setText(":  " + response.getCustomer().getCompanyName());
                        supplierPhoneTv.setText(":  " + response.getCustomer().getPhone());
                        addressTv.setText(":  " + response.getCustomer().getAddress());
                        total = response.getOrderInfo().getTotal();


                        String enterPriseName = response.getEnterprise_name();
                        NotificationPendingPurchaseAdapter adapter = new NotificationPendingPurchaseAdapter(getActivity(), response.getItems(), enterPriseName);
                        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRv.setAdapter(adapter);

                        discountTv.setText("" + DataModify.addFourDigit(response.getOrderInfo().getDiscountAmount()) + MtUtils.priceUnit);
                        paid.setText("" + DataModify.addFourDigit(response.getPaymentInfo().getPaidAmount()) + MtUtils.priceUnit);
                        double totalAmount = Double.parseDouble(response.getOrderInfo().getDiscountAmount()) + Double.parseDouble(response.getOrderInfo().getGrandTotal());
                        totalAmountTv.setText("" + DataModify.addFourDigit(String.valueOf(totalAmount)) + MtUtils.priceUnit);
                        grandTotal.setText("" + DataModify.addFourDigit(String.valueOf(response.getOrderInfo().getGrandTotal())) + MtUtils.priceUnit);
                        paymentTypeTv.setText("" + response.getPayment_type());


                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                        progressDialog.dismiss();
                    }
                });
    }

    @OnClick(R.id.approveBtn)
    public void approveBtnClick() {
        hideKeyboard(getActivity());
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Empty field");
            noteEt.requestFocus();
            return;
        }
        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.declineBtn)
    public void declineBtnClick() {
        hideKeyboard(getActivity());
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note is Mandatory");
            noteEt.requestFocus();
            return;
        }
        approval = false;

        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.backbtn)
    public void onBackBtnClick() {
        hideKeyboard(getActivity());
        getActivity().onBackPressed();
    }

    public void confirmApprovePendingPurchaseDialog() {
        approveDeclinePendingPurchaseViewModel.pendingPurchaseApproveRequest(getActivity(), refOrderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                    if (duePaymentResponse.getStatus() == 500 || duePaymentResponse == null) {
                        errorMessage(getActivity().getApplication(), "");
                        return;
                    }
                    if (duePaymentResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + duePaymentResponse.getMessage());
                        return;
                    }
                    Toasty.success(getContext(), "" + duePaymentResponse.getMessage(), Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });

    }

    public void confirmDeclinePendingPurchaseDialog() {
        approveDeclinePendingPurchaseViewModel.pendingPurchaseDeclineRequest(getActivity(), refOrderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                    if (duePaymentResponse.getStatus() == 500 || duePaymentResponse == null) {
                        errorMessage(getActivity().getApplication(), "");
                        return;
                    }
                    if (duePaymentResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + duePaymentResponse.getMessage());
                        return;
                    }
                    Toasty.success(getContext(), "" + duePaymentResponse.getMessage(), Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    public void confirmApprovePendingSaleDialog() {
        pendingSaleApproveDeclineViewModel.pendingSalesApproveRequest(getActivity(), refOrderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                    Toasty.success(getContext(), "Pending Purchase Approved", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    public void confirmDeclinePendingSaleDialog() {
        pendingSaleApproveDeclineViewModel.pendingSalesDeclineRequest(getActivity(), refOrderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                    Toasty.success(getContext(), "Pending Purchase Decline", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    @Override
    public void save() {
        if (approval == true) {
            if (portion.equals("PENDING_PURCHASE")) {
                confirmApprovePendingPurchaseDialog();
            }
            if (portion.equals("PENDING_SALE")) {
                confirmApprovePendingSaleDialog();
            }
            if (portion.equals("SALES_WHOLE_ORDER_CANCEL")) {
                String currentVendorId = null;
                if (orderVendorId != null) {
                    currentVendorId = orderVendorId;
                } else {
                    currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                }

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                salesReturnViewModel.approveSalesReturnWholeOrderCancel(getActivity(), noteEt.getText().toString(), refOrderId, currentVendorId)
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
            }
        } else {
            if (portion.equals("PENDING_PURCHASE")) {
                confirmDeclinePendingPurchaseDialog();
                return;
            }
            if (portion.equals("PENDING_SALE")) {
                confirmDeclinePendingSaleDialog();
                return;
            }

            if (portion.equals("SALES_WHOLE_ORDER_CANCEL")) {

                String currentVendorId = null;
                if (orderVendorId != null) {
                    currentVendorId = orderVendorId;
                } else {
                    currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                }
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                salesReturnViewModel.declineApproveSalesReturnWholeOrderCancel(getActivity(), noteEt.getText().toString(), refOrderId, currentVendorId)
                        .observe(getViewLifecycleOwner(), response -> {
                            progressDialog.dismiss();
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getStatus());
                                return;
                            }
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                        });
            }

        }
    }


    @Override
    public void imageUri(Intent uri) {

    }
}