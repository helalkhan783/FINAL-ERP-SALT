package com.ismos_salt_erp.view.fragment.production.washingAndCrushing.addNew;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.WashingAndCrushingAdapter;
import com.ismos_salt_erp.databinding.FragmentWashingAndCrushingBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.permission.SharedPreferenceForStore;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WashingAndCrushing extends BaseFragment implements WashingAndCrushingAdapterItemClick {

    public static FragmentWashingAndCrushingBinding binding;

    private MyDatabaseHelper myDatabaseHelper;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private SaleViewModel saleViewModel;

    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    List<SalesRequisitionItems> itemsList;
    WashingAndCrushingAdapter adapter;


    /**
     * For store initial product list
     */
    public static List<SalesRequisitionItems> initProductListResponse;
    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<SalesRequisitionItems> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();

    /**
     * for search
     */
    private List<SalesRequisitionItemsResponse> searchProductLists;
    private List<String> productNameList;
    private ArrayAdapter<String> productArrayAdapter;


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

    private String selectedEnterPrice, selectedStore;


    private static boolean finalTotalCount = false;
    private boolean allOk = true;

   public static SharedPreferenceForStore sharedPreferenceForStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_washing_and_crushing, container, false);
        getDataFromPreviousFragment();
        sharedPreferenceForStore = new SharedPreferenceForStore(getActivity());

        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        initProductListResponse = new ArrayList<>();
        initProductListResponse.clear();

        getLocalDataIfHave();

        binding.toolbar.setClickHandle(() -> {
            sharedPreferenceForStore.deleteData();
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * for set Page Data
         */
        getPageDataFromServer();
        /**
         * for handle total btn click
         */
        binding.setClickHandle(() -> {

            if (selectedStore == null) {
                infoMessage(getActivity().getApplication(), "Please select store");
                return;
            }

            if (itemsList == null || itemsList.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Please search item");

                return;
            }
            binding.itemSearchEt.getText().clear();
            finalTotalCount = true;
            getFinalAllQuantity();//for count updated quantity object

            updatedQuantityProductList.clear();
            updatedQuantityProductList.addAll(itemsList);

            try {
                for (int i = 0; i < itemsList.size(); i++) {
                    String stock = ((TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).getText().toString();
                    if (stock.equals("0")) {
                        infoMessage(getActivity().getApplication(), "Item stock unavailable");
                        return;
                    }
                }
            } catch (Exception e) {
            }
            for (int i = 0; i < updatedQuantityProductList.size(); i++) {
                if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) == 0) {
                    infoMessage(getActivity().getApplication(), "Please enter Quantity");

                    return;
                }
            }

            if (updatedQuantityProductList.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Empty Quantity");
                return;
            }
            if (selectedStore == null) {
                infoMessage(getActivity().getApplication(), "Please select store");
                return;
            }

            initProductListResponse.clear();


            /**
             * validation and save
             */
            validationRecyclerData();
        });

        /**
         * handle search item click
         */
        binding.itemSearchEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (productNameList.get(position).equals(NO_DATA_FOUND)) {
                clearEt();
                return;
            }
            checkStore(selectedStore,getActivity(),getString(R.string.toast_info));//check store select for item search
            clearEt();



            /**
             * save Data to local database
             */
            long rowId = myDatabaseHelper.insertData(
                    searchProductLists.get(position).getProductID(),
                    searchProductLists.get(position).getProductTitle(),
                    "0", searchProductLists.get(position).getUnit(),
                    searchProductLists.get(position).getUnit_name(), "0", "0", "0"
            );

            if (rowId == -1) {
                //Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
            }


            /**
             * now get all local data from db
             */
            Cursor cursor = myDatabaseHelper.displayAllData();
            if (cursor.getCount() == 0) {//means didn't have any data
                Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                return;
            }

            itemsList = new ArrayList<>();
            itemsList.clear();
            while (cursor.moveToNext()) {
                SalesRequisitionItems item = new SalesRequisitionItems();
                item.setProductID(cursor.getString(1));
                item.setProductTitle(cursor.getString(2));
                item.setQuantity(cursor.getString(3));
                item.setUnit(cursor.getString(4));
                item.setUnit_name(cursor.getString(5));
                itemsList.add(item);
            }


            /**
             * Now handle stock  if have selected store
             */

            /**
             * after set store load all product stock data------
             */
            List<String> productIdList = new ArrayList<>();
            /**
             * for handle stock
             */
            if (selectedStore != null) {
                productIdList.clear();
                for (int i = 0; i < itemsList.size(); i++) {
                    productIdList.add(itemsList.get(i).getProductID());
                }
                saleViewModel.getProductStockDataByProductId(getActivity(), productIdList, selectedStore)
                        .observe(getViewLifecycleOwner(), response -> {
                            if (response == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (response.getStatus() != 200) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }


                            try {
                                adapter = new WashingAndCrushingAdapter(getActivity(), itemsList, null, this, response.getLists());
                                binding.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                binding.productList.setAdapter(adapter);
                            } catch (Exception e) {
                            }

                          /*  try {
                                for (int i = 0; i < response.getLists().size(); i++) {
                                    binding.productList.getLayoutManager()
                                            .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);
                                    ((EditText) binding.productList.getLayoutManager().findViewByPosition(i)
                                            .findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                }
                            } catch (Exception e) {
                                Log.d("ERROR", "" + e.getMessage());
                            }*/
                        });
            }


        });

        /**
         * for search
         */
        binding.itemSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.itemSearchEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.itemSearchEt.isPerformingCompletion()) { // selected a product
                    return;
                }
                if (!s.toString().trim().isEmpty() && !isDataFetching) {
                    String currentText = binding.itemSearchEt.getText().toString();
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    } else {
                        //getProfileDetailsByProfileId(currentText, selectedBusinessType);
                        getProductBySearchKey(currentText);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterPrice = enterpriseResponseList.get(position).getStoreID();
                 /***
                 * ok now set store dropdown data by (optional) enterprise id
                 */
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
        binding.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectedEnterPrice == null) {
                    infoMessage(getActivity().getApplication(), "Select Enterprise First !!");
                    return;
                }
               try {
                   selectedStore = storeResponseList.get(position).getStoreID();
                  // sharedPreferenceForStore.saveStoreId(selectedStore);

                   allOk = true;
               }catch (Exception e){}

                /**
                 * Now handle stock  if have selected store
                 */

                /**
                 * after set store load all product stock data------
                 */
                List<String> productIdList = new ArrayList<>();
                /**
                 * for handle stock
                 */
                if (selectedStore != null) {
                    productIdList.clear();
                    if (itemsList == null) {
                        return;
                    }
                    for (int i = 0; i < itemsList.size(); i++) {
                        productIdList.add(itemsList.get(i).getProductID());
                    }
                    saleViewModel.getProductStockDataByProductId(getActivity(), productIdList, selectedStore)
                            .observe(getViewLifecycleOwner(), response -> {
                                if (response == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() != 200) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }

                                for (int i = 0; i < response.getLists().size(); i++) {
                                    try {
                                        binding.productList.getLayoutManager()
                                                .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);

                                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i)
                                                .findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                    } catch (Exception e) {

                                    }
                                }

                            });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void clearEt() {
        binding.itemSearchEt.getText().clear();

    }

    private void validationRecyclerData() {
        List<String> idList = new ArrayList<>();
        idList.clear();
        for (int i = 0; i < updatedQuantityProductList.size(); i++) {
            idList.add(updatedQuantityProductList.get(i).getProductID());
        }

        saleViewModel.getProductStockDataByProductId(getActivity(), idList, selectedStore)
                .observe(getViewLifecycleOwner(), response -> {
                    for (int i = 0; i < response.getLists().size(); i++) {
                        try {
                            if (response.getLists().get(i).getStockQty() == 0) {
                                if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) == 0) {

                                } else {
                                    if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > 0) {
                                        (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
                                        ((TextView) binding.productList.getLayoutManager()
                                                .findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                        allOk = false;
                                        return;
                                    }
                                }

                            }
                            if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > response.getLists().get(i).getStockQty()) {
                                (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
                                ((TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                allOk = false;
                                return;
                            }
                            allOk = true;
                        } catch (Exception e) {
                        }
                    }
                    if (allOk) {//now all ok
                        try {
                            sharedPreferenceForStore.saveStoreId(selectedEnterPrice,selectedStore);


                            Bundle bundle = new Bundle();
                            if (selectedEnterPrice == null) {
                                bundle.putString("selectedEnterpriseId", "");
                            } else {
                                bundle.putString("selectedEnterpriseId", selectedEnterPrice);
                            }
                            if (selectedStore == null) {
                                bundle.putString("selectedStoreId", "");
                            } else {
                                bundle.putString("selectedStoreId", selectedStore);
                            }
                            hideKeyboard(getActivity());
                            Navigation.findNavController(getView()).navigate(R.id.action_washingAndCrushing_to_confirmWashingAndCrushing, bundle);
                        } catch (Exception e) {
                            Log.d("ERROR", "error");
                        }
                    }
                });
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        binding.toolbar.toolbarTitle.setText(getArguments().getString("portion"));
    }

    private void getLocalDataIfHave() {
        /**
         * now get all local data from db
         */
        Cursor cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            // Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }

        itemsList = new ArrayList<>();
        itemsList.clear();
        while (cursor.moveToNext()) {
            SalesRequisitionItems item = new SalesRequisitionItems();
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
            itemsList.add(item);
        }
        /**
         * after set store load all product stock data------
         */
        List<String> productIdList = new ArrayList<>();
        /**
         * for handle stock
         */
        if (selectedStore != null) {
            productIdList.clear();
            for (int i = 0; i < itemsList.size(); i++) {
                productIdList.add(itemsList.get(i).getProductID());
            }
            saleViewModel.getProductStockDataByProductId(getActivity(), productIdList, selectedStore)
                    .observe(getViewLifecycleOwner(), response -> {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() != 200) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }


                        try {
                            adapter = new WashingAndCrushingAdapter(getActivity(), itemsList, null, this, response.getLists());
                            binding.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.productList.setAdapter(adapter);
                        } catch (Exception e) {
                        }

                          /*  try {
                                for (int i = 0; i < response.getLists().size(); i++) {
                                    binding.productList.getLayoutManager()
                                            .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);
                                    ((EditText) binding.productList.getLayoutManager().findViewByPosition(i)
                                            .findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                }
                            } catch (Exception e) {
                                Log.d("ERROR", "" + e.getMessage());
                            }*/

                    });
        }
    }

    private void getProductBySearchKey(String currentText) {
        saleViewModel.getSearchProduct(getActivity(), currentText, Collections.singletonList("736"))//here 736 for ensure this is washing crushing search
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
                    searchProductLists = new ArrayList<>();
                    searchProductLists.clear();

                    productNameList = new ArrayList<>();
                    productNameList.clear();

                    searchProductLists.addAll(response.getItems());

                    for (int i = 0; i < response.getItems().size(); i++) {
                        productNameList.add("" + response.getItems().get(i).getProductTitle());
                    }

                    if (productNameList.isEmpty()) {
                        productNameList.add(NO_DATA_FOUND);
                    }
                    try {
                        productArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, productNameList);
                        binding.itemSearchEt.setAdapter(productArrayAdapter);
                        binding.itemSearchEt.showDropDown();
                        isDataFetching = false;
                    } catch (Exception e) {
                    }
                });

    }

    private void nowSetStoreListByEnterPriseId() {
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

                   try {
                       for (int i = 0; i < response.getEnterprise().size(); i++) {
                           storeNameList.add("" + response.getEnterprise().get(i).getStoreName());
                           if (sharedPreferenceForStore.getStoreId() != null) {
                               if (sharedPreferenceForStore.getStoreId().equals(storeResponseList.get(i).getStoreID())) {
                                   binding.store.setSelection(i);
                               }
                           }

                       }
                   }catch (Exception e){}
                    binding.store.setItem(storeNameList);

                   /* if (storeResponseList.size() == 1) {
                        binding.store.setSelection(0);
                        selectedStore = storeResponseList.get(0).getStoreID();
                    }*/
                });
    }

    private void getPageDataFromServer() {
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
            if (sharedPreferenceForStore.getEnterpriseId() != null){
                if (sharedPreferenceForStore.getEnterpriseId().equals(response.getEnterprise().get(i).getStoreID())){
                    binding.enterPrice.setSelection(i);
                }
            }

        }
        binding.enterPrice.setItem(enterpriseNameList);
        if (enterpriseResponseList.size() == 1) {
            binding.enterPrice.setSelection(0);
            selectedEnterPrice = enterpriseResponseList.get(0).getStoreID();
        }

    }

    public static List<String> getAllQuantity() {
        List<String> paymentLimitAmount = new ArrayList<>();
        try {
            for (int i = 0; i < binding.productList.getChildCount(); i++) {
                try {
                    paymentLimitAmount.add(((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString());
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }

            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        return paymentLimitAmount;
    }


    public List<String> getFinalAllQuantity() {
        List<String> paymentLimitAmount = new ArrayList<>();
        updatedQuantityProductList.clear();
        updatedQuantityList.clear();
        for (int i = 0; i < binding.productList.getChildCount(); i++) {
            try {
                /**
                 * for handle updated quantity object
                 */
                if (finalTotalCount) {
                    String currentQuantity = ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString();
                    if (!currentQuantity.equals("0")) {
                        updatedQuantityProductList.add(itemsList.get(i));//set updated product quantity object
                        updatedQuantityList.add(currentQuantity);
                    }
//                    finalTotalCount= false;
                }
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
        }
        return paymentLimitAmount;
    }


    @Override
    public void removeBtn(int position) {
        allOk = true;//for  handle submit time validation
        int value = myDatabaseHelper.deleteData(itemsList.get(position).getProductID());
        if (value > 0) {
            //Toast.makeText(getContext(), "deleted ", Toast.LENGTH_SHORT).show();
            itemsList.remove(position);
            adapter.notifyItemRemoved(position);
            /**
             * now manage loading time total quantity
             */
            int totalQuantity = 0;
            for (int i = 0; i < itemsList.size(); i++) {
                totalQuantity += Integer.parseInt(itemsList.get(i).getQuantity());
            }
            WashingAndCrushing.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem) {
        allOk = true;//for  handle submit time validation
        Boolean isUpdated = myDatabaseHelper
                .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), "0", "0", "0");

        if (isUpdated) {
            //Toast.makeText(getContext(), "Updated ", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(getContext(), "failed update ", Toast.LENGTH_SHORT).show();
        }


        /**
         * now get all local data from db
         */
        Cursor cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            // Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }

        itemsList.clear();
        while (cursor.moveToNext()) {
            SalesRequisitionItems item = new SalesRequisitionItems();
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
            itemsList.add(item);
        }
    }

    @Override
    public void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem) {
        allOk = true;//for  handle submit time validation
        Boolean isUpdated = myDatabaseHelper
                .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), "0", "0", "0");

        if (isUpdated) {
            //Toast.makeText(getContext(), "Updated ", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(getContext(), "failed update ", Toast.LENGTH_SHORT).show();
        }


        /**
         * now get all local data from db
         */
        Cursor cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            // Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }

        itemsList.clear();
        while (cursor.moveToNext()) {
            SalesRequisitionItems item = new SalesRequisitionItems();
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
            itemsList.add(item);
        }
    }

}