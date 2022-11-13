package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PendingRequisitionListAdapter;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentPendingRequisitionListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.Company;
import com.ismos_salt_erp.serverResponseModel.CompanyNameResponse;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.serverResponseModel.StoreNameResponse;
import com.ismos_salt_erp.utils.NoDataFoundCheckerShowBuddy;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PendingRequisitionListViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PendingRequisitionListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private PendingRequisitionListViewModel pendingRequisitionListViewModel;
    FragmentPendingRequisitionListBinding binding;
    List<CompanyNameResponse> companyNameResponseList;
    List<String> companyList;
    List<String> enterpriseList;
    List<StoreNameResponse> storeNameResponseList;
    List<String> storeNameList;
    String selectedStoreId;
    String selectedCompanyId;


    List<Company> companyResponse;
    List<Enterprize> enterpriseResponse;
    List<String> enterpriseNameList;

    private Boolean isStartDate = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_requisition_list, container, false);

        pendingRequisitionListViewModel = new ViewModelProvider(getActivity()).get(PendingRequisitionListViewModel.class);
        getDataFromPreviousFragment();
        init();

        getActivity().runOnUiThread(this::showPendingRequisitionListInRecyclerView);

        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());

        binding.companyNameDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCompanyId = companyResponse.get(position).getCustomerID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.enterpriseDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStoreId = enterpriseResponse.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }

    private void init() {
        binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.startDate.setOnClickListener(this);
        binding.endDate.setOnClickListener(this);
        binding.searchBtn.setOnClickListener(this);
        binding.resetBtn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDataFromPreviousFragment() {
        binding.toolbar.toolbarTitle.setText("Pending Req. List");

     /*   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);

        binding.startDate.setText(currentDate);
        binding.endDate.setText(currentDate);*/
    }

    private void showPendingRequisitionListInRecyclerView() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        pendingRequisitionListViewModel.getPendingRequisitionList(getActivity(), selectedStoreId, binding.startDate.getText().toString(), binding.endDate.getText().toString(), selectedCompanyId).observe(getViewLifecycleOwner(), response -> {
        progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "");
                return;
            }

            if (response.getStatus() == 400){
                infoMessage(getActivity().getApplication(),""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            companyResponse = new ArrayList<>();
            companyList = new ArrayList<>();
            companyResponse.clear();
            companyList.clear();
            companyResponse.addAll(response.getCompanyList());
            for (int i = 0; i < companyResponse.size(); i++) {
                companyList.add("" + companyResponse.get(i).getCompanyName() + "@" + companyResponse.get(i).getCustomerFname());
                if (selectedCompanyId != null) {
                    if (selectedCompanyId.equals(companyResponse.get(i).getCustomerID())) {
                        binding.companyNameDropDown.setSelection(i);
                    }
                }
            }
            binding.companyNameDropDown.setItem(companyList);
            /**
             * now set Data to Enterprise list
             */
            enterpriseResponse = new ArrayList<>();
            enterpriseResponse.clear();
            enterpriseNameList = new ArrayList<>();

            enterpriseResponse.addAll(response.getEnterprizeList());
            for (int i = 0; i < response.getEnterprizeList().size(); i++) {
                enterpriseNameList.add("" + response.getEnterprizeList().get(i).getStoreName());
                if (selectedStoreId != null) {
                    if (selectedStoreId.equals(response.getEnterprizeList().get(i).getStoreID())) {
                        binding.enterpriseDropDown.setSelection(i);
                    }
                }
            }
            binding.enterpriseDropDown.setItem(enterpriseNameList);
            if (response.getLists().isEmpty() || response.getLists() == null){
                binding.pendingRequisitionListRv.setVisibility(View.GONE);
                binding.noDataFound.setVisibility(View.VISIBLE);
                return;
            }
            binding.pendingRequisitionListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.pendingRequisitionListRv.setHasFixedSize(true);
            PendingRequisitionListAdapter adapter = new PendingRequisitionListAdapter(getActivity(), response.getLists());
            binding.pendingRequisitionListRv.setAdapter(adapter);


        });
    }


/*
    @OnClick(R.id.startDate)
    public void clickStartDate() {
        showDatePickerDialog();
        isStartDate = true;
    }
*/


  /*  @OnClick(R.id.endDate)
    public void clickEndDate() {
        showDatePickerDialog();
    }*/

    public void showDatePickerDialog() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);
        if (isStartDate) {
            binding.startDate.setText(selectedDate);
            isStartDate = false;
            return;
        }
        binding.endDate.setText(selectedDate);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filterBtn) {
            if (binding.expandableView.isExpanded()) {
                binding.expandableView.collapse();
                return;
            }
            binding.expandableView.expand();
            return;
        }

        if (v.getId() == R.id.searchBtn) {
            showPendingRequisitionListInRecyclerView();
        }
        if (v.getId() == R.id.resetBtn) {
            selectedStoreId = null;
            selectedCompanyId = null;
            binding.startDate.setText("");
            binding.endDate.setText("");
            binding.companyNameDropDown.clearSelection();
            binding.enterpriseDropDown.clearSelection();
            showPendingRequisitionListInRecyclerView();
        }

        if (v.getId() == R.id.startDate) {
            showDatePickerDialog();
            isStartDate = true;
        }
        if (v.getId() == R.id.endDate) {
            showDatePickerDialog();
        }
    }
}