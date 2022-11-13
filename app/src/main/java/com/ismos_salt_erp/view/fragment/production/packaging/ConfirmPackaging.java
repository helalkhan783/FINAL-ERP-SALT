package com.ismos_salt_erp.view.fragment.production.packaging;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ConfirmPackagingClickHandle;
import com.ismos_salt_erp.databinding.FragmentConfirmPackagingBinding;
import com.ismos_salt_erp.localDatabase.MyPackagingDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.CustomerResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.DueCollectionViewModel;
import com.ismos_salt_erp.viewModel.PackagingViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ConfirmPackaging extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentConfirmPackagingBinding binding;
    private PackagingViewModel packagingViewModel;
    private DueCollectionViewModel dueCollectionViewModel;
    private MyPackagingDatabaseHelper myPackagingDatabaseHelper;
    public static int isBack = 0;


    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    private ArrayAdapter<String> customerNameAdapter;


    /**
     * for search Reference  person
     */
    private List<CustomerResponse> referencePersonResponseList;
    private List<String> referencePersonNameList;


    private String selectedReferencePerson, selectedStore,selectedEnterPrice, selectedPackagingId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_packaging, container, false);
        packagingViewModel = new ViewModelProvider(this).get(PackagingViewModel.class);
        dueCollectionViewModel = new ViewModelProvider(this).get(DueCollectionViewModel.class);
        myPackagingDatabaseHelper = new MyPackagingDatabaseHelper(getContext());
        binding.toolbar.toolbarTitle.setText("Confirm Packaging");
        binding.toolbar.setClickHandle(() -> {
            myPackagingDatabaseHelper.deleteAllData();
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        getDataFromPreviousFragment();

        /**
         * Now get Page Data From Server
         */
        getPageDataFromServer();


        binding.setClickHandle(new ConfirmPackagingClickHandle() {
            @Override
            public void submit() {
                validationAndSave();
            }

            @Override
            public void dateBtn() {
                showDatePickerDialog();
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

    private void validationAndSave() {
        if (selectedStore == null) {
            infoMessage(getActivity().getApplication(), "Store selection missing");
            return;
        }
        if (selectedReferencePerson == null) {
            binding.referencePerson.setError("Empty");
            binding.referencePerson.requestFocus();
            return;
        }
        if (binding.note.getText().toString().isEmpty()) {
            binding.note.setError("Empty");
            binding.note.requestFocus();
            return;
        }
        List<String> itemIdList = new ArrayList<>();
        List<String> convertedIdList = new ArrayList<>();
        List<String> weightList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> packagingNotes = new ArrayList<>();
        List<String> totalWeightList = new ArrayList<>();

        List<String> packedProductList = new ArrayList<>();//will add all pkt product id here

        for (int i = 0; i < AddNewPackaging.packagingDatabaseModelList.size(); i++) {
            if (!(AddNewPackaging.packagingDatabaseModelList.get(i).getItemId().equals("0"))
                    && !(AddNewPackaging.packagingDatabaseModelList.get(i).getPackedId().equals("0"))) {
                itemIdList.add(AddNewPackaging.packagingDatabaseModelList.get(i).getItemId());
                convertedIdList.add(AddNewPackaging.packagingDatabaseModelList.get(i).getPackedId());
                weightList.add(AddNewPackaging.packagingDatabaseModelList.get(i).getWeight());
                quantityList.add(AddNewPackaging.packagingDatabaseModelList.get(i).getQuantity());
                packedProductList.add("");
                if (AddNewPackaging.packagingDatabaseModelList.get(i).getNote() == null || AddNewPackaging.packagingDatabaseModelList.get(i).getNote().isEmpty()) {
                    packagingNotes.add("");
                } else {
                    packagingNotes.add(AddNewPackaging.packagingDatabaseModelList.get(i).getNote());
                }
                try {
                    totalWeightList.add(((TextView) AddNewPackaging.binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.totalWeight)).getText().toString());
                } catch (Exception e) {
                    Log.d("ERROR", "ERROR");
                }
            }
        }
        packagingViewModel.confirmPackaging(
                getActivity(), selectedStore, binding.note.getText().toString(), itemIdList, convertedIdList, weightList, quantityList, packedProductList,
                totalWeightList, packagingNotes, selectedPackagingId, binding.processingDate.getText().toString(), selectedReferencePerson,selectedEnterPrice)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    hideKeyboard(getActivity());
                    successMessage(getActivity().getApplication(), "" + response.getMessage());
                    myPackagingDatabaseHelper.deleteAllData();
                    AddNewPackaging.packagingDatabaseModelList.clear();
                    isBack = 1;
                    getActivity().onBackPressed();
                });


    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        packagingViewModel.getNextPackagingId(getActivity())
                .observe(getViewLifecycleOwner(), nextPackagingId -> {
                    binding.processNo.setText(String.valueOf(nextPackagingId.getPackagingID()));
                    selectedPackagingId = String.valueOf(nextPackagingId.getPackagingID());
                });
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedStore = getArguments().getString("selectedStoreId");
        selectedEnterPrice = getArguments().getString("selectedEnterPrice");


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

}