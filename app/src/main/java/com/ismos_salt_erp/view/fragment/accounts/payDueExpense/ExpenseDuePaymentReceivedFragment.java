package com.ismos_salt_erp.view.fragment.accounts.payDueExpense;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ExpenseDueOrdersAdapter;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AccountResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.MainBank;
import com.ismos_salt_erp.serverResponseModel.PaymentToExpenseResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.ExpenseVendorViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ExpenseDuePaymentReceivedFragment extends AddUpDel implements DatePickerDialog.OnDateSetListener {

    private ExpenseVendorViewModel expenseVendorViewModel;
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;


    View view;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @BindView(R.id.remarksEtExpenseDue)
    EditText remarksEt;
    @BindView(R.id.accountNoEtExpenseDue)
    EditText accountNoEt;
    @BindView(R.id.brandsEtExpenseDue)
    EditText brandsEt;
    @BindView(R.id.nameTvExpenseDue)
    TextView nameTv;
    @BindView(R.id.phoneTvExpenseDue)
    TextView phoneTv;
    @BindView(R.id.addressTvExpenseDue)
    TextView addressTv;
    @BindView(R.id.payableDueExpenseDue)
    TextView payableDue;
    @BindView(R.id.paidAmountEtExpenseDue)
    TextView paidAmountEt;
    @BindView(R.id.enterPrice)
    SmartMaterialSpinner enterPrice;
    @BindView(R.id.paymentToOptionExpenseDue)
    SmartMaterialSpinner paymentToOptionSupplier;
    @BindView(R.id.paymentMethodTvExpenseDue)
    SmartMaterialSpinner paymentMethodTv;
    @BindView(R.id.paymentSubMethodExpenseDue)
    SmartMaterialSpinner paymentSubMethod;
    @BindView(R.id.selectBankTvExpenseDue)
    SmartMaterialSpinner selectBankTv;
    @BindView(R.id.SelectAccountNoTvExpenseDue)
    SmartMaterialSpinner selectAccountNoTv;
    @BindView(R.id.remainingTvExpenseDue)
    TextView remainingRemarksEt;
    @BindView(R.id.dateExpenseDue)
    TextView date;
    @BindView(R.id.totalDueExpenseDue)
    TextView totalDue;
    @BindView(R.id.saveBtnExpenseDue)
    Button saveBtn;

    @BindView(R.id.paymentSnd)
    LinearLayout paymentSnd;

    List<String> selectedBankIdList = new ArrayList<>();

    ArrayList<String> selectedOrderListFromPrevious;
    String customerId;
    List<MainBank> banks = new ArrayList<>();
    List<PaymentTypes> receiptMethodList = new ArrayList<>();
    List<PaymentToExpenseResponse> paymentToOptionList = new ArrayList<>();
    List<String> paymentToOptionNameList = new ArrayList<>();
    List<String> receiptSubMethodList = new ArrayList<>();
    List<String> bankList = new ArrayList<>();
    List<String> bankIdList = new ArrayList<>();
    List<String> optionList = new ArrayList<>();
    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String selectedBankId = "0";
    String selectPaymentSubType;
    String selectPaymentOptionSupplier;
    private String selectedAccountNo;

    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
    List<String> customerOrderIdList;
    private String selectedEnterPrice;
    private SalesRequisitionViewModel salesRequisitionViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_expense_due_payment_received, container, false);
        ButterKnife.bind(this, view);
        expenseVendorViewModel = ViewModelProviders.of(this).get(ExpenseVendorViewModel.class);
        duePaymentReceivedViewModel = ViewModelProviders.of(this).get(DuePaymentReceivedViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        getDataFromPreviousFragment();
        hideKeyboard(getActivity());
        //set date by default
        setDate();

        /**
         * now get the page data from server
         */
        expenseVendorViewModel.apiCallForGetExpenseVendorByVendorAndCustomerId(getActivity(), customerId);
        expenseVendorViewModel.getExpenseVendorByVendorAndCustomerId().observe(getViewLifecycleOwner(), expenseDueResponse -> {
            try {
                /**
                 * set payment to option
                 */
                paymentToOptionList.addAll(expenseDueResponse.getPaymentTo());

                for (int i = 0; i < paymentToOptionList.size(); i++) {
                    paymentToOptionNameList.add(paymentToOptionList.get(i).getMainBankName());
                }


                paymentToOptionSupplier.setItem(paymentToOptionNameList);
                /**
                 * set  receiptMethod
                 */
                receiptMethodList.clear();
                receiptMethodList.addAll(expenseDueResponse.getPaymentTypes());

                /**
                 * set  receiptSubMethod
                 */
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_1());
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_2());
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_3());
                /**
                 * set the bank list
                 */
                banks = expenseDueResponse.getMainBanks();
          /*  banks.forEach(mainBank -> {
                bankList.add(mainBank.getMainBankName());
                bankIdList.add(mainBank.getMainBankID());
            });*/

                for (int i = 0; i < banks.size(); i++) {
                    bankList.add(banks.get(i).getMainBankName());
                    bankIdList.add(banks.get(i).getMainBankID());
                }

                List<String> nam = new ArrayList<>();
                nam.clear();
                for (int i = 0; i < receiptMethodList.size(); i++) {
                    nam.add(receiptMethodList.get(i).getName());
                }
                paymentMethodTv.setItem(nam);
                paymentMethodTv.setSelection(0);
                selectedReceiptMethod = "1";


                paymentSubMethod.setItem(receiptSubMethodList);
        /*    paymentSubMethod.setSelection(0);
            selectPaymentSubType = "1";*/


                selectBankTv.setItem(bankList);
           /* selectBankTv.setSelectedIndex(0);
            selectedBankId = bankIdList.get(0);*/
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
        });


        selectAccountNoTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectAccountNoTv.setText(item.toString());
                selectAccountNoTv.setSelected(true);

                selectedAccountNo = String.valueOf(selectedBankIdList.get(position));

                //selectAccountNoTv.setSelectedIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paymentSubMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectPaymentSubType = receiptSubMethodList.get(position);
                selectPaymentSubType = String.valueOf(position + 1);
                // paymentSubMethod.setText(item.toString());
                paymentSubMethod.setSelected(true);
                if (position == 3) {
                    selectPaymentSubType = "5";
                }
                //receiptSubMethod.setSelectedIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paymentMethodTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //selectedReceiptMethod = receiptMethodList.get(position);//set selected ReceiptMethod for send to server
                selectedReceiptMethod = receiptMethodList.get(position).getId();//set selected ReceiptMethod for send to server

                if (position == 0 || position == 1) {
                    paymentSnd.setVisibility(View.GONE);
                } else {
                    paymentSnd.setVisibility(View.VISIBLE);
                }
                //paymentMethodTv.setText(item.toString());
                paymentMethodTv.setSelected(true);
                //receiptMethodTv.setSelectedIndex(position);
                if (position == 3) {
                    if (position == 3) {
                        receiptSubMethodList.clear();
                        receiptSubMethodList.add("Online Deposit");
                        paymentSubMethod.setItem(receiptSubMethodList);
                        if (receiptSubMethodList.size() == 1) {
                            paymentSubMethod.setSelection(0);
                        }
                        selectPaymentSubType = "4";

                    }
                }
                if (position == 2) {
                    String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    ok(customerId, selectedUserVendorId, "1"); // get due orders from server

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paymentToOptionSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPaymentOptionSupplier = String.valueOf(position + 1);
                // paymentToOptionSupplier.setText(item.toString());
                paymentToOptionSupplier.setSelected(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectBankTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedBankId = bankIdList.get(position);

                accountList(selectedBankId, selectedEnterPrice);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paidAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double currentAmount = Double.parseDouble(paidAmountEt.getText().toString().trim().isEmpty() ? "0" : paidAmountEt.getText().toString().trim());
                Double totalDueAmount = Double.parseDouble(totalDue.getText().toString().trim().isEmpty() ? "0" : totalDue.getText().toString().trim());
                if (currentAmount < 0) {
                    paidAmountEt.setText(String.valueOf(0));
                    return;
                }
                if (currentAmount > totalDueAmount) {
                    paidAmountEt.setText(String.valueOf(totalDueAmount));
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterPrice = enterpriseResponseList.get(position).getStoreID();
                accountList(selectedBankId, selectedEnterPrice);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getPageDataFromServer();
        return view;
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
        enterPrice.setItem(enterpriseNameList);


    }


    private void accountList(String selectedBankId, String enterPrice) {

        duePaymentReceivedViewModel.apiCallForGetAccountListByBankNameId(//call api for get the list
                getActivity(), PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID(),
                selectedBankId, enterPrice);//

        duePaymentReceivedViewModel.getAccountListByBankId().observe(getViewLifecycleOwner(), accountNumberListResponse -> {
            accountResponseList.clear();

            setItemsToSelectAccountTv(accountNumberListResponse.getLists());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDate() {
        date.setText("" + DataModify.currentDate());
    }

    private void ok(String customerId, String selectedUserVendorId, String s) {
        expenseVendorViewModel.getExpenseVendorByVendorAndCustomerId().observe(getViewLifecycleOwner(), expenseDueResponse -> {
            try {

                /**
                 * set  receiptSubMethod
                 */
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_1());
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_2());
                receiptSubMethodList.add(expenseDueResponse.getPaymentSubTypes().get_3());
                receiptSubMethodList.add("Outward Transferred");


                paymentSubMethod.setItem(receiptSubMethodList);
        /*    paymentSubMethod.setSelection(0);
            selectPaymentSubType = "1";*/


            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
        });

    }

    private void setItemsToSelectAccountTv(List<AccountResponse> accountResponseList) {
        try {
            optionList.clear();
            for (int i = 0; i < accountResponseList.size(); i++) {
                selectedBankIdList.add(accountResponseList.get(i).getBankID());
                String option = accountResponseList.get(i).getAccountantName() + "/" + accountResponseList.get(i).getBankBranch() + "/" + accountResponseList.get(i).getAccountNumber();
                optionList.add(option);
            }


            selectAccountNoTv.setItem(optionList);
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        toolbar.setText(AccountsUtil.payDueExpense);
        selectedOrderListFromPrevious = getArguments().getStringArrayList("selectedDueList");
        customerId = getArguments().getString("customerId");
        nameTv.setText(getArguments().getString("customerName"));
        phoneTv.setText(getArguments().getString("customerPhone"));
        addressTv.setText(getArguments().getString("customerAddress"));
        totalDue.setText(DataModify.addFourDigit(getArguments().getString("totalDue")));
        remainingRemarksEt.setText(DataModify.addFourDigit(getArguments().getString("totalDue")));
        payableDue.setText(DataModify.addFourDigit(getArguments().getString("totalDue")));
        customerOrderIdList = getArguments().getStringArrayList("selectedOrderIds");
        /**
         * we want to show set current date in dateView
         */
        date.setText(getCustomCurrentDateTime("yyy/MM/dd"));
    }




    @OnClick(R.id.saveBtnExpenseDue)
    public void payExpenseDue() {

        String paymentTypeVal = selectedReceiptMethod;
        if (paymentTypeVal == null) {
            message("Please Select your Payment method");
            return;
        }


        if (selectedReceiptMethod == null) {
            message("Please select payment method");
            return;
        }

        if (!paymentTypeVal.equals("1")) {//here 1 means cash

            if (!paymentTypeVal.equals("2")) {

                if (selectPaymentSubType == null) {
                    message( "Please select payment sub method");
                    return;
                }
                if (selectedBankId == null) {
                    message( "Please select bank");
                    return;
                }
                if (selectedAccountNo == null) {
                    message("Please Select account number");
                    return;
                }

                if (selectPaymentOptionSupplier == null) {
                    message("Please select payment to (Bank)");
                    return;
                }
                if (brandsEt.getText().toString().isEmpty()) {
                    brandsEt.setError("Branch name mandatory");
                    brandsEt.requestFocus();
                    return;
                }
                if (accountNoEt.getText().toString().isEmpty()) {
                    accountNoEt.setError("Account number mandatory");
                    accountNoEt.requestFocus();
                    return;
                }
            }
        }
        if (paidAmountEt.getText().toString().isEmpty()) {
            paidAmountEt.setError("Paid Amount Empty");
            paidAmountEt.requestFocus();
            return;
        }
        if (remarksEt.getText().toString().isEmpty()) {
            remarksEt.setError("Remarks Mandatory");
            remarksEt.requestFocus();
            return;
        }

        ExpenseDueOrdersAdapter.selectedOrderList.clear();
        showDialog(getString(R.string.confirm_dialog_title));
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        /**
         * for clear searching orders
         */
        ExpenseDueOrdersAdapter.selectedOrderList.clear();
        getActivity().onBackPressed();
    }

    @OnClick(R.id.dateExpenseDue)
    public void getDate() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    /**
     * for set user selected date
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            submit();
        }
    }

    private void submit() {
        String customDiscount = "0";

        String storeId = selectedEnterPrice;
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String paymentSubType = selectPaymentSubType;
        String paymentTypeVal = selectedReceiptMethod;

        expenseVendorViewModel.apiCallForPayExpenseDue(
                        getActivity(), storeId, customerId, new ArrayList<>(customerOrderIdList), paidAmountEt.getText().toString(), totalDue.getText().toString(), customDiscount, userId,
                        paymentTypeVal, paymentSubType, date.getText().toString(), remarksEt.getText().toString(), selectedBankId, brandsEt.getText().toString(), accountNoEt.getText().toString(), selectedAccountNo)
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
                    ExpenseDueOrdersAdapter.selectedOrderList.clear();
                    getActivity().onBackPressed();
                });
    }

    @Override
    public void imageUri(Intent uri) {

    }
}