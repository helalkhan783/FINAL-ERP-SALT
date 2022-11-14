package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

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

import com.google.gson.Gson;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PendingWashingAndCrushingAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.GetPendingWashingCrushingDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.PendingWashingCrushingItem;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.GetPendingWashingCrushingViewModel;
import com.ismos_salt_erp.viewModel.WashingAndCrushingViewmodel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class GetPendingWashingAndCrushingDetails extends AddUpDel {
    private View view;
    private GetPendingWashingCrushingViewModel getPendingWashingCrushingViewModel;
    private GetPendingWashingCrushingDetailsResponse pageData;
    private WashingAndCrushingViewmodel washingAndCrushingViewmodel;


    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @BindView(R.id.outputItem)
    TextView outputItem;
    @BindView(R.id.refferName)
    TextView referrerName;
    @BindView(R.id.phoneNum)
    TextView phone;
    @BindView(R.id.noteETT)
    TextView note;
    @BindView(R.id.washingAndCrushingNumber)
    TextView washingAndCrushingNumber;
    @BindView(R.id.dateTv)
    TextView date;
    @BindView(R.id.processByName)
    TextView processByName;
    @BindView(R.id.ItemRecyclerView)
    RecyclerView itemRecyclerView;
    @BindView(R.id.noteEt)
    EditText noteEt;
    @BindView(R.id.approveDeclineOption)
    LinearLayout approveDeclineOption;

    private String orderId, status, portion, vendorId;//store click order id from previous notification fragment
    private boolean approival;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_get_pending_washing_and_crushing_details, container, false);
        ButterKnife.bind(this, view);
        getPendingWashingCrushingViewModel = ViewModelProviders.of(this).get(GetPendingWashingCrushingViewModel.class);
        washingAndCrushingViewmodel = ViewModelProviders.of(this).get(WashingAndCrushingViewmodel.class);
        /**
         * now get data from previous fragment
         */
        getDataFromPreviousFragment();

        /**
         * approve and decline option will be VISIBLE only for profile id 7
         */
        if (!getProfileTypeId(getActivity().getApplication()).equals("7")) {
            view.findViewById(R.id.approveWashingAndCrushingDetails).setVisibility(View.GONE);
            view.findViewById(R.id.WashingAndCrushingDetailsDeclineBtn).setVisibility(View.GONE);
        }

        if (status != null) {//status get from notification list adapter
            if (status.equals("2")) {//here 2 means pending washing & Crushing and this value comes from notification list adapter via bundle
                checkApproveDeclinePermission();
            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }
        /**
         * if not come from notification
         */
        try {
            if (portion != null) {
                if (portion.equals("WASHING_&_CRUSHING_DETAILS")) {
                    approveDeclineOption.setVisibility(View.GONE);
                }
                if (portion.equals("PENDING_WASHING_&_CRUSHING_DETAILS")) {
                    checkApproveDeclinePermission();
                }


            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }

        if (vendorId != null) {//get details show request from dash board
            setNotificationDashBoardDetailsToView(orderId, vendorId);
        } else {//get details view request from notifications
            String vendorID = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
            setNotificationDashBoardDetailsToView(orderId, vendorID);
        }

        return view;
    }

    private void checkApproveDeclinePermission() {
        if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                    PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1430)) {//here 1430 isa permission for approve and declined pending washing and crushing
                approveDeclineOption.setVisibility(View.VISIBLE);
            } else {
                approveDeclineOption.setVisibility(View.GONE);
            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }
    }

    private void setNotificationDashBoardDetailsToView(String orderID, String vendorID) {
        /**
         * now get page info from server
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        getPendingWashingCrushingViewModel.getPendingWashingDetails(getActivity(), orderID, vendorID)
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
                    pageData = response;//store the response for future approve and decline request
                    /**
                     * now set data to view
                     */
                    try {
                        outputItem.setText(":  " + response.getItems().getOutputItem());
                        referrerName.setText(":  " + response.getReferrer().getCustomerFname());
                        phone.setText(":  " + response.getReferrer().getPhone());
                        note.setText(":  " + response.getOrderInfo().getNote());
                        washingAndCrushingNumber.setText(":  " + response.getOrderInfo().getOrderID());
                        date.setText(":  " + new DateFormatRight(getActivity(), response.getOrderInfo().getOrderDate()).onlyDayMonthYear() + " " + response.getOrderInfo().getOrderTime());
                        processByName.setText(":  " + response.getOrderInfo().getUserName());
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                    Log.d("RESPONSE", new Gson().toJson(response));
                    //  Toast.makeText(getContext(), "" + new Gson().toJson(response), Toast.LENGTH_SHORT).show();
                    /**
                     * now set item list in recyclerview
                     */
                    setDateToRv(response.getItems().getItems());
                });
    }

    private void setDateToRv(List<PendingWashingCrushingItem> items) {
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemRecyclerView.setHasFixedSize(true);
        /**
         * set data to recyclerview
         */
        PendingWashingAndCrushingAdapter adapter = new PendingWashingAndCrushingAdapter(getActivity(), items);

        itemRecyclerView.setAdapter(adapter);

    }

    private void getDataFromPreviousFragment() {
        toolbar.setText(getArguments().getString("pageName"));
        orderId = getArguments().getString("RefOrderId");
        status = getArguments().getString("status");
        portion = getArguments().getString("portion");//this portion originally comes from dashboard all needed washing crushing view details
        vendorId = getArguments().getString("vendorId");
    }

    @OnClick(R.id.backbtn)
    public void backVBtnClick() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.approveWashingAndCrushingDetails)
    public void WashingAndCrushingDetails() {
        hideKeyboard(getActivity());
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approival = true;
        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.WashingAndCrushingDetailsDeclineBtn)
    public void decline() {
        hideKeyboard(getActivity());
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approival = false;
        showDialog(getString(R.string.decline_dialog_title));

    }


    public void confirmApprove() {
        washingAndCrushingViewmodel.approveWashingAndCrushing(getActivity(), orderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        Toasty.success(getContext(), "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });

    }

    public void confirmDecline() {
        washingAndCrushingViewmodel.declineWashingAndCrushing(getActivity(), orderId, noteEt.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        Toasty.success(getContext(), "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });

    }


    @Override
    public void save() {
        if (approival == true) {
            confirmApprove();
        } else {
            confirmDecline();
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}