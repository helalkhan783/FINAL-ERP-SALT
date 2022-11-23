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
     public static boolean isOk = false;
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

        toolbarTitle = binding.toolbar.toolbarTitle;
        binding.toolbar.totalQtyTv.setVisibility(View.VISIBLE);
        getLocalDataIfHave();
        binding.toolbar.setClickHandle(() -> {
            sharedPreferenceForStore.deleteData();
            myDatabaseHelper.deleteAllData();
             cursor = myDatabaseHelper.displayAllData();
            if (cursor.getCount() != 0) {//means didn't have any data
                if (list != null || list.isEmpty()) {
                    list.clear();
                }
            }
            getActivity().onBackPressed();
        });
        binding.itemSearchEt.setText("");

        getPageDataFromServer();

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

                List<String> productIdList = new ArrayList<>();

                if (selectedStore != null) {
                    productIdList.clear();
                    if (list == null) {
                        return;
                    }

                    for (int i = 0; i < list.size(); i++) {
                        productIdList.add(list.get(i).getProductID());
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
                         return;
                    }
                    if (response.getStatus() != 200) {
                         return;
                    }
                    adapter1 = new AddnewSaleCustomAdapter(getActivity(), response.getItems(), AddNewSale.this);
                    binding.productList.setAdapter(adapter1);
                    binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));


                });

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
    public void removeBtn(int i) {

    }


    private void inserData(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        myDatabaseHelper.insertData(
                productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice
        );

        getDatFromLocal();


    }


    private void getDatFromLocal() {
        list.clear();
        list.addAll(myDatabaseHelper.getDataFromLoca());




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