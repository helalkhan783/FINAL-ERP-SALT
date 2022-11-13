package com.ismos_salt_erp.view.fragment.notificationsManage;

import android.app.ProgressDialog;
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

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PendingIodizationItemAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PendingIodizationDetailsResponse;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.IodizationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PendingIodizationDetailsFragment extends BaseFragment {
    private View view;
    private IodizationViewModel iodizationViewModel;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.iodizationNumberTV)
    TextView iodizationNumberTV;
    @BindView(R.id.iodizationDateTv)
    TextView iodizationDateTv;
    @BindView(R.id.processbyTv)
    TextView processbyTv;
    @BindView(R.id.outputItemTv)
    TextView outputItemTv;
    @BindView(R.id.refferName)
    TextView refferName;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.noteTV)
    TextView noteTV;
    @BindView(R.id.iodizationNote)
    EditText iodizationNote;
    @BindView(R.id.iodizationRecyclerView)
    RecyclerView iodizationRecyclerView;
    @BindView(R.id.approveDeclineOption)
    LinearLayout approveDeclineOption;


    String orderId, status, vendorId;//store orderId from previous fragment


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending_iodization_details, container, false);
        ButterKnife.bind(this, view);
        iodizationViewModel = ViewModelProviders.of(this).get(IodizationViewModel.class);
        getDataFromPreviousFragment();
        /***
         * now get the pending iodization details from server
         */
        getIodizationDetailsFromServer();


        return view;
    }

    private void getIodizationDetailsFromServer() {
        if (status != null) {
            if (status.equals("2")) {
                try {
                    if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
                        if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1) ||//manageALlPreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions().contains("1")
                                PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1431)) {//here 1431 is permission for approve decline Pending Iodization
                            approveDeclineOption.setVisibility(View.VISIBLE);
                        } else {
                            approveDeclineOption.setVisibility(View.GONE);
                        }
                    } else {
                        approveDeclineOption.setVisibility(View.GONE);
                    }
                } catch (Exception E) {
                    Log.d("ERROR", "" + E.getMessage());
                }


            }
        } else {
            approveDeclineOption.setVisibility(View.GONE);
        }

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        String currentVendorId = null;
        if (vendorId != null) {
            currentVendorId = vendorId;
        } else {
            currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        }

        iodizationViewModel.getPendingIodizationDetails(getActivity(), orderId, currentVendorId)
                .observe(getViewLifecycleOwner(), this::onChanged);
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText(getArguments().getString("pageName"));
        orderId = getArguments().getString("RefOrderId");
        status = getArguments().getString("status");
        vendorId = getArguments().getString("vendorId");
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveIodization)
    public void approveIodization() {
        if (iodizationNote.getText().toString().isEmpty()) {
            iodizationNote.setError("Note Mandatory");
            iodizationNote.requestFocus();
            return;
        }
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());
        confirmApproveDialog();
    }

    @OnClick(R.id.declineIodization)
    public void declineIodization() {
        if (iodizationNote.getText().toString().isEmpty()) {
            iodizationNote.setError("Note Mandatory");
            iodizationNote.requestFocus();
            return;
        }

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());
        confirmDeclineDialog();
    }


    private void onChanged(PendingIodizationDetailsResponse response) {
        if (response == null) {
            errorMessage(getActivity().getApplication(), "Something Wrong");
            return;
        }
        if (response.getStatus() == 400) {
            infoMessage(getActivity().getApplication(), response.getMessage());
            getActivity().onBackPressed();
            return;
        }
        if (response.getStatus() == 200) {
            try {
                iodizationNumberTV.setText(":  " + "#" + orderId);
                iodizationDateTv.setText(":  " + response.getItems().getDate());
                processbyTv.setText(":  " + response.getOrderInfo().getUserName());
                outputItemTv.setText(":  " + response.getItems().getOutputItem());
                //  Log.d("OUTPUT_ITEM", "" + response.getItems().getOutputItem());
                refferName.setText(":  " + response.getReferrer().getCustomerFname());
                phoneNumber.setText(":  " + response.getReferrer().getPhone());
                noteTV.setText(":  " + response.getOrderInfo().getNote());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
/**
 * now set iodization items to recyclerview
 */
            iodizationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            iodizationRecyclerView.setHasFixedSize(true);
/**
 * now set iodization item in recyclerView
 */
            PendingIodizationItemAdapter adapter = new PendingIodizationItemAdapter(getActivity(), response.getItems().getItems());
            iodizationRecyclerView.setAdapter(adapter);
        }
    }

    public void confirmApproveDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to Approve ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    iodizationViewModel.approveIodizationDetails(getActivity(), orderId, iodizationNote.getText().toString())
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
                                hideKeyboard(getActivity());
                                successMessage(requireActivity().getApplication(), "" + response.getMessage());
                                getActivity().onBackPressed();
                            });
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void confirmDeclineDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Do you want to decline ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    iodizationViewModel.declineIodizationDetails(getActivity(), orderId, iodizationNote.getText().toString())
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
                                successMessage(requireActivity().getApplication(), "" + response.getMessage());
                                getActivity().onBackPressed();
                            });
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


}