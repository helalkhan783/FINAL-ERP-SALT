package com.ismos_salt_erp.view.fragment.purchase_requisition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.databinding.FragmentConfrimPurchaseRequisitionBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.HandleDiscount;
import com.ismos_salt_erp.viewModel.AddRequisitionViewmodel;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class ConfrimPurchaseRequisitionFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    FragmentConfrimPurchaseRequisitionBinding binding;
    String customerId;
    private Boolean isStartDate = false;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private AddRequisitionViewmodel addRequisitionViewmodel;
    private DiscountViewModel discountViewModel;
    private DueCollectionViewModel dueCollectionViewModel;
    private ArrayAdapter<String> customerArrayAdapter;
    List<CustomerResponse> customerResponseList = new ArrayList<>();
    List<CustomerResponse> customerList;
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
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private String NO_DATA_FOUND = "No Data Found";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confrim_purchase_requisition, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Purchase Requisition");
        binding.toolbar.filterBtn.setVisibility(View.GONE);
        binding.toolbar.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        salesRequisitionViewModel = ViewModelProviders.of(this).get(SalesRequisitionViewModel.class);
        discountViewModel = ViewModelProviders.of(this).get(DiscountViewModel.class);
        dueCollectionViewModel = ViewModelProviders.of(this).get(DueCollectionViewModel.class);
        addRequisitionViewmodel = ViewModelProviders.of(this).get(AddRequisitionViewmodel.class);
        setCurrentDate();
        loadDataToRecyclerView();
        loadEnterPrice();

        binding.layout.startDate.setOnClickListener(v -> {
            isStartDate = true;
            showDatePickerDialog();
        });
        binding.layout.EndDate.setOnClickListener(v -> showDatePickerDialog());
        binding.layout.customerSearchEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (customerResponseList.get(position).equals(NO_DATA_FOUND)) {
                binding.layout.customerSearchEt.getText().clear();
                return;
            }
            customerId = customerResponseList.get(position).getCustomerID();
            Log.d("CUSTOMER_ID", customerId);
        });
        binding.layout.customerSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.layout.customerSearchEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (binding.layout.customerSearchEt.isPerformingCompletion()) {
                    return;
                }
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                        return;
                    }
                    String currentText = binding.layout.customerSearchEt.getText().toString();
                    customerId = null;//For handle proper selection
                    getCustomerDetailsFromServer(currentText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.layout.discountEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentTotal = totalPrice;// Double.parseDouble(getArguments().getString("total"));
                double discountNote = 0;
                if (binding.layout.discountEt.getText().toString().isEmpty()) {
                    discountNote = 0;
                    binding.layout.discountEt.requestFocus();
                    return;
                }
                discountNote = Double.parseDouble(binding.layout.discountEt.getText().toString());
                binding.layout.discountTv.setText("" + discountNote);


                double afterDiscount = currentTotal - discountNote;
                binding.layout.grandTotalLast.setText(String.valueOf(afterDiscount));
                binding.layout.grandTotal.setText(String.valueOf(afterDiscount));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.layout.receiptAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double paidAmount = Double.parseDouble(binding.layout.receiptAmountEt.getText().toString());
                    double totalDue = currentTotal - paidAmount;
                    binding.layout.due.setText("" + totalDue);
                    if (binding.layout.receiptAmountEt.getText().toString().isEmpty()) {
                        binding.layout.due.setText("" + totalPrice);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.layout.selectEnterPriceDdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.layout.selectEnterPriceDdown.setSelected(true);
                enterpriseId = Integer.parseInt(enterpriseResponsesList.get(position).getStoreID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.subMitTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        saveData();
            }
        });
        getLocalData();
        return binding.getRoot();
    }


    private void setCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        binding.layout.startDate.setText(formatter.format(date));
        binding.layout.EndDate.setText(formatter.format(date));
    }

    private void loadDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(PurchaseRequisitonFragment.updatedQuantityProductList);//get static data from (AddNewSale) Fragment
        /**
         * set set selected data to recyclerview
         */
        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);

    }

    private void loadEnterPrice() {
        salesRequisitionViewModel.getEnterpriseResponse(getActivity()).observe(getViewLifecycleOwner(), enterprise -> {
            enterpriseResponsesList.addAll(enterprise.getEnterprise());
            List<String> enterPriseName = new ArrayList<>();

            for (int i = 0; i < enterprise.getEnterprise().size(); i++) {
                enterPriseName.add(enterprise.getEnterprise().get(i).getStoreName());
            }
            binding.layout.selectEnterPriceDdown.setItem(enterPriseName);


        });
    }

    private void getLocalData() {
        for (int i = 0; i < PurchaseRequisitonFragment.updatedQuantityProductList.size(); i++) {
            totalPrice += Integer.parseInt(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getTotalPrice());
            discount += Integer.parseInt(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getDiscount());
        }
        int finalTotalPrice = totalPrice + discount;
        binding.layout.totalAmount.setText("" + totalPrice);
        binding.layout.grandTotal.setText("" + totalPrice);
        binding.layout.grandTotalLast.setText("" + totalPrice);
        binding.layout.due.setText("" + totalPrice);
    }

    public void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
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
            for (int i = 0; i < customerList.size(); i++) {
                customerNameList.add("" + customerList.get(i).getCompanyName());
            }


            if (customerNameList.isEmpty()) { // show message in the item if the list is empty
                customerNameList.add(NO_DATA_FOUND);
            }
            customerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
            binding.layout.customerSearchEt.setAdapter(customerArrayAdapter);
            binding.layout.customerSearchEt.showDropDown();
            isDataFetching = false;
        });
    }


    private void saveData() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do You Want to Purchase Req it ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
           // Toast.makeText(getContext(), "At first give me API, o kaku.....", Toast.LENGTH_SHORT).show();
         //validationAndSave();
        });

        alertDialog.show();




    }

    private void validationAndSave() {
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
        for (int i = 0; i < PurchaseRequisitonFragment.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getProductID());
                unitList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getProductTitle());
                perPriceList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getPrice());
                discountList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getDiscount());
                totalPriceList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getTotalPrice());
                qtys.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getQuantity());
                productIdList.add(PurchaseRequisitonFragment.updatedQuantityProductList.get(i).getProductID());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }

        if (!(isInternetOn(getActivity()))) {
            hideKeyboard(getActivity());
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }

        String receiptAmount = binding.layout.receiptAmountEt.getText().toString();

        if (receiptAmount.isEmpty()) {
            receiptAmount = "0";//for default value
        }

        String total_discount = binding.layout.discountEt.getText().toString();
        if (total_discount.isEmpty()) {
            total_discount = "0";//for default value
        }

        if (customerId == null) {
            binding.layout. customerSearchEt.setError("Search Customer by Name");
            binding.layout.customerSearchEt.requestFocus();
            return;
        }
        String finalReceiptAmount = receiptAmount;
        String finalTotal_discount = total_discount;
        String finalPaymentTypeSelected = "1";


        discountViewModel.getLastOrderId(getActivity()).observe(getViewLifecycleOwner(), lastOrderId -> {
            addRequisitionViewmodel.apiCallForCreateRequisition(
                    getActivity(), productIdList, String.valueOf(enterpriseId), unitList, String.valueOf(lastOrderId), customerId, productTitleList,
                    /*sellingPriceList*/perPriceList, qtys, finalTotal_discount, finalReceiptAmount, finalPaymentTypeSelected, binding.layout.startDate.getText().toString(), binding.layout.EndDate.getText().toString()
            );
            addRequisitionViewmodel.isAddRequisitionSuccessful().observe(getViewLifecycleOwner(), duePaymentResponse -> {
                if (duePaymentResponse.getStatus() == 200) {
                    Toasty.success(getContext(), "" + duePaymentResponse.getMessage(), Toasty.LENGTH_LONG).show();
                }
            });
            getActivity().onBackPressed();
        });

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
        if (isStartDate) {
            binding.layout.startDate.setText(selectedDate);
            isStartDate = false;
            return;
        }

        binding.layout.EndDate.setText(selectedDate);
    }


}
