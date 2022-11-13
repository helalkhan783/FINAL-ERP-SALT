package com.ismos_salt_erp.view.fragment.miller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CertificateInfoAdapter;
import com.ismos_salt_erp.adapter.MillerOwnerInfoAdapter;
import com.ismos_salt_erp.databinding.FragmentMillerDetailsViewBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.MillerUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.serverResponseModel.view_details.EmployeeData;
import com.ismos_salt_erp.serverResponseModel.view_details.MillerCertificateDatum;
import com.ismos_salt_erp.serverResponseModel.view_details.MillerOwnerDatum;
import com.ismos_salt_erp.serverResponseModel.view_details.ProfileData;
import com.ismos_salt_erp.serverResponseModel.view_details.QcData;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MillerDetailsViewFragment extends BaseFragment {
    private FragmentMillerDetailsViewBinding binding;
    private MillerViewDetailsViewModel millerViewDetailsViewModel;
    private UpdateMillerViewModel updateMillerViewModel;

    String slId, portion;
    List<String> permissionNameType;
    List<String> permissionIdList;
    String reviewStatus;
    String permissionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_details_view, container, false);
        millerViewDetailsViewModel = new ViewModelProvider(this).get(MillerViewDetailsViewModel.class);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.toolbar.toolbarTitle.setText("Miller Profile Details");

        getPeriviousData();

        getAllDetailsFromViewModel();

        approveDeclineOptionSensor();
        binding.savetn.setOnClickListener(v -> approveMiller());


        permissionNameType = new ArrayList<>();
        permissionNameType.clear();
        permissionIdList = new ArrayList<>();
        permissionIdList.clear();

        permissionNameType.addAll(Arrays.asList("Approve", "Decline"));
        permissionIdList.addAll(Arrays.asList("1", "2"));
        binding.permissionSelect.setItem(permissionNameType);


        binding.permissionSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                permissionId = permissionIdList.get(position);
                binding.permissionSelect.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * For approval Submit
         */
        binding.submittedStatus.setOnClickListener(v -> approvalSubmitDialog());

        return binding.getRoot();
    }

    private void approveDeclineOptionSensor() {

        try {

            if (portion.equals(MillerUtils.millerHistoryList)) {
                String profileTypeId = getProfileTypeId(getActivity().getApplication());
                if (profileTypeId.equals("4") || profileTypeId.equals("5") || profileTypeId.equals("6")) {
                    if (havePermission()) {
                        if (reviewStatus == null || reviewStatus.equals("0")) {
                            binding.layoutPermission.setVisibility(View.VISIBLE);
                            binding.savetn.setVisibility(View.VISIBLE);
                        }
                    }
                    return;
                }
                /*if (profileTypeId.equals("7") || profileTypeId.equals("6")) {
                    checkStatus();
                }*/
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

    }


    private void approveMiller() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do You Want to Confirm This Sale ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (permissionId == null) {
                binding.permissionSelect.setEnableErrorLabel(true);
                binding.permissionSelect.setErrorText("Empty");
                return;
            }
            millerViewDetailsViewModel.approveMiller(getActivity(), slId, permissionId, reviewStatus).observe(getViewLifecycleOwner(), response -> {
                try {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        successMessage(getActivity().getApplication(), "Successful");
                        getActivity().onBackPressed();
                    }

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            });
        });

        alertDialog.show();

    }


    private void getAllDetailsFromViewModel() {
        hideKeyboard(getActivity());
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerViewDetailsViewModel.getMillerViewDetails(getActivity(), slId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }

            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                return;
            }

            if (response.getStatus() == 200) {
                /** set profile data*/
                setDataInView(response.getProfileData());
                reviewStatus = response.getProfileData().getReviewStatus();

                /** set ownerInfo*/
                if (response.getOwnerData().isEmpty()) {
                    binding.companyOwnerInfoRv.setVisibility(View.GONE);
                    binding.onerTv.setVisibility(View.VISIBLE);
                } else {
                    setOwnerInfo(response.getOwnerData());
                }

                /** now set certificateInfo */

                if (response.getCertificateData().isEmpty()) {
                    binding.certificketInfoRv.setVisibility(View.GONE);
                    binding.certificketTv.setVisibility(View.VISIBLE);
                } else {
                    setCertificateInfoInRv(response.getCertificateData());
                }
                /** now set QC information */

                setQcData(response.getQcData());
            }

            /** now set employee information */

            setEmployeeInformation(response.getEmployeeData());
        });
    }

    private void setEmployeeInformation(EmployeeData employeeData) {
        binding.fullTimeMale.setText(employeeData.getFullTimeMale());
        binding.fullTimeFemale.setText(employeeData.getFullTimeFemale());

        binding.partTimeMale.setText(employeeData.getPartTimeMale());
        binding.partTimeFemale.setText(employeeData.getPartTimeFemail());

        binding.totalTechMale.setText(employeeData.getTotalTechMale());
        binding.totalTechFemale.setText(employeeData.getTotalTechFemale());

        binding.totalEmployeeMale.setText(String.valueOf(employeeData.getTotalEmployeeMale()));
        binding.totalEmployeeFemale.setText(String.valueOf(employeeData.getTotalEmployeeFemale()));

    }

    private void setQcData(QcData qcData) {
        if (qcData.getHaveLaboratory() == null) {
            binding.haveALabe.setText(":");
        } else {
            binding.haveALabe.setText(":  " + qcData.getHaveLaboratory());

        }
        if (qcData.getTrainedLaboratoryPerson() == null) {
            binding.labPerson.setText(":");
        } else {
            binding.labPerson.setText(":  " + qcData.getTrainedLaboratoryPerson());

        }
        if (qcData.getLaboratoryPerson() == null) {
            binding.numberLabPerson.setText(":");
        } else {
            binding.numberLabPerson.setText(":  " + qcData.getLaboratoryPerson());

        }
        if (qcData.getStandardProcedure() == null) {
            binding.procedure.setText(":");
        } else {
            binding.procedure.setText(":  " + qcData.getStandardProcedure());

        }
        if (qcData.getUseTestKit() == null) {
            binding.testKit.setText(":");
        } else {
            binding.testKit.setText(":  " + qcData.getUseTestKit());
        }
        if (qcData.getLabRemarks() == null) {
            binding.remarkQc.setText(":");
        } else {
            binding.remarkQc.setText(":  " + qcData.getLabRemarks());
        }
    }

    private void setCertificateInfoInRv(List<MillerCertificateDatum> certificateData) {
        binding.certificketInfoRv.setLayoutManager(new LinearLayoutManager(getContext()));
        CertificateInfoAdapter adapter = new CertificateInfoAdapter(getContext(), certificateData);
        binding.certificketInfoRv.setAdapter(adapter);
    }

    private void setOwnerInfo(List<MillerOwnerDatum> ownerInfo) {

        binding.companyOwnerInfoRv.setLayoutManager(new LinearLayoutManager(getContext()));
        MillerOwnerInfoAdapter adapter = new MillerOwnerInfoAdapter(getContext(), ownerInfo);
        binding.companyOwnerInfoRv.setAdapter(adapter);

    }

    private void getPeriviousData() {

        slId = getArguments().getString("slId");
        portion = getArguments().getString("portion");
    }

    private void setDataInView(ProfileData profileData) {

        binding.nameTv.setText(":  "+profileData.getDisplayName());

        if (profileData.getProcessTypeName() == null) {
            binding.processType.setText(":");
        } else {
            binding.processType.setText(":  " + profileData.getProcessTypeName());
        }


        if (profileData.getMillTypeName() == null) {
            binding.typeOfMiller.setText(":");
        } else {
            binding.typeOfMiller.setText(":  " + profileData.getMillTypeName());
        }

        if (profileData.getCapacity() == null) {
            binding.capacitYTpa.setText(":");
        } else {
            binding.capacitYTpa.setText(":  " + profileData.getCapacity());
        }
        if (profileData.getZoneName() == null) {
            binding.zone.setText(":  ");
        } else {
            binding.zone.setText(":  " + profileData.getZoneName());
        }
        if (profileData.getMillID() == null) {
            binding.millerId.setText(":  ");
        } else {
            binding.millerId.setText(":  " + profileData.getMillID());
        }

        if (profileData.getReviewStatus() == null) {
            binding.reviewStatus.setText(":  ");
        } else {
            if (profileData.getReviewStatus().equals("0")) {
                binding.reviewStatus.setText(":  " + "Pending Review");

            }
            if (profileData.getReviewStatus().equals("1")) {
                binding.reviewStatus.setText(":  " + "Approved");

            }
        }

        if (profileData.getOwnerTypeName() == null) {
            binding.typeOfOwner.setText(":  ");
        } else {
            binding.typeOfOwner.setText(":  " + profileData.getOwnerTypeName());
        }

        if (profileData.getDivisionName() == null) {
            binding.division.setText(":  ");
        } else {
            binding.division.setText(":  " + profileData.getDivisionName());
        }

        if (profileData.getDistrictName() == null) {
            binding.district.setText(":");
        } else {
            binding.district.setText(":  " + profileData.getDistrictName());
        }

        if (profileData.getUpazilaName() == null) {
            binding.thanaUpazilla.setText(":");
        } else {
            binding.thanaUpazilla.setText(":  " + profileData.getUpazilaName());

        }

        if (profileData.getRemarks() == null) {
            binding.remarks.setText(":  ");

        } else {
            binding.remarks.setText(":  " + profileData.getRemarks());
        }


        try {
            if (profileData.getIsSubmit().equals("1")) {
                binding.submittedStatus.setVisibility(View.GONE);
                binding.alreadySubmitted.setVisibility(View.VISIBLE);
                return;
            }


            if (profileData.getIsSubmit() == null) {
                if (getProfileTypeId(getActivity().getApplication()).equals("7")) {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.
                            getInstance(getContext()).getUserCredentials().getPermissions()).contains(1444) ||
                            PermissionUtil.currentUserPermissionList(PreferenceManager.
                                    getInstance(getContext()).getUserCredentials().getPermissions()).contains(1)) {
                        binding.alreadySubmitted.setVisibility(View.GONE);
                        binding.submittedStatus.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        binding.submittedStatus.setVisibility(View.GONE);
                        binding.alreadySubmitted.setVisibility(View.VISIBLE);
                        binding.alreadySubmitted.setText("Not submit");
                        return;
                    }
                }
            }
            if (getProfileTypeId(getActivity().getApplication()).equals("4")) {
                binding.submittedStatus.setVisibility(View.GONE);
                binding.alreadySubmitted.setVisibility(View.VISIBLE);
                binding.alreadySubmitted.setText("Not submit");
            }

        } catch (Exception e) {
            Log.d("ERRor", "" + e.getMessage());
        }


    }


    public void approvalSubmitDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Approval");
        alertDialog.setMessage("Do You Want To Submit For Approval ?");
        /**
         * For submit Dialog
         */
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> updateMillerViewModel.submitMillerApproval(getActivity(), slId)
                        .observe(getViewLifecycleOwner(), response -> {
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                return;
                            }
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            dialog.dismiss();

                            binding.alreadySubmitted.setVisibility(View.VISIBLE);
                            binding.submittedStatus.setVisibility(View.GONE);
                        }));
        /**
         * For cancel Dialog
         */
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        slId = getArguments().getString("slId");
        portion = getArguments().getString("portion");
/*

        try {

            if (portion.equals(MillerUtils.millerHistoryList)) {
                String profileTypeId = getProfileTypeId(getActivity().getApplication());
                if (profileTypeId.equals("4") || profileTypeId.equals("5")) {
                    if (havePermission()) {
                       // if (reviewStatus == null || reviewStatus.equals("0")) {
                            binding.layoutPermission.setVisibility(View.VISIBLE);
                            binding.savetn.setVisibility(View.VISIBLE);
                       // }
                    }
                    return;
                }
                if (profileTypeId.equals("7") || profileTypeId.equals("6")) {
                    checkStatus();
                }
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
*/

    }

    /**
     * check Permission for approve and decline
     */
    private boolean havePermission() {
        try {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getActivity()).getUserCredentials().getPermissions()).contains(1449) || PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getActivity()).getUserCredentials().getPermissions()).contains(1)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void checkStatus() {
        if (reviewStatus.equals("2")) {
            binding.tv.setText("Declined");
            binding.tv.setVisibility(View.VISIBLE);
            return;
        }
        if (reviewStatus.equals("1")) {
            binding.tv.setText("Approved");
            binding.tv.setVisibility(View.VISIBLE);
            return;
        }

        if (reviewStatus.equals("0") || reviewStatus == null) {
            binding.tv.setText("Pending");
            binding.tv.setVisibility(View.VISIBLE);
        }

    }
}