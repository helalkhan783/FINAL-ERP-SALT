package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.QuotationItemAdapter;
import com.ismos_salt_erp.viewModel.ApproveDeclineQuotationViewModel;
import com.ismos_salt_erp.viewModel.QuotationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PendingQuotationDetails extends Fragment {
    private View view;
    private QuotationViewModel quotationViewModel;
    private ApproveDeclineQuotationViewModel approveDeclineQuotationViewModel;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.quotationNo)
    TextView quotationNo;
    @BindView(R.id.quotationDate)
    TextView quotationDate;
    @BindView(R.id.validTill)
    TextView validTill;
    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.quotationRecyclerView)
    RecyclerView quotationRecyclerView;
    @BindView(R.id.noteEt)
    EditText noteEt;

    String orderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending_quotation_details, container, false);
        ButterKnife.bind(this, view);
        quotationViewModel = ViewModelProviders.of(this).get(QuotationViewModel.class);
        approveDeclineQuotationViewModel = ViewModelProviders.of(this).get(ApproveDeclineQuotationViewModel.class);
        /**
         * for get data from previous fragment
         */
        getdataFromPreviousFragment();
        /**
         * now get data from server
         */
        getPendingQoutationDetailsFromServer();

        return view;
    }

    private void getPendingQoutationDetailsFromServer() {
        quotationViewModel.getQuotationDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    quotationNo.setText("#" + orderId);
                    quotationDate.setText(response.getQuotationInfo().getQuotationCreateDate());
                    validTill.setText(response.getQuotationInfo().getValidTill());
                    customerName.setText(response.getQuotationInfo().getCustomerFname());
                    companyName.setText(response.getQuotationInfo().getCustomerFname());
                    /**
                     * now set the item response to recyclerview
                     */
                    QuotationItemAdapter adapter = new QuotationItemAdapter(getActivity(), response.getQuotationDetails(), response.getQuotationInfo().getTotalAmount());

                    quotationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    quotationRecyclerView.setHasFixedSize(true);
                    quotationRecyclerView.setAdapter(adapter);


                });
    }

    private void getdataFromPreviousFragment() {
        toolbar.setText(getArguments().getString("pageName"));
        orderId = getArguments().getString("RefOrderId");
    }


    @OnClick(R.id.approveBtn)
    public void approveBtn() {
        approveDeclineQuotationViewModel.approvePendingQuotation(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getContext(), "Approved", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();

                });
    }

    @OnClick(R.id.declineBtn)
    public void declineBtn() {

        String note = noteEt.getText().toString();
        if (note.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approveDeclineQuotationViewModel.declinePendingQuotation(getActivity(), orderId, note)
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getContext(), "Declined", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    @OnClick(R.id.backbtn)
    public void backBtn() {
        getActivity().onBackPressed();
    }
}