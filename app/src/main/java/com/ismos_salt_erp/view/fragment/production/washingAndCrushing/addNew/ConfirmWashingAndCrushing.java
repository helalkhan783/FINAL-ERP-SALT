package com.ismos_salt_erp.view.fragment.production.washingAndCrushing.addNew;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ConfirmNewSaleSelectedProductListAdapter;
import com.ismos_salt_erp.clickHandle.ConfirmWashingAndCrushingClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmWashingAndCrushingBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.ProductionOutputList;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DiscountViewModel;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
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

public class ConfirmWashingAndCrushing extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentConfirmWashingAndCrushingBinding binding;
    private DueCollectionViewModel dueCollectionViewModel;
    private MyDatabaseHelper myDatabaseHelper;

    private WashingViewModel washingViewModel;
    private DiscountViewModel discountViewModel;
    private SaleViewModel saleViewModel;
    /**
     * Store previous fragment Data
     */
    private String selectedEnterpriseId, selectedStoreId;
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


    private String selectedReferencePerson, selectedStore, selectedOutput;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_washing_and_crushing, container, false);
        binding.toolbar.toolbarTitle.setText("Confirm Washing & Crushing");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        washingViewModel = new ViewModelProvider(this).get(WashingViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
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
        binding.setClickHandle(new ConfirmWashingAndCrushingClickHandle() {
            @Override
            public void save() {
                hideKeyboard(getActivity());
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
                    return;
                }

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


                confirmAddNewWashingCrushingDialog(getActivity());
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

    private void validationAndSave() {


/**
 * for set updated productIdList,unitList
 */
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        for (int i = 0; i < WashingAndCrushing.updatedQuantityProductList.size(); i++) {
            proDuctIdList.add(WashingAndCrushing.updatedQuantityProductList.get(i).getProductID());
            unitList.add(WashingAndCrushing.updatedQuantityProductList.get(i).getUnit());
            productTitleList.add(WashingAndCrushing.updatedQuantityProductList.get(i).getProductTitle());
        }


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        washingViewModel.addNewWashingAndCrushing(
                getActivity(), selectedEnterpriseId, lastOrderId, selectedReferencePerson, proDuctIdList, Collections.singletonList(selectedStoreId),
                WashingAndCrushing.updatedQuantityList, unitList, productTitleList, binding.note.getText().toString(),
                selectedStore, selectedOutput, "25", binding.processingDate.getText().toString()).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
            @Override
            public void onChanged(DuePaymentResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something Wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), " " + response.getMessage());
                    return;
                }
                myDatabaseHelper.deleteAllData();
                WashingAndCrushing.sharedPreferenceForStore.deleteData();
                successMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
            }
        });

    }

    private void getReferencePersonBySearchKey(String currentText) {
        /**
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
                    }try {

                        productionOutputLists = new ArrayList<>();
                        productionOutputLists.clear();
                        productionOutputNameLists = new ArrayList<>();
                        productionOutputNameLists.clear();
                        productionOutputLists.addAll(response.getItems());

                        for (int i = 0; i < response.getItems().size(); i++) {
                            productionOutputNameLists.add("" + response.getItems().get(i).getProductTitle());
                        }
                        binding.output.setItem(productionOutputNameLists);

                    }catch (Exception e){}
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
                    }
                    binding.store.setItem(storeNameList);

                    if (storeResponseList.size() == 1) {
                        binding.store.setSelection(0);
                        selectedStore = storeResponseList.get(0).getStoreID();
                    }
                });
    }

    private void getDataFromPreviousFragment() {
      try {
          assert getArguments() != null;
          selectedEnterpriseId = getArguments().getString("selectedEnterpriseId");
          selectedStoreId = getArguments().getString("selectedStoreId");
          /**
           * set current date will send input from user
           */
          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
          Date date = new Date(System.currentTimeMillis());
          //System.out.println(formatter.format(date));
          String currentDate = formatter.format(date);
          /*System.out.println(dtf.format(now));*/
          binding.processingDate.setText(currentDate);
      }catch (Exception e){}
    }

    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(WashingAndCrushing.updatedQuantityProductList);//get static data from (WashingAndCrushing) Fragment
        /**
         * set set selected data to recyclerview
         * here selected data adapter code and below (ConfirmNewSaleSelectedProductListAdapter) adapter code are same that's the reason i use it
         */
       try {
           ConfirmNewSaleSelectedProductListAdapter adapter = new ConfirmNewSaleSelectedProductListAdapter(getActivity(), selectedItem);
           binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
           binding.selectedProductsRv.setAdapter(adapter);
       }catch (Exception e){}
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


    public void confirmAddNewWashingCrushingDialog(FragmentActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to confirm this Washing & Crushing ?");//set warning title
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