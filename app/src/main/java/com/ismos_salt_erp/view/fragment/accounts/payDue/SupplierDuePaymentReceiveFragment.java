package com.ismos_salt_erp.view.fragment.accounts.payDue;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CustomerOrderAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AccountResponse;
import com.ismos_salt_erp.serverResponseModel.MainBank;
import com.ismos_salt_erp.serverResponseModel.PaymentToResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.SupplierDueViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupplierDuePaymentReceiveFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private SupplierDueViewModel supplierDueViewModel;
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;

    View view;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    String paymentLimit;

    @BindView(R.id.accountNoEt)
    EditText accountNoEt;
    @BindView(R.id.brandsEt)
    EditText brandsEt;
    @BindView(R.id.nameTvSupplier)
    TextView nameTv;
    @BindView(R.id.phoneTvSupplier)
    TextView phoneTv;
    @BindView(R.id.addressTvSupplier)
    TextView addressTv;
    @BindView(R.id.receivableDueSupplier)
    TextView receivableDue;
    @BindView(R.id.dueLimitSupplier)
    TextView dueLimitTv;
    @BindView(R.id.paidAmountEtSupplier)
    TextView paidAmountEt;
    @BindView(R.id.paymentToOptionSupplier)
    SmartMaterialSpinner paymentToOptionSupplier;
    @BindView(R.id.receiptMethodTvSupplier)
    SmartMaterialSpinner receiptMethodTv;
    @BindView(R.id.receiptSubMethodSupplier)
    SmartMaterialSpinner receiptSubMethod;
    @BindView(R.id.selectBankTvSupplier)
    SmartMaterialSpinner selectBankTv;
    @BindView(R.id.SelectAccountNoTvSupplier)
    SmartMaterialSpinner selectAccountNoTv;
    @BindView(R.id.ReceiptRemarksSupplier)
    EditText receiptRemarksEt;
    @BindView(R.id.dateSupplier)
    TextView date;
    @BindView(R.id.totalDueSupplier)
    TextView totalDue;
    @BindView(R.id.saveBtnSupplier)
    Button saveBtn;


    @BindView(R.id.receiptSubMethodDdownView)
    LinearLayout receiptSubMethodDdownView;
    @BindView(R.id.bankDDownView)
    LinearLayout bankDDownView;
    @BindView(R.id.accountDdownView)
    LinearLayout accountDdownView;
    @BindView(R.id.paymentToDDownView)
    LinearLayout paymentToDDownView;
    @BindView(R.id.withoutCash)
    LinearLayout withoutCash;


    List<String> selectedBankIdList = new ArrayList<>();

    String customerId;
    List<MainBank> banks = new ArrayList<>();
    List<PaymentTypes> receiptMethodList = new ArrayList<>();
    List<PaymentToResponse> paymentToOptionList = new ArrayList<>();
    List<String> paymentToOptionNameList = new ArrayList<>();
    List<String> receiptSubMethodList = new ArrayList<>();
    List<String> bankList = new ArrayList<>();
    List<String> bankIdList = new ArrayList<>();
    List<String> optionList = new ArrayList<>();
    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String selectedBankId;
    String selectPaymentSubType;
    String selectPaymentOptionSupplier;
    private String selectedAccountNo;


    List<String> customerOrderIdList;


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_supplier_due_payment_receive, container, false);
        ButterKnife.bind(this, view);
        supplierDueViewModel = ViewModelProviders.of(this).get(SupplierDueViewModel.class);
        duePaymentReceivedViewModel = ViewModelProviders.of(this).get(DuePaymentReceivedViewModel.class);
        getDataFromPreViousFragment();

        /**
         * set customer info from previous DueCollectionFragment
         */
        setCustomerInformation();


        /**
         * get page data from server
         */
        supplierDueViewModel.apiCallForGetSupplierOrder(getActivity(), customerId);

        supplierDueViewModel.getSupplierOrders().observe(getViewLifecycleOwner(), supplierOrdersResponse -> {

            /**
             * set payment to option
             */
            paymentToOptionList.addAll(supplierOrdersResponse.getPaymentTo());

            for (int i = 0; i < paymentToOptionList.size(); i++) {
                paymentToOptionNameList.add(paymentToOptionList.get(i).getMainBankName());
            }


            paymentToOptionSupplier.setItem(paymentToOptionNameList);

            /**
             * for set current customer due limit
             */

            dueLimitTv.setText(String.valueOf(supplierOrdersResponse.getCustomer().getDueLimit()));
            /**
             * set  receiptMethod
             */

            /**
             * set  receiptSubMethod
             */
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_1());
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_2());
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_3());
            /**
             * set the bank list
             */
            banks = supplierOrdersResponse.getMainBanks();
           /* banks.forEach(mainBank -> {
                bankList.add(mainBank.getMainBankName());
                bankIdList.add(mainBank.getMainBankID());
            });*/
            for (int i = 0; i < banks.size(); i++) {
                bankList.add(banks.get(i).getMainBankName());
                bankIdList.add(banks.get(i).getMainBankID());
            }

            receiptMethodList.addAll(supplierOrdersResponse.getPaymentTypes());
            List<String> nam = new ArrayList<>();
            nam.clear();
            for (int i = 0; i < receiptMethodList.size(); i++) {
                nam.add(receiptMethodList.get(i).getName());
            }

            receiptMethodTv.setItem(nam);
            receiptMethodTv.setSelection(0);
            selectedReceiptMethod = String.valueOf(1);

            receiptSubMethod.setItem(receiptSubMethodList);
            receiptSubMethod.setSelection(0);//by default selected first option
            selectPaymentSubType = receiptSubMethodList.get(1);


            selectBankTv.setItem(bankList);
           /* selectBankTv.setSelection(0);
            selectedBankId = banks.get(0).getMainBankID();*/
        });



        selectAccountNoTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // selectAccountNoTv.setText(item.toString());
                selectAccountNoTv.setSelected(true);

                selectedAccountNo = String.valueOf(selectedBankIdList.get(position));

                //selectAccountNoTv.setSelectedIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        receiptSubMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectPaymentSubType = String.valueOf(position + 1);
                 receiptSubMethod.setSelected(true);
             }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * set receipt method after click
         */
        receiptMethodTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1) {//here position 0 means this is cash so...
                    withoutCash.setVisibility(View.GONE);
                } else {
                    withoutCash.setVisibility(View.VISIBLE);
                }
                selectedReceiptMethod = receiptMethodList.get(position).getId();//set selected ReceiptMethod for send to server

                 receiptMethodTv.setSelected(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        paymentToOptionSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPaymentOptionSupplier = String.valueOf(position + 1);
                //paymentToOptionSupplier.setText(item.toString());
                paymentToOptionSupplier.setSelected(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * when user select the bank account then get the the bank account list by user selected bank account name id
         */
        selectBankTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedBankId = banks.get(position).getMainBankID();
                /**
                 * now get bank account list by bank name id
                 */
                duePaymentReceivedViewModel.apiCallForGetAccountListByBankNameId(//call api for get the list
                        getActivity(), PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID(),
                        banks.get(position).getMainBankID(),"");//

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
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setItemsToSelectAccountTv(List<AccountResponse> accountResponseList) {
        optionList.clear();
        accountResponseList.forEach(accountResponse -> {
            selectedBankIdList.add(accountResponse.getBankID());

            String option = accountResponse.getAccountantName() + "/" + accountResponse.getBankBranch() + "/" + accountResponse.getAccountNumber();
            optionList.add(option);
        });
        selectAccountNoTv.setItem(optionList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCustomerInformation() {
        customerId = getArguments().getString("customerId");
        nameTv.setText(getArguments().getString("customerName"));
        phoneTv.setText(getArguments().getString("customerPhone"));
        addressTv.setText(getArguments().getString("customerAddress"));
        totalDue.setText(getArguments().getString("totalDue"));
        receivableDue.setText(getArguments().getString("totalDue"));
        /**
         * we want to show set current date in dateView
         */
        date.setText(getCustomCurrentDateTime("yyy/MM/dd"));
    }

    private void getDataFromPreViousFragment() {
        toolbar.setText("Pay Due");
        paymentLimit = getArguments().getString("paymentLimit");
        customerOrderIdList = getArguments().getStringArrayList("selectedOrderIds");
    }

    @OnClick(R.id.backbtn)
    public void backBtn() {
        getActivity().onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.saveBtnSupplier)
    public void saveBtnClick() {
        String customDiscount = "0";

        String token = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
        String collectedPaidAmount = paidAmountEt.getText().toString();
        String receiptRemarks = receiptRemarksEt.getText().toString();

        String branch = brandsEt.getText().toString();
        String accountNo = accountNoEt.getText().toString();

        if (collectedPaidAmount.isEmpty()) {
            paidAmountEt.setError("Paid amount mandatory");
            paidAmountEt.requestFocus();
            return;
        }
        if (Integer.parseInt(selectedReceiptMethod) != 1) {//here 1 means cash without cash branch and account no is mandatory
            if (Integer.parseInt(selectedReceiptMethod) != 2) {//here 2 means cheque without cash branch and account no is mandatory

                if (selectedBankId == null) {
                    infoMessage(getActivity().getApplication(), "Please select bank");
                    return;
                }
                if (selectedAccountNo == null) {
                    infoMessage(getActivity().getApplication(), "Please select account number");
                    return;
                }
                if (selectPaymentOptionSupplier == null) {
                    infoMessage(requireActivity().getApplication(), "Please select payment to (Bank)");
                    return;
                }

                if (branch.isEmpty()) {
                    brandsEt.setError("Branch Mandatory");
                    brandsEt.requestFocus();
                    return;
                }
                if (accountNo.isEmpty()) {
                    accountNoEt.setError("Account No is Mandatory");
                    accountNoEt.requestFocus();
                    return;
                }
            }

        }
        if (receiptRemarks.isEmpty()) {
            receiptRemarksEt.setError("Receipt Remarks Mandatory");
            receiptRemarksEt.requestFocus();
            return;
        }


        if (Double.parseDouble(paymentLimit) != 0) {//if payment limit have 0 then user can set any amount
            if (Double.parseDouble(collectedPaidAmount) < Double.parseDouble(paymentLimit) || Double.parseDouble(collectedPaidAmount) > Double.parseDouble(paymentLimit)) {
                paidAmountEt.setError("Paid Amount Should be " + paymentLimit);
                return;
            }
        }


        String storeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions();
        String profileTypeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getProfileTypeId();
        String paymentTypeVal = selectedReceiptMethod;


        String selectedPaymentToOption = selectPaymentOptionSupplier;


        String paymentSubType = selectPaymentSubType;
        String totalDuee = totalDue.getText().toString();

        //asks for confirmation
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setIcon(R.drawable.warning_btn);
        alertDialog.setMessage("Are you Sure ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) ->
                        supplierDueViewModel.apiCallForPaySupplierDue(getActivity(),
                                token, new HashSet<>(customerOrderIdList), collectedPaidAmount, totalDuee, storeId, userId, permissions, profileTypeId, customerId, vendorId,
                                paymentTypeVal, paymentSubType, customDiscount, selectedBankId, branch, accountNo,
                                receiptRemarks,
                                date.getText().toString(),
                                selectedPaymentToOption,"",""
                        ).observe(getViewLifecycleOwner(), response -> {
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                return;
                            }
                            CustomerOrderAdapter.selectedOrderList.clear();
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                        }));

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    @OnClick(R.id.dateSupplier)
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
        date.setText(selectedDate);
    }
}