package com.ismos_salt_erp.view.fragment.accounts.receiveDue;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CustomerOrderAdapter;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AccountResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.DueOrdersResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.MainBank;
import com.ismos_salt_erp.serverResponseModel.Order;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.CustomerOrderViewmodel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.PayDueAmountViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DueCollectionFragment extends AddUpDel
        implements DatePickerDialog.OnDateSetListener {
    List<CustomerResponse> customerList;
    String customerId;
    private DueCollectionViewModel dueCollectionViewModel;

    private CustomerOrderViewmodel customerOrderViewmodel;
    View view;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerArrayAdapter;
    List<CustomerResponse> customerResponseList;
    List<String> customerNameList;
    @BindView(R.id.backbtn)
    ImageButton backBtn;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.image_view)
    ImageView imageView; // collapsing image view
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;
    @BindView(R.id.ordersRv)
    RecyclerView ordersRv;
    @BindView(R.id.customerNameEt)
    AutoCompleteTextView customerNameEt;
    @BindView(R.id.receivedNow)
    TextView receivedNow;
    @BindView(R.id.tv_total_amount)
    TextView totalDue;
    @BindView(R.id.customerNameTv)
    TextView customerNameTv;
    @BindView(R.id.tv_notice)
    TextView noOrderFound;
    @BindView(R.id.addPortion)
    LinearLayout addPortion;
    @BindView(R.id.savePortion)
    LinearLayout savePortion;
    //----------------------
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
    @BindView(R.id.totalDue)
    TextView totalDueTv;
    @BindView(R.id.receiptMethodTv)
    SmartMaterialSpinner receiptMethodTv;
    @BindView(R.id.receiptSubMethod)
    SmartMaterialSpinner receiptSubMethod;
    @BindView(R.id.selectBankTv)
    SmartMaterialSpinner selectBankTv;
    @BindView(R.id.SelectAccountNoTv)
    SmartMaterialSpinner selectAccountNoTv;

    @BindView(R.id.enterPrice)
    SmartMaterialSpinner enterPrice;
    @BindView(R.id.ReceiptRemarks)
    EditText receiptRemarksEt;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.subPaymentAll)
    LinearLayout subPaymentAll;
    List<MainBank> banks = new ArrayList<>();
    private List<PaymentTypes> receiptMethodList = new ArrayList<>();
    List<String> receiptSubMethodList = new ArrayList<>();
    List<String> bankList = new ArrayList<>();
    List<String> bankIdList = new ArrayList<>();
    List<String> optionList = new ArrayList<>();
    List<String> accountBankId = new ArrayList<>();
    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String paymentTypeVal;
    String paymentSubType;
    String selectedBankId = "0";
    String selectPaymentSubType;
    String accountNo;

    List<String> selectedOrderList;

    private String selectedEnterPrice;
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;
    private PayDueAmountViewModel payDueAmountViewModel;
    List<Order> orders = new ArrayList<>();

    //set data for move next fragment
    String customerPhone;
    String customerAddress;
    private String No_DATA_FOUND = "No Data Found";

    private boolean forBack = false;
    String selectedCustomerId;
    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
    private SalesRequisitionViewModel salesRequisitionViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_due_collection, container, false);
        ButterKnife.bind(this, view);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        dueCollectionViewModel = ViewModelProviders.of(this).get(DueCollectionViewModel.class);
        customerOrderViewmodel = ViewModelProviders.of(this).get(CustomerOrderViewmodel.class);
        duePaymentReceivedViewModel = ViewModelProviders.of(this).get(DuePaymentReceivedViewModel.class);
        payDueAmountViewModel = ViewModelProviders.of(this).get(PayDueAmountViewModel.class);
        getPageDataFromServer();
        back.setOnClickListener(v -> {
            savePortion.setVisibility(View.GONE);
            addPortion.setVisibility(View.VISIBLE);
        });
//default date set
        setDate();
        getPreviousFragmentData();
        HIDE_KEYBOARD(getActivity());
        customerNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!customerNameEt.isPerformingCompletion()) {
                    return;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (customerNameEt.isPerformingCompletion()) { // selected a product
                    return;
                }

                // if item is not selected
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    collapseLayout(); // collapse bottom layout
                    String currentText = customerNameEt.getText().toString();
                    String currentUserToken = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
                    String currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    /**
                     * for search a customer
                     */
                    getCustomersFromServer(currentUserToken, currentVendorId, currentText);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        customerNameEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (customerNameList.get(position).equals(No_DATA_FOUND)) {
                customerNameEt.getText().clear();
                return;
            }
            customerNameEt.getText().clear();

            if (!customerResponseList.isEmpty()) {//if have any customer in the current list
                customerNameEt.clearFocus();
                HIDE_KEYBOARD(getActivity());


                selectedCustomerId = customerResponseList.get(position).getCustomerID();
                String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                /**
                 * get due orders by selected customer id and vendor id
                 */

                getDueOrdersAndShowInRecyclerView(selectedCustomerId, selectedUserVendorId, "2"); // get due orders from server


            } else {
                customerNameEt.getText().clear();
            }
        });

        // for save
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
                if (position == 3) {
                    receiptSubMethodList.clear();
                    receiptSubMethodList.add("Online Deposit");
                    receiptSubMethod.setItem(receiptSubMethodList);
                    if (receiptSubMethodList.size() == 1) {
                        receiptSubMethod.setSelection(0);
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
        receiptSubMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        selectBankTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBankId = banks.get(position).getMainBankID();
                accountList(selectedBankId, selectedEnterPrice);
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


        return view;
    }

    private void accountList(String selectedBankId, String enterPrice) {
        /**
         * now get bank account list by bank name id
         */

        // Toast.makeText(getContext(), "bank = "+selectedBankId+" @ Enter = "+enterPrice, Toast.LENGTH_SHORT).show();

        duePaymentReceivedViewModel.apiCallForGetAccountListByBankNameId(//call api for get the list
                getActivity(), PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID(),
                selectedBankId, selectedEnterPrice);//

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
        enterPrice.setItem(enterpriseNameList);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDate() {
        date.setText("" + DataModify.currentDate());
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

            receiptSubMethod.setItem(receiptSubMethodList);
            return;


        });


    }

    /**
     * for get customer orders by customers id
     */
    @SuppressLint("SetTextI18n")
    private void getDueOrdersAndShowInRecyclerView(String selectedCustomerId, String vendorId, String number) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        dueCollectionViewModel.apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(getActivity(), selectedCustomerId, vendorId);

        customerOrderViewmodel.getOrderListByCustomer(getActivity(), selectedCustomerId, number).observe(getViewLifecycleOwner(), new Observer<DueOrdersResponse>() {
            @Override
            public void onChanged(DueOrdersResponse dueOrdersResponse) {
                if (dueOrdersResponse == null) {
                    errorMessage(getActivity().getApplication(), "Something Wrong");
                    return;
                }
                if (dueOrdersResponse.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + "backend error");
                    return;
                }


                orders.clear();
                orders = dueOrdersResponse.getOrders();///for store current due response


                customerNameTv.setText(dueOrdersResponse.getCustomer().getCompanyName() + " @ " + dueOrdersResponse.getCustomer().getCustomerFname());//set customer name
                customerPhone = dueOrdersResponse.getCustomer().getPhone();
                customerAddress = dueOrdersResponse.getCustomer().getAddress();
                ordersRv.setLayoutManager(new LinearLayoutManager(getContext()));


                //customerId = getArguments().getString("customerId");
                nameTv.setText(dueOrdersResponse.getCustomer().getCompanyName() + "@" + dueOrdersResponse.getCustomer().getCustomerFname());
                phoneTv.setText(dueOrdersResponse.getCustomer().getPhone());
                addressTv.setText(dueOrdersResponse.getCustomer().getAddress());

                /**
                 * for show selected customer list in Recycler view
                 */


//                for (int i = 0; i < orderList.size(); i++) {
//                    // if (Integer.parseInt(orderList.get(i).getDue()) > 0) {
//                    if (Double.parseDouble(orderList.get(i).getDue()) > 0) {
//                        mainDueOrderList.add(orderList.get(i));
//                    }
//                }

                double totalDueOfTheUser = 0;

                for (int i = 0; i < dueOrdersResponse.getOrders().size(); i++) {
                    totalDueOfTheUser += Double.parseDouble(dueOrdersResponse.getOrders().get(i).getDue());
                }


                totalDue.setText(DataModify.addFourDigit(String.valueOf(totalDueOfTheUser)));//set total due

                //   receivableDue.setText(getArguments().getString("totalDue"));

                if (!dueOrdersResponse.getOrders().isEmpty()) {
                    ordersRv.setVisibility(View.VISIBLE);
                    noOrderFound.setVisibility(View.GONE);
                    totalDueTv.setText("" + DataModify.addFourDigit(String.valueOf(totalDueOfTheUser)));

                    CustomerOrderAdapter adapter = new CustomerOrderAdapter(getActivity(), dueOrdersResponse.getOrders(), "receipt");
                    ordersRv.setAdapter(adapter);

                    dueLimitTv.setText(DataModify.addFourDigit(dueOrdersResponse.getCustomer().getDueLimit()));
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

                    for (int i = 0; i < banks.size(); i++) {
                        bankList.add(banks.get(i).getMainBankName());
                        bankIdList.add(banks.get(i).getMainBankID());
                    }


                    /**
                     * by default selected first option means cash //by default selected cash
                     */
                    List<String> nameList = new ArrayList<>();
                    nameList.clear();
                    for (int i = 0; i < receiptMethodList.size(); i++) {
                        nameList.add(receiptMethodList.get(i).getName());
                    }
                    receiptMethodTv.setItem(nameList);
                    receiptMethodTv.setSelection(0);
                    selectedReceiptMethod = String.valueOf(1);

                    receiptSubMethod.setItem(receiptSubMethodList);

                    selectBankTv.setItem(bankList);

                    return;
                } else {
                    ordersRv.setVisibility(View.GONE);
                    noOrderFound.setText("No order Found");
                    noOrderFound.setVisibility(View.VISIBLE);
                }


            }
        });


        /*dueCollectionViewModel.getGetDueOrders().observe(getViewLifecycleOwner(), dueOrdersResponse -> {

        });*/

    }

    // hide keyboard
    public static void HIDE_KEYBOARD(FragmentActivity activity) {
        try {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }

    private void getPreviousFragmentData() {
        /**
         * get data from management fragment
         */
        toolbarTitle.setText(getArguments().getString("name"));
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


    }


    @OnClick(R.id.backbtn)
    public void backClick() {
        CustomerOrderAdapter.selectedOrderList.clear();
        getActivity().onBackPressed();
    }

    @OnClick(R.id.image_view)
    public void setArrowBtnClick() {
        expandAndCollapseLayout();
    }

    @OnClick(R.id.receivedNow)
    public void receivedNowClick() {
        selectedOrderList = new ArrayList<>(CustomerOrderAdapter.selectedOrderList);
        if (selectedOrderList.isEmpty()) {
            hideKeyboard(getActivity());
            infoMessage(getActivity().getApplication(), "Please Select Orders");
            return;
        }
        double currentSelectedTotal = 0;
        for (int i = 0; i < orders.size(); i++) {
            for (int i1 = 0; i1 < CustomerOrderAdapter.selectedOrderList.size(); i1++) {
                if (orders.get(i).getOrderID().equals(selectedOrderList.get(i1))) {
                    currentSelectedTotal += Double.parseDouble(orders.get(i).getDue());
                }
            }
        }
        receivableDue.setText("" + DataModify.addFourDigit(String.valueOf(currentSelectedTotal)));
        savePortion.setVisibility(View.VISIBLE);
        addPortion.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getCustomersFromServer(String token, String vendorID, String key) {
        dueCollectionViewModel.apiCallForGetCustomers(getActivity(), token, vendorID, key);
        dueCollectionViewModel.getCustomerList().observe(this, customerSearchResponse -> {
            isDataFetching = true;
            customerResponseList = new ArrayList<>();
            customerResponseList.clear();
            customerResponseList.addAll(customerSearchResponse.getLists());
            customerNameList = new ArrayList<>();
            customerList = customerSearchResponse.getLists();

            for (int i = 0; i < customerList.size(); i++) {
                CustomerResponse customerResponse = customerList.get(i);
                customerNameList.add("" + customerResponse.getCompanyName() + " @ " + customerResponse.getCustomerFname());
            }
            if (customerNameList.isEmpty()) { // show message in the item if the list is empty
                customerNameList.add(No_DATA_FOUND);
            }
            customerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
            customerNameEt.setAdapter(customerArrayAdapter);
            customerNameEt.showDropDown();
            isDataFetching = false;
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void expandAndCollapseLayout() {
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
        } else {
            expandableLayout.expand();
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
        }
    }


    // collapse the layout
    @SuppressLint("UseCompatLoadingForDrawables")
    private void collapseLayout() {
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
        }
    }

    @OnClick(R.id.saveBtn)
    public void saveCurrentUserDuePayment() {

        paymentTypeVal = selectedReceiptMethod;
        paymentSubType = selectPaymentSubType;

        if (selectedEnterPrice == null) {
            message(getString(R.string.enterprise_info));
            return;

        }
        if (!paymentTypeVal.equals("1")) {//here 1 means cash

            if (!paymentTypeVal.equals("2")) {

                if (selectedReceiptMethod == null) {
                    message("please select receipt method");
                    return;
                }

                if (selectPaymentSubType == null) {
                    message("Please select receipt sub method");
                    return;
                }

                if (selectedBankId == null) {
                    message("Please select bank");
                    return;
                }


                if (accountNo == null) {
                    message("Please select account number");
                    return;
                }

            }
        }
        if (paidAmountEt.getText().toString().isEmpty()) {
            paidAmountEt.setError("Paid amount is mandatory");
            paidAmountEt.requestFocus();
            return;
        }
        if (paymentTypeVal == null) {
            message("Please select your receipt method");
            receiptMethodTv.requestFocus();
            return;
        }

        if (receiptRemarksEt.getText().toString().isEmpty()) {
            receiptRemarksEt.setError("Remarks mandatory");
            receiptRemarksEt.requestFocus();
            return;
        }

        if (selectedEnterPrice == null) {
            message(getString(R.string.enterprise_info));
            return;
        }

        showDialog(getString(R.string.confirm_dialog_title));


    }


    @OnClick(R.id.date)
    public void getDate() {
        DateTimePicker.openDatePicker(this, getActivity());

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void save() {
        String storeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions();
        String profileTypeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getProfileTypeId();
        selectedOrderList = new ArrayList<>(CustomerOrderAdapter.selectedOrderList);

        payDueAmountViewModel.apiCallForPayDueAmount(getActivity(), vendorId, new HashSet<>(selectedOrderList), paidAmountEt.getText().toString(),
                totalDue.getText().toString(), selectedEnterPrice, userId, permissions, profileTypeId, paymentTypeVal,
                paymentSubType, accountNo, date.getText().toString(),
                receiptRemarksEt.getText().toString(), selectedCustomerId, receivableDue.getText().toString()).observe(getViewLifecycleOwner(), response -> {
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
        });

    }

    @Override
    public void imageUri(Intent uri) {

    }
}