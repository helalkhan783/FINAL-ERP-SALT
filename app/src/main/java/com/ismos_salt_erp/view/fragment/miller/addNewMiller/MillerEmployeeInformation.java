package com.ismos_salt_erp.view.fragment.miller.addNewMiller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ConfirmSaleClickhandle;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentMillerEmployeeInformationBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.sale.newSale.AddNewSale;
import com.ismos_salt_erp.view.fragment.sale.newSale.ConfirmNewSale;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MillerEmployeeInformation extends AddUpDel {
    public FragmentMillerEmployeeInformationBinding binding;
    private String selectedEnterpriseId, selectedStoreId, grandTotal, vat, carringCost;
    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private DiscountViewModel discountViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    private String NO_DATA_FOUND = "No Data Found";
    public static boolean isConfirmSubmitData = false;

    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;

    /**
     * For customer search
     */
    private List<CustomerResponse> customerResponseList;
    private List<String> customerNameList;


    public String selectedCustomer;

    private boolean isOkk = false;
    /**
     * Store previous fragment Data
     */

    double totalPrice = 0, discount = 0;

    Cursor cursor;


    private String paymentType = "1";
    String myKey = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_employee_information, container, false);
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());

        binding.toolbar.toolbarTitle.setText("Confirm Sale");
        getDataFromPreviousFragment();

        binding.toolbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                getActivity().onBackPressed();
            }
        });

//        binding.onlinePayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("selectedEnterpriseId", getArguments().getString("selectedEnterpriseId"));
//                bundle.putString("selectedStoreId", getArguments().getString("selectedStoreId"));
//                Navigation.findNavController(getView()).navigate(R.id.action_millerEmployeeInformation_to_onlinePaymentPagee, bundle);
//            }
//        });


        clickAdd();
        visibleImage();


        /**
         * handle customer suggested item click
         */
        binding.newTv.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());

            if (customerNameList.get(position).equals(NO_DATA_FOUND)) {
                binding.customerName.getText().clear();
            } else {
                selectedCustomer = customerResponseList.get(position).getCustomerID();

                try {
                    binding.customerName.setText(customerResponseList.get(position).getCompanyName() + " @ " + customerResponseList.get(position).getCustomerFname());
                    hideKeyboard(getActivity());
                } catch (Exception e) {
                    Log.d("ERROR", e.getLocalizedMessage());
                }
            }
        });

        /**
         * now search customer by customer name
         */



        binding.customerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.customerName.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getCustomerBySearchKey();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * for save sale
         */
        binding.setClickHandle(new ConfirmSaleClickhandle() {
            @Override
            public void save() {
                hideKeyboard(getActivity());
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
                    return;
                }

                if (selectedCustomer == null) {
                    binding.customerName.setError("Empty");
                    binding.customerName.requestFocus();
                    return;
                }

                showDialog(getString(R.string.confirm_sale_dialog_title));


            }

            @Override
            public void addNewCustomer() {
                hideKeyboard(getActivity());
                Navigation.findNavController(getView()).navigate(R.id.action_millerEmployeeInformation_to_addNewCustomer);
            }
        });


        getLocalData();

        binding.paidAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDueToDueTv();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return binding.getRoot();

    }



    private void clickAdd() {
        binding.cashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = "1";
                visibleImage();
            }
        });
        binding.bkashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = "2";
                visibleImage();
            }
        });
        binding.nogodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = "3";
                visibleImage();
            }
        });
        binding.rocketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = "4";
                visibleImage();
            }
        });
    }

    private void visibleImage() {
        if (paymentType.equals("1")) {
            binding.bkashYesImage.setVisibility(View.GONE);
            binding.cashYesImage.setVisibility(View.VISIBLE);
            binding.rocketYesImage.setVisibility(View.GONE);
            binding.nogodYesImage.setVisibility(View.GONE);
        }
        if (paymentType.equals("2")) {
            binding.bkashYesImage.setVisibility(View.VISIBLE);
            binding.cashYesImage.setVisibility(View.GONE);
            binding.rocketYesImage.setVisibility(View.GONE);
            binding.nogodYesImage.setVisibility(View.GONE);
        }
        if (paymentType.equals("3")) {
            binding.bkashYesImage.setVisibility(View.GONE);
            binding.cashYesImage.setVisibility(View.GONE);
            binding.rocketYesImage.setVisibility(View.GONE);
            binding.nogodYesImage.setVisibility(View.VISIBLE);
        }
        if (paymentType.equals("4")) {
            binding.bkashYesImage.setVisibility(View.GONE);
            binding.cashYesImage.setVisibility(View.GONE);
            binding.rocketYesImage.setVisibility(View.VISIBLE);
            binding.nogodYesImage.setVisibility(View.GONE);
        }
    }

    private void setDueToDueTv() {
        Double due = 0.0, paidAmount = 0.0, totalAmount = 0.0;
        String paidString = binding.paidAmount.getText().toString().trim();
        String totalString = binding.totalTv.getText().toString().trim();
        if (paidString.isEmpty()) {
            paidString = "0.0";
        }
        if (totalString.isEmpty()) {
            totalString = "0.0";
        }

        due = Double.parseDouble(totalString) - Double.parseDouble(paidString);
        binding.due.setText(DataModify.addFourDigit(String.valueOf(due)));

    }



    private void getLocalData() {

        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
        AddNewSale.list.clear();
        while (cursor.moveToNext()) {
            SalesRequisitionItems item = new SalesRequisitionItems();
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
            item.setPrice(cursor.getString(6));
            item.setDiscount(cursor.getString(7));
            item.setTotalPrice(cursor.getString(8));
            AddNewSale.list.add(item);

        }


        totalPrice = 0;
        for (int i = 0; i < AddNewSale.list.size(); i++) {
            totalPrice += Double.parseDouble(AddNewSale.list.get(i).getTotalPrice());
            discount += Double.parseDouble(AddNewSale.list.get(i).getDiscount());
        }
        totalPrice = Double.parseDouble(grandTotal);


        //   binding.totalTv.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
        binding.due.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
        //  binding.paidAmount.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
        binding.totalTv.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
        // binding.totalTv1.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
        //  binding.totalTv2.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));
    }


    private void submit(String status) {

        /**
         * for set updated productIdList,unitList
         */
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> perPriceList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<String> totalPriceList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        totalPriceList.clear();
        discountList.clear();
        perPriceList.clear();
        proDuctIdList.clear();
        unitList.clear();
        quantityList.clear();
        productTitleList.clear();
        for (int i = 0; i < AddNewSale.list.size(); i++) {
            try {
                proDuctIdList.add(AddNewSale.list.get(i).getProductID());
                unitList.add(AddNewSale.list.get(i).getUnit());
                productTitleList.add(AddNewSale.list.get(i).getProductTitle());
                perPriceList.add(AddNewSale.list.get(i).getPrice());
                discountList.add(AddNewSale.list.get(i).getDiscount());
                totalPriceList.add(AddNewSale.list.get(i).getTotalPrice());
                quantityList.add(AddNewSale.list.get(i).getQuantity());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }

        String due = binding.due.getText().toString();

        if (due == null || due.isEmpty()) {
            due = binding.totalTv.getText().toString();
        }

        String discount = ConfirmNewSale.binding.discountAndRemarksBox.discountEt.getText().toString();
        if (!discount.isEmpty() || discount != null) {
            if (ConfirmNewSale.discountPercent.equals("2")) {
                discount = discount + "%";
            }
        }


        String paidAmount = null;
        if (status.equals("saveUnPaid")) {
            paidAmount = binding.paidAmount.getText().toString();
            if (paidAmount.isEmpty() || paidAmount == null) {
                paidAmount = "0";
            }
        }
        if (!status.equals("saveUnPaid")) {
            paidAmount = binding.totalTv.getText().toString();
            if (paidAmount.isEmpty() || paidAmount == null) {
                paidAmount = "0";
            }
        }


        /**
         * for get last order Id
         **/
        String finalDue = due;

        String finalDiscount = discount;
        String finalPaidAmount = paidAmount;
        discountViewModel.getLastOrderId(getActivity()).observe(getViewLifecycleOwner(), lastOrderId ->
                saleViewModel.newSale(
                        getActivity(), selectedEnterpriseId, String.valueOf(lastOrderId), selectedCustomer, proDuctIdList,
                        Arrays.asList(selectedStoreId), quantityList, unitList, productTitleList,
                        "", "",
                        "", ""
                        , discountList, finalPaidAmount, finalDue, binding.totalTv.getText().toString()
                        , finalDiscount == null ? "0.0:" : finalDiscount, perPriceList, vat, carringCost).observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        // binding.discountAndRemarksBox.paidAmount.setText("");
                        return;
                    }
                    successMessage(getActivity().getApplication(), "" + response.getMessage());


                    getActivity().onBackPressed();

                    /**
                     * after successfully add new sale to server delete all data from local DB
                     */
                    AddNewSale.sharedPreferenceForStore.deleteData();
                    myDatabaseHelper.deleteAllData();//delete all data from local database
                    isConfirmSubmitData = true;

                }));
    }


    private void getCustomerBySearchKey() {

        /**
         * call
         */
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
        }

        String currentText = binding.customerName.getText().toString();
        if (currentText.isEmpty()) {
            currentText = "00000000000";
        }

        dueCollectionViewModel
                .apiCallForGetCustomers(
                        getActivity(),
                        getToken(getActivity().getApplication()),
                        getVendorId(getActivity().getApplication()),
                        currentText
                );
        /**
         * get data from above calling api
         */
        dueCollectionViewModel.getCustomerList()
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    isDataFetching = true;
                    //    selectedCustomer = null;
                    customerResponseList = new ArrayList<>();
                    customerResponseList.clear();
                    customerResponseList.addAll(response.getLists());

                    customerNameList = new ArrayList<>();
                    customerNameList.clear();


                    for (int i = 0; i < response.getLists().size(); i++) {
                        customerNameList.add("" + response.getLists().get(i).getCompanyName() + "@" + response.getLists().get(i).getCustomerFname());

                    }

                    if (customerNameList.isEmpty()) {
                        // customerNameList.add(NO_DATA_FOUND);
                    }

                    customerNameAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
                    binding.newTv.setAdapter(customerNameAdapter);

                    binding.newTv.showDropDown();
                    isDataFetching = false;
                });

    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        grandTotal = getArguments().getString("grandTotal");
        vat = getArguments().getString("vat");
        carringCost = getArguments().getString("carringCost");
    }


    @Override
    public void save() {
        submit("saveUnPaid");

    }

    @Override
    public void imageUri(Intent uri) {

    }
}