package com.ismos_salt_erp.view.fragment.all_report.packeting_report;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentPacketingReportBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.miller.PacketMIllerReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketReportAssociationList;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketReportMillerList;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketReportReferer;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketingPageDataReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.store.PacketReportStore;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.store.PacketReportStorteResponse;

import com.ismos_salt_erp.viewModel.report_all_view_model.PacketingReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PacketingReportFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private PacketingReportViewModel packetingReportViewModel;

    private ProgressDialog progressDialog;
    private boolean isStartDate = false;//for detect select start or end date


    /**
     * for Association list
     */
    private List<String> associationNameList;
    private List<PacketReportAssociationList> associationLists;
    /**
     * for store List
     */
    private List<String> storeNameList;
    private List<PacketReportStore> storeLists;
    /**
     * for referrer list
     */
    private List<String> referrerNameList;
    private List<PacketReportReferer> packetReportReferers;


    /**
     * miller List for type id 6 ,7
     */
    private List<String> millerNameList;
    private List<PacketReportMillerList> millerLists;

    private String selectAssociationId, millerProfileId, referrerId, storeId;
    String startDate, endDate, portion,from,pageName;
    String profileTypeId, profileId,associationId;//for profile type id 6,7
    FragmentPacketingReportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_packeting_report, container, false);

        packetingReportViewModel = new ViewModelProvider(this).get(PacketingReportViewModel.class);

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


        progressDialog = new ProgressDialog(getContext());
        getPreviousFragmentData();
        if (portion.equals(ReportUtils.iodineUsedReport) ){
            binding.referrerLayout.setVisibility(View.GONE);
        }
        if (from !=null){
            if (from.equals("FromDashboard")){
                binding.referrerLayout.setVisibility(View.GONE);
            }
        }
        /** get Data from credential */
        profileId = getProfileId(getActivity().getApplication());


        getPageData();

        setOnClick();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


        /** for search button Click */
        binding.search.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("startDate", binding.startDate.getText().toString());
            bundle.putString("endDate", binding.EndDate.getText().toString());
            bundle.putString("millerProfileId", millerProfileId);
            bundle.putString("referrerId", referrerId);
            bundle.putString("portion", portion);
            bundle.putString("storeId", storeId);
            bundle.putString("pageName", pageName);
            bundle.putString("from", from);
            Navigation.findNavController(getView()).navigate(R.id.action_packetingReportFragment_to_purchaseReturnListFragment, bundle);

        });



         binding.selectReferrer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                referrerId = packetReportReferers.get(position).getCustomerID();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.miller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                millerProfileId = millerLists.get(position).getStoreID();
                getstoreData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.selectStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeId = storeLists.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }



    private void getstoreData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        packetingReportViewModel.getPacketStore(getActivity(), millerProfileId).observe(getViewLifecycleOwner(), new Observer<PacketReportStorteResponse>() {
            @Override
            public void onChanged(PacketReportStorteResponse response) {

                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(getActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    setInStoreSpinner(response);
                }
            }
        });

    }

    private void setInStoreSpinner(PacketReportStorteResponse response) {
        storeNameList = new ArrayList<>();
        storeNameList.clear();
        storeLists = new ArrayList<>();
        storeLists.clear();
        storeLists.addAll(response.getMillerList());

        for (int i = 0; i < response.getMillerList().size(); i++) {
            storeNameList.add(response.getMillerList().get(i).getStoreName());
        }
        binding.selectStore.setItem(storeNameList);
    }


    private void getPreviousFragmentData() {
try {

    portion = getArguments().getString("portion");
    from = getArguments().getString("from");
    pageName = getArguments().getString("pageName");
     binding.toolbar.toolbarTitle.setText(pageName);

    if (portion !=null){
        if (portion.equals("Top Ten Supplier (Based on Purchase)")||portion.equals("Top Ten Customer (Based on Sale)")
                ||portion.equals("Current Available Balance") ){
            binding.enterPriseLayout.setVisibility(View.GONE);
        }
    }
}catch (Exception e){
    Log.d("ERROR",e.getMessage());
}

    }



    private void setDataInMillerSpinner(PacketMIllerReportResponse response) {

        millerNameList = new ArrayList<>();
        millerNameList.clear();
        millerLists = new ArrayList<>();
        millerLists.clear();

        millerLists.addAll(response.getMillerList());
        for (int i = 0; i < response.getMillerList().size(); i++) {
            millerNameList.add(""+response.getMillerList().get(i).getDisplayName());
        }
        binding.miller.setItem(millerNameList);
    }


    private void getPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        progressDialog.show();
        packetingReportViewModel.getPacketReportPageData(getActivity(), profileId).observe(getViewLifecycleOwner(), new Observer<PacketingPageDataReportResponse>() {
            @Override
            public void onChanged(PacketingPageDataReportResponse response) {
                progressDialog.dismiss();
               try {
                   if (response == null) {
                       errorMessage(getActivity().getApplication(), "something wrong");
                       return;
                   }
                   if (response.getStatus() == 400) {
                       infoMessage(getActivity().getApplication(), response.getMessage());
                       return;
                   }
                   if (response.getStatus() == 200) {

                       setDataInView(response);

                      // associationId =  response.getAssociationID();

                   }
               }
               catch (Exception e){
                   Log.d("Error",e.getMessage());
               }
            }
        });

    }


    private void setDataInView(PacketingPageDataReportResponse response) {

        /** for association List */

        if (response.getAsociationList().isEmpty() || response.getAsociationList() == null) {
            Toast.makeText(getContext(), "Association is null", Toast.LENGTH_SHORT).show();
        } else {
         }

          /** for referrer list */
            referrerNameList = new ArrayList<>();
            referrerNameList.clear();
            packetReportReferers = new ArrayList<>();
            packetReportReferers.addAll(response.getReferer());

            for (int i = 0; i < response.getReferer().size(); i++) {
                referrerNameList.add(""+response.getReferer().get(i).getCustomerFname());
            }
            binding.selectReferrer.setItem(referrerNameList);


        /**   miller list for ProfileTypeId 6,7  */



                millerNameList = new ArrayList<>();
                millerNameList.clear();
                millerLists = new ArrayList<>();
                millerLists.clear();

                millerLists.addAll(response.getMillerList());
                for (int i = 0; i < response.getMillerList().size(); i++) {
                    millerNameList.add(""+ response.getMillerList().get(i).getDisplayName());
                }
                binding.miller.setItem(millerNameList);

    }

    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                isStartDate = true;
                showDatePickerDialog();
                break;

            case R.id.EndDate:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        DateTimePicker.openDatePicker(this, getActivity());
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);

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
        selectAssociationId = null;
        millerProfileId = null;
        referrerId = null;
        startDate = null;
        endDate = null;
        associationId = null;

    }

}