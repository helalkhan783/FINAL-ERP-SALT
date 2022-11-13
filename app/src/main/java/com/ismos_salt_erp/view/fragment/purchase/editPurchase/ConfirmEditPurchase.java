package com.ismos_salt_erp.view.fragment.purchase.editPurchase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.clickHandle.ConfirmPurchaseEditClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmEditPurchaseBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SearchTransportList;
import com.ismos_salt_erp.serverResponseModel.VehicleList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.HandleDiscount;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.PurchaseEditViewModel;
import com.ismos_salt_erp.viewModel.PurchaseViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ConfirmEditPurchase extends AddUpDel implements CompoundButton.OnCheckedChangeListener  {
    private FragmentConfirmEditPurchaseBinding binding;


    public static boolean isConfirmSubmitData = false;
    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private DiscountViewModel discountViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    private PurchaseEditViewModel purchaseEditViewModel;
    private UpdateMillerViewModel updateMillerViewModel;
    private PurchaseViewModel purchaseViewModel;

    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;
    private boolean isOpen = false;
    private boolean isClose = true;
    /**
     * For customer search
     */
    private List<CustomerResponse> customerResponseList;
    private List<String> customerNameList;

    /**
     * for transport search
     */
    private List<SearchTransportList> searchTransportLists;
    private List<String> searchTransportNameList;

    private String selectedCustomer, selectedTransport;


    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId;
    private String siId, orderId;
    double totalPrice = 0, discount = 0;

    String vehicleId, price;
    List<VehicleList> vehicleLists;
    List<String> vehicleNameList;
    private ArrayAdapter<String> vehicleAdapter;
    HandleDiscount handleDiscount;
    String discountType = EditPurchaseData.discountType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_edit_purchase, container, false);

        binding.toolbar.toolbarTitle.setText("Confirm Purchase Update");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        purchaseEditViewModel = new ViewModelProvider(this).get(PurchaseEditViewModel.class);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();

        binding.toolbar.setClickHandle(() -> {
            myDatabaseHelper.deleteAllData();
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });

        getLocalData();

        if (discountType !=null){
            if (discountType.equals("1")) {
                binding.discountAndRemarksBox.discountPerchantBox.setSelected(true);
                binding.discountAndRemarksBox.discountPerchantBox.setChecked(true);
                binding.discountAndRemarksBox.discountEt.setHint("Discount %");
                binding.discountAndRemarksBox.discountTv.setText("Discount %");
                binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
            }
            if (discountType.equals("2")) {
                binding.discountAndRemarksBox.fixedDiscoutBox.setSelected(true);
                binding.discountAndRemarksBox.fixedDiscoutBox.setChecked(true);
                binding.discountAndRemarksBox.discountEt.setHint("Discount Amount");
                binding.discountAndRemarksBox.discountTv.setText("Discount Amount");
                binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
            }
        }


        setOnClick();
        /**
         * for save sale
         */
        binding.setClickHandle(new ConfirmPurchaseEditClickHandle() {
            @Override
            public void save() {
                hideKeyboard(getActivity());
                if (binding.discountAndRemarksBox.paidAmount.getText().toString().isEmpty()){
                    binding.discountAndRemarksBox.paidAmount.setError("Empty Amount");
                    binding.discountAndRemarksBox.paidAmount.requestFocus();
                    return;
                }
                if (binding.note.getText().toString().isEmpty()) {
                    binding.note.setError("Empty");
                    binding.note.requestFocus();
                    return;
                }
                showDialog(getString(R.string.purchase_edit_dialog_title));
            }

            @Override
            public void addNewCustomer() {
                //Navigation.findNavController(getView()).navigate(R.id.action_confirmEditPurchase_to_addNewCustomer);
            }
        });
        /**
         * for visible add transport
         */
        binding.addTransportTv.setOnClickListener(v -> {
            if (binding.expandableView.isExpanded()) {
                binding.expandableView.setExpanded(false);
                return;
            } else {
                binding.expandableView.setExpanded(true);
            }
        });


        //handle customer suggested item click
        binding.customerName.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (customerNameList.get(position).equals(NO_DATA_FOUND)) {
                binding.customerName.getText().clear();
            } else {
                selectedCustomer = customerResponseList.get(position).getCustomerID();
            }


            /**
             * now set data defend on selected customer
             */
            try {
                binding.companyName.setText(customerResponseList.get(position).getCompanyName());
                binding.ownerName.setText(customerResponseList.get(position).getCustomerFname());
                binding.contactNumber.setText(customerResponseList.get(position).getPhone());
                binding.address.setText(
                        customerResponseList.get(position).getAddress() + " " +
                                customerResponseList.get(position).getThana() + ", " +
                                customerResponseList.get(position).getDistrict() + ", " +
                                customerResponseList.get(position).getDivision());
            } catch (Exception e) {
                Log.d("ERROR", e.getLocalizedMessage());
            }


        });
        /**
         * handle searchVehicle suggested item click
         */
//        binding.searchVehicle.setOnItemClickListener((parent, view, position, id) -> {
//            hideKeyboard(getActivity());
//            if (searchTransportNameList.get(position).equals(NO_DATA_FOUND)) {
//                binding.searchVehicle.getText().clear();
//            } else {
//                selectedTransport = searchTransportLists.get(position).getId();
//                /**
//                 * now set transport details based on selected tranport
//                 */
//                setTransportDetailsBySelectedTransport(searchTransportLists.get(position));
//            }
//
//
//        });


        // now search customer by customer name
        binding.customerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.customerName.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.customerName.isPerformingCompletion()) { // selected a product
                    return;
                }
                if (!s.toString().trim().isEmpty() && !isDataFetching) {
                    String currentText = binding.customerName.getText().toString();
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    } else {
                        selectedCustomer = null;//for handle proper selected customer
                        getCustomerBySearchKey(currentText);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // now handle add transport
        binding.addTransportLayout.searchTransport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.addTransportLayout.searchTransport.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.addTransportLayout.searchTransport.isPerformingCompletion()) {
                    return;
                }
                if (!s.toString().trim().isEmpty() && !isDataFetching) {
                    String searchKey = binding.addTransportLayout.searchTransport.getText().toString();
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    } else {
                        vehicleId = null;
                        getVehicleData(searchKey);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * now handle add transport
         */
//        binding.searchVehicle.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (!binding.searchVehicle.isPerformingCompletion()) {
//                    return;
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (binding.searchVehicle.isPerformingCompletion()) { // selected a product
//                    return;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!s.toString().trim().isEmpty() && !isDataFetching) {
//                    String currentText = binding.searchVehicle.getText().toString();
//                    if (!(isInternetOn(getActivity()))) {
//                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
//                    } else {
//                        selectedTransport = null;
//                        getTransportDetailsBySearchKey(currentText);
//                    }
//
//                }
//            }
//        });


        binding.addTransportLayout.searchTransport.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (vehicleNameList.get(position).equals(NO_DATA_FOUND)) {
                binding.addTransportLayout.searchTransport.getText().clear();
            }

            vehicleId = vehicleLists.get(position).getId();
            binding.addTransportLayout.driverName.setText(vehicleLists.get(position).getPersonName());
            binding.addTransportLayout.driverPhone.setText(vehicleLists.get(position).getPhone());
            binding.addTransportLayout.transportName.setText(vehicleLists.get(position).getTransportName());
            binding.addTransportLayout.fare.setText(vehicleLists.get(position).getTransportName());


        });

        /**
         * handle accountancy
         */
        handleDiscount = new HandleDiscount(getActivity(), "e",
                binding.discountAndRemarksBox.discountEt, binding.discountAndRemarksBox.fixedDiscoutBox,
                binding.discountAndRemarksBox.discountPerchantBox, binding.discountAndRemarksBox.discount,
                binding.discountAndRemarksBox.grandTotal, binding.discountAndRemarksBox.totalTv,
                binding.discountAndRemarksBox.total, binding.discountAndRemarksBox.paidAmount, binding.discountAndRemarksBox.due
        );
        handleDiscount.calculation(discountType);
        handleDiscount.forPaidVariable("ok");

//        binding.searchVehicle.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (!binding.searchVehicle.isPerformingCompletion()) {
//                    return;
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (binding.searchVehicle.isPerformingCompletion()) { // selected a product
//                    return;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!s.toString().trim().isEmpty() && !isDataFetching) {
//                    String currentText = binding.searchVehicle.getText().toString();
//                    if (!(isInternetOn(getActivity()))) {
//                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
//                    } else {
//                        selectedTransport = null;
//                        getTransportDetailsBySearchKey(currentText);
//                    }
//
//                }
//            }
//        });

        return binding.getRoot();
    }



    private void getVehicleData(String searchKey) {
        purchaseViewModel.getVehicleData(getActivity(), searchKey);

        purchaseViewModel.getVehicleList().observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() != 200) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            isDataFetching = true;
            vehicleId = null;
            vehicleNameList = new ArrayList<>();
            vehicleNameList.clear();
            vehicleLists = new ArrayList<>();
            vehicleLists.clear();
            vehicleLists.addAll(response.getLists());

            for (int i = 0; i < vehicleLists.size(); i++) {
                vehicleNameList.add("" + vehicleLists.get(i).getVehicleShipNo());
            }
            if (vehicleNameList.isEmpty()) {
                vehicleNameList.add(NO_DATA_FOUND);
            }
            vehicleAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, vehicleNameList);
            binding.addTransportLayout.searchTransport.setAdapter(vehicleAdapter);
            binding.addTransportLayout.searchTransport.showDropDown();
            isDataFetching = false;
        });
    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        updateMillerViewModel.getEditPurchasePageInfo(getActivity(), siId).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getOrderDetails().getPaymentType().equals("Cash")) {
                binding.discountAndRemarksBox.cashCheckBox.setSelected(true);
            }
            discountType = response.getOrder().getDiscountType();
            if (response.getOrder().getDiscountType().equals("2")) {
                if (!response.getOrder().getDiscountAmount().equals("0.00")) {
                    binding.discountAndRemarksBox.discountEt.setHint("Discount Amount");
                    binding.discountAndRemarksBox.discountTv.setText("Discount Amount");
                    binding.discountAndRemarksBox.fixedDiscoutBox.setSelected(true);
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
                }
            }
            if (response.getOrder().getDiscountType().equals("1")) {
                if (!response.getOrder().getDiscountAmount().equals("0.00")) {
                    binding.discountAndRemarksBox.discountEt.setHint("Discount %");
                    binding.discountAndRemarksBox.discountTv.setText("Discount %");
                    binding.discountAndRemarksBox.fixedDiscoutBox.setSelected(true);
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
                }
            }
            binding.discountAndRemarksBox.discount.setText(response.getOrder().getDiscountAmount());
            binding.discountAndRemarksBox.discountEt.setText(response.getOrder().getDiscountAmount());
            /**
             * now set customer data
             */
            binding.customerName.setText(response.getOrderDetails().getCustomer().getCustomerFname());
            binding.companyName.setText(response.getOrderDetails().getCustomer().getCompanyName());
            binding.contactNumber.setText(response.getOrderDetails().getCustomer().getPhone());
            binding.address.setText(response.getOrderDetails().getCustomer().getAddress());
            binding.ownerName.setText(response.getOrderDetails().getCustomer().getCustomerFname());
        });
    }

    private void getLocalData() {
        binding.discountAndRemarksBox.accountLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < EditPurchaseData.updatedQuantityProductList.size(); i++) {
            totalPrice += Double.parseDouble(EditPurchaseData.updatedQuantityProductList.get(i).getTotalPrice());
            discount += Double.parseDouble(EditPurchaseData.updatedQuantityProductList.get(i).getDiscount());
        }
        double finalTotalPrice = totalPrice + discount;
        try {
            binding.discountAndRemarksBox.total.setText(String.valueOf(finalTotalPrice));
            binding.discountAndRemarksBox.grandTotal.setText(String.valueOf(finalTotalPrice));
            binding.discountAndRemarksBox.discount.setText(EditPurchaseData.discountAmount);
            binding.discountAndRemarksBox.totalTv.setText(String.valueOf(totalPrice));
            binding.discountAndRemarksBox.paidAmount.setText("" + EditPurchaseData.collectedAmount);

            if (Double.parseDouble(EditPurchaseData.discountAmount) > 0) {
                if (discountType.equals("1")){
                    binding.discountAndRemarksBox.discountEt.setText(EditPurchaseData.percentDiscount);
                }
                if (discountType.equals("2")){
                    binding.discountAndRemarksBox.discountEt.setText(String.valueOf(EditPurchaseData.discountAmount));
                }
                double grandTotal = totalPrice - Double.parseDouble(EditPurchaseData.discountAmount);
                binding.discountAndRemarksBox.totalTv.setText(String.valueOf(grandTotal));
                binding.discountAndRemarksBox.grandTotal.setText(String.valueOf(grandTotal));
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        double collectedAmount = Double.parseDouble(EditPurchaseData.collectedAmount);
        double totalAmount = 0.0, due = 0.0;
        if (!binding.discountAndRemarksBox.totalTv.getText().toString().isEmpty()) {
            totalAmount = Double.parseDouble(binding.discountAndRemarksBox.totalTv.getText().toString());

        }
        due = totalAmount - collectedAmount;
        binding.discountAndRemarksBox.due.setText("" + due);
        /**
         * set previous   customer data
         */

        try {
            binding.customerName.setText("" + EditPurchaseData.customerName);
            binding.companyName.setText("" + EditPurchaseData.companyName);
            binding.ownerName.setText("" + EditPurchaseData.customerName);
            binding.contactNumber.setText("" + EditPurchaseData.customerPhone);
            binding.address.setText("" + EditPurchaseData.address);
        }catch (Exception e){

        }

    }

    private void submit() {
        if (selectedCustomer == null) {
            selectedCustomer = EditPurchaseData.customerId;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);

        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> sellingPriceList = new ArrayList<>();
        List<String> previousQuantityList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();
        List<String> buyingPrice = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        soldFromList.clear();
        for (int i = 0; i < EditPurchaseData.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(EditPurchaseData.updatedQuantityProductList.get(i).getProductID());
                unitList.add(EditPurchaseData.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(EditPurchaseData.updatedQuantityProductList.get(i).getProductTitle());
                sellingPriceList.add(EditPurchaseData.currentSaleItemList.get(i).getSellingPrice());
                buyingPrice.add(EditPurchaseData.updatedQuantityProductList.get(i).getPrice());
                previousQuantityList.add(EditPurchaseData.currentSaleItemList.get(i).getQuantity());
                soldFromList.add(EditPurchaseData.currentSaleItemList.get(i).getSoldFrom());
            } catch (Exception e) {
                Log.d("Error", "" + e.getMessage());
            }

        }
        String discount = binding.discountAndRemarksBox.discountEt.getText().toString();
        if (binding.discountAndRemarksBox.discountPerchantBox.isChecked()) {
            discount = discount + "%";
        }

        if (discount == null || discount.isEmpty()){
            discount="0";
        }
        String paidAmount = binding.discountAndRemarksBox.paidAmount.getText().toString();
        if (paidAmount == null || paidAmount.isEmpty()){
            paidAmount = "0";
        }

        if (discountType == null) {
            discountType = "0";
        }

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        purchaseEditViewModel.submitPurchaseEditData(
                getActivity(), orderId, siId, selectedCustomer,
                proDuctIdList, soldFromList,
                EditPurchaseData.updatedQuantityList, unitList, buyingPrice,
                productTitleList, Collections.singletonList("0"), previousQuantityList, "1",
                discount, discountType,
                currentDate, binding.discountAndRemarksBox.totalTv.getText().toString(), paidAmount, "0",
                binding.note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
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

                    /**
                     * after successfully add new sale Edit data to server delete all data from local DB
                     */

                    myDatabaseHelper.deleteAllData();//delete all data from local database
                    isConfirmSubmitData = true;
                });


    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        siId = getArguments().getString("siId");
        orderId = getArguments().getString("orderId");
    }

//    private void setTransportDetailsBySelectedTransport(SearchTransportList searchTransportList) {
//        binding.driverName.setText(searchTransportList.getPersonName());
//        binding.phone.setText(searchTransportList.getPhone());
//        //binding.vehicleFare.setText("");
//        binding.vehicleNumber.setText("" + searchTransportList.getVehicleShipNo());
//    }


    private void getCustomerBySearchKey(String currentText) {
        /**
         * call
         */
        dueCollectionViewModel
                .apiCallForGetSupplier(
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
                    selectedCustomer = null;
                    customerResponseList = new ArrayList<>();
                    customerResponseList.clear();
                    customerResponseList.addAll(response.getLists());

                    customerNameList = new ArrayList<>();
                    customerNameList.clear();


                    for (int i = 0; i < response.getLists().size(); i++) {
                        customerNameList.add("" + response.getLists().get(i).getCustomerFname());
                    }

                    if (customerNameList.isEmpty()) {
                        customerNameList.add(NO_DATA_FOUND);
                    }

                    customerNameAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
                    binding.customerName.setAdapter(customerNameAdapter);
                    binding.customerName.showDropDown();
                    isDataFetching = false;
                });

    }


    //    private void getTransportDetailsBySearchKey(String currentText) {
//        saleViewModel.searchTransport(getActivity(), currentText)
//                .observe(getViewLifecycleOwner(), response -> {
//                    if (response == null) {
//                        errorMessage(getActivity().getApplication(), "Something Wrong");
//                        return;
//                    }
//                    if (response.getStatus() != 200) {
//                        errorMessage(getActivity().getApplication(), "Something Wrong");
//                        return;
//                    }
//
//                    isDataFetching = true;
//                    searchTransportLists = new ArrayList<>();
//                    searchTransportLists.clear();
//                    searchTransportNameList = new ArrayList<>();
//                    searchTransportNameList.clear();
//
//
//                    searchTransportLists.addAll(response.getLists());
//
//
//                    for (int i = 0; i < response.getLists().size(); i++) {
//                        searchTransportNameList.add(response.getLists().get(i).getTransportName());
//                    }
//
//                    if (searchTransportNameList.isEmpty()) {
//                        searchTransportNameList.add(NO_DATA_FOUND);
//                    }
//                    customerNameAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, searchTransportNameList);
//                    binding.searchVehicle.setAdapter(customerNameAdapter);
//                    binding.searchVehicle.showDropDown();
//                    isDataFetching = false;
//                });
//    }
    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(EditPurchaseData.updatedQuantityProductList);//get static data from (EditPurchaseData) Fragment
        /**
         * set set selected data to recyclerview
         */
        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);
    }



    private void setOnClick() {
        binding.discountAndRemarksBox.discountPerchantBox.setOnCheckedChangeListener(this);
        binding.discountAndRemarksBox.fixedDiscoutBox.setOnCheckedChangeListener(this);
        binding.discountAndRemarksBox.remarksBox.setOnCheckedChangeListener(this);

        // binding.addTransportLayout.searchTransport.setOnItemClickListener();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.fixedDiscoutBox:
                binding.discountAndRemarksBox.discountEt.setHint("Discount Amount");
                binding.discountAndRemarksBox.discountTv.setText("Discount Amount");
                if (isChecked) {
                    handleDiscount.calculation("2");
                    if (binding.discountAndRemarksBox.discountPerchantBox.isChecked()) {
                        binding.discountAndRemarksBox.discountPerchantBox.setChecked(false);
                        binding.discountAndRemarksBox.discountPerchantBox.setSelected(false);
                    }
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.discountPerchantBox:
                binding.discountAndRemarksBox.discountEt.setHint("Discount %");
                binding.discountAndRemarksBox.discountTv.setText("Discount %");
                if (isChecked) {
                    handleDiscount.calculation("1");
                    if (binding.discountAndRemarksBox.fixedDiscoutBox.isChecked()) {
                        binding.discountAndRemarksBox.fixedDiscoutBox.setChecked(false);
                        binding.discountAndRemarksBox.fixedDiscoutBox.setSelected(false);
                    }
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.discountAndRemarksBox.discountLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.remarksBox:
                if (isChecked) {
                    binding.discountAndRemarksBox.remarksLayout.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.discountAndRemarksBox.remarksLayout.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true){
            submit();
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}