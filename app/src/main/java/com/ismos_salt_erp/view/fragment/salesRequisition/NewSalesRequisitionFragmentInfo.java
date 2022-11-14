package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.HandleDiscount;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.viewModel.AddRequisitionViewmodel;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSalesRequisitionFragmentInfo extends AddUpDel implements DatePickerDialog.OnDateSetListener {
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private AddRequisitionViewmodel addRequisitionViewmodel;
    private DiscountViewModel discountViewModel;
    private DueCollectionViewModel dueCollectionViewModel;
    private ArrayAdapter<String> customerArrayAdapter;
    List<CustomerResponse> customerResponseList = new ArrayList<>();
    List<CustomerResponse> customerList;
    View view;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private String NO_DATA_FOUND = "No Data Found";
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.selectedProductsRv)
    RecyclerView selectedProductsRv;
    @BindView(R.id.discountEt)
    EditText discountEt;
    @BindView(R.id.selectEnterPriceDdown)
    SmartMaterialSpinner selectEnterPriceDdown;
    @BindView(R.id.totalAmount)
    TextView totalAmount;
    @BindView(R.id.grandTotal)
    TextView grandTotal;
    @BindView(R.id.discountTv)
    TextView discountNoteTV;
    @BindView(R.id.grandTotalLast)
    TextView grandTotalLast;
    @BindView(R.id.due)
    TextView due;
    /*    @BindView(R.id.paymentTypeDown)
        MaterialSpinner paymentTypeDown;*/
    @BindView(R.id.customerSearchEt)
    AutoCompleteTextView customerSearchEt;
    @BindView(R.id.receiptAmountEt)

    EditText receiptAmountEt;
    @BindView(R.id.startDate)
    TextView startDate;


    @BindView(R.id.EndDate)
    TextView endDate;
    String customerId;
    private Boolean isStartDate = false;

    public static List<String> selectedPriceList;
    public static List<String> quantityList;
    public List<String> productIdList = new ArrayList<>();
    List<String> productTitleList = new ArrayList<>();//this is title list
    private List<SalesRequisitionItemsResponse> salesRequisitionItemsResponsesList;
    private List<SalesRequisitionItems> salesRequisitionItemsList;
    List<EnterpriseResponse> enterpriseResponsesList = new ArrayList<>();
    int enterpriseId;
    int paymentType;
    private String selectedEnterpriseId, selectedStoreId;
    int totalPrice = 0, discount = 0;
    HandleDiscount handleDiscount;
    double currentTotal;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_sales_requisition2, container, false);
        ButterKnife.bind(this, view);
        salesRequisitionViewModel = ViewModelProviders.of(this).get(SalesRequisitionViewModel.class);
        discountViewModel = ViewModelProviders.of(this).get(DiscountViewModel.class);
        dueCollectionViewModel = ViewModelProviders.of(this).get(DueCollectionViewModel.class);
        addRequisitionViewmodel = ViewModelProviders.of(this).get(AddRequisitionViewmodel.class);
        setCurrentDate();
        loadDataToRecyclerView();
        loadEnterPrice();
        toolbarTitle.setText("Confirm Sale Requisition");
        customerSearchEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (customerResponseList.get(position).equals(NO_DATA_FOUND)) {
                customerSearchEt.getText().clear();
                return;
            }
            customerId = customerResponseList.get(position).getCustomerID();
            Log.d("CUSTOMER_ID", customerId);
        });

        customerSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!customerSearchEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (customerSearchEt.isPerformingCompletion()) {
                    return;
                }
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                        return;
                    }
                    String currentText = customerSearchEt.getText().toString();
                    customerId = null;//For handle proper selection
                    getCustomerDetailsFromServer(currentText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        discountEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentTotal = totalPrice;// Double.parseDouble(getArguments().getString("total"));
                double discountNote = 0;
                if (discountEt.getText().toString().isEmpty()) {
                    discountNote = 0;
                    discountEt.requestFocus();
                    return;
                }
                discountNote = Double.parseDouble(discountEt.getText().toString());
                discountNoteTV.setText("" + discountNote);


                double afterDiscount = currentTotal - discountNote;
                grandTotalLast.setText(String.valueOf(afterDiscount));
                grandTotal.setText(String.valueOf(afterDiscount));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        receiptAmountEt.setText("0.0000");
        receiptAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double paidAmount = Double.parseDouble(receiptAmountEt.getText().toString());
                    double totalDue = totalPrice - paidAmount;
                    due.setText("" + totalDue);
                    if (totalPrice < paidAmount) {
                        receiptAmountEt.setError("Unavailable amount");
                        receiptAmountEt.requestFocus();
                        return;
                    }
                    if (receiptAmountEt.getText().toString().isEmpty()) {

                        due.setText("" + totalPrice);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        selectEnterPriceDdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectEnterPriceDdown.setSelected(true);
                enterpriseId = Integer.parseInt(enterpriseResponsesList.get(position).getStoreID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getLocalData();

        return view;
    }


    private void getCustomerDetailsFromServer(String currentText) {
        String token = PreferenceManager.getInstance(getActivity()).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(getActivity()).getUserCredentials().getVendorID();
        dueCollectionViewModel.apiCallForGetCustomers(getActivity(), token, vendorId, currentText);
        dueCollectionViewModel.getCustomerList().observe(getViewLifecycleOwner(), customerSearchResponse -> {

            if (customerSearchResponse == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (customerSearchResponse.getStatus() != 200) {
                infoMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            isDataFetching = true;
            customerResponseList.clear();
            customerResponseList.addAll(customerSearchResponse.getLists());
            List<String> customerNameList = new ArrayList<>();
            customerList = new ArrayList<>();
            customerList.clear();
            customerList = customerSearchResponse.getLists();


            // customerList.forEach(customerResponse -> customerNameList.add(customerResponse.getCompanyName()));//for api level 28 or above
            for (int i = 0; i < customerList.size(); i++) {
                customerNameList.add("" + customerList.get(i).getCompanyName());
            }


            if (customerNameList.isEmpty()) { // show message in the item if the list is empty
                customerNameList.add(NO_DATA_FOUND);
            }
            customerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
            customerSearchEt.setAdapter(customerArrayAdapter);
            customerSearchEt.showDropDown();
            isDataFetching = false;
        });
    }

    private void loadEnterPrice() {
        salesRequisitionViewModel.getEnterpriseResponse(getActivity()).observe(getViewLifecycleOwner(), enterprise -> {
            enterpriseResponsesList.addAll(enterprise.getEnterprise());
            List<String> enterPriseName = new ArrayList<>();

            for (int i = 0; i < enterprise.getEnterprise().size(); i++) {
                enterPriseName.add(enterprise.getEnterprise().get(i).getStoreName());
            }
            selectEnterPriceDdown.setItem(enterPriseName);


        });
    }

    private void getLocalData() {
        for (int i = 0; i < NewSaleFragment.updatedQuantityProductList.size(); i++) {
            totalPrice += Integer.parseInt(NewSaleFragment.updatedQuantityProductList.get(i).getTotalPrice());
            discount += Integer.parseInt(NewSaleFragment.updatedQuantityProductList.get(i).getDiscount());
        }
        int finalTotalPrice = totalPrice + discount;
        totalAmount.setText("" + totalPrice);
        grandTotal.setText("" + totalPrice);
        grandTotalLast.setText("" + totalPrice);
        due.setText("" + totalPrice);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(NewSaleFragment.updatedQuantityProductList);//get static data from (AddNewSale) Fragment
        /**
         * set set selected data to recyclerview
         */
        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        selectedProductsRv.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void setCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        startDate.setText(formatter.format(date));
        endDate.setText(formatter.format(date));
    }

    @OnClick(R.id.addMoreBtn)
    public void addMoreBtn() {
        Navigation.findNavController(getView()).navigate(R.id.action_newSalesRequisitionFragment2_to_newSaleFragment);
    }

    @OnClick(R.id.backbtn)
    public void onClickBackBtn() {
        getActivity().onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.card_submit_btn)
    public void submitInfo() {
        if (customerId == null) {
            customerSearchEt.setError("Search Customer by Name");
            customerSearchEt.requestFocus();
            return;
        }

       showDialog(getString(R.string.add_dialog_title));

    }

    /**
     * for set start order date
     */
    @OnClick(R.id.startDate)
    public void getStartOrderDate() {
        isStartDate = true;
        showDatePickerDialog();
    }

    /**
     * for set end order date
     */
    @OnClick(R.id.EndDate)
    public void getEndOrderDate() {
        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (isStartDate) {
            startDate.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
            isStartDate = false;
            return;
        }

        endDate.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
    }

    @Override
    public void save() {
        submit();
    }

    private void submit() {
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> perPriceList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<String> totalPriceList = new ArrayList<>();
        List<String> qtys = new ArrayList<>();

        totalPriceList.clear();
        discountList.clear();
        perPriceList.clear();
        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        for (int i = 0; i < NewSaleFragment.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(NewSaleFragment.updatedQuantityProductList.get(i).getProductID());
                unitList.add(NewSaleFragment.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(NewSaleFragment.updatedQuantityProductList.get(i).getProductTitle());
                perPriceList.add(NewSaleFragment.updatedQuantityProductList.get(i).getPrice());
                discountList.add(NewSaleFragment.updatedQuantityProductList.get(i).getDiscount());
                totalPriceList.add(NewSaleFragment.updatedQuantityProductList.get(i).getTotalPrice());
                qtys.add(NewSaleFragment.updatedQuantityProductList.get(i).getQuantity());
                productIdList.add(NewSaleFragment.updatedQuantityProductList.get(i).getProductID());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }



        String receiptAmount = receiptAmountEt.getText().toString();

        if (receiptAmount.isEmpty()) {
            receiptAmount = "0";//for default value
        }

        String total_discount = discountEt.getText().toString();
        if (total_discount.isEmpty()) {
            total_discount = "0";//for default value
        }
        String finalReceiptAmount = receiptAmount;
        String finalTotal_discount = total_discount;
        String finalPaymentTypeSelected = "1";


        discountViewModel.getLastOrderId(getActivity()).observe(getViewLifecycleOwner(), lastOrderId -> {
            addRequisitionViewmodel.apiCallForCreateRequisition(
                    getActivity(), productIdList, String.valueOf(enterpriseId), unitList, String.valueOf(lastOrderId), customerId, productTitleList,
                    /*sellingPriceList*/perPriceList, qtys, finalTotal_discount, finalReceiptAmount, finalPaymentTypeSelected, startDate.getText().toString(), endDate.getText().toString()
            ).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
                @Override
                public void onChanged(DuePaymentResponse response) {
                    if (response == null) {
                        return;
                    }
                    if (response.getStatus() == 400) {

                        return;
                    }

                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                }
            });


        });
    }

    @Override
    public void imageUri(Intent uri) {

    }
}