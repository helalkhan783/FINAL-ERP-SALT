package com.ismos_salt_erp.view.fragment.sale.editSale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.clickHandle.ConfirmEditNewSale;
import com.ismos_salt_erp.databinding.FragmentConfirmSaleEditBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SearchTransportList;
import com.ismos_salt_erp.serverResponseModel.VehicleList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.HandleDiscount;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.UpdateSaleViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfirmSaleEdit extends AddUpDel implements CompoundButton.OnCheckedChangeListener {
    private FragmentConfirmSaleEditBinding binding;
    private UpdateSaleViewModel updateSaleViewModel;


    public static boolean isConfirmSubmitData = false;
    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private DiscountViewModel discountViewModel;
    private MyDatabaseHelper myDatabaseHelper;


    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;

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

    double totalPrice = 0, discount = 0;

    String vehicleId, price;
    List<VehicleList> vehicleLists;
    List<String> vehicleNameList;
    private ArrayAdapter<String> vehicleAdapter;
    HandleDiscount handleDiscount;
    String discountType = EditSaleData.discountType;
    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId, siId, orderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_sale_edit, container, false);

        binding.toolbar.toolbarTitle.setText("Confirm Sale Edit");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        updateSaleViewModel = new ViewModelProvider(this).get(UpdateSaleViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });

        binding.setClickHandle(new ConfirmEditNewSale() {
            @Override
            public void addNewCustomer() {
                Navigation.findNavController(getView()).navigate(R.id.action_confirmSaleEdit_to_addNewCustomer);
            }

            @Override
            public void submit() {
                hideKeyboard(getActivity());
                if (selectedCustomer == null) {
                    selectedCustomer = EditSaleData.customerId;

                 }

                if (binding.discountAndRemarksBox.paidAmount.getText().toString().isEmpty()) {
                    binding.discountAndRemarksBox.paidAmount.setError("Empty Amount");
                    binding.discountAndRemarksBox.paidAmount.requestFocus();
                    return;
                }


                if (binding.note.getText().toString().isEmpty()) {
                    binding.note.setError("Empty");
                    binding.note.requestFocus();
                    return;
                }

                showDialog("Do You Want to Edit This Sale ?");

            }
        });

        getLocalData();

        setOnClick();

        try {
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
        } catch (Exception e) {
        }


        /**
         * handle customer suggested item click
         */
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
//                 * now set transport details based on selected transport
//                 */
//                setTransportDetailsBySelectedTransport(searchTransportLists.get(position));
//            }
//
//
//        });

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

        return binding.getRoot();
    }

    private void getLocalData() {
        binding.discountAndRemarksBox.accountLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < EditSaleData.updatedQuantityProductList.size(); i++) {
            totalPrice += Double.parseDouble(EditSaleData.updatedQuantityProductList.get(i).getTotalPrice());
            discount += Double.parseDouble(EditSaleData.updatedQuantityProductList.get(i).getDiscount());
        }
        double finalTotalPrice = totalPrice + discount;
        try {
            binding.discountAndRemarksBox.total.setText(String.valueOf(finalTotalPrice));
            binding.discountAndRemarksBox.grandTotal.setText(String.valueOf(finalTotalPrice));
            binding.discountAndRemarksBox.discount.setText(EditSaleData.discountAmount);
            binding.discountAndRemarksBox.totalTv.setText(String.valueOf(totalPrice));
            binding.discountAndRemarksBox.paidAmount.setText("" + EditSaleData.collectedAmount);


            if (Double.parseDouble(EditSaleData.discountAmount) > 0) {
                if (discountType.equals("1")) {
                    binding.discountAndRemarksBox.discountEt.setText(EditSaleData.percentDiscount);
                }
                if (discountType.equals("2")) {
                    binding.discountAndRemarksBox.discountEt.setText(String.valueOf(EditSaleData.discountAmount));
                }
                double grandTotal = totalPrice - Double.parseDouble(EditSaleData.discountAmount);
                binding.discountAndRemarksBox.totalTv.setText(String.valueOf(grandTotal));
                binding.discountAndRemarksBox.grandTotal.setText(String.valueOf(grandTotal));
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        double collectedAmount = Double.parseDouble(EditSaleData.collectedAmount);
        double totalAmount = 0.0, due = 0.0;
        if (!binding.discountAndRemarksBox.totalTv.getText().toString().isEmpty()) {
            totalAmount = Double.parseDouble(binding.discountAndRemarksBox.totalTv.getText().toString());

        }
        due = totalAmount - collectedAmount;
        binding.discountAndRemarksBox.due.setText("" + due);
/**
 *  set customer previous data
 */
        try {
            binding.customerName.setText("" + EditSaleData.customerName);
            binding.companyName.setText("" + EditSaleData.companyName);
            binding.ownerName.setText("" + EditSaleData.customerName);
            binding.contactNumber.setText("" + EditSaleData.customerPhone);
            binding.address.setText("" + EditSaleData.address);
        } catch (Exception e) {

        }

    }


    private void submit() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);

        /**
         * for set updated productIdList,unitList
         */
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> sellingPriceList = new ArrayList<>();
        List<String> previousQuantityList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        soldFromList.clear();
        try {
            for (int i = 0; i < EditSaleData.updatedQuantityProductList.size(); i++) {
                proDuctIdList.add(EditSaleData.updatedQuantityProductList.get(i).getProductID());
                unitList.add(EditSaleData.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(EditSaleData.updatedQuantityProductList.get(i).getProductTitle());
                sellingPriceList.add(EditSaleData.currentSaleItemList.get(i).getSellingPrice());
                previousQuantityList.add(EditSaleData.currentSaleItemList.get(i).getQuantity());
                soldFromList.add(EditSaleData.currentSaleItemList.get(i).getSoldFrom());
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }

        /**
         * now send edit data to server
         */

        String discount = binding.discountAndRemarksBox.discountEt.getText().toString();
        if (binding.discountAndRemarksBox.discountPerchantBox.isChecked()) {
            discount = discount + "%";
        }
        if (discount == null || discount.isEmpty()) {
            discount = "0";
        }
        if (discountType == null) {
            discountType = "0";
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        updateSaleViewModel.submitSalesEditData(
                getActivity(), orderId, siId, selectedCustomer,
                proDuctIdList, soldFromList,
                EditSaleData.updatedQuantityList, unitList, sellingPriceList,
                productTitleList, Collections.singletonList("0"), previousQuantityList, "1",
                discount, discountType,
                currentDate, binding.discountAndRemarksBox.totalTv.getText().toString(), binding.discountAndRemarksBox.paidAmount.getText().toString(), "0",
                binding.note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        Log.d("RESPONSE_EDIT_SALE", "" + response.getMessage());
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
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

  /*  private void setTransportDetailsBySelectedTransport(SearchTransportList searchTransportList) {
        binding.driverName.setText(searchTransportList.getPersonName());
        binding.phone.setText(searchTransportList.getPhone());
        binding.vehicleFare.setText("");
        binding.vehicleNumber.setText("" + searchTransportList.getVehicleShipNo());
    }
*/

    private void getCustomerBySearchKey(String currentText) {

        /**
         * call
         */
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

//
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
        selectedItem.addAll(EditSaleData.updatedQuantityProductList);//get static data from (EditSaleData) Fragment
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
                    try {
                        handleDiscount.calculation("2");
                    } catch (Exception e) {
                    }
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
                    try {
                        handleDiscount.calculation("1");
                    } catch (Exception e) {
                    }
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
    public void save() {
        submit();
    }

    @Override
    public void imageUri(Intent uri) {

    }
}