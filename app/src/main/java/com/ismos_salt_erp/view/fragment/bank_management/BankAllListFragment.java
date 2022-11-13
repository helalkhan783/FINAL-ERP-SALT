package com.ismos_salt_erp.view.fragment.bank_management;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.account_report.BankReportListAdapter;
import com.ismos_salt_erp.adapter.bank_list_adapter.BankAccountListAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.BankBalanceAdapterForReport;
import com.ismos_salt_erp.databinding.FragmentBankAllListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.permission.WithdrawType;
import com.ismos_salt_erp.serverResponseModel.Bank;
import com.ismos_salt_erp.serverResponseModel.account_report.BankLedgerReportResponse;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.BankmanagementUtils;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.BankManageMentViewModel;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.ismos_salt_erp.viewModel.DashBoardViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;


public class BankAllListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    FragmentBankAllListBinding binding;
    BankManageMentViewModel bankManageMentViewModel;
    DashBoardViewModel dashBoardViewModel;
    private CustomerReportViewModel customerReportViewModel;


    private LinearLayoutManager linearLayoutManager;
    String portion, pageName;
    ProgressDialog progressDialog;

    // for bank
    List<Bank> bankList;

    String bankId, withdrawPosition, transactionType;
    List<String> type;
    WithdrawType withdrawType[] = new WithdrawType[2];

    List<String> transactionTypeList = new ArrayList<>();
    private boolean isStartDate = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_all_list, container, false);
        getPreviousData();
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());
        bankManageMentViewModel = new ViewModelProvider(this).get(BankManageMentViewModel.class);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);


        getAllList();
        setDataToSpinner();

        binding.toolbar.filterBtn.setOnClickListener(v -> {
            if (binding.expandableView.isExpanded()) {
                binding.expandableView.setExpanded(false);
                return;
            }
            binding.expandableView.setExpanded(true);

        });

        binding.type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                withdrawPosition = withdrawType[position].getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankId = bankList.get(position).getBankID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.transactionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transactionTypeList.get(position).equals("In")) {
                    transactionType = "1";
                }
                if (transactionTypeList.get(position).equals("Out")) {
                    transactionType = "2";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // search data
        binding.filterSearchBtn.setOnClickListener(v -> getAllList());
        binding.resetBtn.setOnClickListener(v -> {
            binding.startDate.setText("");
            binding.EndDate.setText("");
            binding.type1.clearSelection();
            binding.bank.clearSelection();
            binding.transactionType.clearSelection();
            bankId = null;
            withdrawPosition = null;
            transactionType = null;
            getAllList();
        });

        binding.startDate.setOnClickListener(v -> {
            showDatePicker();
            isStartDate = true;
        });
        binding.EndDate.setOnClickListener(v -> showDatePicker());


        binding.bankListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    binding.expandableView.collapse();
                }
            }
        });


        return binding.getRoot();
    }

    private void showDatePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    private void setDataToSpinner() {
        type = new ArrayList<>();
        withdrawType[0] = new WithdrawType("Withdraw", "1");
        withdrawType[1] = new WithdrawType("Deposit", "2");
        for (int i = 0; i < withdrawType.length; i++) {
            type.add("" + withdrawType[i].getType());
        }

        binding.type1.setItem(type);

        // fro
        transactionTypeList.add("In");
        transactionTypeList.add("Out");
        binding.transactionType.setItem(transactionTypeList);
    }


    private void getAllList() {
        progressDialog = new ProgressDialog(getContext());
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        if (portion.equals(BankmanagementUtils.bankAccountlist)) {
            binding.footerForBankList.setVisibility(View.GONE);
            getBankAccountList();
        }
        if (portion.equals(AccountsUtil.bankBalanceList)) {
            binding.footerForBankList.setVisibility(View.GONE);
            getBankBalanceList();
        }

        if (portion.equals(AccountsUtil.bnkTranxList)) {
            getBankTransactionHistory();
            getPageData();
        }

    }

    private void getPageData() {
        customerReportViewModel.getBankData(getActivity()).observe(getViewLifecycleOwner(), resposne -> {
            if (resposne == null || resposne.getStatus() == 400) {
                errorMessage(getActivity().getApplication(), resposne.getMessage());
                return;
            }
            bankList = new ArrayList<>();
            bankList.addAll(resposne.getBanks());
            List<String> bankNameList = new ArrayList<>();

            for (int i = 0; i < bankList.size(); i++) {
                bankNameList.add("" + bankList.get(i).getBankName());
                if (bankId != null) {
                    if (bankId.equals(bankList.get(i).getBankID())) {
                        binding.bank.setSelection(i);
                    }
                }
            }

            binding.bank.setItem(bankNameList);

        });

    }

    private void getBankTransactionHistory() {
        customerReportViewModel.getBankLedgerList(getActivity(), binding.startDate.getText().toString(), binding.EndDate.getText().toString(), withdrawPosition, transactionType, bankId).observe(getViewLifecycleOwner(), new Observer<BankLedgerReportResponse>() {
            @Override
            public void onChanged(BankLedgerReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists().isEmpty()) {
                        binding.bankListRecyclerView.setVisibility(View.GONE);
                        binding.dataNotFound.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        binding.dataNotFound.setVisibility(View.GONE);
                        binding.bankListRecyclerView.setVisibility(View.VISIBLE);
                        binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
                        binding.bankListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.bankListRecyclerView.setHasFixedSize(true);
                        BankReportListAdapter adapter = new BankReportListAdapter(getActivity(), response.getLists(), "bankAccount");
                        binding.bankListRecyclerView.setAdapter(adapter);
                        double in = 0.0, out = 0.0, total = 0.0;
                        for (int i = 0; i < response.getLists().size(); i++) {
                            in += response.getLists().get(i).getDepositeAmountIn();
                            out += Double.parseDouble(response.getLists().get(i).getDepositeAmountOut());
                        }

                        total = in - out;
                        binding.totalDebit.setText(" " + DataModify.addFourDigit(String.valueOf(in)) + MtUtils.priceUnit);
                        binding.totalCredit.setText(" " + DataModify.addFourDigit(String.valueOf(out)) + MtUtils.priceUnit);
                        binding.totalAmount.setText(" " + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);

                    }
                }


            }

        });

    }

    private void getBankBalanceList() {
        dashBoardViewModel.getBankBalanceList(getActivity(), binding.startDate.toString(), binding.EndDate.getText().toString()).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "");
                return;
            }

            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.bankListRecyclerView.setVisibility(View.GONE);
                    //  binding.footerForBankList.setVisibility(View.GONE);
                    binding.dataNotFound.setVisibility(View.VISIBLE);

                    return;
                }
                //    binding.footerForBankList.setVisibility(View.VISIBLE);
                BankBalanceAdapterForReport adapter1 = new BankBalanceAdapterForReport(getActivity(), response.getList(), "account");
                binding.bankListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.bankListRecyclerView.setAdapter(adapter1);

            }
        });
    }

    private void getBankAccountList() {
        bankManageMentViewModel.getBankAccountList(getActivity()).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "SomethingWrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getLists() == null || response.getLists().isEmpty()) {
                    binding.bankListRecyclerView.setVisibility(View.GONE);
                    binding.dataNotFound.setVisibility(View.VISIBLE);
                    return;

                } else {

                    BankAccountListAdapter adapter = new BankAccountListAdapter(getActivity(), response.getLists());
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.bankListRecyclerView.setLayoutManager(linearLayoutManager);
                    binding.bankListRecyclerView.setAdapter(adapter);

                }
            }

        });

    }

    private void getPreviousData() {
        portion = getArguments().getString("portion");
        pageName = getArguments().getString("pageName");
        binding.toolbar.toolbarTitle.setText("" + pageName);

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);
        if (!isStartDate) {
            binding.EndDate.setText(selectedDate);
        } else {
            binding.startDate.setText(selectedDate);
            isStartDate = false;
        }
    }
}