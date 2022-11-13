package com.ismos_salt_erp.view.fragment.production.packating;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.AddNewPackatingAdapter;
import com.ismos_salt_erp.clickHandle.AddNewPackagingToolbarHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewPackatingBinding;
import com.ismos_salt_erp.localDatabase.PackatingDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.PackatingModel;
import com.ismos_salt_erp.serverResponseModel.PacketingList;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PacketingViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewPackating extends BaseFragment implements AddNewPackatingRecyclerClickHandle {
    public static FragmentAddNewPackatingBinding binding;
    private PacketingViewModel packetingViewModel;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private SaleViewModel saleViewModel;
    private PackatingDatabaseHelper packatingDatabaseHelper;
    private boolean isAllOk = true;
    /**
     * for page required Data
     */
    private List<PacketingList> pageInitialData;
    private List<String> pageInitialDataNameList;

    /**
     * For enterprise
     */
    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
    /**
     * for store
     */
    List<EnterpriseList> storeResponseList;
    List<String> storeNameList;
    /**
     * for database presenter
     */
    public static List<PackatingModel> databasepackagingList;


    public static String selectedEnterPrice, selectedStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_packating, container, false);
        packetingViewModel = new ViewModelProvider(this).get(PacketingViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        packatingDatabaseHelper = new PackatingDatabaseHelper(getContext());
        binding.toolbar.toolbarTitle.setText("Cartoning");
        /**
         * set enterprise and store data from  server
         */
        setEnterPriseAndsStoreDataFromServer();
        /**
         *get Page required data from server
         */
        getPageRequiredListDataFromServer();

        binding.toolbar.setClickHandle(new AddNewPackagingToolbarHandle() {
            @Override
            public void back() {
                hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }

            @Override
            public void addMore() {
                addOneMoreDataToList();
            }
        });
        binding.setClickHandle(() -> {
            /**
             * now validation all data and save
             */
            isAllOk = true;
            validationAndSave();
        });

        /**
         * For handle enterprise click
         */
        binding.enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterPrice = enterpriseResponseList.get(position).getStoreID();
                selectedStore = null;
                /**
                 * ok now set store dropdown data by (optional) enterprise id
                 */
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
                    return;
                }
                nowSetStoreListByEnterPriseId();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * handle empty store click handle
         */
        binding.store.setOnEmptySpinnerClickListener(() -> {
            if (selectedEnterPrice == null) {
                infoMessage(getActivity().getApplication(), "Select Enterprise First !!");
            }
        });
        /**
         * for handle store item click
         */
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

        return binding.getRoot();
    }

    private void addInitialProduct() {
        Cursor cursor = packatingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            long rowId = packatingDatabaseHelper.insertData("0", "0", "0", "0", "0");
            if (rowId == -1) {
                Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
                return;
            } else {
              //  Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * now get all local data from db
         */
        Cursor cursor2 = packatingDatabaseHelper.displayAllData();
        if (cursor2.getCount() == 0) {//means didn't have any data
            return;
        }
        databasepackagingList = new ArrayList<>();
        databasepackagingList.clear();


        while (cursor2.moveToNext()) {
            PackatingModel model = new PackatingModel();
            model.setId(cursor2.getInt(0));
            model.setItemId(cursor2.getString(1));
            model.setWeight(cursor2.getString(2));
            model.setQuantity(cursor2.getString(3));
            model.setAvailable(cursor2.getString(4));
            model.setTotal(cursor2.getString(5));
            databasepackagingList.add(model);
        }


        /**
         * now load data to recycler view
         */
        AddNewPackatingAdapter adapter = new AddNewPackatingAdapter(getActivity(), databasepackagingList, pageInitialData, AddNewPackating.this);
        binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productList.setAdapter(adapter);
    }

    private void nowSetStoreListByEnterPriseId() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), selectedEnterPrice)
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

    private void setEnterPriseAndsStoreDataFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        salesRequisitionViewModel.getEnterpriseResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    /**
                     * all Ok now set data to view
                     */
                    setPageData(response);
                });
    }

    private void setPageData(GetEnterpriseResponse response) {

        enterpriseResponseList = new ArrayList<>();
        enterpriseResponseList.clear();
        enterpriseNameList = new ArrayList<>();
        enterpriseNameList.clear();
        enterpriseResponseList.addAll(response.getEnterprise());

        for (int i = 0; i < response.getEnterprise().size(); i++) {
            enterpriseNameList.add("" + response.getEnterprise().get(i).getStoreName());
        }
        binding.enterPrice.setItem(enterpriseNameList);
        if (enterpriseResponseList.size() == 1) {
            binding.enterPrice.setSelection(0);
            selectedEnterPrice = enterpriseResponseList.get(0).getStoreID();
        }

    }

    private void getPageRequiredListDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        packetingViewModel.getPacketingPageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    pageInitialData = new ArrayList<>();
                    pageInitialData.clear();
                    pageInitialData.addAll(response.getItems());

                    pageInitialDataNameList = new ArrayList<>();
                    pageInitialDataNameList.clear();
                    for (int i = 0; i < response.getItems().size(); i++) {
                        pageInitialDataNameList.add(response.getItems().get(i).getProductTitle());
                    }
                    /**
                     * add initial product
                     */
                    addInitialProduct();

                });

    }

    private void addOneMoreDataToList() {

        long rowId = packatingDatabaseHelper.insertData("0", "0", "0", "0", "0");
        if (rowId == -1) {
            Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
        }
        /**
         * now get all local data from db
         */
        Cursor cursor = packatingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        databasepackagingList = new ArrayList<>();
        databasepackagingList.clear();


        while (cursor.moveToNext()) {
            PackatingModel current = new PackatingModel();
            current.setId(cursor.getInt(0));
            current.setItemId(cursor.getString(1));
            current.setWeight(cursor.getString(2));
            current.setQuantity(cursor.getString(3));
            current.setAvailable(cursor.getString(4));
            current.setTotal(cursor.getString(5));
            databasepackagingList.add(current);
        }
        AddNewPackatingAdapter adapter = new AddNewPackatingAdapter(getActivity(), databasepackagingList, pageInitialData, AddNewPackating.this);
        binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productList.setAdapter(adapter);
    }

    @Override
    public void selectItemName(int holderPosition, PacketingList packating, PackatingModel model) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        /**
         * now get current selected product stock available
         */
        packetingViewModel.getProductStockByRefProductId(getActivity(), selectedStore, Collections.singletonList(packating.getRefProductID()))
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(requireActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    String currentAvailable = response.getLists().get(0).getStockQty();
                    ((TextView) binding.productList.getLayoutManager()
                            .findViewByPosition(holderPosition).findViewById(R.id.available)).setText(currentAvailable);


                    int rowId = packatingDatabaseHelper.updateData(
                            String.valueOf(model.getId()), packating.getProductID(), packating.getProductDimensions(), model.getQuantity(), currentAvailable,
                            packating.getProductDimensions());
                    if (rowId == -1) {
                        return;
                    }


                    /**
                     * now get all local data from db
                     */
                    Cursor cursor = packatingDatabaseHelper.displayAllData();
                    if (cursor.getCount() == 0) {//means didn't have any data
                        Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    databasepackagingList = new ArrayList<>();
                    databasepackagingList.clear();


                    while (cursor.moveToNext()) {
                        PackatingModel current = new PackatingModel();
                        current.setId(cursor.getInt(0));
                        current.setItemId(cursor.getString(1));
                        current.setWeight(cursor.getString(2));
                        current.setQuantity(cursor.getString(3));
                        current.setAvailable(cursor.getString(4));
                        current.setTotal(cursor.getString(5));
                        databasepackagingList.add(current);
                    }
                    try {
                        ((TextView) binding.productList.getLayoutManager().findViewByPosition(holderPosition).findViewById(R.id.size))
                                .setText(packating.getProductDimensions());

                        ((TextView) binding.productList.getLayoutManager().findViewByPosition(holderPosition).findViewById(R.id.total))
                                .setText(packating.getProductDimensions());


                    } catch (Exception e) {

                    }
                    Log.d("ERROR", "TEST");

                });
    }

    @Override
    public void changeQuantity(String currentQuantity, int holderPosition, PackatingModel model) {


        try {
            String currentTotal = ((TextView) binding.productList.getLayoutManager().findViewByPosition(holderPosition).findViewById(R.id.size)).getText().toString();


            double total = 0.0;

            total = Double.parseDouble(currentQuantity) * Double.parseDouble(currentTotal);


            ((TextView) binding.productList.getLayoutManager().findViewByPosition(holderPosition).findViewById(R.id.total))
                    .setText(String.valueOf(total));


            if (currentQuantity.isEmpty()) {
                currentQuantity = "1";
            }

            int rowId = packatingDatabaseHelper.updateData(
                    String.valueOf(model.getId()), databasepackagingList.get(holderPosition).getItemId(),
                    databasepackagingList.get(holderPosition).getWeight(), currentQuantity, databasepackagingList.get(holderPosition).getAvailable(),
                    String.valueOf(total));
            if (rowId == -1) {
                return;
            }
            /**
             * now get all local data from db
             */
            Cursor cursor = packatingDatabaseHelper.displayAllData();
            if (cursor.getCount() == 0) {//means didn't have any data
                Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                return;
            }
            databasepackagingList = new ArrayList<>();
            databasepackagingList.clear();


            while (cursor.moveToNext()) {
                PackatingModel current = new PackatingModel();
                current.setId(cursor.getInt(0));
                current.setItemId(cursor.getString(1));
                current.setWeight(cursor.getString(2));
                current.setQuantity(cursor.getString(3));
                current.setAvailable(cursor.getString(4));
                current.setTotal(cursor.getString(5));
                databasepackagingList.add(current);
            }


            /**
             * now set Total quantity
             */
            double currentTotalQuantity = 0.0;
            for (int i = 0; i < databasepackagingList.size(); i++) {
                currentTotalQuantity += Double.parseDouble(databasepackagingList.get(i).getQuantity());
            }

            binding.totalQuantity.setText("Total Quantity: " + currentTotalQuantity);

        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        Log.d("ERROR", "TEST");
    }

    private void validationAndSave() {
        for (int i = 0; i < databasepackagingList.size(); i++) {
            try {
                String currentAvailable = databasepackagingList.get(i).getAvailable();
                String currentTotal = databasepackagingList.get(i).getTotal();

                if (Double.parseDouble(currentTotal) > Double.parseDouble(currentAvailable)) {
                    isAllOk = false;
                    return;
                }
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        }
        if (isAllOk) {
            Bundle bundle = new Bundle();
            bundle.putString("selectedEnterpriseId", selectedEnterPrice);
            bundle.putString("selectedStore", selectedEnterPrice);
            Navigation.findNavController(getView()).navigate(R.id.action_addNewPackating_to_confirmPackating, bundle);
        }
    }
}