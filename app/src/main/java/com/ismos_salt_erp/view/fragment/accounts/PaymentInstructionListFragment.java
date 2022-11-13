package com.ismos_salt_erp.view.fragment.accounts;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PaymentInstructionListAdapter;
import com.ismos_salt_erp.clickHandle.EditItemClickHandle;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.DueLimitLayoutBinding;
import com.ismos_salt_erp.databinding.FragmentPaymentInstructionListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.page_data.AllPageData;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentInstruction;
import com.ismos_salt_erp.serverResponseModel.PaymentInstructionListResponse;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseSupplierList;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.items.edit.EditItem;
import com.ismos_salt_erp.view.fragment.items.edit.UpdatePriceData;
import com.ismos_salt_erp.view.fragment.sale.editSale.EditItemClick;
import com.ismos_salt_erp.viewModel.PaymentInstructionListViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PaymentInstructionListFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, UpdatePriceData {
    List<PaymentInstructionListResponse> paymentInstructionListResponseList;
    private PaymentInstructionListViewModel paymentInstructionListViewModel;

    String vendorId;
    PaymentInstructionListAdapter adapter;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private boolean isStartDate = false;
    private ReportViewModel reportViewModel;
    FragmentPaymentInstructionListBinding binding;
    List<ReportPurchaseSupplierList> supplierLists;

    List<String> supplierName;
    String supplierId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_instruction_list, container, false);

        paymentInstructionListViewModel = ViewModelProviders.of(this).get(PaymentInstructionListViewModel.class);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        binding.appbar.filterBtn.setVisibility(View.VISIBLE);
        binding.appbar.toolbarTitle.setText("Payment Instruction List");
        binding.appbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                getActivity().onBackPressed();
            }
        });
        getPaymentInstructionResponse(vendorId);

        setOnClick();

        getSupplierData();


        binding.supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierId = supplierLists.get(position).getCustomerID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void getSupplierData() {
        String profileId = PreferenceManager.getInstance(getActivity()).getUserCredentials().getProfileId();

        reportViewModel.getPurchaseReportPageData(getActivity(), profileId).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 400 || response.getStatus() == 500) {
                Toasty.error(getActivity(), "Something Wrong Contact to Support", Toasty.LENGTH_LONG).show();
                return;
            }

            supplierLists = new ArrayList<>();
            supplierName = new ArrayList<>();
            supplierLists.addAll(response.getSupplierList());
            for (int i = 0; i < supplierLists.size(); i++) {
                supplierName.add("" + supplierLists.get(i).getCompanyName() + "@" + supplierLists.get(i).getCustomerFname());
            }

            binding.supplier.setItem(supplierName);
        });

    }


    private void setOnClick() {
        binding.appbar.filterBtn.setOnClickListener(this);
        binding.resetBtn.setOnClickListener(this);
        binding.filterSearchBtn.setOnClickListener(this);
        binding.startDate.setOnClickListener(this);
        binding.endDate.setOnClickListener(this);
        binding.appbar.backbtn.setOnClickListener(this);
    }


    @SuppressLint("SetTextI18n")
    private void getPaymentInstructionResponse(String vendorID) {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.paymentInstructionListRv, binding.noDataFound)) {
            return;
        }
        isDataFetching = true;

        binding.paymentInstructionListRv.setHasFixedSize(true);
        binding.paymentInstructionListRv.setLayoutManager(new GridLayoutManager(getContext(), 1));

        paymentInstructionListViewModel.apiCallForGetPaymentInstructionTotalList(getActivity(), vendorID, binding.startDate.getText().toString(), binding.endDate.getText().toString(), supplierId).observe(getViewLifecycleOwner(), new Observer<PaymentInstruction>() {
            @Override
            public void onChanged(PaymentInstruction paymentInstruction) {
                paymentInstructionListResponseList = paymentInstruction.getPaymentInstructionListResponseList();
                if (paymentInstructionListResponseList.isEmpty()) {
                    binding.paymentInstructionListRv.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    binding.noDataFound.setText("No data found");
                }
                if (!paymentInstructionListResponseList.isEmpty()) {
                    binding.paymentInstructionListRv.setVisibility(View.VISIBLE);
                    binding.noDataFound.setVisibility(View.GONE);
                    adapter = new PaymentInstructionListAdapter(getActivity(), paymentInstructionListResponseList, PaymentInstructionListFragment.this);
                    binding.paymentInstructionListRv.setAdapter(adapter);
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filterBtn) {
            if (binding.expandableView.isExpanded()) {
                binding.expandableView.setExpanded(false);
                return;
            }
            binding.expandableView.setExpanded(true);

        }

        if (v.getId() == R.id.resetBtn) {
            binding.endDate.setText("");
            binding.startDate.setText("");
            supplierId = null;
            binding.supplier.clearSelection();
            getPaymentInstructionResponse(vendorId);
        }
        if (v.getId() == R.id.filterSearchBtn) {
            getPaymentInstructionResponse(vendorId);
        }

        if (v.getId() == R.id.startDate) {
            timePicker();
            isStartDate = true;
        }

        if (v.getId() == R.id.endDate) {
            timePicker();
        }
        if (v.getId() == R.id.backbtn) {
            getActivity().onBackPressed();
        }

    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);

        if (!isStartDate) {
            binding.endDate.setText(selectedDate);
        } else {
            binding.startDate.setText(selectedDate);
            isStartDate = false;
        }
    }


    @Override
    public void updatePriceList(int possition, String itemId, String oldValue, String companyName,String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DueLimitLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.due_limit_layout, null, false);
        builder.setView(binding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        binding.dueLimitEt.setText("" + oldValue);
        binding.updateDueLimitTv.setText("Payment Limit Edit");
        binding.company.setText(   companyName);
        binding.levelTv.setText("Payment Limit");

        binding.btnOk.setOnClickListener(v1 -> {
            try {
                if (binding.dueLimitEt.getText().toString().isEmpty()) {
                    binding.dueLimitEt.setError("Empty field");
                    binding.dueLimitEt.requestFocus();
                    return;
                }

                paymentInstructionListViewModel.updatePaymentLimit(getActivity(), itemId, binding.dueLimitEt.getText().toString(),date).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
                    @Override
                    public void onChanged(DuePaymentResponse response) {
                        if (response.getStatus() == 500 || response == null) {
                            errorMessage(getActivity().getApplication(), "Contuct to support");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        getPaymentInstructionResponse(vendorId);
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                    }
                });


                alertDialog.dismiss();
            } catch (Exception e) {
            }
        });
        binding.cancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

    }
}