package com.ismos_salt_erp.view.fragment.production.iodization.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.clickHandle.ConfirmEditIodizationClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmEditIodizationBinding;
import com.ismos_salt_erp.localDatabase.MyWashingCrushingHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.ProductionOutputList;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.WashingCrushingModel;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.IodizationViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.WashingViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ConfirmEditIodization extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentConfirmEditIodizationBinding binding;

    private DueCollectionViewModel dueCollectionViewModel;
    private MyWashingCrushingHelper myDatabaseHelper;
    private IodizationViewModel iodizationViewModel;


    private WashingViewModel washingViewModel;
    private DiscountViewModel discountViewModel;
    private SaleViewModel saleViewModel;
    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId, orderId;
    /**
     * For output
     */
    private List<ProductionOutputList> productionOutputLists;
    private List<String> productionOutputNameLists;
    private String lastOrderId;//will use as a order id
    /**
     * for search Reference  person
     */
    private List<CustomerResponse> referencePersonResponseList;
    private List<String> referencePersonNameList;

    /**
     * for store
     */
    List<EnterpriseList> storeResponseList;
    List<String> storeNameList;

    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;


    private String selectedReferencePerson, selectedStore, selectedOutput, destinationStore,referenceId,refName;
    private String destinationStoreId, output, note;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_edit_iodization, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Edit Iodization");
        myDatabaseHelper = new MyWashingCrushingHelper(getContext());
        washingViewModel = new ViewModelProvider(this).get(WashingViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        iodizationViewModel = new ViewModelProvider(this).get(IodizationViewModel.class);
        getDataFromPreviousFragment();

        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();
        /**
         * Now get Page Data From Server
         */
        getPageDataFromServer();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * now handle page all click
         */
        binding.setClickHandle(new ConfirmEditIodizationClickHandle() {
            @Override
            public void save() {
                if (lastOrderId == null) {
                    infoMessage(getActivity().getApplication(), "missing Last OrderId");
                    return;
                }
                if (selectedReferencePerson == null) {
                    binding.referencePerson.setError("Empty");
                    binding.referencePerson.requestFocus();
                    return;
                }
                if (selectedOutput == null) {
                    binding.output.setEnableErrorLabel(true);
                    binding.output.setErrorText("Empty");
                    binding.output.requestFocus();
                    return;
                }
                if (selectedStore == null) {
                    binding.store.setEnableErrorLabel(true);
                    binding.store.setErrorText("Empty");
                    binding.store.requestFocus();
                    return;
                }

                if (binding.note.getText().toString().isEmpty()) {
                    binding.note.setError("Empty");
                    binding.note.requestFocus();
                    return;
                }
                if (!isInternetOn(getActivity())) {
                    infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
                    return;
                }
                hideKeyboard(getActivity());
                editIodizationDialog();
            }

            @Override
            public void datePicker() {
                showDatePickerDialog();
            }
        });


        /**
         * now handle search Reference Person
         */
        binding.referencePerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.referencePerson.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.referencePerson.isPerformingCompletion()) { // selected a product
                    return;
                }
                if (!s.toString().trim().isEmpty() && !isDataFetching) {
                    String currentText = binding.referencePerson.getText().toString();
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    } else {
                        selectedReferencePerson = null;//for handle proper selected customer
                        getReferencePersonBySearchKey(currentText);
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /**
         * handle Reference Person suggested item click
         */
        binding.referencePerson.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (referencePersonNameList.get(position).equals(NO_DATA_FOUND)) {
                binding.referencePerson.getText().clear();
            } else {
                selectedReferencePerson = referencePersonResponseList.get(position).getCustomerID();
            }
        });

        /**
         * handle empty store click handle
         */
        binding.store.setOnEmptySpinnerClickListener(() -> {
            if (selectedEnterpriseId == null) {
                infoMessage(getActivity().getApplication(), "Select Enterprise First !!");
            }
        });
        binding.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStore = storeResponseList.get(position).getStoreID();
                binding.store.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.output.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOutput = productionOutputLists.get(position).getProductID();
                binding.output.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }


    private void getReferencePersonBySearchKey(String currentText) {
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
                    selectedReferencePerson = null;
                    referencePersonResponseList = new ArrayList<>();
                    referencePersonResponseList.clear();
                    referencePersonResponseList.addAll(response.getLists());

                    referencePersonNameList = new ArrayList<>();
                    referencePersonNameList.clear();


                    for (int i = 0; i < response.getLists().size(); i++) {
                        referencePersonNameList.add("" + response.getLists().get(i).getCustomerFname());
                    }

                    if (referencePersonNameList.isEmpty()) {
                        referencePersonNameList.add(NO_DATA_FOUND);
                    }

                    customerNameAdapter = new ArrayAdapter<String>(getContext(), R.layout.filter_model, R.id.customerNameModel, referencePersonNameList);
                    binding.referencePerson.setAdapter(customerNameAdapter);
                    binding.referencePerson.showDropDown();
                    isDataFetching = false;
                });
    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());
        /**
         * set output list
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        washingViewModel.getProductionOutputList(getActivity(), "2")//here production_type 2 for Iodization,
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    productionOutputLists = new ArrayList<>();
                    productionOutputLists.clear();
                    productionOutputNameLists = new ArrayList<>();
                    productionOutputNameLists.clear();
                    productionOutputLists.addAll(response.getItems());

                    for (int i = 0; i < response.getItems().size(); i++) {
                        productionOutputNameLists.add("" + response.getItems().get(i).getProductTitle());
                        if (output != null) {
                            if (output.equals(response.getItems().get(i).getProductID())) {
                                binding.output.setSelection(i);
                                selectedOutput = output;
                            }
                        }
                    }
                    binding.output.setItem(productionOutputNameLists);

                });
        /**
         * now set process no will this no use for order Id
         */
        discountViewModel.getLastOrderId(getActivity())
                .observe(getViewLifecycleOwner(), lastOrderIdFromServer -> {
                    lastOrderId = String.valueOf(lastOrderIdFromServer);
                    binding.processNo.setText(lastOrderId);
                });

        /**
         * now get store id based on enterprise
         */
        nowSetStoreListByEnterPriseId();
    }

    private void nowSetStoreListByEnterPriseId() {

        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), selectedEnterpriseId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    storeResponseList = new ArrayList<>();
                    storeResponseList.clear();
                    storeNameList = new ArrayList<>();
                    storeNameList.clear();

                    storeResponseList.addAll(response.getEnterprise());

                    for (int i = 0; i < response.getEnterprise().size(); i++) {
                        storeNameList.add("" + response.getEnterprise().get(i).getStoreName());
                        if (destinationStoreId != null) {
                            if (destinationStoreId.equals(response.getEnterprise().get(i).getStoreID())) {
                                binding.store.setSelection(i);
                                selectedStoreId = destinationStoreId;
                            }
                        }
                    }
                    binding.store.setItem(storeNameList);

                    if (storeResponseList.size() == 1) {
                        binding.store.setSelection(0);
                        selectedStore = storeResponseList.get(0).getStoreID();
                    }
                });
    }

    private void showSelectedDataToRecyclerView() {
        List<WashingCrushingModel> selectedItem = new ArrayList<>();
        selectedItem.addAll(EditIodization.updatedQuantityProductList);//get static data from (WashingAndCrushing) Fragment
        /**
         * set set selected data to recyclerview
         * here selected data adapter code and below (ConfirmNewSaleSelectedProductListAdapter) adapter code are same that's the reason i use it
         */


        List<SalesRequisitionItems> list = new ArrayList<>();
        for (int i = 0; i < selectedItem.size(); i++) {
            SalesRequisitionItems salesRequisitionItems = new SalesRequisitionItems();
            salesRequisitionItems.setProductID(selectedItem.get(i).getProductID());
            salesRequisitionItems.setProductTitle(selectedItem.get(i).getProductTitle());
            salesRequisitionItems.setCategory(selectedItem.get(i).getCategory());
            salesRequisitionItems.setUnit(selectedItem.get(i).getUnit());
            salesRequisitionItems.setUnit_name(selectedItem.get(i).getUnit_name());
            salesRequisitionItems.setQuantity(selectedItem.get(i).getQuantity());
            list.add(salesRequisitionItems);
        }

        ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), list);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);
    }

    private void getDataFromPreviousFragment() {


        assert getArguments() != null;
        selectedEnterpriseId = getArguments().getString("selectedEnterprise");
        selectedStoreId = getArguments().getString("selectedStoreId");
        orderId = getArguments().getString("orderId");
        destinationStore = getArguments().getString("destinationStore");


         destinationStoreId = getArguments().getString("destinationStoreId");
        output = getArguments().getString("output");
        note = getArguments().getString("note");
         referenceId = getArguments().getString("referenceId");
        refName = getArguments().getString("refName");
        binding.referencePerson.setText(""+refName);
        selectedReferencePerson = referenceId;

        /**
         * set current date will send input from user
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);
        /*System.out.println(dtf.format(now));*/
        binding.processingDate.setText(currentDate);


    }

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
        dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }

        String selectedDate = dayOfMonth + "-" + month + "-" + year;//set the selected date
        binding.processingDate.setText(selectedDate);
    }

    private void validationAndSave() {
        /**
         * for set updated productIdList,unitList
         */
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        for (int i = 0; i < EditIodization.updatedQuantityProductList.size(); i++) {
            try {
                proDuctIdList.add(EditIodization.updatedQuantityProductList.get(i).getProductID());
                unitList.add(EditIodization.updatedQuantityProductList.get(i).getUnit());
                productTitleList.add(EditIodization.updatedQuantityProductList.get(i).getProductTitle());
                soldFromList.add(EditIodization.updatedQuantityProductList.get(i).getSoldFrom());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }


        /**
         * for get Previous quantity
         */
        List<String> oldQuantityList = new ArrayList<>();
        List<String> oldSoldFromList = new ArrayList<>();
        oldSoldFromList.clear();
        oldQuantityList.clear();

        for (int i = 0; i < EditIodization.getPreviousSaleInfoResponse.getOrderDetails().getItems().size(); i++) {
            try {
                oldQuantityList.add(EditIodization.getPreviousSaleInfoResponse.getOrderDetails().getItems().get(i).getQuantity());
                oldSoldFromList.add(EditIodization.getPreviousSaleInfoResponse.getOrderDetails().getItems().get(i).getSoldFrom());
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }

        }
        /**
         * all ok now send edit information to server
         **/

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        iodizationViewModel.submitEditIodizationInfo(
                getActivity(), orderId, lastOrderId, selectedReferencePerson, proDuctIdList, soldFromList,
                EditIodization.updatedQuantityList, Collections.singletonList("0"), productTitleList, Collections.singletonList("0"),
                oldQuantityList, oldSoldFromList, binding.processingDate.getText().toString(), "0", binding.note.getText().toString(),
                getArguments().getString("destinationStoreId"), selectedOutput,  selectedStore
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            try {
                myDatabaseHelper.deleteAllData();
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();
        });

    }

    private void editIodizationDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext() );
        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to update this Iodization ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.unicef_main));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            validationAndSave();
        });
        /* if (alertDialog.getWindow() != null) {
         *//**
         * for show sliding animation in alert dialog
         *//*
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }*/
        alertDialog.show();


    }


}