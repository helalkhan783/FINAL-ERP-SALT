package com.ismos_salt_erp.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ExpenseDuePaymentApprovalAdapter;

import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.ExpenseDuePaymentPendingDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ExpenseDuePaymentApproveDetails extends AddUpDel {
    private View view;
    private ExpenseDuePaymentPendingDetailsViewModel expenseDuePaymentPendingDetailsViewModel;

    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.shimmerViewContainer)
    ShimmerFrameLayout shimmerViewContainer;
    @BindView(R.id.shimmerViewContainerLay)
    LinearLayout shimmerViewContainerLay;

    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.orderNotFound)
    TextView orderNotFound;
    @BindView(R.id.approvalOption)
    RelativeLayout approvalOption;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;


    @BindView(R.id.expenseDuePaymentApprovalRv)
    RecyclerView expenseDuePaymentApprovalRv;
    @BindView(R.id.noteEditText)
    TextView noteEditText;

    String batch, customer, status, typeKey; // here typekey 4 means receipt
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expense_due_payment_approve_details, container, false);
        ButterKnife.bind(this, view);
        expenseDuePaymentPendingDetailsViewModel = new ViewModelProvider(this).get(ExpenseDuePaymentPendingDetailsViewModel.class);
        getDataFromPreviousFragment();

        /**
         * now get Expense Payment Due details from server
         */
        getExpensePaymentDueFromServer();


        return view;
    }

    private void getDataFromPreviousFragment() {

        batch = getArguments().getString("batch");
        customer = getArguments().getString("customer");
        status = getArguments().getString("status");
        typeKey = getArguments().getString("typeKey");

        if (typeKey.equals("4")) {// 4 means receipt details
            titleSet("Receipt Details");
            if (status.equals("2")) {//here 2 means pending
                titleSet("Receipt Pending Details");
                approvalOption.setVisibility(View.VISIBLE);
            }
        }

        if (typeKey.equals("37")) {//   means Payment details
            titleSet("Payment Details");
            if (status.equals("2")) {//here 2 means pending
                titleSet("Payment Pending Details");
                approvalOption.setVisibility(View.VISIBLE);
            }
        }
        if (typeKey.equals("36")) {//   means expense details
            titleSet("Expense Details");
            if (status.equals("2")) {//here 2 means pending
                titleSet("Expense Pending Details");
                approvalOption.setVisibility(View.VISIBLE);
            }
        }

    }

    private void titleSet(String name) {
        toolbar.setText(name);
    }

    private void getExpensePaymentDueFromServer() {
        expenseDuePaymentPendingDetailsViewModel.getExpenseDuePaymentApprovalDetails(getActivity(), batch, customer, typeKey)
                .observe(getViewLifecycleOwner(), response -> {
                    shimmerViewContainerLay.setVisibility(View.VISIBLE);
                    shimmerViewContainer.startShimmer();
                    if (response == null || response.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        errorMessage(getActivity().getApplication(), "" + response.getMessage());
                        //getActivity().onBackPressed();
                        return;
                    }

                    companyName.setText(":  " + response.getCustomerInfo().getCompanyName() + "@" + response.getCustomerInfo().getCustomerFname());
                    phone.setText(":  " + response.getCustomerInfo().getPhone());
                    address.setText(":  " + response.getCustomerInfo().getAddress());
                    /**
                     * now show all pending payment approval in recyclerview
                     */


                    if (response.getLists().isEmpty() || response.getLists() == null) {
                        expenseDuePaymentApprovalRv.setVisibility(View.GONE);
                        orderNotFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    expenseDuePaymentApprovalRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    expenseDuePaymentApprovalRv.setHasFixedSize(true);
                    ExpenseDuePaymentApprovalAdapter approvalAdapter = new ExpenseDuePaymentApprovalAdapter(getActivity(), response.getLists());
                    expenseDuePaymentApprovalRv.setAdapter(approvalAdapter);

                    shimmerViewContainerLay.setVisibility(View.GONE);
                    shimmerViewContainer.startShimmer();
                    mainLayout.setVisibility(View.VISIBLE);

                });
    }


    @OnClick(R.id.approveBtn)
    public void approveDuePayment() {
        String noteVal = noteEditText.getText().toString();
        if (noteVal.isEmpty()) {
            noteEditText.setError("Note mandatory");
            noteEditText.requestFocus();
            return;
        }
        approval = true;
        showDialog("Do you want to approve ?");
    }

    @OnClick(R.id.declineBtn)
    public void declineDuePayment() {
        String noteVal = noteEditText.getText().toString();
        if (noteVal.isEmpty()) {
            noteEditText.setError("Note mandatory");
            noteEditText.requestFocus();
            return;
        }
        approval = false;
        showDialog("Do you want to decline ?");
    }


    @OnClick(R.id.backbtn)
    public void backBtn() {
        getActivity().onBackPressed();
    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            if (approval == true) {
                submitApprove();
            } else {
                submitDecline();
            }
        }
    }

    private void submitDecline() {
        expenseDuePaymentPendingDetailsViewModel.declineExpenseDuePaymentApprovalDetails(getActivity(), batch)
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getContext(), "Declined", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    private void submitApprove() {
        expenseDuePaymentPendingDetailsViewModel.approveExpenseDuePaymentApprovalDetails(getActivity(), batch, typeKey)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null || response.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        errorMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    successMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                });
    }

    @Override
    public void imageUri(Intent uri) {

    }
}