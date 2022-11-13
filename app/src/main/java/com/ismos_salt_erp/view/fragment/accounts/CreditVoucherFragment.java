package com.ismos_salt_erp.view.fragment.accounts;

import static com.ismos_salt_erp.view.fragment.accounts.receiveDue.DueCollectionFragment.HIDE_KEYBOARD;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentCreditVoucherBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AccountResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerAndSupplierList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.MainBank;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.PayDueAmountViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class CreditVoucherFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    FragmentCreditVoucherBinding binding;
    private DueCollectionViewModel dueCollectionViewModel;
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;
    private PayDueAmountViewModel payDueAmountViewModel;


    private ArrayAdapter<String> customerArrayAdapter;
    List<CustomerAndSupplierList> customerResponseList;
    List<String> customerNameList;
    List<CustomerAndSupplierList> customerList;

    String selectedCustomerId;


    List<MainBank> banks;
    List<PaymentTypes> receiptMethodList = new ArrayList<>();
    List<String> receiptSubMethodList = new ArrayList<>();
    List<String> bankList = new ArrayList<>();
    List<String> bankIdList;
    List<String> optionList = new ArrayList<>();
    List<String> accountBankId = new ArrayList<>();
    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String paymentTypeVal;
    String paymentSubType;
    String selectedBankId;
    String selectPaymentSubType;
    String accountNo;

    List<String> selectedOrderList;
    String portion;

    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private SalesRequisitionViewModel salesRequisitionViewModel;

    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
    String selectedEnterPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_credit_voucher, container, false);
        getPreviousFragmentData();
        dueCollectionViewModel = ViewModelProviders.of(this).get(DueCollectionViewModel.class);
        duePaymentReceivedViewModel = ViewModelProviders.of(this).get(DuePaymentReceivedViewModel.class);
        payDueAmountViewModel = ViewModelProviders.of(this).get(PayDueAmountViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);

        binding.customerNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.customerNameEt.isPerformingCompletion()) {
                    return;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (binding.customerNameEt.isPerformingCompletion()) { // selected a product
                    return;
                }

                // if item is not selected
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    String currentText = binding.customerNameEt.getText().toString();
                    String currentUserToken = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
                    String currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
                    /**
                     * for search a customer
                     */
                    getCustomersFromServer(currentUserToken, currentVendorId, currentText, userId);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.customerNameEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (customerNameList.get(position).equals("No DATA FOUND")) {
                binding.customerNameEt.getText().clear();
                return;
            }
            binding.customerNameEt.getText().clear();

            if (!customerResponseList.isEmpty()) {//if have any customer in the current list
                binding.customerNameEt.clearFocus();
                HIDE_KEYBOARD(getActivity());


                selectedCustomerId = customerResponseList.get(position).getCustomerID();
                String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                /**
                 * get due orders by selected customer id and vendor id
                 */

                getDueOrdersAndShowInRecyclerView(selectedCustomerId, selectedUserVendorId, "2"); // get due orders from server


            } else {
                binding.customerNameEt.getText().clear();
            }
        });
        binding.toolbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });


        // for save
        binding.receiptMethodTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReceiptMethod = receiptMethodList.get(position).getId();
                if (position == 0 || position == 1) {
                    binding.subPaymentAll.setVisibility(View.GONE);
                    return;
                }
                binding.subPaymentAll.setVisibility(View.VISIBLE);
                if (position == 3) {
                    receiptSubMethodList.clear();
                    receiptSubMethodList.add("Online Deposit");
                    binding.receiptSubMethod.setItem(receiptSubMethodList);
                    if (receiptSubMethodList.size() == 1) {
                        binding.receiptSubMethod.setSelection(0);
                        selectPaymentSubType = "4";
                    }

                }
                if (position == 2) {
                    String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    ok(selectedCustomerId, selectedUserVendorId, "1"); // get due orders from server

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.receiptSubMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPaymentSubType = String.valueOf(position + 1);
                if (position == 3) {
                    selectPaymentSubType = "5";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * when user select the bank account then get the the bank account list by user selected bank account name id
         */
        binding.selectBankTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBankId = banks.get(position).getMainBankID();
                accountList(selectedBankId, selectedEnterPrice);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.SelectAccountNoTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountNo = accountBankId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterPrice = enterpriseResponseList.get(position).getStoreID();
                accountList(selectedBankId, selectedEnterPrice);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        getPageDataFromServer();
        return binding.getRoot();
    }

    private void accountList(String selectedBankId, String enterPrice) {
        if (selectedBankId == null || enterPrice == null) {
            return;
        }

        duePaymentReceivedViewModel.apiCallForGetAccountListByBankNameId(//call api for get the list
                getActivity(), PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID(),
                selectedBankId, enterPrice);//

        /**
         * now get the list from viewModel and the bank account list to selectAccountNoTv dropdown
         */
        duePaymentReceivedViewModel.getAccountListByBankId().observe(getViewLifecycleOwner(), accountNumberListResponse -> {
            accountResponseList.clear();
            /**
             * select account list in accountList dropdown
             */
            setItemsToSelectAccountTv(accountNumberListResponse.getLists());
        });
    }

    private void getPageDataFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        salesRequisitionViewModel.getEnterpriseResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        //    errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        //   errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    setPageData(response);
                });
    }

    private void setPageData(GetEnterpriseResponse response) {

        enterpriseResponseList = new ArrayList<>();
        enterpriseResponseList.clear();
        enterpriseNameList = new ArrayList<>();
        enterpriseNameList.clear();
        enterpriseResponseList.addAll(response.getEnterprise());

        for (int i = 0; i < response.getEnterprise().size(); i++) {
            enterpriseNameList.add("" + response.getEnterprise().get(i).getStoreName());
        }
        binding.enterPrice.setItem(enterpriseNameList);


    }

    private void getPreviousFragmentData() {
        portion = getArguments().getString("portion");
        binding.toolbar.toolbarTitle.setText("" + portion);
        if (portion.equals(AccountsUtil.debitVoucher)) {
            binding.receiptTvLevel.setText("Paid Amount");
            binding.receiptTvLevel1.setText("Payment Method");
            binding.subMethodTv.setText("Payment Sub Method");
            binding.paidAmountEt.setHint("Paid Amount");
        }
    }

    private void showDialog() {
        paymentTypeVal = selectedReceiptMethod;
        paymentSubType = selectPaymentSubType;
        if (!paymentTypeVal.equals("1")) {//here 1 means cash

            if (!paymentTypeVal.equals("2")) {

                if (selectedReceiptMethod == null) {
                    infoMessage(getActivity().getApplication(), "please select receipt method");
                    return;
                }

                if (selectPaymentSubType == null) {
                    infoMessage(getActivity().getApplication(), "Please select receipt sub method");
                    return;
                }

                if (selectedBankId == null) {
                    infoMessage(getActivity().getApplication(), "Please select bank");
                    return;
                }


                if (accountNo == null) {
                    infoMessage(getActivity().getApplication(), "Please select account number");
                    return;
                }

            }
        }
        if (binding.paidAmountEt.getText().toString().isEmpty()) {
            binding.paidAmountEt.setError("Paid amount is mandatory");
            binding.paidAmountEt.requestFocus();
            return;
        }
        if (paymentTypeVal == null) {
            Toasty.info(getContext(), "Please select your receipt method", Toasty.LENGTH_LONG).show();
            return;
        }

        if (binding.ReceiptRemarks.getText().toString().isEmpty()) {
            binding.ReceiptRemarks.setError("Remarks mandatory");
            binding.ReceiptRemarks.requestFocus();
            return;
        }
        if (portion.equals(AccountsUtil.creditVoucher)) {
            saveData("51");
        }
        if (portion.equals(AccountsUtil.debitVoucher)) {
            saveData("50");
        }

    }

    private void saveData(String type) {
        String storeId = selectedEnterPrice;
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions();
        String profileTypeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getProfileTypeId();
        payDueAmountViewModel.creditVoucherResponse(getActivity(), vendorId, null, binding.paidAmountEt.getText().toString(),
                null, storeId, userId, permissions, profileTypeId, paymentTypeVal,
                paymentSubType, accountNo, binding.date.getText().toString(),
                binding.ReceiptRemarks.getText().toString(), selectedCustomerId, type, selectedBankId).observe(getViewLifecycleOwner(), response -> {
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
    }

    private void setItemsToSelectAccountTv(List<AccountResponse> accountResponseList) {
        optionList.clear();
        accountBankId.clear();
        for (int i = 0; i < accountResponseList.size(); i++) {
            String option = accountResponseList.get(i).getAccountantName() + "/" + accountResponseList.get(i).getBankBranch() + "/" + accountResponseList.get(i).getAccountNumber();
            optionList.add(option);
            accountBankId.add(accountResponseList.get(i).getBankID());
        }
        binding.SelectAccountNoTv.setItem(optionList);
    }

    private void getDueOrdersAndShowInRecyclerView(String selectedCustomerId, String vendorId, String number) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        dueCollectionViewModel.apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(getActivity(), selectedCustomerId, vendorId);

        dueCollectionViewModel.getGetDueOrders().observe(getViewLifecycleOwner(), dueOrdersResponse -> {
            if (dueOrdersResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (dueOrdersResponse.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + "backend error");
                return;
            }


/*

            customerNameTv.setText(dueOrdersResponse.getCustomer().getCustomerFname());//set customer name
            customerPhone = dueOrdersResponse.getCustomer().getPhone();
            customerAddress = dueOrdersResponse.getCustomer().getAddress();
            ordersRv.setLayoutManager(new LinearLayoutManager(getContext()));
*/


            //customerId = getArguments().getString("customerId");
            binding.nameTv.setText(dueOrdersResponse.getCustomer().getCompanyName() + "@" + dueOrdersResponse.getCustomer().getCustomerFname());
            binding.phoneTv.setText(dueOrdersResponse.getCustomer().getPhone());
            binding.addressTv.setText(dueOrdersResponse.getCustomer().getAddress());


            /**
             * set  receiptMethod
             */

            receiptMethodList.clear();
            receiptMethodList.addAll(dueOrdersResponse.getPaymentTypes());


            /**
             * set  receiptSubMethod
             */

            receiptSubMethodList.clear();
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_1());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_2());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_3());
            /**
             * set the bank list
             */
            banks = new ArrayList<>();
            banks.clear();
            banks.addAll(dueOrdersResponse.getMainBanks());
            bankIdList = new ArrayList<>();
            bankIdList.clear();

            for (int i = 0; i < banks.size(); i++) {
                bankList.add(banks.get(i).getMainBankName());
                bankIdList.add(banks.get(i).getMainBankID());
            }


            /**
             * by default selected first option means cash //by default selected cash
             */
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < receiptMethodList.size(); i++) {
                nameList.add(receiptMethodList.get(i).getName());
            }
            binding.receiptMethodTv.setItem(nameList);
            binding.receiptMethodTv.setSelection(0);
            selectedReceiptMethod = String.valueOf(1);

            binding.receiptSubMethod.setItem(receiptSubMethodList);

            binding.selectBankTv.setItem(bankList);

            binding.infoPortionLay.setVisibility(View.VISIBLE);

            return;
        });

    }

    private void getCustomersFromServer(String currentUserToken, String currentVendorId, String currentText, String userId) {
        dueCollectionViewModel.apiCallForGetAndSupplierListCustomers(getActivity(), currentUserToken, currentVendorId, currentText, userId);
        dueCollectionViewModel.getCustomerAndSupplierList().observe(this, response -> {
            isDataFetching = true;
            customerResponseList = new ArrayList<>();
            customerResponseList.clear();
            customerResponseList.addAll(response.getLists());
            customerNameList = new ArrayList<>();
            customerNameList.clear();
            customerList = response.getLists();

            for (int i = 0; i < customerList.size(); i++) {
                CustomerAndSupplierList customerResponse = customerList.get(i);
                customerNameList.add("" + customerResponse.getCompanyName() + " @ " + customerResponse.getCustomerFname());
            }
            if (customerNameList.isEmpty()) { // show message in the item if the list is empty
                customerNameList.add("No_DATA_FOUND");
            }
            customerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
            binding.customerNameEt.setAdapter(customerArrayAdapter);
            binding.customerNameEt.showDropDown();
            isDataFetching = false;
        });

    }

    private void ok(String selectedCustomerId, String selectedUserVendorId, String s) {

        // dueCollectionViewModel.apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(getActivity(), selectedCustomerId, selectedUserVendorId);
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        dueCollectionViewModel.getGetDueOrders().observe(getViewLifecycleOwner(), dueOrdersResponse -> {
            if (dueOrdersResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (dueOrdersResponse.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + "backend error");
                return;
            }
            receiptSubMethodList.clear();
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_1());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_2());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_3());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_5());

            /**
             * by default selected first option means cash //by default selected cash
             */
            // receiptMethodTv.setItem(receiptMethodList);
            // receiptMethodTv.setSelection(0);
            //     selectedReceiptMethod = String.valueOf(1);

            binding.receiptSubMethod.setItem(receiptSubMethodList);
            return;


        });


    }

    public void getDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");

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

        binding.date.setText(selectedDate);
    }


}