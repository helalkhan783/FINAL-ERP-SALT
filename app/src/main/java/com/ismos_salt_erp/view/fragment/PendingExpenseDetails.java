package com.ismos_salt_erp.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.bumptech.glide.Glide;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PendingExpenseItemsAdapter;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.ExpenseViewModel;
import com.ismos_salt_erp.viewModel.PendingExpenseApproveDeclineViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PendingExpenseDetails extends AddUpDel {
    private View view;
    private ExpenseViewModel expenseViewModel;
    private PendingExpenseApproveDeclineViewModel pendingExpenseApproveDeclineViewModel;
    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.noteEditText)
    EditText note;

    @BindView(R.id.enterPriseDetails)
    TextView enterPriseDetails;
    @BindView(R.id.enterPriseName)
    TextView enterPriseName;
    @BindView(R.id.totalAmount)
    TextView totalAmount;
    @BindView(R.id.paidAmountEt)
    TextView paidAmount;
    @BindView(R.id.vendor)
    TextView vendor;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.entryDate)
    TextView entryDate;
    @BindView(R.id.processByImg)
    CircularImageView processByImg;
    @BindView(R.id.processByName)
    TextView processByName;
    @BindView(R.id.approvalOption)
    LinearLayout approvalOption;
    @BindView(R.id.pendingExpenseRv)
    RecyclerView pendingExpenseRv;
    String orderId, paymentID, status;

    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending_expense_details, container, false);
        ButterKnife.bind(this, view);
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        pendingExpenseApproveDeclineViewModel = ViewModelProviders.of(this).get(PendingExpenseApproveDeclineViewModel.class);
        /**
         * for get data from previous fragment
         */
        getDataFromPreviousFragment();
        /**
         * now get pending expense details from server
         */
        getPendingExpenseDetailsFromServer();


        return view;
    }

    private void getPendingExpenseDetailsFromServer() {
        expenseViewModel.getPendingExpenseDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    paymentID = response.getPaymentAmount().getPaymentID();//store payment id for approve and decline expense

                    enterPriseDetails.setText(":  #" + orderId);
                    enterPriseName.setText(response.getExpenseInfo().getStoreName());
                    totalAmount.setText(":  " + response.getPaymentAmount().getTotalAmount() + " Taka");
                    paidAmount.setText(":  " + response.getPaymentAmount().getPaidAmount() + " Taka");
                    vendor.setText(":  " + response.getExpenseInfo().getCompanyName() + "@" + response.getExpenseInfo().getCustomerFname());
                    if (response.getExpenseInfo().getExpenseCategory() != null) {
                        type.setText(":  " + response.getExpenseInfo().getExpenseCategory());
                    }

                    entryDate.setText(":  " + response.getExpenseInfo().getEntryDateTime());

                    Glide.with(this)
                            .load(response.getProcessedBy().getProfilePhoto())
                            .placeholder(R.drawable.dash_icon)
                            .error(R.drawable.dash_icon)
                            .override(200, 200)
                            .into(processByImg);

                    processByName.setText(response.getProcessedBy().getFullName());
                    /**
                     * now set the pending amount to  recyclerView
                     */
                    PendingExpenseItemsAdapter adapter = new PendingExpenseItemsAdapter(getActivity(), response.getExpenseLists());
                    pendingExpenseRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    pendingExpenseRv.setHasFixedSize(true);
                    pendingExpenseRv.setAdapter(adapter);
                });
    }

    private void getDataFromPreviousFragment() {
        toolBar.setText("Expense Details");
        orderId = getArguments().getString("RefOrderId");
        status = getArguments().getString("status");
        if (status.equals("2")) {
            approvalOption.setVisibility(View.VISIBLE);
            toolBar.setText(getArguments().getString("pageName"));
        }
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.approveBtn)
    public void approvePendingExpense() {

        if (note.getText().toString().isEmpty()) {
            note.setError("Note Mandatory");
            note.requestFocus();
            return;
        }


    }

    @OnClick(R.id.declineBtn)
    public void declinePendingExpense() {

        if (note.getText().toString().isEmpty()) {
            note.setError("Note Mandatory");
            note.requestFocus();
            return;
        }
        approval = false;
        showDialog(getString(R.string.decline_dialog_title));
    }

    @Override
    public void save() {
        if (approval == true) {
            approveConfirm();
        } else {
            declineConfirm();
        }
    }

    private void declineConfirm() {
        pendingExpenseApproveDeclineViewModel.declinePendingExpense(getActivity(), orderId, paymentID, note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        Toasty.success(getContext(), "Expense Declined", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
    }

    private void approveConfirm() {
        pendingExpenseApproveDeclineViewModel.approvePendingExpense(getActivity(), orderId, paymentID, note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        Toasty.success(getContext(), "Expense Approved", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }

                });
    }

    @Override
    public void imageUri(Intent uri) {

    }
}