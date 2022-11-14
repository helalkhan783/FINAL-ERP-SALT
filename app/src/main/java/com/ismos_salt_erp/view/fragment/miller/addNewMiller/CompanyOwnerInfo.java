package com.ismos_salt_erp.view.fragment.miller.addNewMiller;

import static io.reactivex.internal.util.NotificationLite.isError;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentCompanyOwnerInfoBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.AddNewPurchase;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.ConfirmPurchase;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.PurchaseViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CompanyOwnerInfo extends AddUpDel implements View.OnClickListener {
    private FragmentCompanyOwnerInfoBinding binding;

    private String selectedEnterpriseId, selectedStoreId, grandTotal, vat, carringCost;
    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private DiscountViewModel discountViewModel;
    private PurchaseViewModel purchaseViewModel;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_owner_info, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Purchase");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        getDataFromPreviousFragment();
        getLocalData();

        binding.toolbar.setClickHandle((ToolbarClickHandle) () -> getActivity().onBackPressed());
        clickAdd();
        visibleImage();

        binding.customerName.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());

            if (customerNameList.get(position).equals(NO_DATA_FOUND)) {
                binding.customerName.getText().clear();
            } else {
                selectedCustomer = customerResponseList.get(position).getCustomerID();


            }
        });
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
        binding.cashCard.setOnClickListener(this);
        binding.bkashCard.setOnClickListener(this);
        binding.nogodCard.setOnClickListener(this);
        binding.rocketCard.setOnClickListener(this);
        binding.btn.setOnClickListener(this);
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

    private void getLocalData() {

        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
        AddNewPurchase.list.clear();
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
            AddNewPurchase.list.add(item);

        }


        totalPrice = 0;
        for (int i = 0; i < AddNewPurchase.list.size(); i++) {
            totalPrice += Double.parseDouble(AddNewPurchase.list.get(i).getTotalPrice());
            discount += Double.parseDouble(AddNewPurchase.list.get(i).getDiscount());
        }
        totalPrice = Double.parseDouble(grandTotal);


        binding.due.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));

        binding.totalTv.setText(DataModify.addFourDigit(String.valueOf(totalPrice)));

    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        grandTotal = getArguments().getString("grandTotal");
        vat = getArguments().getString("vat");
        carringCost = getArguments().getString("carringCost");
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

    private void getCustomerBySearchKey() {

        String currentText = binding.customerName.getText().toString();
        if (currentText.isEmpty()) {
            currentText = "00000000000";
        }
        dueCollectionViewModel
                .apiCallForGetSupplier(
                        getActivity(),
                        getToken(getActivity().getApplication()),
                        getVendorId(getActivity().getApplication()),
                        currentText
                );

        dueCollectionViewModel.getCustomerList()
                .observe(getViewLifecycleOwner(), response -> {
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() != 200) {
                            infoMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }

                        isDataFetching = true;

                        customerResponseList = new ArrayList<>();
                        customerResponseList.clear();
                        customerResponseList.addAll(response.getLists());

                        customerNameList = new ArrayList<>();
                        customerNameList.clear();


                        for (int i = 0; i < response.getLists().size(); i++) {
                            customerNameList.add("" + response.getLists().get(i).getCustomerFname() + "@" + response.getLists().get(i).getCompanyName());
                        }


                        customerNameAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
                        binding.customerName.setAdapter(customerNameAdapter);
                        binding.customerName.showDropDown();
                        isDataFetching = false;
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                });

    }


    private void submit(String status) {


        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> perPriceList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<String> totalPriceList = new ArrayList<>();

        //for price an discount
        totalPriceList.clear();
        discountList.clear();
        perPriceList.clear();
        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();

        for (int i = 0; i < AddNewPurchase.list.size(); i++) {
            proDuctIdList.add(AddNewPurchase.list.get(i).getProductID());
            unitList.add(AddNewPurchase.list.get(i).getUnit());
            productTitleList.add(AddNewPurchase.list.get(i).getProductTitle());
            quantityList.add(AddNewPurchase.list.get(i).getQuantity());
            perPriceList.add(AddNewPurchase.list.get(i).getPrice());
            discountList.add(AddNewPurchase.list.get(i).getDiscount());
            totalPriceList.add(AddNewPurchase.list.get(i).getTotalPrice());
        }

        String due = binding.due.getText().toString();

        if (due == null || due.isEmpty()) {
            due = binding.totalTv.getText().toString();
        }

        String discount = ConfirmPurchase.binding.discountAndRemarksBox.discountEt.getText().toString();
        if (!discount.isEmpty() || discount != null) {
            if (ConfirmPurchase.discountPercent.equals("2")) {
                discount = discount + "%";
            }
        }



        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        String finalDiscount = discount;

        discountViewModel.getLastOrderId(getActivity()).observe(getViewLifecycleOwner(), lastOrderId ->
                purchaseViewModel.newPurchase(
                                getActivity(), selectedEnterpriseId, String.valueOf(lastOrderId), selectedCustomer, proDuctIdList,
                                Arrays.asList(selectedStoreId), quantityList, unitList, productTitleList,
                                perPriceList, discountList, totalPriceList,
                                binding.paidAmount.getText().toString().isEmpty() ? "0": binding.paidAmount.getText().toString(),
                                binding.due.getText().toString(),
                                finalDiscount,
                                "", "1",
                                "", "", "", "")
                        .observe(getViewLifecycleOwner(), response -> {
                            progressDialog.dismiss();
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something wrong");
                                return;
                            }

                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                if (isError(response.getMessage())) {

                                }
                                return;
                            }
                            if (response.getStatus() != 200) {
                                errorMessage(getActivity().getApplication(), "Something wrong" + response.getMessage());
                                return;
                            }
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();

                            AddNewPurchase.sharedPreferenceForStore.deleteData();
                            myDatabaseHelper.deleteAllData();//delete all data from local database
                            isConfirmSubmitData = true;
                        }));
    }

    @Override
    public void save() {

            submit("saveUnPaid");


    }

    @Override
    public void imageUri(Intent uri) {

    }

    @Override
    public void onClick(View v) {
        hideKeyboard(getActivity());
        if (v.getId() == R.id.cashBook) {
            paymentType = "1";
            visibleImage();
        }
        if (v.getId() == R.id.bkashCard) {
            paymentType = "2";
            visibleImage();
        }
        if (v.getId() == R.id.nogodCard) {
            paymentType = "3";
            visibleImage();
        }
        if (v.getId() == R.id.rocketCard) {
            paymentType = "4";
            visibleImage();
        }
        if (v.getId() == R.id.btn) {
            if (selectedCustomer == null) {
                message(getString(R.string.add_customer_mes));
                return;
            }
            showDialog(getString(R.string.purchase_dialog_message));
        }
    }
}



