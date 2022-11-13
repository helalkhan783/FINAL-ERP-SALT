package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ReconciliationAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.ReconciliationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ReconciliationDetailsFragment extends AddUpDel {
    private View view;
    private ReconciliationViewModel reconciliationViewModel;


    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @BindView(R.id.reconciliationNo)
    TextView reconciliationNo;
    @BindView(R.id.enterprise)
    TextView enterprise;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.itemRv)
    RecyclerView itemRv;
    @BindView(R.id.noteEt)
    EditText noteEt;
    @BindView(R.id.approveDeclineOption)
    LinearLayout approveDeclineOption;


    String orderId, status, vendorId;//for store id from previous fragment
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reconciliation_details, container, false);
        ButterKnife.bind(this, view);
        reconciliationViewModel = ViewModelProviders.of(this).get(ReconciliationViewModel.class);
        /**
         * now get Data from previous fragment
         */
        nowGetDataFromPreviousFragment();
        /**
         * now get Data from server
         */
        getReconciliationDetailsFromServer();


        return view;
    }

    private void getReconciliationDetailsFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        /**
         * Now Check Permission
         */
        if (status != null) {//status get from notification list adapter
            if (status.equals("2")) {//here 2 means pending washing & Crushing and this value comes from notification list adapter via bundle
                checkApproveDeclinePermission();
            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }
        String currentVendorId = null;
        if (vendorId != null) {
            currentVendorId = vendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        reconciliationViewModel.getReconciliationDetails(getActivity(), orderId, currentVendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    reconciliationNo.setText(":  #" + response.getOrderInfo().getOrderID());
                    enterprise.setText(":  " + response.getEnterprise());
                    date.setText(":  " + response.getOrderInfo().getOrderDate() + " " + response.getOrderInfo().getOrderTime());
                    note.setText(":  " + response.getOrderInfo().getNote());
                    /**
                     * now set order list to RecyclerView
                     **/
                    ReconciliationAdapter adapter = new ReconciliationAdapter(getActivity(), response.getDetails());
                    itemRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    itemRv.setHasFixedSize(true);
                    itemRv.setAdapter(adapter);


                });
    }

    private void checkApproveDeclinePermission() {
        if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||
                    PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1313)) {//here 1313 isa permission for approve and declined pending Reconciliation
                approveDeclineOption.setVisibility(View.VISIBLE);
            } else {
                approveDeclineOption.setVisibility(View.GONE);
            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }
    }

    private void nowGetDataFromPreviousFragment() {
        orderId = getArguments().getString("RefOrderId");
        toolbar.setText(getArguments().getString("pageName"));
        status = getArguments().getString("status");
        vendorId = getArguments().getString("vendorId");
    }


    @OnClick(R.id.approveBtn)
    public void approve() {
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approval = true;
        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.declineBtn)
    public void decline() {
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approval = false;
        showDialog(getString(R.string.decline_dialog_title));

    }


    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }


    public void confirmApprove() {
        reconciliationViewModel.approveReconciliationDetails(getActivity(), orderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getActivity(), "Approved", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }

    public void approveDecline() {
        reconciliationViewModel.declineReconciliationDetails(getActivity(), orderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    Toasty.success(getActivity(), "Declined", Toasty.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                });
    }


    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            if (approval == true) {
                confirmApprove();
            } else {
                approveDecline();
            }
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}