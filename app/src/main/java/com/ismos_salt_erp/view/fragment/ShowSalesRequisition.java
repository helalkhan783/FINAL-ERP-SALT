package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.SalesRequisitionDetailsAdapter;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.viewModel.PendingSalesRequisitionDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class ShowSalesRequisition extends BaseFragment {
    private View view;
    private PendingSalesRequisitionDetailsViewModel pendingSalesRequisitionDetailsViewModel;


    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.noteEt)
    EditText note;


    @BindView(R.id.riquisitionNumber)
    TextView riquisitionNumber;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.customerName)
    TextView customer;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.date)
    TextView date;


    @BindView(R.id.itemRv)
    RecyclerView itemRv;

    //final portion
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.subTotal)
    TextView subTotal;

    private String orderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_sales_requisition, container, false);
        ButterKnife.bind(this, view);
        pendingSalesRequisitionDetailsViewModel = new ViewModelProvider(this).get(PendingSalesRequisitionDetailsViewModel.class);
        getDataFromPreviousFragment();
        /**
         * now get pending sales requisition details from server
         */
        getPendingSalesDetailsFromServer();

        return view;
    }

    private void getPendingSalesDetailsFromServer() {
        pendingSalesRequisitionDetailsViewModel.pendingSalesRequisitionPendingDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response==null || response.getStatus() == 400){
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400){
                        infoMessage(getActivity().getApplication(),""+response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    riquisitionNumber.setText(":  #" + orderId);
                    companyName.setText(":  "+response.getDetails().getCustomer().getCompanyName());
                    customer.setText(":  "+response.getDetails().getCustomer().getCompanyName());
                    phone.setText(":  "+response.getDetails().getCustomer().getPhone());
                    date.setText(":  "+response.getDetails().getRequisitionDate());
                    /**
                     * now set itemList to recyclerview
                     */
                    SalesRequisitionDetailsAdapter adapter = new SalesRequisitionDetailsAdapter(getActivity(), response.getDetails().getItems());

                    itemRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    itemRv.setHasFixedSize(true);
                    itemRv.setAdapter(adapter);
                    /**
                     * now final portion
                     */

                    double totalAmount = 0;
                    for (int i = 0; i < response.getDetails().getItems().size(); i++) {
                        totalAmount += Double.parseDouble(response.getDetails().getItems().get(i).getSellingPrice())
                                * Double.parseDouble(response.getDetails().getItems().get(i).getQuantity());
                    }
                    amount.setText(":  "+ DataModify.addFourDigit(String.valueOf(totalAmount)) + MtUtils.priceUnit);
                    discount.setText(":  "+DataModify.addFourDigit(String.valueOf(response.getDetails().getDiscount())) + MtUtils.priceUnit);
                    double subTotalAmount = totalAmount - response.getDetails().getDiscount();
                    subTotal.setText(":  "+DataModify.addFourDigit(String.valueOf(subTotalAmount)) + MtUtils.priceUnit);
                });
    }

    private void getDataFromPreviousFragment() {
        orderId = getArguments().getString("RefOrderId");
        toolBar.setText("Pending Sales Req.");
    }


    @OnClick(R.id.backbtn)
    public void backPress() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveBtn)
    public void approve() {
        String noteVal = note.getText().toString();
        if (noteVal.isEmpty()) {
            noteVal = " ";
        }
        pendingSalesRequisitionDetailsViewModel.approvePendingSalesRequisitionDetails(getActivity(), orderId, noteVal)
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getActivity(), "Approved", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    @OnClick(R.id.declineBtn)
    public void declined() {
        String noteVal = note.getText().toString();
        if (noteVal.isEmpty()) {
            note.setError("Note Mandatory");
            note.requestFocus();
            return;
        }
        pendingSalesRequisitionDetailsViewModel.declinePendingSalesRequisitionDetails(getActivity(), orderId, noteVal)
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getActivity(), response.getMessage(), Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });

    }
}