package com.ismos_salt_erp.view.fragment.purchase.newPurchase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.adapter.custom_confirm_sale_adapter.ConfirmPurchaseAdapterOne;
import com.ismos_salt_erp.clickHandle.ConfirmPurchaseClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmPurchaseBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.serverResponseModel.VehicleList;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.sale.newSale.AddNewSale;
import com.ismos_salt_erp.view.fragment.sale.newSale.ItemClickOne;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.PurchaseViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ConfirmPurchase extends BaseFragment implements ItemClickOne {

    public static FragmentConfirmPurchaseBinding binding;
    List<SalesRequisitionItems> itemsList;
    int due;
    public static boolean isConfirmSubmitData = false;

    private DueCollectionViewModel dueCollectionViewModel;
    private SaleViewModel saleViewModel;
    private PurchaseViewModel purchaseViewModel;
    private DiscountViewModel discountViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    Cursor cursor;
    private boolean isCheckedCondition = false;

    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;

    /**
     * For customer search
     */
    private List<CustomerResponse> customerResponseList;
    private List<String> customerNameList;

    /**
     * for purchase search
     */
    private final String localSupplier = "Local Supplier";
    private final String foreignSupplier = "Foreign Supplier";
    private List<String> supplierList;
    private String selectedCustomer;
    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId;
    boolean flag;

    List<VehicleList> vehicleLists;
    List<String> vehicleNameList;
    private ArrayAdapter<String> vehicleAdapter;
    String vehicleId, price;
    double totalPrice = 0, discount = 0;
    String discountSenToServer;
    HandleDiscount handleDiscount;

    public static String discountPercent = "2";
    private boolean isOk = true;
    PopupWindow mypopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_purchase, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Purchase");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);

        binding.discountAndRemarksBox.carryingCostLayout.setVisibility(View.GONE);
        binding.discountAndRemarksBox.vatLayout.setVisibility(View.GONE);
        binding.discountAndRemarksBox.carryingCostTVlayout.setVisibility(View.GONE);
        binding.discountAndRemarksBox.vatTvLayout.setVisibility(View.GONE);
        getDataFromPreviousFragment();
        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });
//
//        binding.addTransportTv.setOnClickListener(v -> {
//            if (binding.expandableView.isExpanded()) {
//                binding.expandableView.setExpanded(false);
//                return;
//            } else {
//                binding.expandableView.setExpanded(true);
//            }
//        });
//

//        binding.setClickHandle(new ConfirmPurchaseClickHandle() {
//            @Override
//            public void save() {
//                if (selectedCustomer == null) {
//                    binding.customerName.setError("Empty");
//                    binding.customerName.requestFocus();
//                    return;
//                    //Snackbar.make(getView().findViewById(android.R.id.content), "Please Select Customer Name", Snackbar.LENGTH_SHORT).show();
//                }
//                if (binding.discountAndRemarksBox.remarksBox.isChecked()) {
//                    if (binding.discountAndRemarksBox.remarks.getText().toString().isEmpty()) {
//                        binding.discountAndRemarksBox.remarks.setError("Empty field");
//                        binding.discountAndRemarksBox.remarks.requestFocus();
//                        return;
//                    }
//                }
//                if (!binding.discountAndRemarksBox.cashCheckBox.isChecked()) {
//                    infoMessage(getActivity().getApplication(), "Please check cash");
//                    return;
//                }
//
//                confirmPurchaseDialog(getActivity());
//            }
//
//            @Override
//            public void addNewCustomer() {
////                Navigation.findNavController(getView()).navigate(R.id.action_confirmPurchase_to_addNewCustomer);
//            }
//        });
//
//        /**
//         * handle customer suggested item click
//         */
//        binding.customerName.setOnItemClickListener((parent, view, position, id) -> {
//            hideKeyboard(getActivity());
//            if (customerNameList.get(position).equals(NO_DATA_FOUND)) {
//                binding.customerName.getText().clear();
//            } else {
//                selectedCustomer = customerResponseList.get(position).getCustomerID();
//            }
//
//            /**
//             * now set data defend on selected customer
//             */
//            try {
//                binding.companyName.setText(customerResponseList.get(position).getCompanyName() + " @" + customerResponseList.get(position).getCustomerFname());
//                binding.ownerName.setText(customerResponseList.get(position).getCustomerFname());
//                binding.contactNumber.setText(customerResponseList.get(position).getPhone());
//                binding.address.setText(
//                        customerResponseList.get(position).getAddress() + " " +
//                                customerResponseList.get(position).getThana() + ", " +
//                                customerResponseList.get(position).getDistrict() + ", " +
//                                customerResponseList.get(position).getDivision());
//            } catch (Exception e) {
//                Log.d("ERROR", e.getLocalizedMessage());
//            }
//        });
//
//
//        binding.supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (supplierList.get(position).equals(localSupplier)) {
//                    Navigation.findNavController(getView()).navigate(R.id.action_confirmPurchase_to_addNewLocalSupplier);
//                    return;
//                }
//                if (supplierList.get(position).equals(foreignSupplier)) {
//                    Navigation.findNavController(getView()).navigate(R.id.action_confirmPurchase_to_addNewForignSupplier);
//                    return;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        /**
//         * now search customer by customer name
//         */
//        binding.customerName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (!binding.customerName.isPerformingCompletion()) {
//                    return;
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (binding.customerName.isPerformingCompletion()) { // selected a product
//                    return;
//                }
//                if (!s.toString().trim().isEmpty() && !isDataFetching) {
//                    String currentText = binding.customerName.getText().toString();
//                    if (!(isInternetOn(getActivity()))) {
//                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
//                    } else {
//                        selectedCustomer = null;//for handle proper selected customer
//                        getCustomerBySearchKey(currentText);
//
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.addTransportLayout.searchTransport.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (!binding.addTransportLayout.searchTransport.isPerformingCompletion()) {
//                    return;
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (binding.addTransportLayout.searchTransport.isPerformingCompletion()) {
//                    return;
//                }
//                if (!s.toString().trim().isEmpty() && !isDataFetching) {
//                    String searchKey = binding.addTransportLayout.searchTransport.getText().toString();
//                    if (!(isInternetOn(getActivity()))) {
//                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
//                    } else {
//                        vehicleId = null;
//                        getVehicleData(searchKey);
//                    }
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//        binding.addTransportLayout.searchTransport.setOnItemClickListener((parent, view, position, id) -> {
//            hideKeyboard(getActivity());
//            if (vehicleNameList.get(position).equals(NO_DATA_FOUND)) {
//                binding.addTransportLayout.searchTransport.getText().clear();
//            }
//
//            vehicleId = vehicleLists.get(position).getId();
//            binding.addTransportLayout.driverName.setText(vehicleLists.get(position).getPersonName());
//            binding.addTransportLayout.driverPhone.setText(vehicleLists.get(position).getPhone());
//            binding.addTransportLayout.transportName.setText(vehicleLists.get(position).getTransportName());
//            binding.addTransportLayout.fare.setText(vehicleLists.get(position).getTransportName());
//
//
//        });

        binding.discountAndRemarksBox.discountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");


                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.discountAndRemarksBox.carringCostEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");
                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.discountAndRemarksBox.vatEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");

                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.discountAndRemarksBox.confirmPaymetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("selectedEnterpriseId", getArguments().getString("selectedEnterpriseId"));
                bundle.putString("selectedStoreId", getArguments().getString("selectedStoreId"));
                bundle.putString("grandTotal", binding.discountAndRemarksBox.grandTotal.getText().toString().trim());
                bundle.putString("vat", binding.discountAndRemarksBox.vatEt.getText().toString().trim());
                bundle.putString("carringCost", binding.discountAndRemarksBox.carringCostTv.getText().toString().trim());


                Navigation.findNavController(getView()).navigate(R.id.action_confirmPurchase_to_companyOwnerInfo, bundle);
            }
        });

        binding.discountAndRemarksBox.percentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageImage();
                setPopUpWindow();
                mypopupWindow.showAsDropDown(binding.discountAndRemarksBox.percentCard, 0, 0);
            }
        });


        getLocalData();


/**
 * for payment
 */
//        handleDiscount = new HandleDiscount(getActivity(), "",
//                binding.discountAndRemarksBox.discountEt, binding.discountAndRemarksBox.fixedDiscoutBox,
//                binding.discountAndRemarksBox.discountPerchantBox, binding.discountAndRemarksBox.discount,
//                binding.discountAndRemarksBox.grandTotal, binding.discountAndRemarksBox.totalTv,
//                binding.discountAndRemarksBox.total, binding.discountAndRemarksBox.paidAmount, binding.discountAndRemarksBox.due
//        );
//
//        handleDiscount.calculation();
//        handleDiscount.forPaidVariable();
        return binding.getRoot();
    }

    private void manageImage() {
        if (!isOk) {
            binding.discountAndRemarksBox.percentCard.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            isOk = true;
        } else {
            binding.discountAndRemarksBox.percentCard.setImageResource(R.drawable.baseline_arrow_drop_up_24);
            isOk = false;
        }
    }

    private void setPopUpWindow() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_popup_layout_one, null);

        mypopupWindow = new PopupWindow(view, 420, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        LinearLayout percent, fixed;
        TextView percentTv, fixedTv;
        percent = (LinearLayout) view.findViewById(R.id.percent);
        fixed = (LinearLayout) view.findViewById(R.id.fixed);
        percentTv = (TextView) view.findViewById(R.id.percentTv);
        fixedTv = (TextView) view.findViewById(R.id.fixedTv);

        if (discountPercent.equals("2")) {
            percentTv.setTextColor(Color.parseColor("#9964AE"));
        }
        if (discountPercent.equals("1")) {
            fixedTv.setTextColor(Color.parseColor("#9964AE"));
        }


        percent.setOnClickListener(v -> {
            manageImage();
            discountPercent = "2";
            binding.discountAndRemarksBox.discountSelectorTv.setText("%");

            discountCalculation(discountPercent);
            mypopupWindow.dismiss();
        });

        fixed.setOnClickListener(v -> {
            manageImage();
            discountPercent = "1";
            binding.discountAndRemarksBox.discountSelectorTv.setText("Fixed");

            discountCalculation(discountPercent);
            mypopupWindow.dismiss();
        });


    }

//    private void getVehicleData(String searchKey) {
//        purchaseViewModel.getVehicleData(getActivity(), searchKey);
//
//        purchaseViewModel.getVehicleList().observe(getViewLifecycleOwner(), response -> {
//            if (response == null) {
//                errorMessage(getActivity().getApplication(), "Something Wrong");
//                return;
//            }
//            if (response.getStatus() != 200) {
//                errorMessage(getActivity().getApplication(), "Something Wrong");
//                return;
//            }
//            isDataFetching = true;
//            vehicleId = null;
//            vehicleNameList = new ArrayList<>();
//            vehicleNameList.clear();
//
//
//            vehicleLists = new ArrayList<>();
//            vehicleLists.clear();
//            vehicleLists.addAll(response.getLists());
//
//            for (int i = 0; i < vehicleLists.size(); i++) {
//                vehicleNameList.add("" + vehicleLists.get(i).getVehicleShipNo());
//            }
//            if (vehicleNameList.isEmpty()) {
//                vehicleNameList.add(NO_DATA_FOUND);
//            }
//            vehicleAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, vehicleNameList);
//            binding.addTransportLayout.searchTransport.setAdapter(vehicleAdapter);
//            binding.addTransportLayout.searchTransport.showDropDown();
//            isDataFetching = false;
//        });
//
//
//    }

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
        double finalTotalPrice = totalPrice + discount;
        binding.discountAndRemarksBox.total.setText(DataModify.addFourDigit(String.valueOf(finalTotalPrice)));
        discountCalculation(discountPercent);

    }

    public void discountCalculation(String discountType) {

        try {
            double discount = 0, total = 0, totalPrice1 = 0;
            String discountString = binding.discountAndRemarksBox.discountEt.getText().toString().trim();
            if (discountString.isEmpty()) {
                discountString = "0.0";
            }
            discount = Double.parseDouble(discountString);

            total = Double.parseDouble(binding.discountAndRemarksBox.total.getText().toString());

            if (discountType.equals("1")) {
                totalPrice1 = total - discount;
            }
            if (discountType.equals("2")) {
                discount = total * discount / 100;
                totalPrice1 = total - discount;

            }
            if (total < discount) {
                binding.discountAndRemarksBox.discountEt.setError("Invalid discount");
                binding.discountAndRemarksBox.discountEt.requestFocus();
                return;
            }
            binding.discountAndRemarksBox.grandTotal.setText(DataModify.addFourDigit(String.valueOf(totalPrice1)));
            binding.discountAndRemarksBox.totalDiscountTv.setText(DataModify.addFourDigit(String.valueOf(discount)));


            calculateGrandTotal(total);

        } catch (Exception e) {
        }

    }

    private void calculateGrandTotal(double subTotal) {
        double vatPercent = 0.0000;
        String carringCost = binding.discountAndRemarksBox.carringCostEt.getText().toString().trim();
        String vatCost = binding.discountAndRemarksBox.vatEt.getText().toString().trim();
        String dis = binding.discountAndRemarksBox.totalDiscountTv.getText().toString().trim();
        if (dis.isEmpty()) {
            dis = "0";
        }
        if (vatCost.isEmpty()) {
            vatCost = "0.0000";
        }
// vat count as percent of total
        double vat = subTotal - Double.parseDouble(dis);

        vatPercent = vat * Double.parseDouble(vatCost) / 100;

        if (carringCost.isEmpty()) {
            carringCost = "0.0000";
        }

        double totalExtraCost = 0.0, grandTotalWithCarringCost = 0.0;
        String total = binding.discountAndRemarksBox.grandTotal.getText().toString().trim();
        totalExtraCost = Double.parseDouble(carringCost) + vatPercent;
        grandTotalWithCarringCost = Double.parseDouble(total) + totalExtraCost;
        binding.discountAndRemarksBox.grandTotal.setText("" + DataModify.addFourDigit(String.valueOf(grandTotalWithCarringCost)));
        binding.discountAndRemarksBox.vatTv.setText("" + DataModify.addFourDigit(String.valueOf(vatPercent)));
        binding.discountAndRemarksBox.carringCostTv.setText("" + DataModify.addFourDigit(String.valueOf(carringCost)));


    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
        selectedStoreId = getArguments().getString("selectedStoreId");
        supplierList = new ArrayList<>();
        supplierList.clear();
        supplierList.add(localSupplier);
        supplierList.add(foreignSupplier);
        //     binding.supplier.setItem(supplierList);
    }

//    private void setTransportDetailsBySelectedTransport(SearchTransportList searchTransportList) {
//        binding.driverName.setText(searchTransportList.getPersonName());
//        binding.phone.setText(searchTransportList.getPhone());
//        // binding.vehicleFare.setText("");
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
                    try {
                        if (response == null) {
                            //errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() != 200) {
                            //infoMessage(getActivity().getApplication(), "Something Wrong");
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
                            customerNameList.add("" + response.getLists().get(i).getCustomerFname() + "@" + response.getLists().get(i).getCompanyName());
                        }

                        if (customerNameList.isEmpty()) {
                            customerNameList.add(NO_DATA_FOUND);
                        }

                        customerNameAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, customerNameList);
                        //  binding.customerName.setAdapter(customerNameAdapter);
                        //  binding.customerName.showDropDown();
                        isDataFetching = false;
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
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
        selectedItem.addAll(AddNewPurchase.list);//get static data from (AddNewPurchase) Fragment


        ConfirmPurchaseAdapterOne adapter = new ConfirmPurchaseAdapterOne(getActivity(), selectedItem, ConfirmPurchase.this);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        myDatabaseHelper.deleteAllData();
    }

    private boolean isError(String errorMessage) {

        if (errorMessage.startsWith("[") && errorMessage.endsWith("]")) {
            return true;
        }
        return false;
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONArray(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public void confirmPurchaseDialog(FragmentActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do You Want to Purchase it ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            validationAndSave();
        });

        alertDialog.show();
    }

    private void validationAndSave() {
        if (!isInternetOn(getActivity())) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }

//        if (selectedTransport == null) {
//            binding.searchVehicle.setError("Empty");
//            binding.searchVehicle.requestFocus();
//            return;
//            //Snackbar.make(getView().findViewById(android.R.id.content), "Please Select Transport", Snackbar.LENGTH_SHORT).show();
//        }
        /**
         * for set updated productIdList,unitList
         */
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

        for (int i = 0; i < AddNewPurchase.updatedQuantityProductList.size(); i++) {
            proDuctIdList.add(AddNewPurchase.updatedQuantityProductList.get(i).getProductID());
            unitList.add(AddNewPurchase.updatedQuantityProductList.get(i).getUnit());
            productTitleList.add(AddNewPurchase.updatedQuantityProductList.get(i).getProductTitle());
            quantityList.add(AddNewPurchase.updatedQuantityProductList.get(i).getQuantity());
            perPriceList.add(AddNewPurchase.updatedQuantityProductList.get(i).getPrice());
            discountList.add(AddNewPurchase.updatedQuantityProductList.get(i).getDiscount());
            totalPriceList.add(AddNewPurchase.updatedQuantityProductList.get(i).getTotalPrice());
        }

        if (binding.discountAndRemarksBox.discountEt.getText().toString().isEmpty()) {
            discountSenToServer = "0";
        }


        String discount = binding.discountAndRemarksBox.discountEt.getText().toString();
     /*   if (binding.discountAndRemarksBox.discountPerchantBox.isChecked()) {
            discount = discount + "%";
        }*/

        if (discount == null || discount.isEmpty()) {
            discount = "0";
        }
        hideKeyboard(getActivity());

     /*   String paidAmount = binding.discountAndRemarksBox.paidAmount.getText().toString();
        if (paidAmount == null || paidAmount.isEmpty()) {
            paidAmount = "0";
        }
        String due = binding.discountAndRemarksBox.due.getText().toString();

        if (due == null || due.isEmpty()) {
            due = binding.discountAndRemarksBox.totalTv.getText().toString();
        }*/

        /**
         * for get last order Id
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        String finalDiscount = discount;
        String finalPaidAmount = "paidAmount";
        String finalDue = "due";
        discountViewModel.getLastOrderId(getActivity()).observe(getViewLifecycleOwner(), lastOrderId ->
                purchaseViewModel.newPurchase(
                                getActivity(), selectedEnterpriseId, String.valueOf(lastOrderId), selectedCustomer, proDuctIdList,
                                Arrays.asList(selectedStoreId), quantityList, unitList, productTitleList,
                                perPriceList, discountList, totalPriceList,
                                finalPaidAmount,
                                finalDue,
                                finalDiscount,
                                binding.discountAndRemarksBox.remarks.getText().toString(), "1",
                                "", "", "", "")
                        .observe(getViewLifecycleOwner(), response -> {
                            progressDialog.dismiss();
                            if (response == null) {
                                //      errorMessage(getActivity().getApplication(), "Something wrong");
                                return;
                            }

                            if (response.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                if (isError(response.getMessage())) {
                                    if (isJSONValid(response.getMessage())) {

                                    }
                                }
                                return;
                            }


                            if (response.getStatus() != 200) {
                                //    errorMessage(getActivity().getApplication(), "Something wrong" + response.getMessage());
                                return;
                            }
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                            /**
                             * after successfully add new sale to server delete all data from local DB
                             */
                            AddNewPurchase.sharedPreferenceForStore.deleteData();
                            myDatabaseHelper.deleteAllData();//delete all data from local database
                            isConfirmSubmitData = true;
                        }));
    }


    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {

    }

    @Override
    public void update(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {

    }

    @Override
    public void updateConfirm(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPric, String productId) {
        double totalPrice = 0.0;
        totalPrice = Double.parseDouble(quantity) * Double.parseDouble(price);

        updateDataToLocal(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, String.valueOf(totalPrice));

    }

    private void updateDataToLocal(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        Boolean isUpdated = myDatabaseHelper
                .updateData(productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice);

        if (isUpdated) {
            //  Toast.makeText(getContext(), "Updated ", Toast.LENGTH_SHORT).show();
        } else {
            //  Toast.makeText(getContext(), "failed update ", Toast.LENGTH_SHORT).show();
        }
        getLocalData();
    }


    @Override
    public void removeBtn(int position) {
        if (AddNewPurchase.list.size() == 1) {
            // adapter.notifyItemRangeRemoved(0, AddNewSale.list.size());
            return;
        }

        binding.selectedProductsRv.getAdapter().notifyItemRangeChanged(position, AddNewPurchase.list.size());
        int value = myDatabaseHelper.deleteData(AddNewPurchase.list.get(position).getProductID());
        if (value > 0) {
            AddNewPurchase.list.remove(position);
            showSelectedDataToRecyclerView();
        }

        getLocalData();
    }
}