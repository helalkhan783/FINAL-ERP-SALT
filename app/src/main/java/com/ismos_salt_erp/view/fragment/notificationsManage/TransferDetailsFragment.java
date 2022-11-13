package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.TransferDetailsItemAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.TransferViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class TransferDetailsFragment extends AddUpDel {
    private View view;

    private TransferViewModel transferViewModel;

    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @BindView(R.id.transferNumber)
    TextView transferNumber;
    @BindView(R.id.referer)
    TextView referer;
    @BindView(R.id.transferredFrom)
    TextView transferredFrom;
    @BindView(R.id.transferredTo)
    TextView transferredTo;
    @BindView(R.id.dateTime)
    TextView dateTime;
    @BindView(R.id.processByImg)
    CircularImageView processByImg;
    @BindView(R.id.processByName)
    TextView processByName;

    @BindView(R.id.noteEt)
    TextView note;
    @BindView(R.id.transferItemsRV)
    RecyclerView transferItemsRV;
    @BindView(R.id.approveDeclineOption)
    LinearLayout approveDeclineOption;

    @BindView(R.id.noteEditText)
    EditText finalNote;
    String orderId, status, vendorId;
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transfer_details, container, false);
        ButterKnife.bind(this, view);
        transferViewModel = ViewModelProviders.of(this).get(TransferViewModel.class);
        /**
         * for get data from previous fragment
         */
        getDataFromPreviousFragment();

        /**
         * approve and decline option will be VISIBLE only for profile id 7
         */


        if (PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1312")) {
            view.findViewById(R.id.approveBtn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.declineBtn).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.approveBtn).setVisibility(View.GONE);
            view.findViewById(R.id.declineBtn).setVisibility(View.GONE);
        }

        /**
         * now get transfer details from server
         */
        getTransferDetailsFromServer();
        return view;
    }

    private void getTransferDetailsFromServer() {

        if (status != null) {//status get from notification list adapter
            if (status.equals("2")) {//here 2 means pending Iodization and this value comes from notification list adapter via bundle
                if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                            PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1312)) {
                        approveDeclineOption.setVisibility(View.VISIBLE);
                    } else {
                        approveDeclineOption.setVisibility(View.GONE);
                    }
                } else {
                    approveDeclineOption.setVisibility(View.GONE);
                }

            } else {
                approveDeclineOption.setVisibility(View.GONE);
            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }


        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        toolbar.setText("Transfer Details");
        String currentVendorId = null;
        if (vendorId != null) {
            currentVendorId = vendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }
        transferViewModel.getPendingTransferDetails(getActivity(), orderId, currentVendorId)
                .observe(getViewLifecycleOwner(), response -> {
                    try {
                        if (response == null || response.getStatus() == 500) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                            return;
                        }
                        transferNumber.setText(":  #" + orderId);
                        if (response.getCustomerInfo() != null) {
                            if (response.getCustomerInfo().getCustomerFname() == null) {
                                referer.setText(":  ");
                            } else {
                                referer.setText(":  " + response.getCustomerInfo().getCustomerFname());

                            }
                        } else referer.setText(":  ");
                        if (response.getTransferInfo().getOrderDate() != null) {
                            dateTime.setText(":  " + new DateFormatRight(getActivity(), response.getTransferInfo().getOrderDate()).onlyDayMonthYear() + " " + response.getTransferInfo().getOrderTime());
                        }
                        note.setText(":  " + response.getTransferInfo().getNote());
                        processByName.setText(" " + response.getTransferInfo().getFullName());
                        if (response.getTransferFrom() == null) {
                            transferredFrom.setText(":  ");
                        } else {
                            transferredFrom.setText(":  " + response.getTransferFrom());

                        }
                        if (response.getTransferTo() == null) {
                            transferredTo.setText(":  ");
                        } else {
                            transferredTo.setText(":  " + response.getTransferTo());

                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }

                    Glide.with(this)
                            .load(response.getTransferInfo().getProfilePhoto())
                            .placeholder(R.drawable.app_logo)
                            .error(R.drawable.app_logo)
                            .override(200, 200)
                            //.centerCrop();
                            .into(processByImg);  // imageview object


                    transferItemsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    transferItemsRV.setHasFixedSize(true);
                    /**
                     * now set item list to recycler view
                     */
                    TransferDetailsItemAdapter adapter = new TransferDetailsItemAdapter(getActivity(), response.getItems());
                    transferItemsRV.setAdapter(adapter);
                });
    }

    private void getDataFromPreviousFragment() {
        orderId = getArguments().getString("RefOrderId");
        status = getArguments().getString("status");
        vendorId = getArguments().getString("vendorId");
        toolbar.setText(getArguments().getString(getArguments().getString("pageName")));
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveBtn)
    public void approveBtn() {
        String noteVal = finalNote.getText().toString();
        if (noteVal.isEmpty()) {
            finalNote.setError("Note Mandatory");
            finalNote.requestFocus();
            return;
        }
        approval = true;
        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.declineBtn)
    public void declineBtn() {
        if (finalNote.getText().toString().isEmpty()) {
            finalNote.setError("Note Mandatory");
            finalNote.requestFocus();
            return;
        }
        approval = false;
        hideKeyboard(getActivity());

        showDialog(getString(R.string.decline_dialog_title));
    }


    public void confirmApprove() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        transferViewModel.approvePendingTransfer(getActivity(), orderId, finalNote.getText().toString())
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
                    if (response.getStatus() == 200) {
                        hideKeyboard(getActivity());
                        Toasty.success(getContext(), "Pending Transfer Approved", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
    }

    public void declineApprove() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        transferViewModel.declinedPendingTransfer(getActivity(), orderId, finalNote.getText().toString())
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
                    if (response.getStatus() == 200) {
                        hideKeyboard(getActivity());
                        Toasty.success(getContext(), "Pending Transfer Declined", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
    }


    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            if (approval == true) {
                confirmApprove();
            } else {
                declineApprove();
            }
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}