package com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentCustomerReportBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.navigatehelaper.HomePageHelperClass;
import com.ismos_salt_erp.permission.SharedPreferenceForReport;
import com.ismos_salt_erp.permission.Type1;
import com.ismos_salt_erp.permission.WithdrawType;
import com.ismos_salt_erp.serverResponseModel.Bank;
import com.ismos_salt_erp.serverResponseModel.CustomerReportResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseSupplierList;
import com.ismos_salt_erp.serverResponseModel.UserList;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AccountsListViewModel;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.ismos_salt_erp.viewModel.Store;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class CustomerReportFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener {
    private FragmentCustomerReportBinding binding;
    private CustomerReportViewModel customerReportViewModel;
    private String portion, pageName, valueForManageLayout;

    // for store
    private List<Store> storeList;
    private List<ReportPurchaseSupplierList> supplierResponseLists;
    String customerId, SupplierID, storeId, customerName, companyName;
    private boolean isStartDate = false;
    String visible, customer = "Customer";
    // for transaction type
    List<String> transactionType;
    Type1 typeList[] = new Type1[2];//

    // for withdraw type
    List<String> type;
    WithdrawType withdrawType[] = new WithdrawType[2];

    // transaction type for Transaction in
    String transactionId, userId, withdrawPosition, bankId, expenseTypeId;
    List<UserList> userLists;

    List<Bank> bankList;
    SharedPreferenceForReport sharedPreferenceForReport;
    private ExpenseType response1;
    private List<PaymentTypes> paymentTypes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_report, container, false);
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);
        sharedPreferenceForReport = new SharedPreferenceForReport(getActivity());
        getPreviousFragmentData();
        binding.toolbar.setClickHandle(() -> {
            getActivity().onBackPressed();
            deletePreferenceData();
        });

        allVisibleAndGoneLogic();
        // for bank ledger
        if (portion.equals(AccountReportUtils.bankLedger)) {
            getBankData();
            binding.enterpriseAndCustomerLayout.setVisibility(View.GONE);
            binding.userLayout.setVisibility(View.GONE);
            binding.typeAndBankLayout.setVisibility(View.VISIBLE);

            type = new ArrayList<>();
            withdrawType[0] = new WithdrawType("Withdraw", "1");
            withdrawType[1] = new WithdrawType("Deposit", "2");
            for (int i = 0; i < withdrawType.length; i++) {
                type.add("" + withdrawType[i].getType());
            }

            binding.withDrawType.setItem(type);
        }

        getDataPageData();
        transactionSpinnerHandle();
        setOnClick();
        binding.transactioSpinnerForReceipt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transactionId = paymentTypes.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeId = storeList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerId = supplierResponseLists.get(position).getCustomerID();
                customerName = supplierResponseLists.get(position).getCustomerFname();
                companyName = supplierResponseLists.get(position).getCompanyName();

                sharedPreferenceForReport.saveCustomerId(supplierResponseLists.get(position).getCustomerID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SupplierID = supplierResponseLists.get(position).getCustomerID();
                customerName = supplierResponseLists.get(position).getCustomerFname();
                companyName = supplierResponseLists.get(position).getCompanyName();
                sharedPreferenceForReport.saveCustomerId(supplierResponseLists.get(position).getCustomerID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // for bank ledger
        binding.withDrawType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                withdrawPosition = withdrawType[position].getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.search.setOnClickListener(v -> {


            if (valueForManageLayout.equals("1")) {
                if (customerId == null) {

                    infoMessage(getActivity().getApplication(), "Please select " + binding.customerTv.getText().toString());
                    return;
                }
            }


            if (ifExpense()) {
                if (binding.startDate.getText().toString().isEmpty()) {
                    binding.startDate.setError("Empty start date");
                    binding.startDate.requestFocus();
                    return;
                }
                if (binding.EndDate.getText().toString().isEmpty()) {
                    binding.EndDate.setError("Empty start date");
                    binding.EndDate.requestFocus();
                    return;
                }

                if (storeId == null) {
                    infoMessage(getActivity().getApplication(), getString(R.string.enterprise_info));
                    return;
                }

            }

            Bundle bundle = new Bundle();
            bundle.putString("startDate", binding.startDate.getText().toString());
            bundle.putString("endDate", binding.EndDate.getText().toString());
            bundle.putString("supplierId", sharedPreferenceForReport.getCustomerId());
            bundle.putString("SupplierID", sharedPreferenceForReport.getCustomerId());
            bundle.putString("storeId", storeId);
            bundle.putString("customerName", customerName);
            bundle.putString("companyName", companyName);
            bundle.putString("portion", portion);
            bundle.putString("pageName", pageName);
            bundle.putString("userId", userId);
            bundle.putString("transactionId", transactionId);
            bundle.putString("expenseTypeId", expenseTypeId);
            // for bank ledger
            bundle.putString("withdrawPosition", withdrawPosition);
            bundle.putString("bankId", bankId);

            if (portion.equals(AccountReportUtils.cashBook)) {
                gotoAccountList(bundle);
                return;
            }
            if (portion.equals(AccountReportUtils.dayBook)) {
                gotoAccountList(bundle);
                return;
            }
            Navigation.findNavController(getView()).navigate(R.id.action_customerReportFragment_to_purchaseReturnListFragment, bundle);
        });

        // for time period


        List<String> periodNameList = new ArrayList<>();
        periodNameList.clear();
        periodNameList.add("All Time");
        periodNameList.add("Custom Range");
        binding.selectPeriod.setItem(periodNameList);

        binding.selectPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (periodNameList.get(position).equals("Custom Range")) {
                    visible = "1";
                    binding.datePortion.setVisibility(View.VISIBLE);
                } else {
                    visible = "0";
                    binding.datePortion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.transactionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transactionId = typeList[position].getValue();
                Toast.makeText(getContext(), "" + transactionId, Toast.LENGTH_SHORT).show();
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
        binding.user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userId = userLists.get(position).getUserId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.expenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expenseTypeId = response1.getLists().get(position).getExpenseTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void gotoAccountList(Bundle bundle) {
        bundle.putString("PageName", pageName);
        bundle.putString("from", "Report");
        Navigation.findNavController(getView()).navigate(R.id.action_customerReportFragment_to_accountsListFragment2t, bundle);

    }

    private void allVisibleAndGoneLogic() {
        if (portion.equals(AccountReportUtils.supplierLedgerReport) ||
                portion.equals(AccountReportUtils.payment) ||
                portion.equals(AccountReportUtils.paymentInstructions) ||
                portion.equals(AccountReportUtils.creditors) ||
                portion.equals(AccountReportUtils.transactionOut)) {
            setLevel("Company");
        }
        if (portion.equals(AccountReportUtils.supplierLedgerReport)) {
            setLevel("Supplier");
        }
        if (portion.equals(AccountReportUtils.customerLedgerReport)) {
            setLevel("Customer");

        }
        if (portion.equals(AccountReportUtils.paymentInstructions)) {
            setLevel("Supplier");
            binding.lay.setVisibility(View.GONE);

        }
        if (portion.equals(AccountReportUtils.cashBook) || portion.equals(AccountReportUtils.dayBook)) {
            binding.userLayout.setVisibility(View.GONE);
            binding.customerLayout.setVisibility(View.GONE);
            binding.enterpriseLayout.setVisibility(View.VISIBLE);

        }

        if (portion.equals(AccountReportUtils.debitors) || portion.equals(AccountReportUtils.creditors)) {
            binding.dateLay.setVisibility(View.GONE);
            binding.customerLayout.setVisibility(View.GONE);
            binding.lay.setVisibility(View.GONE);
            binding.enterpriseLayout.setVisibility(View.VISIBLE);
        }

        if (portion.equals(AccountReportUtils.vendorLedgerReport)) {
            setLevel("Vendor");
            binding.enterpriseLayout.setVisibility(View.VISIBLE);
            binding.enterpriseLayout.setVisibility(View.GONE);

        }

        if (ifExpense()) {
            binding.enterpriseLayout.setVisibility(View.VISIBLE);
            binding.customerLayout.setVisibility(View.GONE);
            binding.lay.setVisibility(View.GONE);
            binding.expenseTypeLayout.setVisibility(View.VISIBLE);
            getExpenseTypeList();
        }
        if (portion.equals(AccountReportUtils.receipt) || portion.equals(AccountReportUtils.dayBook) || portion.equals(AccountReportUtils.cashBook)) {
            setLevel("Company");
        }

    }

    private void getExpenseTypeList() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        customerReportViewModel.expenseTypeList(getActivity()).observe(getViewLifecycleOwner(), new Observer<ExpenseType>() {
            @Override
            public void onChanged(ExpenseType response) {
                if (response == null || response.getStatus() == 500 || response.getStatus() == 400) {
                    return;
                }
                response1 = response;
                List<String> expenseTypeList = new ArrayList<>();
                for (int i = 0; i < response1.getLists().size(); i++) {
                    expenseTypeList.add("" + response1.getLists().get(i).getExpenseCategory());
                }
                binding.expenseType.setItem(expenseTypeList);
            }
        });
    }

    private boolean ifExpense() {
        if (portion.equals(AccountReportUtils.expense)) {
            return true;
        }
        return false;
    }

    private void setLevel(String name) {
        binding.customerTv.setText(name);
    }

    private void getBankData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        customerReportViewModel.getBankData(getActivity()).observe(getViewLifecycleOwner(), resposne -> {
            if (resposne == null || resposne.getStatus() == 400) {
                errorMessage(getActivity().getApplication(), resposne.getMessage());
                return;
            }
            bankList = new ArrayList<>();
            bankList.addAll(resposne.getBanks());
            List<String> bankNameList = new ArrayList<>();

            for (int i = 0; i < bankList.size(); i++) {
                bankNameList.add("" + bankList.get(i).getBankName() + "@" + bankList.get(i).getBankBranch() + "@" + bankList.get(i).getAccountType());
            }

            binding.bank.setItem(bankNameList);

        });
    }

    private void getDataPageData() {
        customerReportViewModel.getCustomerReportPageData(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(requireActivity().getApplication(), response.getMessage());
                return;
            }

            if (response.getStatus() == 200) {

                setDataInSpinner(response);
            }
        });
        customerReportViewModel.getUserpageList(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 400) {
                return;
            }
            List<String> userNameList = new ArrayList<>();
            userLists = new ArrayList<>();
            userLists.addAll(response.getUserLists());
            for (int i = 0; i < response.getUserLists().size(); i++) {
                userNameList.add("" + response.getUserLists().get(i).getUserName());
            }

            binding.user.setItem(userNameList);
        });

    }

    private void transactionSpinnerHandle() {
        if (portion.equals(AccountReportUtils.transactionOut) ||
                portion.equals(AccountReportUtils.transactionIn) ||
                portion.equals(AccountReportUtils.receipt) ||
                portion.equals(AccountReportUtils.payment)) {
            binding.transactioSpinnerForReceipt.setVisibility(View.VISIBLE);
            binding.trxTypeDaybokLayout.setVisibility(View.GONE);

            binding.customerTv.setText("Company");
            if (!portion.equals(AccountReportUtils.payment)) {
                binding.enterpriseLayout.setVisibility(View.VISIBLE);
            }
            getTransactionTypeData();


        }
        if (portion.equals(AccountReportUtils.receipt)) {
            binding.customerTv.setText("Company");
            binding.enterpriseLayout.setVisibility(View.GONE);
        }


        if (!(portion.equals(AccountReportUtils.transactionOut) || portion.equals(AccountReportUtils.transactionIn) || portion.equals(AccountReportUtils.receipt) || portion.equals(AccountReportUtils.payment))) {
            binding.transactioSpinnerForReceipt.setVisibility(View.GONE);
            binding.trxTypeDaybokLayout.setVisibility(View.VISIBLE);
            transactionType = new ArrayList<>();
            transactionType.clear();
            typeList[0] = new Type1("In", "1");
            typeList[1] = new Type1("Out", "2");
            for (int i = 0; i < typeList.length; i++) {
                transactionType.add(typeList[i].getType());

            }
            binding.transactionType.setItem(transactionType);

            return;
        }


    }

    private void getTransactionTypeData() {
          AccountsListViewModel accountsListViewModel = new ViewModelProvider(this).get(AccountsListViewModel.class);
        accountsListViewModel.receiptHistory(getActivity(), "", "", "", "", "", "", "")
                .observe(getViewLifecycleOwner(), response -> {


                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        paymentTypes = new ArrayList<>();
                        paymentTypes.clear();
                        paymentTypes.addAll(response.getPaymentTypes());
                        List<String> paymentNamelList = new ArrayList<>();
                        paymentNamelList.clear();
                        for (int i = 0; i < paymentTypes.size(); i++) {
                            paymentNamelList.add(paymentTypes.get(i).getName());
                        }

                        binding.transactioSpinnerForReceipt.setItem(paymentNamelList);

                    }
                });

    }


    private void setDataInSpinner(CustomerReportResponse response) {
        // store data set to spinner
        storeList = new ArrayList<>();
        storeList.clear();
        storeList.addAll(response.getStoreList());

        List<String> storeNameList = new ArrayList<>();
        storeNameList.clear();

        for (int i = 0; i < storeList.size(); i++) {
            storeNameList.add(storeList.get(i).getStoreName());
        }
        binding.store.setItem(storeNameList);

//for supplier
        if (portion.equals(AccountReportUtils.creditors) ||
                portion.equals(AccountReportUtils.supplierLedgerReport) ||
                portion.equals(AccountReportUtils.payment) ||
                portion.equals(AccountReportUtils.paymentInstructions) ||
                portion.equals(AccountReportUtils.transactionOut)) {
            setSupplierData(response, "1");

            return;
        }

        if (portion.equals(AccountReportUtils.dayBook) || portion.equals(AccountReportUtils.cashBook)) {
            setSupplierData(response, "2");
            setCustomer(response, "2");
            return;
        }

        //for vendor
        if (portion.equals(AccountReportUtils.vendorLedgerReport) || portion.equals(AccountReportUtils.expense)) {
            List<String> supplierNameList = new ArrayList<>();
            supplierNameList.clear();
            supplierResponseLists = new ArrayList<>();
            supplierResponseLists.addAll(response.getVendorList());
            for (int i = 0; i < response.getVendorList().size(); i++) {
                supplierNameList.add("" + response.getVendorList().get(i).getCompanyName() + "@" + response.getVendorList().get(i).getCustomerFname());
                if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                    if (sharedPreferenceForReport.getCustomerId().equals(response.getVendorList().get(i).getCustomerID())) {
                        binding.customer.setSelection(i);
                    }
                }

            }
            binding.customer.setItem(supplierNameList);
            return;
        }

        // for customer

        if (portion.equals(AccountReportUtils.customerLedgerReport) || valueForManageLayout.equals("2")) {
            setCustomer(response, "1");
            return;
        }

    }

    private void setCustomer(CustomerReportResponse response, String s) {
        List<String> supplierNameList = new ArrayList<>();
        supplierNameList.clear();
        supplierResponseLists = new ArrayList<>();
        supplierResponseLists.addAll(response.getCustomerList());

        for (int i = 0; i < response.getCustomerList().size(); i++) {
            supplierNameList.add("" + response.getCustomerList().get(i).getCompanyName() + "@" + response.getCustomerList().get(i).getCustomerFname());

            if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                if (sharedPreferenceForReport.getCustomerId().equals(response.getCustomerList().get(i).getCustomerID())) {
                    binding.customer.setSelection(i);
                }
            }

        }

        binding.customer.setItem(supplierNameList);

    }

    private void setSupplierData(CustomerReportResponse response, String number) {
        List<String> supplierNameList = new ArrayList<>();
        supplierNameList.clear();
        supplierResponseLists = new ArrayList<>();
        supplierResponseLists.addAll(response.getSupplierList());

        for (int i = 0; i < response.getSupplierList().size(); i++) {
            supplierNameList.add("" + response.getSupplierList().get(i).getCompanyName() + "@" + response.getSupplierList().get(i).getCustomerFname());
            if (sharedPreferenceForReport.getCustomerId() != null || !sharedPreferenceForReport.getCustomerId().isEmpty()) {
                if (sharedPreferenceForReport.getCustomerId().equals(response.getSupplierList().get(i).getCustomerID())) {
                    if (number.equals("1")) {
                        binding.customer.setSelection(i);
                    }
                    if (number.equals("2")) {
                        binding.supplierSpinner.setSelection(i);
                    }
                }
            }
        }
        if (number.equals("1")) {
            binding.customer.setItem(supplierNameList);
        }
        if (number.equals("2")) {
            binding.supplierSpinner.setItem(supplierNameList);
        }
    }


    private void getPreviousFragmentData() {
        portion = getArguments().getString("portion");
        pageName = getArguments().getString("pageName");
        valueForManageLayout = getArguments().getString("valueForManageLayout");
        binding.toolbar.toolbarTitle.setText(pageName);
        if (valueForManageLayout != null) {
            if (valueForManageLayout.equals("1")) {
                binding.enterpriseLayout.setVisibility(View.GONE);
                binding.transactionTypeLayput.setVisibility(View.GONE);
                binding.userLayout.setVisibility(View.GONE);

            }
            if (valueForManageLayout.equals("2")) {
                binding.enterpriseLayout.setVisibility(View.GONE);
            }
        }

    }


    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
        binding.supplierLayout.setOnClickListener(this);
        binding.customerLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                timePicker();
                isStartDate = true;
                break;

            case R.id.EndDate:
                timePicker();
                break;

            case R.id.customerLayout:
                deletePreferenceData();
                break;
            case R.id.supplierLayout:
                deletePreferenceData();
                break;
        }
    }

    private void deletePreferenceData() {
        sharedPreferenceForReport.deleteCustomerData();
        binding.customer.clearSelection();
        binding.supplierSpinner.clearSelection();
        customerName = "";
        companyName = "";

    }

    private void timePicker() {
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
        binding.startDate.setText("");
        binding.EndDate.setText("");
        customerId = null;
        storeId = null;
        userId = null;
        transactionId = null;
        withdrawPosition = null;
        bankId = null;
        expenseTypeId = null;
    }


}