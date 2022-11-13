package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.viewModel.EditedDuePaymentDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class PendingEditedPurchasePendingDetails extends BaseFragment {

    private View view;
    private EditedDuePaymentDetailsViewModel editedDuePaymentDetailsViewModel;

    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @BindView(R.id.PreviousRefNumber)
    TextView PreviousRefNumber;
    @BindView(R.id.previousAmountDetails)
    TextView previousAmountDetails;
    @BindView(R.id.previousPaymentDate)
    TextView previousPaymentDate;
    @BindView(R.id.previousCollectedAmount)
    TextView previousCollectedAmount;
    @BindView(R.id.previousCustomDiscount)
    TextView previousCustomDiscount;


    @BindView(R.id.updatedRefNo)
    TextView updatedRefNo;
    @BindView(R.id.updatedPaymentAmount)
    TextView updatedPaymentAmount;
    @BindView(R.id.updatedPaymentDate)
    TextView updatedPaymentDate;

    @BindView(R.id.totalAmount)
    TextView totalAmount;
    @BindView(R.id.updatedCollectedAmount)
    TextView updatedCollectedAmount;
    @BindView(R.id.updatedCustomDiscount)
    TextView updatedCustomDiscount;

    String orderId;
    @BindView(R.id.noteEt)
    EditText noteEt;
/*    @OnClick(R.id.approveBtn)
    TextView approveBtn;
    @OnClick(R.id.declineBtn)
    TextView declineBtn;
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending_edited_purchase_pending_details, container, false);
        ButterKnife.bind(this, view);
        editedDuePaymentDetailsViewModel = ViewModelProviders.of(this).get(EditedDuePaymentDetailsViewModel.class);
        /**
         * for get data from previous fragment
         */
        getDataFromPreviousFragment();
        /**
         * now get Page Data from server
         */
        getDataFromServer();


        return view;
    }

    private void getDataFromServer() {

        editedDuePaymentDetailsViewModel.getEditedPaymentDueDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    PreviousRefNumber.setText("#" + response.getPaymentInfo().getOrderID());
                    updatedRefNo.setText("#" + response.getPaymentInfo().getOrderID());


                    previousAmountDetails.setText("#" + response.getPaymentInfo().getPaymentID());
                    updatedPaymentAmount.setText("#" + response.getPaymentInfo().getPaymentID());

                    previousPaymentDate.setText(response.getPaymentInfo().getPaymentDateTime());
                    updatedPaymentDate.setText(response.getPaymentInfo().getEditAttemptTime());

                    previousCollectedAmount.setText(response.getPaymentInfo().getTotalAmount());

                    previousCustomDiscount.setText(response.getPaymentInfo().getCustomDiscount());

                    totalAmount.setText(response.getOrderAmount().getTotal());
                    updatedCollectedAmount.setText(response.getPaymentInfo().getPaidAmount());
                    updatedCustomDiscount.setText(response.getPaymentInfo().getCustomDiscount());


                });
    }


    private void getDataFromPreviousFragment() {
        orderId = getArguments().getString("RefOrderId");
        toolbar.setText(getArguments().getString("pageName"));
    }

    @OnClick(R.id.backbtn)
    public void onClickBackBtn() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveBtn)
    public void approveBtn() {
        String noteVal = noteEt.getText().toString();

        if (noteVal.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }


        editedDuePaymentDetailsViewModel.approveEditedPayment(getActivity(), orderId, noteVal)
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
    }


    @OnClick(R.id.declineBtn)
    public void declineBtn() {
        String noteVal = noteEt.getText().toString();

        if (noteVal.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }

        editedDuePaymentDetailsViewModel.declineEditedPayment(getActivity(), orderId, noteVal)
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
    }
}
