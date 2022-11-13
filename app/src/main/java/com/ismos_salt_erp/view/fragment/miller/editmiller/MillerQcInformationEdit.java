package com.ismos_salt_erp.view.fragment.miller.editmiller;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentMillerQcInformationEditBinding;
import com.ismos_salt_erp.serverResponseModel.GetPreviousMillerInfoResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;


public class MillerQcInformationEdit extends BaseFragment {
    private FragmentMillerQcInformationEditBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;


    private ViewPager viewPager;
    private String selectedHaveALaboratory = null;
    private String selectedTestKitRadioGroup = null;
    private String selectedlaboratoryPersonRadioGroup = null;
    private String selectedprocedureRadioGroup = null;

    private String sid;

    private GetPreviousMillerInfoResponse getPreviousMillerInfoResponse;


    public MillerQcInformationEdit(String slId) {
        this.sid = slId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_qc_information_edit, container, false);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        viewPager = getActivity().findViewById(R.id.viewPager);
        /**
         * get Previous selected data and set to ui
         */
        setPreviousDataToView();
        /**
         * For have Laboratory
         */
        binding.haveLaboratoryRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioBtnID = group.getCheckedRadioButtonId();

            View radioB = group.findViewById(radioBtnID);
            int position = group.indexOfChild(radioB);
            selectedHaveALaboratory = String.valueOf(position == 0 ? 1 : 0);
        });
        binding.testKitRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioBtnID = group.getCheckedRadioButtonId();

            View radioB = group.findViewById(radioBtnID);
            int position = group.indexOfChild(radioB);
            selectedTestKitRadioGroup = String.valueOf(position == 0 ? 1 : 0);
        });


        binding.laboratoryPersonRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioBtnID = group.getCheckedRadioButtonId();

            View radioB = group.findViewById(radioBtnID);
            int position = group.indexOfChild(radioB);
            selectedlaboratoryPersonRadioGroup = String.valueOf(position == 0 ? 1 : 0);
        });

        binding.procedureRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int radioBtnID = group.getCheckedRadioButtonId();

            View radioB = group.findViewById(radioBtnID);
            int position = group.indexOfChild(radioB);
            selectedprocedureRadioGroup = String.valueOf(position == 0 ? 1 : 0);
        });


        /**
         * Now update the profile information
         */

        binding.setClickHandle(() -> validationAndSubmit());


        return binding.getRoot();
    }

    private void setPreviousDataToView() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        updateMillerViewModel.getPreviousMillerInfoBySid(getActivity(), sid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    getPreviousMillerInfoResponse = response;
                    /**
                     * set standard procedure
                     */
                    try {
                        ((RadioButton) binding.procedureRadioGroup.getChildAt(response.getQcInfo().getStandardProcedure().equals("1") ? 0 : 1)).setChecked(true);
                        /**
                         *  handle trainedLaboratoryPerson
                         */
                        ((RadioButton) binding.laboratoryPersonRadioGroup.getChildAt(response.getQcInfo().getTrainedLaboratoryPerson().equals("1") ? 0 : 1)).setChecked(true);
                        /**
                         * For use test kit
                         */
                        ((RadioButton) binding.testKitRadioGroup.getChildAt(response.getQcInfo().getUseTestKit().equals("1") ? 0 : 1)).setChecked(true);

                        /**
                         * For handle  have Laboratory
                         */
                        ((RadioButton) binding.haveLaboratoryRadioGroup.getChildAt(response.getQcInfo().getHaveLaboratory().equals("1") ? 0 : 1)).setChecked(true);


                        binding.number.setText(response.getQcInfo().getLaboratoryPerson());
                        binding.remarks.setText(response.getQcInfo().getLabRemarks());
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getLocalizedMessage());
                    }
                });
    }

    private void validationAndSubmit() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        updateMillerViewModel.updateQcInformation(
                getActivity(), getPreviousMillerInfoResponse.getQcInfo().getStatus(),
                binding.remarks.getText().toString(), binding.number.getText().toString(),
                selectedTestKitRadioGroup,
                selectedlaboratoryPersonRadioGroup,
                selectedprocedureRadioGroup, selectedHaveALaboratory,
                getPreviousMillerInfoResponse.getQcInfo().getSlId(),
                sid
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong here");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            viewPager.setCurrentItem(4);
        });


    }
}