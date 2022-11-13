package com.ismos_salt_erp.view.fragment.sale.newSale;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.NewSaleProductListAdapter;
import com.ismos_salt_erp.adapter.custom_adapte.AddnewSaleCustomAdapter;
import com.ismos_salt_erp.databinding.FragmentAddNewSaleBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.permission.SharedPreferenceForStore;
import com.ismos_salt_erp.serverResponseModel.CashBookList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.filter.FilterClass;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddNewSale extends BaseFragment implements ItemClickOne {
    public static FragmentAddNewSaleBinding binding;
    private MyDatabaseHelper myDatabaseHelper;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private SaleViewModel saleViewModel;
    List<SalesRequisitionItems> itemsList = new ArrayList<>();
    public static boolean isOk = false;
    public static List<SalesRequisitionItems> initProductListResponse;
    private List<SalesRequisitionItemsResponse> searchProductLists;
    AddnewSaleCustomAdapter adapter1;

    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;

    List<EnterpriseList> storeResponseList;
    List<String> storeNameList;
    private String selectedEnterPrice, selectedStore;
    public static SharedPreferenceForStore sharedPreferenceForStore;
    private boolean ifAddAllDataToLoca = false;
    public static List<SalesRequisitionItems> list;
    public TextView toolbarTitle;
    Cursor cursor;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_sale, container, false);
        binding.toolbar.toolbarTitle.setText("Add New Sale");
        sharedPreferenceForStore = new SharedPreferenceForStore(getActivity());

        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        initProductListResponse = new ArrayList<>();
        initProductListResponse.clear();
        toolbarTitle = binding.toolbar.toolbarTitle;
        binding.toolbar.totalQtyTv.setVisibility(View.VISIBLE);
        getLocalDataIfHave();
        binding.toolbar.setClickHandle(() -> {
            sharedPreferenceForStore.deleteData();
            myDatabaseHelper.deleteAllData();
            hideKeyboard(getActivity());
            cursor = myDatabaseHelper.displayAllData();
            if (cursor.getCount() != 0) {//means didn't have any data
                if (list != null || list.isEmpty()) {
                    list.clear();
                }
            }

            getActivity().onBackPressed();
        });
        binding.itemSearchEt.setText("");


        /**
         * for set Page Data
         */
        getPageDataFromServer();
        /**
         * for handle total btn click
         */
        binding.setClickHandle(() -> {
            if (ifAddAllDataToLoca == false) {
                return;
            }
            if (selectedStore == null) {
                infoMessage(getActivity().getApplication(), "Please select store");
                return;
            }
            if (list == null || list.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Please select item");
                return;
            }

            /**
             * validation and save
             */
            validationRecyclerData();
        });

        HandleClick();
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String productySearchKey = binding.searchEt.getText().toString().trim();
                if (productySearchKey.isEmpty()){
                    productySearchKey = "new";
                }
                getProductBySearchKey(productySearchKey);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getProductBySearchKey("new");
      //  filterData();

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
                //  sharedPreferenceForStore.saveStoreId(selectedStore);
                binding.store.setErrorText("");
                binding.store.setEnableErrorLabel(false);



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
                    try {
                        for (int i = 0; i < itemsList.size(); i++) {
                            productIdList.add(itemsList.get(i).getProductID());
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                    saleViewModel.getProductStockDataByProductId(getActivity(), productIdList, selectedStore)
                            .observe(getViewLifecycleOwner(), response -> {
                                if (response == null) {
                                    //  errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() != 200) {
                                    //   errorMessage(getActivity().getApplication(), "Something Wrong");
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

    private void HandleClick() {
    }

    private void filterData() {
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
                query = query.toString().toLowerCase();

                if (searchProductLists == null) {
                    Toast.makeText(getContext(), "Empty list", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<SalesRequisitionItemsResponse> filteredList = FilterClass.productFilter(searchProductLists, query);
                if (filteredList.isEmpty()) {
                    binding.productList.setVisibility(View.GONE);
                    binding.jkl.setVisibility(View.GONE);
                    binding.dataNoFound.setVisibility(View.VISIBLE);
                    return;
                }
                binding.productList.setVisibility(View.VISIBLE);
                binding.jkl.setVisibility(View.VISIBLE);
                binding.dataNoFound.setVisibility(View.GONE);
               // setDataToRv(filteredList);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void validationRecyclerData() {
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
            Navigation.findNavController(getView()).navigate(R.id.action_addNewSale_to_confirmNewSale, bundle);
        } catch (Exception e) {
            Log.d("ERROR", "error");
        }


//        saleViewModel.getProductStockDataByProductId(getActivity(), idList, selectedStore)
//                .observe(getViewLifecycleOwner(), response -> {
//                    try {
//                        for (int i = 0; i < response.getLists().size(); i++) {
//                            if (response.getLists().get(i).getStockQty() == 0) {
//                                if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) == 0) {
//                                } else {
//                                    if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > 0) {
//                                        (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
//                                        ((TextView) binding.productList.getLayoutManager()
//                                                .findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + "");
//                                        allOk = false;
//                                        return;
//                                    }
//                                }
//
//                            }
//                            if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) > response.getLists().get(i).getStockQty()) {
//                                (binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setVisibility(View.VISIBLE);
//                                ((TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.stock)).setText(response.getLists().get(i).getStockQty() + "");
//                                allOk = false;
//                                return;
//                            }
//                            allOk = true;
//                        }
//
//                    } catch (Exception e) {
//                    }
//                    if (allOk) {//now all ok
//                        try {
//                            sharedPreferenceForStore.saveStoreId(selectedEnterPrice, selectedStore);
//                            Bundle bundle = new Bundle();
//                            if (selectedEnterPrice == null) {
//                                bundle.putString("selectedEnterpriseId", "");
//                            } else {
//                                bundle.putString("selectedEnterpriseId", selectedEnterPrice);
//                            }
//                            if (selectedStore == null) {
//                                bundle.putString("selectedStoreId", "");
//                            } else {
//                                bundle.putString("selectedStoreId", selectedStore);
//                            }
//                            hideKeyboard(getActivity());
//                            Navigation.findNavController(getView()).navigate(R.id.action_addNewSale_to_confirmNewSale, bundle);
//                        } catch (Exception e) {
//                            Log.d("ERROR", "error");
//                        }
//                    }
//                });
    }

    private void getLocalDataIfHave() {
        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
        setDataToView();


    }


    private void getProductBySearchKey(String currentText) {
        saleViewModel.getSearchProduct(getActivity(), currentText, Arrays.asList("736", "737", "738", "739", "740", "741"))
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        //       errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        //    errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    searchProductLists = new ArrayList<>();
                    searchProductLists.clear();
                    searchProductLists.addAll(response.getItems());
                    // here start customise
                    setDataToRv(searchProductLists);

                });

    }

    private void setDataToRv(List<SalesRequisitionItemsResponse> searchProductLists) {
        adapter1 = new AddnewSaleCustomAdapter(getActivity(), searchProductLists, AddNewSale.this);
        binding.productList.setAdapter(adapter1);
        binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void nowSetStoreListByEnterPriseId() {
        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), selectedEnterPrice)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        //   errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        //  errorMessage(getActivity().getApplication(), "Something Wrong");
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
                                if (sharedPreferenceForStore.getStoreId().equals(response.getEnterprise().get(i).getStoreID())) {
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
                        //    errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        //   errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

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


    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPrice, String productId) {


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

    @Override
    public void updateConfirm(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPric, String productId) {
//use for confirm page
    }

    @Override
    public void removeBtn(int position) {

    }


    private void inserData(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        myDatabaseHelper.insertData(
                productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice
        );

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


}