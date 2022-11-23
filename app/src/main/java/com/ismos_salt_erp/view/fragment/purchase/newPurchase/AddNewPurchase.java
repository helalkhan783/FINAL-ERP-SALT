package com.ismos_salt_erp.view.fragment.purchase.newPurchase;

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
import com.ismos_salt_erp.adapter.NewPurchaseProductListAdapter;
import com.ismos_salt_erp.adapter.custom_adapte.AddnewSaleCustomAdapter;
import com.ismos_salt_erp.databinding.FragmentAddNewPurchase2Binding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.permission.SharedPreferenceForStore;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.sale.newSale.ItemClickOne;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddNewPurchase extends BaseFragment implements ItemClickOne {
    public static FragmentAddNewPurchase2Binding binding;
    public static boolean isOk;
    public static List<SalesRequisitionItems> list;
    private boolean ifAddAllDataToLoca = false;
    private MyDatabaseHelper myDatabaseHelper;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private SaleViewModel saleViewModel;

    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    List<SalesRequisitionItems> itemsList = new ArrayList<>();
    NewPurchaseProductListAdapter adapter;
    Cursor cursor;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_purchase2, container, false);
        binding.toolbar.toolbarTitle.setText("Add New Purchase");

        binding.toolbar.totalQtyTv.setVisibility(View.VISIBLE);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        initProductListResponse = new ArrayList<>();
        sharedPreferenceForStore = new SharedPreferenceForStore(getActivity());

        initProductListResponse.clear();

        getLocalDataIfHave();

        binding.toolbar.setClickHandle(() -> {
            sharedPreferenceForStore.deleteData();
            myDatabaseHelper.deleteAllData();
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


            if (updatedQuantityProductList.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Empty Quantity");
                return;
            }
            if (selectedStore == null) {
                infoMessage(getActivity().getApplication(), "Please select store");
                return;
            }
            try {
                for (int i = 0; i < itemsList.size(); i++) {

                    String stock = ((TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).getText().toString();
                    String typeQty = ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString();
                   /* if (Double.parseDouble(stock) < Double.parseDouble(typeQty) || typeQty.isEmpty()) {
                        //   infoMessage(getActivity().getApplication(), "Item stock unavailable");
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).setError("Unavailable");
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).requestFocus();

                        return;
                    }
*/

                    if (Integer.parseInt(itemsList.get(i).getQuantity()) == 0) {
                        //    infoMessage(getActivity().getApplication(), "Please enter quantity");
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).setError("Enter quantity");
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).requestFocus();
                        return;
                    }
                    if (Integer.parseInt(itemsList.get(i).getPrice()) == 0) {
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.priceEt)).setError("Enter price");
                        ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.priceEt)).requestFocus();
                        //infoMessage(getActivity().getApplication(), "Please enter quantity");
                        //infoMessage(getActivity().getApplication(), "Please enter price");
                        return;
                    }
                }
            } catch (Exception e) {
            }
            initProductListResponse.clear();


            /**
             * validation and save
             */
            validationRecyclerData();
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


                String productySearchKey = binding.itemSearchEt.getText().toString().trim();
                if (productySearchKey.isEmpty()) {
                    productySearchKey = "new";
                }
                getProductBySearchKey(productySearchKey);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getProductBySearchKey("new");

        binding.enterPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterPrice = enterpriseResponseList.get(position).getStoreID();
                selectedStore = null;
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
                selectedStore = storeResponseList.get(position).getStoreID();
                binding.store.setErrorText("");
                binding.store.setEnableErrorLabel(false);
                allOk = true;

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
                                 //   errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() != 200) {
                                //    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }

                                for (int i = 0; i < response.getLists().size(); i++) {
                                    try {
                                        binding.productList.getLayoutManager()
                                                .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);
                                        ((TextView) binding.productList.getLayoutManager().findViewByPosition(i)
                                                .findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + "");

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
        if (selectedStore == null) {
            binding.store.setEnableErrorLabel(true);
            binding.store.setErrorText("Empty");
            return;
        }


        List<String> idList = new ArrayList<>();
        idList.clear();
        for (int i = 0; i < updatedQuantityProductList.size(); i++) {
            idList.add(updatedQuantityProductList.get(i).getProductID());
        }

        saleViewModel.getProductStockDataByProductId(getActivity(), idList, selectedStore)
                .observe(getViewLifecycleOwner(), response -> {
                    /* for (int i = 0; i < response.getLists().size(); i++) {
                        if (response.getLists().get(i).getStockQty() == 0) {
                            if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) == 0) {

                            } else {
                                if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > 0) {
                                    (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
                                    ((EditText) binding.productList.getLayoutManager()
                                            .findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                                    allOk = false;
                                    return;
                                }
                            }

                        }
                        if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > response.getLists().get(i).getStockQty()) {
                            (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
                            ((EditText) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + " Available");
                            allOk = false;
                            return;
                        }
                        allOk = true;
                    }*/
                    // if (allOk) {//now all ok


                    try {
                        sharedPreferenceForStore.saveStoreId(selectedEnterPrice, selectedStore);

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
                        Navigation.findNavController(getView()).navigate(R.id.action_addNewPurchase2_to_confirmPurchase, bundle);
                    } catch (Exception e) {
                        Log.d("ERROR", "error");
                    }
                    // }
                });
    }

    private void getLocalDataIfHave() {
        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
        setDataToView();
    }

    private void setDataToView() {
        double totalQty = 0.0, totalPrice = 0.0;
        for (int i = 0; i < list.size(); i++) {
            totalQty = totalQty + Double.parseDouble(list.get(i).getQuantity());
            totalPrice = totalPrice + Double.parseDouble(list.get(i).getTotalPrice());
        }
        binding.toolbar.totalQtyTv.setText("" + totalQty);
        binding.totalQuantity.setText("" + DataModify.addFourDigit(String.valueOf(totalPrice)) + "  TK");
        ifAddAllDataToLoca = true;
    }

    private void getProductBySearchKey(String currentText) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        saleViewModel.getSearchProduct(getActivity(), currentText, Arrays.asList("736", "737", "738", "739", "740", "741", "742", "743"))
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                   //     errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                    //    errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    adapter = new NewPurchaseProductListAdapter(getActivity(), response.getItems(),AddNewPurchase.this  );
                    binding.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.productList.setAdapter(adapter);


//
//
//
//                    isDataFetching = true;
//                    searchProductLists = new ArrayList<>();
//                    searchProductLists.clear();
//
//                    productNameList = new ArrayList<>();
//                    productNameList.clear();
//
//                    searchProductLists.addAll(response.getItems());
//
//                    for (int i = 0; i < response.getItems().size(); i++) {
//                        productNameList.add("" + response.getItems().get(i).getProductTitle());
//                    }
//
//                    if (productNameList.isEmpty()) {
//                        productNameList.add(NO_DATA_FOUND);
//                    }
//                    productArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, productNameList);
//                    binding.itemSearchEt.setAdapter(productArrayAdapter);
//                    binding.itemSearchEt.showDropDown();
//                    isDataFetching = false;
                });

    }

    private void nowSetStoreListByEnterPriseId() {
        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), selectedEnterPrice)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                //        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                   //     errorMessage(getActivity().getApplication(), "Something Wrong");
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
                    } catch (Exception e) {
                    }

                    binding.store.setItem(storeNameList);

                    if (storeResponseList.size() == 1) {
                        binding.store.setSelection(0);
                        selectedStore = storeResponseList.get(0).getStoreID();
                    }
                });
    }

    private void getPageDataFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        salesRequisitionViewModel.getEnterpriseResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                  //      errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                    //    errorMessage(getActivity().getApplication(), "Something Wrong");
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
            if (sharedPreferenceForStore.getEnterpriseId() != null) {
                if (sharedPreferenceForStore.getEnterpriseId().equals(response.getEnterprise().get(i).getStoreID())) {
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
                        updatedQuantityProductList.add(initProductListResponse.get(i));//set updated product quantity object
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
    public void insertQuantity(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {
        double totalPriceP = 0.0;
        totalPriceP = Double.parseDouble(quantity) * Double.parseDouble(price);
        list = new ArrayList<>();
        list.clear();
        //  now get all local data from db
        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            inserData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, String.valueOf(totalPriceP));
            isOk = true;
            return;
        }
        getDatFromLocal();


        inserData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, String.valueOf(totalPriceP));


    }



    @Override
    public void update(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {
        double totalPriceP = 0.0;
        totalPriceP = Double.parseDouble(quantity) * Double.parseDouble(price);
        //  getDatFromLocal();
        updateDataToLocal(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, String.valueOf(totalPriceP));

    }
    private void updateDataToLocal(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        Boolean isUpdated = myDatabaseHelper
                .updateData(productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice);

        if (isUpdated) {
            //  Toast.makeText(getContext(), "Updated ", Toast.LENGTH_SHORT).show();
        } else {
            //  Toast.makeText(getContext(), "failed update ", Toast.LENGTH_SHORT).show();
        }
        getDatFromLocal();

    }

    @Override
    public void updateConfirm(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPric, String productId) {

    }


    private void inserData(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        long rowId = myDatabaseHelper.insertData(
                productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice
        );

        if (rowId == -1) {
            //   Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
        } else {
            //   Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
        }
        getDatFromLocal();


    }
    private void getDatFromLocal() {
        list.clear();
        Cursor cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
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
            list.add(item);
            itemsList.add(item);
        }


        if (list != null || !list.isEmpty()) {
            AddnewSaleCustomAdapter.isOk = false;
        }

        setDataToView();
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
            AddNewPurchase.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
//        myDatabaseHelper.deleteAllData();
    }


}