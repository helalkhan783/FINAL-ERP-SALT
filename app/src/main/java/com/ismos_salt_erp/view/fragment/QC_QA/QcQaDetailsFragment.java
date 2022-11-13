package com.ismos_salt_erp.view.fragment.QC_QA;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentQcQaDetailsBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.Qc_QaViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QcQaDetailsFragment extends BaseFragment {
    private FragmentQcQaDetailsBinding binding;
    private Qc_QaViewModel qc_qaViewModel;
    private List<String> approveDeclineOption;

    private String id,type, status, portion, selectedOption;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qc_qa_details, container, false);
        qc_qaViewModel = new ViewModelProvider(this).get(Qc_QaViewModel.class);
        binding.toolbar.toolbarTitle.setText("QcQa Details");
        getDataFromPreviousFragment();
        hideKeyboard(getActivity());
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * Now get page data from Server
         */
        getPageDataFromServer();
        binding.approveDeclineDDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    binding.declineReasonOption.setVisibility(View.GONE);
                } else {
                    binding.declineReasonOption.setVisibility(View.VISIBLE);
                }
                binding.approveDeclineDDown.setEnableErrorLabel(false);
                selectedOption = String.valueOf(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.save.setOnClickListener(v -> {
            hideKeyboard(getActivity());

            if (selectedOption == null) {
                binding.approveDeclineDDown.setEnableErrorLabel(true);
                binding.approveDeclineDDown.setErrorText("Empty");
                binding.approveDeclineDDown.requestFocus();
                return;
            }
            if (selectedOption.equals("Decline")) {
                if (binding.declineReason.getText().toString().isEmpty()) {
                    binding.declineReason.setError("Empty");
                    binding.declineReason.requestFocus();
                    return;
                }
            }

            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            /**
             * For approve and decline confirmation dialog
             */
            confirmDialog("Do you want to " + approveDeclineOption.get(Integer.parseInt(selectedOption) - 1)+" ?");
        });

        return binding.getRoot();
    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        approveDeclineOption = new ArrayList<>();
        approveDeclineOption.clear();
        approveDeclineOption.addAll(Arrays.asList("Approve", "Decline"));
        binding.approveDeclineDDown.setItem(approveDeclineOption);


        if (getProfileTypeId(getActivity().getApplication()).equals("7")) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1340)) {
                if (status != null && status.equals("2")) {
                    binding.approveDeclineOption.setVisibility(View.VISIBLE);
                } else {
                    binding.approveDeclineOption.setVisibility(View.GONE);
                }
            } else {
                binding.approveDeclineOption.setVisibility(View.GONE);
            }
        } else {
            binding.approveDeclineOption.setVisibility(View.GONE);
        }


        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        qc_qaViewModel.getQcQaPageDetails(getActivity(), id,type)
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
                        binding.testDate.setText(":  " + response.getQcInfo().getTestDate());
                         binding.model.setText(":  " + response.getQcInfo().getModel());
                        binding.processBy.setText(":  " + response.getQcInfo().getEntryUserName());


                        if (response.getQcInfo().getReviewBy() != null){
                            binding.reviewBy.setText(":  " + response.getQcInfo().getReviewBy());
                        }

                        binding.note.setText(":  " + response.getQcInfo().getNote());
                        /**
                         * Now set test List to recycler view
                         */
                        QcQaTestListAdapter adapter = new QcQaTestListAdapter(getActivity(), response.getQcDetails());
                        binding.qcqaTestRV.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.qcqaTestRV.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }


                });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (getProfileTypeId(getActivity().getApplication()).equals("4")) {
            binding.aprovalStatus.setVisibility(View.GONE);
            binding.save.setVisibility(View.GONE);
        }
    }


    public void confirmDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.unicef_main);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    qc_qaViewModel.approveDeclineQcQaDetails(getActivity(), selectedOption, binding.declineReason.getText().toString(), "1", id)
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
                 });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        id = getArguments().getString("SL_ID");
        type = getArguments().getString("type");
        status = getArguments().getString("status");
        portion = getArguments().getString("portion");
        binding.toolbar.toolbarTitle.setText(getArguments().getString("pageName"));
    }
}