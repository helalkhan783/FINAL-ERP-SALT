package com.ismos_salt_erp.view.fragment.all_report.lisence_report;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentReportLisenceBinding;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.serverResponseModel.miller_response.LicenceMillerList;
import com.ismos_salt_erp.serverResponseModel.response.MIllerLienceReportCertificateType;
import com.ismos_salt_erp.serverResponseModel.response.MillerLicenceReportResponse;
import com.ismos_salt_erp.serverResponseModel.response.MillerReportAsociationList;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportLicenceFragment extends BaseFragment
        implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private FragmentReportLisenceBinding binding;
    private LicenceReportViewModel licenceReportViewModel;
    private boolean isStartDate = false;

    private List<MillerReportAsociationList> asociationLists;

    private List<MIllerLienceReportCertificateType> certificateTypeList;

    private List<LicenceMillerList> licenceMillerLists;

    String associationId, portion, pageName;
    private String selectAssociationId, millerProfileId, supplierId, certificateTypeID, startDate, endDate;

    private String associationID;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_lisence, container, false);
        licenceReportViewModel = new ViewModelProvider(this).get(LicenceReportViewModel.class);
        getPreviousFragmentData();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        if (portion.equals(ReportUtils.employeeReport)) {
            //cause employee report has only two field
            binding.licenceLayout.setVisibility(View.GONE);

        }
        setOnClick();
/** get Page Data */

        getPageDataFromViewModel();

/** for search button Click */
        binding.search.setOnClickListener(v -> {
            /** purchase Report from start here */
         /*   if (binding.startDate.getText().toString().isEmpty()) {
                binding.startDate.setError("Empty start date");
                binding.startDate.requestFocus();
                return;
            }
            if (binding.EndDate.getText().toString().isEmpty()) {
                binding.EndDate.setError("Empty start date");
                binding.EndDate.requestFocus();
                return;
            }*/

            Bundle bundle = new Bundle();
            bundle.putString("startDate", binding.startDate.getText().toString());
            bundle.putString("endDate", binding.EndDate.getText().toString());
            bundle.putString("millerProfileId", millerProfileId);
            bundle.putString("supplierId", supplierId);
            bundle.putString("certificateTypeID", certificateTypeID);
            bundle.putString("portion", portion);
            bundle.putString("pageName", pageName);
            Navigation.findNavController(getView()).navigate(R.id.action_reportLicenceFragment2_to_purchaseReturnListFragment, bundle);

  });



  //      getMillerData();

        binding.selectAssociation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                associationId = asociationLists.get(position).getStoreID();
                binding.selectAssociation.setEnableErrorLabel(false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.selectLicence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                certificateTypeID = certificateTypeList.get(position).getCertificateTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.miller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                millerProfileId = licenceMillerLists.get(position).getStoreID();
               // millerProfileId = licenceMillerLists.get(position).getVendorID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }

    private void getPreviousFragmentData() {
        portion = getArguments().getString("portion");
        pageName = getArguments().getString("pageName");
        binding.toolbar.toolbarTitle.setText(pageName);
    }

    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
    }


/*
    private void getMillerData() {
        licenceReportViewModel.getMillerData(getActivity(), associationId).observe(getViewLifecycleOwner(), new Observer<MillerLicenceResponse>() {
            @Override
            public void onChanged(MillerLicenceResponse response) {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    setDataInMillerSpinner(response);
                }
            }
        });


    }
*/

/*
    private void setDataInMillerSpinner(MillerLicenceResponse response) {
        List<String> millerNameList = new ArrayList<>();
        millerNameList.clear();
        licenceMillerLists = new ArrayList<>();
        licenceMillerLists.clear();
        licenceMillerLists.addAll(response.getMillerList());

        for (int i = 0; i < response.getMillerList().size(); i++) {
            millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
        }
        binding.miller.setItem(millerNameList);

    }
*/


    private void getPageDataFromViewModel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        licenceReportViewModel.getLicencePageData(getActivity(), getProfileId(getActivity().getApplication())).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
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
                    setDataInSpinner(response);


                }

            } catch (Exception e) {
                Log.d("Error ", e.getMessage());

            }
        });
    }

    private void setDataInSpinner(MillerLicenceReportResponse response) {
   /*      List<String> associationNameList = new ArrayList<>();
        associationNameList.clear();
        asociationLists = new ArrayList<>();
        asociationLists.clear();
        asociationLists.addAll(response.getAsociationList());


        for (int i = 0; i < response.getAsociationList().size(); i++) {
            associationNameList.add("" + response.getAsociationList().get(i).getDisplayName());
        }
        binding.selectAssociation.setItem(associationNameList);
*/

        /** for licence list*/
        List<String> licenceNameList = new ArrayList<>();
        licenceNameList.clear();
        certificateTypeList = new ArrayList<>();
        certificateTypeList.clear();
        certificateTypeList.addAll(response.getCertificateTypes());


        for (int i = 0; i < response.getCertificateTypes().size(); i++) {
            licenceNameList.add("" + response.getCertificateTypes().get(i).getCertificateTypeName());
        }
        binding.selectLicence.setItem(licenceNameList);


        List<String> millerNameList = new ArrayList<>();
            millerNameList.clear();
            licenceMillerLists = new ArrayList<>();
            licenceMillerLists.clear();
            licenceMillerLists.addAll(response.getMillerList());

            for (int i = 0; i < response.getMillerList().size(); i++) {
                millerNameList.add("" + response.getMillerList().get(i).getStoreName());
            }
            binding.miller.setItem(millerNameList);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                isStartDate = true;
                timePicker();
                break;

            case R.id.EndDate:
                timePicker();
                break;
        }
    }

    private void timePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ReportLicenceFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        assert getFragmentManager() != null;
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }

        String mainMonth, mainDay;

        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date

        if (!isStartDate) {
            binding.EndDate.setText(selectedDate);
            binding.EndDate.setError(null);
        } else {
            binding.startDate.setText(selectedDate);
            binding.startDate.setError(null);
            isStartDate = false;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        startDate = null;
        endDate = null;
        selectAssociationId = null;
        millerProfileId = null;
        supplierId = null;
        certificateTypeID = null;

        if (checkProfileType()) {
            binding.associationLayout.setVisibility(View.GONE);
        }
    }
}