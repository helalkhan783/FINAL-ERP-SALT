package com.ismos_salt_erp.view.fragment.accounts.payDue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CustomerOrderAdapter;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AccountResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.MainBank;
import com.ismos_salt_erp.serverResponseModel.Order;
import com.ismos_salt_erp.serverResponseModel.PaymentToResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.DuePaymentReceivedViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.ismos_salt_erp.viewModel.SupplierDueViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayDueToSupplierSearchFragment extends AddUpDel implements DatePickerDialog.OnDateSetListener {
    List<CustomerResponse> customerResponseList;
    List<CustomerResponse> customerList;
    private ArrayAdapter<String> customerArrayAdapter;
    private SupplierDueViewModel supplierDueViewModel;
    private String No_DATA_FOUND = "No Data Found";
    View view;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    @BindView(R.id.customerNameEtSupplier)
    AutoCompleteTextView customerNameEtSupplier;
    @BindView(R.id.tv_total_amountSupplier)
    TextView totalDue;
    @BindView(R.id.card_submit_btn)
    CardView submitBtn;
    @BindView(R.id.expandable_layoutSupplier)
    ExpandableLayout expandableLayoutSupplier;
    @BindView(R.id.image_viewSupplier)
    ImageView image_viewSupplier;
    @BindView(R.id.customerNameTvSupplier)
    TextView customerNameTv;
    @BindView(R.id.ordersRvSupplier)
    RecyclerView ordersRv;
    @BindView(R.id.tv_noticeSupplier)
    TextView noOrderFound;
    List<String> customerNameList;

    @BindView(R.id.totalDueSupplier)
    TextView totalDueTv;
    double paymentLimit;


    String selectedCustomerId;
    private String customerPhone;
    private String customerAddress;
    private SalesRequisitionViewModel salesRequisitionViewModel;


    //_------------------
    private DuePaymentReceivedViewModel duePaymentReceivedViewModel;


    @BindView(R.id.toolbarTitle)
    TextView toolbar;

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
    @BindView(R.id.addPortion)
    LinearLayout addPortion;
    @BindView(R.id.savePortion)
    LinearLayout savePortion;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.enterPrice)
    SmartMaterialSpinner enterPrice;
    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
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
    List<String> accountBankId = new ArrayList<>();

    List<AccountResponse> accountResponseList = new ArrayList<>();
    String selectedReceiptMethod;
    String selectedBankId = "0";
    String selectPaymentSubType;
    String selectPaymentOptionSupplier;
    private String accountId;

    List<String> selectedOrderList;
    String selectedSupplierId;

    List<String> customerOrderIdList;
    List<Order> mainDueOrderList;

    private String selectedEnterPrice;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pay_due_to_supplier_search_fragment, container, false);
        ButterKnife.bind(this, view);
        supplierDueViewModel = ViewModelProviders.of(this).get(SupplierDueViewModel.class);
        duePaymentReceivedViewModel = new ViewModelProvider(this).get(DuePaymentReceivedViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);

        getDataFromPreviousFragment();

        back.setOnClickListener(v -> {
            savePortion.setVisibility(View.GONE);
            addPortion.setVisibility(View.VISIBLE);
        });

        setDataInDateTv();


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


        /**
         * for search customer (Supplier)
         */
        customerNameEtSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!customerNameEtSupplier.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (customerNameEtSupplier.isPerformingCompletion()) { // selected a product
                    return;
                }
                // if item is not selected
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    collapseLayout(); // collapse bottom layout
                    String currentText = customerNameEtSupplier.getText().toString();
                    String currentUserToken = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
                    String currentVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    /**
                     * method for search a customer
                     */
                    getSuppliersFromServer(currentUserToken, currentVendorId, currentText);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * for get supplier orders by selected customer id
         */
        customerNameEtSupplier.setOnItemClickListener((parent, view, position, id) -> {

            hideKeyboard(getActivity());
            if (customerNameList.get(position).equals(No_DATA_FOUND)) {
                customerNameEtSupplier.getText().clear();
                return;
            }
            customerNameEtSupplier.getText().clear();

            if (!customerResponseList.isEmpty()) {//if have any customer in the current list
                customerNameEtSupplier.clearFocus();
                hideKeyboard(getActivity());

                selectedSupplierId = customerResponseList.get(position).getCustomerID();
                String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                /**
                 * for show supplier order in recyclerView
                 */
                getDueOrdersAndShowInRecyclerView(selectedSupplierId, selectedUserVendorId); // get due orders from server

            } else {
                customerNameEtSupplier.getText().clear();
            }
        });
////////////////////// ----------------SAve page-----------------------////////////////

        receiptMethodTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1) {//here position 0 means this is cash so...
                    withoutCash.setVisibility(View.GONE);
                } else {
                    withoutCash.setVisibility(View.VISIBLE);
                }
                selectedReceiptMethod = receiptMethodList.get(position).getId();//set selected ReceiptMethod for send to server


                if (position == 3) {
                    if (position == 3) {
                        receiptSubMethodList.clear();
                        receiptSubMethodList.add("Online Deposit");
                        receiptSubMethod.setItem(receiptSubMethodList);
                        if (receiptSubMethodList.size() == 1) {
                            receiptSubMethod.setSelection(0);
                        }
                        selectPaymentSubType = "4";

                    }
                }
                if (position == 2) {
                    String selectedUserVendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
                    ok(selectedCustomerId, selectedUserVendorId, "1"); // get due orders from server

                }

                receiptMethodTv.setSelected(true);
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
                accountList(selectedBankId, selectedEnterPrice);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectAccountNoTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                accountId = accountBankId.get(position);
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
        getPageDataFromServer();
        return view;
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
    private void setDataInDateTv() {
        date.setText("" + DataModify.currentDate());

    }

    private void ok(String selectedCustomerId, String selectedUserVendorId, String s) {

        // dueCollectionViewModel.apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(getActivity(), selectedCustomerId, selectedUserVendorId);
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        supplierDueViewModel.getSupplierOrders().observe(getViewLifecycleOwner(), supplierOrdersResponse -> {
            if (supplierOrdersResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (supplierOrdersResponse.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "Backend Error");
                return;
            }
            /**
             * set  receiptSubMethod
             */
            receiptSubMethodList.clear();
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_1());
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_2());
            receiptSubMethodList.add(supplierOrdersResponse.getPaymentSubTypes().get_3());
            receiptSubMethodList.add("Outward Transfer");


            receiptSubMethod.setItem(receiptSubMethodList);

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
        selectAccountNoTv.setItem(optionList);


    }

    /*
         for get orders and show in recyclerview
     */
    private void getDueOrdersAndShowInRecyclerView(String selectedSupplierId, String selectedUserVendorId) {

        mainDueOrderList = new ArrayList<>();

        supplierDueViewModel.apiCallForGetSupplierOrder(getActivity(), selectedSupplierId);

        supplierDueViewModel.getSupplierOrders().observe(getViewLifecycleOwner(), supplierOrdersResponse -> {
            if (supplierOrdersResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (supplierOrdersResponse.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "Backend Error");
                return;
            }
            selectedCustomerId = selectedSupplierId;

            paymentLimit = supplierOrdersResponse.getPaymentLimit().getPayLimitAmount();//store payment limit.

            ///Log.d("DDD",String.valueOf(supplierOrdersResponse.getPaymentLimit()));

            customerNameTv.setText(supplierOrdersResponse.getCustomer().getCompanyName() + "@" + supplierOrdersResponse.getCustomer().getCustomerFname());//set customer name
            customerPhone = supplierOrdersResponse.getCustomer().getPhone();
            customerAddress = supplierOrdersResponse.getCustomer().getAddress();
            ordersRv.setLayoutManager(new LinearLayoutManager(getContext()));


            nameTv.setText(supplierOrdersResponse.getCustomer().getCompanyName() + "@" + supplierOrdersResponse.getCustomer().getCustomerFname());
            phoneTv.setText(supplierOrdersResponse.getCustomer().getPhone());
            addressTv.setText(supplierOrdersResponse.getCustomer().getAddress());


            /**
             * for show selected customer list in Recycler view
             */
            List<Order> orderList = supplierOrdersResponse.getOrders();
            /**
             * if with order don't have any due ignore this order for this purpose so write the condition
             */
            mainDueOrderList.clear();
            mainDueOrderList.addAll(orderList);


            double total = 0;
            for (int i = 0; i < mainDueOrderList.size(); i++) {
                total += Double.parseDouble(mainDueOrderList.get(i).getDue());
            }

            totalDue.setText(DataModify.addFourDigit(String.valueOf(total)));//set total due

            if (!mainDueOrderList.isEmpty()) {
                ordersRv.setVisibility(View.VISIBLE);
                noOrderFound.setVisibility(View.GONE);
                CustomerOrderAdapter adapter = new CustomerOrderAdapter(getActivity(), mainDueOrderList, "payment");
                ordersRv.setAdapter(adapter);

//-----------------
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
                if (String.valueOf(supplierOrdersResponse.getCustomer().getDueLimit()) != null) {
                    dueLimitTv.setText(DataModify.addFourDigit(String.valueOf(supplierOrdersResponse.getCustomer().getDueLimit())));
                }
                /**
                 * set  receiptMethod
                 */
                receiptMethodList.addAll(supplierOrdersResponse.getPaymentTypes());

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

                for (int i = 0; i < banks.size(); i++) {
                    bankList.add(banks.get(i).getMainBankName());
                    bankIdList.add(banks.get(i).getMainBankID());
                }
                List<String> nam = new ArrayList<>();
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
                return;
            } else {
                ordersRv.setVisibility(View.GONE);
                noOrderFound.setText("No order Found");
                noOrderFound.setVisibility(View.VISIBLE);
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getSuppliersFromServer(String currentUserToken, String currentVendorId, String currentText) {

        supplierDueViewModel.apiCallForSearchSuppliers(currentUserToken, currentVendorId, currentText);


        supplierDueViewModel.getAllSuppliers().observe(getViewLifecycleOwner(), customerSearchResponse -> {
            isDataFetching = true;
            customerResponseList = new ArrayList<>();
            customerResponseList.clear();
            customerResponseList.addAll(customerSearchResponse.getLists());
            customerNameList = new ArrayList<>();
            customerNameList.clear();
            customerList = customerSearchResponse.getLists();

            for (int i = 0; i < customerList.size(); i++) {
                customerNameList.add("" + customerList.get(i).getCompanyName() + " @ " + customerList.get(i).getCustomerFname());
            }

            if (customerNameList.isEmpty()) { // show message in the item if the list is empty
                customerNameList.add(No_DATA_FOUND);
            }
            customerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
            customerNameEtSupplier.setAdapter(customerArrayAdapter);
            customerNameEtSupplier.showDropDown();
            isDataFetching = false;
        });
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText("Payment");
    }


    @OnClick(R.id.card_submit_btn)
    public void submit() {
        selectedOrderList = new ArrayList<>(CustomerOrderAdapter.selectedOrderList);
        if (selectedOrderList.isEmpty()) {
            hideKeyboard(getActivity());
            infoMessage(getActivity().getApplication(), "Please Select Order");
            return;
        }
        double currentSelectedTotal = 0, totalDue = 0;
        for (int i = 0; i < mainDueOrderList.size(); i++) {
            totalDue += Double.parseDouble(mainDueOrderList.get(i).getDue());
            for (int i1 = 0; i1 < CustomerOrderAdapter.selectedOrderList.size(); i1++) {
                if (mainDueOrderList.get(i).getOrderID().equals(selectedOrderList.get(i1))) {
                    currentSelectedTotal += Double.parseDouble(mainDueOrderList.get(i).getDue());
                }
            }
        }
        receivableDue.setText("" + DataModify.addFourDigit(String.valueOf(currentSelectedTotal)));
        totalDueTv.setText("" + DataModify.addFourDigit(String.valueOf(totalDue)));
        savePortion.setVisibility(View.VISIBLE);
        addPortion.setVisibility(View.GONE);


      /*  double currentTotalDue = Double.parseDouble(totalDue.getText().toString());
        CustomerOrderAdapter.selectedOrderList.clear();
        if (selectedCustomerId != null && currentTotalDue > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("customerId", selectedCustomerId);
            bundle.putString("totalDue", totalDue.getText().toString());
            bundle.putString("customerName", customerNameTv.getText().toString());
            bundle.putString("customerPhone", customerPhone);
            bundle.putString("customerAddress", customerAddress);
            bundle.putString("paymentLimit", String.valueOf(paymentLimit));
            bundle.putStringArrayList("selectedOrderIds", new ArrayList<>(selectedOrderList));
            Navigation.findNavController(getView()).navigate(R.id.action_payDueToSupplier_to_supplierDuePaymentReceiveFragment, bundle);
        } else {
            if (currentTotalDue == 0.0) {//just for show a specific message to user
                Toasty.info(getContext(), "You don't have any due", Toasty.LENGTH_LONG).show();
                return;
            }
            Toasty.info(getContext(), "Please select a customer by search", Toasty.LENGTH_LONG).show();
        }
*/
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.image_viewSupplier)
    public void setArrowBtnClick() {
        expandAndCollapseLayout();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void expandAndCollapseLayout() {
        if (expandableLayoutSupplier.isExpanded()) {
            expandableLayoutSupplier.collapse();
            image_viewSupplier.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
        } else {
            expandableLayoutSupplier.expand();
            image_viewSupplier.setImageDrawable(getContext().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.saveBtnSupplier)
    public void saveBtnClick() {
        String customDiscount = "0";

        if (paidAmountEt.getText().toString().isEmpty()) {
            paidAmountEt.setError("Paid amount mandatory");
            paidAmountEt.requestFocus();
            return;
        }

        if (selectedEnterPrice == null) {
            message(getString(R.string.enterprise_info));
            return;
        }
        if (Integer.parseInt(selectedReceiptMethod) != 1) {//here 1 means cash without cash branch and account no is mandatory
            if (Integer.parseInt(selectedReceiptMethod) != 2) {//here 2 means cheque without cash branch and account no is mandatory

                if (selectedBankId == null) {
                    message("Please select bank");
                    return;
                }
                if (accountId == null) {
                    message("Please select account number");
                    return;
                }
                if (selectPaymentOptionSupplier == null) {
                    message("Please select payment to (Bank)");
                    return;
                }

                if (brandsEt.getText().toString().isEmpty()) {
                    brandsEt.setError("Branch Mandatory");
                    brandsEt.requestFocus();
                    return;
                }
                if (accountNoEt.getText().toString().isEmpty()) {
                    accountNoEt.setError("Account No is Mandatory");
                    accountNoEt.requestFocus();
                    return;
                }
            }

        }
        if (receiptRemarksEt.getText().toString().isEmpty()) {
            receiptRemarksEt.setError("Receipt Remarks Mandatory");
            receiptRemarksEt.requestFocus();
            return;
        }


        if (paymentLimit != 0) {//if payment limit have 0 then user can set any amount
            if (Double.parseDouble(paidAmountEt.getText().toString()) < paymentLimit || Double.parseDouble(paidAmountEt.getText().toString()) > paymentLimit) {
                paidAmountEt.setError("Paid Amount Should be " + paymentLimit);
                return;
            }
        }

        showDialog(getString(R.string.confirm_dialog_title));
    }


    private void submitData() {
        String token = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
        String storeId = selectedEnterPrice;
        String userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions();
        String profileTypeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getProfileTypeId();
        String paymentTypeVal = selectedReceiptMethod;
        String selectedPaymentToOption = selectPaymentOptionSupplier;
        String paymentSubType = selectPaymentSubType;
        String totalDuee = totalDue.getText().toString();
        selectedOrderList = new ArrayList<>(CustomerOrderAdapter.selectedOrderList);
        supplierDueViewModel.apiCallForPaySupplierDue(getActivity(),
                token, new HashSet<>(selectedOrderList), paidAmountEt.getText().toString(), totalDuee, storeId, userId, permissions, profileTypeId, selectedSupplierId, vendorId,
                paymentTypeVal, paymentSubType, "0", selectedBankId, brandsEt.getText().toString(), accountNoEt.getText().toString(),
                receiptRemarksEt.getText().toString(),
                date.getText().toString(),
                selectedPaymentToOption, accountId, receivableDue.getText().toString()
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
        });

    }


    @OnClick(R.id.dateSupplier)
    public void getDate() {
        DateTimePicker.openDatePicker(this, getActivity());
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
    }

    private void collapseLayout() {
        if (expandableLayoutSupplier.isExpanded()) {
            expandableLayoutSupplier.collapse();
            image_viewSupplier.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
        }
    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            submitData();
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}