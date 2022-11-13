package com.ismos_salt_erp.view.fragment.accounts.receiveDue;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.PayDueAmountViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class DuePaymentReceivedFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    View view;
    private DueCollectionViewModel dueCollectionViewModel;
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;
    private PayDueAmountViewModel payDueAmountViewModel;
    @BindView(R.id.backbtn)
    ImageButton backBtn;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.phoneTv)
    TextView phoneTv;
    @BindView(R.id.addressTv)
    TextView addressTv;
    @BindView(R.id.receivableDue)
    TextView receivableDue;
    @BindView(R.id.dueLimit)
    TextView dueLimitTv;
    @BindView(R.id.paidAmountEt)
    TextView paidAmountEt;
    @BindView(R.id.receiptMethodTv)
    SmartMaterialSpinner receiptMethodTv;
    @BindView(R.id.receiptSubMethod)
    SmartMaterialSpinner receiptSubMethod;
    @BindView(R.id.selectBankTv)
    SmartMaterialSpinner selectBankTv;
    @BindView(R.id.SelectAccountNoTv)
    SmartMaterialSpinner selectAccountNoTv;
    @BindView(R.id.ReceiptRemarks)
    EditText receiptRemarksEt;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.totalDue)
    TextView totalDue;
    @BindView(R.id.saveBtn)
    Button saveBtn;

    @BindView(R.id.subPaymentAll)
    LinearLayout subPaymentAll;


    String customerId;
    List<MainBank> banks = new ArrayList<>();
    List<PaymentTypes> receiptMethodList = new ArrayList<>();
    List<String> receiptSubMethodList = new ArrayList<>();
    List<String> bankList = new ArrayList<>();
    List<String> bankIdList = new ArrayList<>();
    List<String> optionList = new ArrayList<>();
    List<String> accountBankId = new ArrayList<>();
    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String selectedBankId;
    String selectPaymentSubType;
    String accountNo;


    List<String> customerOrderIdList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_due_payment_received, container, false);
        ButterKnife.bind(this, view);
        toolbar.setText("Due Payment Received");//set the toolbar title

        hideKeyboard(getActivity());


        dueCollectionViewModel = ViewModelProviders.of(this).get(DueCollectionViewModel.class);
        duePaymentReceivedViewModel = ViewModelProviders.of(this).get(DuePaymentReceivedViewModel.class);
        payDueAmountViewModel = ViewModelProviders.of(this).get(PayDueAmountViewModel.class);

        /**
         * set customer info from previous DueCollectionFragment
         */
        setCustomerInformation();


        /**
         * now get data for view from server
         */
        dueCollectionViewModel.apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(getActivity(), customerId, PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID());
        dueCollectionViewModel.getGetDueOrders().observe(getViewLifecycleOwner(), dueOrdersResponse -> {
            if (dueOrdersResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (dueOrdersResponse.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + "backend error");
                return;
            }

            /**
             * for set current customer due limit
             */
            dueLimitTv.setText(dueOrdersResponse.getCustomer().getDueLimit());
            /**
             * set  receiptMethod
             */
            receiptMethodList.addAll(dueOrdersResponse.getPaymentTypes());

            /**
             * set  receiptSubMethod
             */
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_1());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_2());
            receiptSubMethodList.add(dueOrdersResponse.getPaymentSubTypes().get_3());
            /**
             * set the bank list
             */
            banks = dueOrdersResponse.getMainBanks();
           /* banks.forEach(mainBank -> {
                bankList.add(mainBank.getMainBankName());
                bankIdList.add(mainBank.getMainBankID());
            });*/
            for (int i = 0; i < banks.size(); i++) {
                bankList.add(banks.get(i).getMainBankName());
                bankIdList.add(banks.get(i).getMainBankID());
            }

            /**
             * by default selected first option means cash //by default selected cash
             */

            List<String> nam = new ArrayList<>();
            nam.clear();
            for (int i = 0; i < receiptMethodList.size(); i++) {
                nam.add(receiptMethodList.get(i).getName());
            }
            receiptMethodTv.setItem(nam);
            receiptMethodTv.setSelection(0);
            selectedReceiptMethod = String.valueOf(1);


            receiptSubMethod.setItem(receiptSubMethodList);
           /* receiptSubMethod.setSelection(0);//by default selected first option
            selectPaymentSubType = receiptSubMethodList.get(1);*/


            selectBankTv.setItem(bankList);
            /*selectBankTv.setSelectedIndex(0);
            selectedBankId = banks.get(0).getMainBankID();*/
        });

        receiptMethodTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReceiptMethod = receiptMethodList.get(position).getId();
                /**
                 * here position 0 and 1 means "Cash & cheque" so we can hide the other payment info
                 */
                if (position == 0 || position == 1) {
                    subPaymentAll.setVisibility(View.GONE);
                    return;
                }
                subPaymentAll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        receiptSubMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPaymentSubType = String.valueOf(position + 1);
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


        selectAccountNoTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountNo = accountBankId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    /**
     * set customer info from previous DueCollectionFragment
     */
    private void setCustomerInformation() {
        customerId = getArguments().getString("customerId");
        nameTv.setText(getArguments().getString("customerName"));
        phoneTv.setText(getArguments().getString("customerPhone"));
        addressTv.setText(getArguments().getString("customerAddress"));
        totalDue.setText(getArguments().getString("totalDue"));
        receivableDue.setText(getArguments().getString("totalDue"));
        customerOrderIdList = getArguments().getStringArrayList("selectedOrderIds");

        /**
         * we want to show set current date in dateView
         */


        /*System.out.println(dtf.format(now));*/
        date.setText(getCustomCurrentDateTime("yyy/MM/dd"));
    }

    private void setItemsToSelectAccountTv(List<AccountResponse> accountResponseList) {
        optionList.clear();
        accountBankId.clear();

        for (int i = 0; i < accountResponseList.size(); i++) {
            String option = accountResponseList.get(i).getAccountantName() + "/" + accountResponseList.get(i).getBankBranch() + "/" + accountResponseList.get(i).getAccountNumber();
            optionList.add(option);
            accountBankId.add(accountResponseList.get(i).getBankID());
        }


        selectAccountNoTv.setItem(optionList);
        /*selectAccountNoTv.setSelection(0);
        accountNo = accountBankId.get(0);*/
    }


    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.saveBtn)
    public void saveCurrentUserDuePayment() {
        String collectedPaidAmount = paidAmountEt.getText().toString();
        String storeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions();
        String profileTypeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getProfileTypeId();
        String paymentTypeVal = selectedReceiptMethod;

        String paymentSubType = selectPaymentSubType;
        String totalDuee = totalDue.getText().toString();
        String remarks = receiptRemarksEt.getText().toString();

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
        if (collectedPaidAmount.isEmpty()) {
            paidAmountEt.setError("Paid Amount is Mandatory");
            paidAmountEt.requestFocus();
            return;
        }
        if (paymentTypeVal == null) {
            Toasty.info(getContext(), "Please Select your Receipt Method", Toasty.LENGTH_LONG).show();
            receiptMethodTv.requestFocus();
            return;
        }

        if (remarks.isEmpty()) {
            receiptRemarksEt.setError("Remarks Mandatory");
            receiptRemarksEt.requestFocus();
            return;
        }


        //asks for confirmation
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage("Are you sure ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) ->
                        payDueAmountViewModel.apiCallForPayDueAmount(getActivity(), vendorId, new HashSet<>(customerOrderIdList), collectedPaidAmount,
                                totalDuee, storeId, userId, permissions, profileTypeId, paymentTypeVal,
                                paymentSubType, accountNo, date.getText().toString(),
                                remarks,"","").observe(getViewLifecycleOwner(), response -> {
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                return;
                            }
                            CustomerOrderAdapter.selectedOrderList.clear();//clear the selected due id
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                        }));

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    @OnClick(R.id.date)
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

    /**
     * for set user selected date
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
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

    @Override
    public void onStart() {
        super.onStart();
        banks.clear();
        receiptMethodList.clear();
        receiptSubMethodList.clear();
        bankList.clear();
        bankIdList.clear();
        optionList.clear();
        accountBankId.clear();
    }
}