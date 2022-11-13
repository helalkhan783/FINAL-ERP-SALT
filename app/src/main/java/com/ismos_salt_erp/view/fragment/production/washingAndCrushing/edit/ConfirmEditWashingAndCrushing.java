package com.ismos_salt_erp.view.fragment.production.washingAndCrushing.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.clickHandle.ConfirmWashingCrushingClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmEditWashingAndCrushingBinding;
import com.ismos_salt_erp.localDatabase.MyWashingCrushingHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.ProductionOutputList;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.WashingCrushingModel;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.EditWashingCrushingViewModel;
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


public class ConfirmEditWashingAndCrushing extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    private FragmentConfirmEditWashingAndCrushingBinding binding;
    private DueCollectionViewModel dueCollectionViewModel;
    private MyWashingCrushingHelper myDatabaseHelper;
    private EditWashingCrushingViewModel editWashingCrushingViewModel;


    private WashingViewModel washingViewModel;
    private DiscountViewModel discountViewModel;
    private SaleViewModel saleViewModel;
    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, destinationStoreId, output, note, selectedStoreId, orderId;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_edit_washing_and_crushing, container, false);

        binding.toolbar.toolbarTitle.setText("Confirm Update Washing & Crushing");


        myDatabaseHelper = new MyWashingCrushingHelper(getContext());
        washingViewModel = new ViewModelProvider(this).get(WashingViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        editWashingCrushingViewModel = new ViewModelProvider(this).get(EditWashingCrushingViewModel.class);
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
        binding.setClickHandle(new ConfirmWashingCrushingClickHandle() {
            @Override
            public void save() {
                hideKeyboard(getActivity());
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your internet Connection");
                    return;
                }
                confirmEditWashingCrushingDialog(getActivity());
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

    private void getReferencePersonBySearchKey(String currentText) {/**
     * call
     */
        dueCollectionViewModel
                .apiCallForGetRefPerson(
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
        /**
         * set output list
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        washingViewModel.getProductionOutputList(getActivity(), "1")//here production_type 1 for Washing & Crushing,
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
        selectedItem.addAll(EditWashingCrushing.updatedQuantityProductList);//get static data from (WashingAndCrushing) Fragment
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
        destinationStoreId = getArguments().getString("destinationStoreId");
        output = getArguments().getString("output");
        note = getArguments().getString("note");
        selectedStoreId = getArguments().getString("selectedStoreId");
        orderId = getArguments().getString("orderId");
        destinationStore = getArguments().getString("destinationStore");
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

        if (note != null) {
            binding.note.setText("" + note);
        }
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
        if (lastOrderId == null) {
            infoMessage(getActivity().getApplication(), "missing Last OrderId");
            return;
        }
        if (selectedReferencePerson == null) {
            infoMessage(getActivity().getApplication(), "Please select Ref.Person");

            return;
        }
        if (selectedOutput == null) {
            infoMessage(getActivity().getApplication(), "Please select output");

            return;
        }
        if (selectedStore == null) {
            infoMessage(getActivity().getApplication(), "Please select store");

            return;
        }

        if (binding.note.getText().toString().isEmpty()) {
            binding.note.setError("Empty");
            binding.note.requestFocus();
            return;
        }

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
        for (int i = 0; i < EditWashingCrushing.updatedQuantityProductList.size(); i++) {
            proDuctIdList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getProductID());
            unitList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getUnit());
            productTitleList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getProductTitle());
            soldFromList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getSoldFrom());
        }


        /**
         * for get Previous quantity
         */
        List<String> oldQuantityList = new ArrayList<>();
        List<String> oldSoldFromList = new ArrayList<>();

        for (int i = 0; i < EditWashingCrushing.getPreviousSaleInfoResponse.getOrderDetails().getItems().size(); i++) {
            oldQuantityList.add(EditWashingCrushing.getPreviousSaleInfoResponse.getOrderDetails().getItems().get(i).getQuantity());
            oldSoldFromList.add(EditWashingCrushing.getPreviousSaleInfoResponse.getOrderDetails().getItems().get(i).getSoldFrom());
        }


        /**
         * all ok now send edit information to server
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        editWashingCrushingViewModel.submitEditedWashingCrushingData(
                getActivity(), orderId, lastOrderId, selectedReferencePerson, proDuctIdList, soldFromList,
                EditWashingCrushing.updatedQuantityList, Collections.singletonList("0"), productTitleList, Collections.singletonList("0"),
                oldQuantityList, oldSoldFromList, binding.processingDate.getText().toString(), "0", binding.note.getText().toString(),
                getArguments().getString("destinationStoreId"), selectedOutput,  selectedStore
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() ==500) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), " " + response.getMessage());
                return;
            }
            myDatabaseHelper.deleteAllData();
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();
        });
    }


    public void confirmEditWashingCrushingDialog(FragmentActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to edit this washing & crushing ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unicef_main));//set warning image
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
        /* if (alertDialog.getWindow() != null) {
         *//**
         * for show sliding animation in alert dialog
         *//*
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }*/
        alertDialog.show();
    }

}