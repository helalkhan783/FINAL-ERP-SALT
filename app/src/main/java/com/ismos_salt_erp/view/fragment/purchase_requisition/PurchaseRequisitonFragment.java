package com.ismos_salt_erp.view.fragment.purchase_requisition;

import android.database.Cursor;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentPurchaseRequisitonBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.sale.newSale.ItemClick;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PurchaseRequisitonFragment extends BaseFragment implements ItemClick {
    public static FragmentPurchaseRequisitonBinding binding;
    private SalesRequisitionViewModel salesRequisitionViewModel;

    private static final String NO_DATA_FOUND = "No Data Found";
    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<SalesRequisitionItems> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();

    private MyDatabaseHelper myDatabaseHelper;
    List<SalesRequisitionItemsResponse> productItemsList;
    List<SalesRequisitionItemsResponse> productsList;
    private List<String> salesProductsList;
    private ArrayAdapter<String> productsArrayAdapter;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    List<SalesRequisitionItems> itemsList;
    PurchaseRequisitionAdapterMy adapter;
    private boolean allOk = true;
    public static RecyclerView salesRequisitionProductListRv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_requisiton, container, false);
        binding.toolbar.filterBtn.setVisibility(View.GONE);
        binding.toolbar.toolbarTitle.setText("Purchase Requisition");
        salesRequisitionViewModel = ViewModelProviders.of(this).get(SalesRequisitionViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        salesRequisitionProductListRv = binding.salesRequisitionProductListRv;
        getLocalDataIfHave();

        binding.toolbar.backbtn.setOnClickListener(v -> {
            myDatabaseHelper.deleteAllData();
            getActivity().onBackPressed();
        });
        /**
         * for control search item text change action
         */
        binding.itemSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!binding.itemSearchEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (binding.itemSearchEt.isPerformingCompletion()) {
                    return;
                }
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    String currentSearchItemName = binding.itemSearchEt.getText().toString();
                    /**
                     * now get Data from server
                     */
                    getSearchingDataFromServer(currentSearchItemName);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.itemSearchEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (salesProductsList.get(position).equals(NO_DATA_FOUND)) {
                binding.itemSearchEt.getText().clear();
                return;
            }
            binding.itemSearchEt.getText().clear();


            /**
             * save Data to local database
             */
            long rowId = myDatabaseHelper.insertData(
                    productItemsList.get(position).getProductID(),
                    productItemsList.get(position).getProductTitle(),
                    "0",
                    productItemsList.get(position).getUnit(),
                    productItemsList.get(position).getUnit_name(), "0", "0", "0"
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
                item.setPrice(cursor.getString(6));
                item.setDiscount(cursor.getString(7));
                item.setTotalPrice(cursor.getString(8));
                itemsList.add(item);
            }
            updatedQuantityProductList.clear();
            updatedQuantityProductList.addAll(itemsList);

            adapter = new PurchaseRequisitionAdapterMy(getActivity(), itemsList, null, this);
            binding.salesRequisitionProductListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.salesRequisitionProductListRv.setAdapter(adapter);

        });

        binding.totalSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_purchaseRequisitonFragment_to_confrimPurchaseRequisitionFragment);
            }
        });

        return binding.getRoot();
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
            item.setPrice(cursor.getString(6));
            item.setDiscount(cursor.getString(7));
            item.setTotalPrice(cursor.getString(8));
            itemsList.add(item);
        }
        updatedQuantityProductList.clear();
        updatedQuantityProductList.addAll(itemsList);

        adapter = new PurchaseRequisitionAdapterMy(getActivity(), itemsList, null, this);
        salesRequisitionProductListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        salesRequisitionProductListRv.setAdapter(adapter);

    }


    /**
     * for get all price from the current recyclerview
     *
     * @return
     */
    public static List<String> getAllPrice() {
        List<String> paymentLimitAmount = new ArrayList<>();
        for (int i = 0; i < salesRequisitionProductListRv.getChildCount(); i++) {


            try {
                paymentLimitAmount.add(((AutoCompleteTextView) salesRequisitionProductListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.priceEt)).getText().toString());
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
            //paymentLimitAmount.add(SalesRequisitionProductsAdapter.MyHolder().priceEt.getText().toString());

        }
        return paymentLimitAmount;
    }

    /**
     * for get all quantity from the recyclerview
     *
     * @return
     */
    public static List<String> getAllQuantity() {
        List<String> paymentLimitAmount = new ArrayList<>();
        try {
            for (int i = 0; i < salesRequisitionProductListRv.getChildCount(); i++) {
                paymentLimitAmount.add(((EditText) salesRequisitionProductListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getLocalizedMessage());
        }
        return paymentLimitAmount;
    }

    /**
     * after get all price and quantity count the total price in this method
     * will call method for the current recyclerview adapter
     *
     * @return
     */
    public static double getTotalPrice() {
        double totalPrice = 0;
        List<String> allPrice = new ArrayList<>();
        allPrice.clear();
        List<String> allQuantity = new ArrayList<>();
        allQuantity.clear();
        allPrice = getAllPrice();
        allQuantity = getAllQuantity();
        try {
            for (int i = 0; i < allPrice.size(); i++) {
                double currentPrice = Double.parseDouble(allPrice.get(i));
                double currentQuantity = Double.parseDouble(allQuantity.get(i));
                totalPrice += currentPrice * currentQuantity;
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getLocalizedMessage());
        }
        Log.d("TOTAL", String.valueOf(totalPrice));
        return totalPrice;
    }

    private void getSearchingDataFromServer(String currentText) {

        isDataFetching = true;
        /**
         * set categoryS id static as like the provided this API so make a list of string
         */
        List<String> categoryS = new ArrayList<>();
        categoryS.addAll(Arrays.asList("736", "737", "738", "739", "740", "741", "742", "743"));

        salesRequisitionViewModel.apiCallForSearchSalesRequisitionItemByName(getActivity(), categoryS, currentText);

        salesRequisitionViewModel.getSearchSalesRequisitionItemByName().observe(getViewLifecycleOwner(), response -> {

            productItemsList = new ArrayList<>();
            productItemsList.clear();
            productItemsList.addAll(response.getItems());
            salesProductsList = new ArrayList<>();
            productsList = response.getItems();

            //  productsList.forEach(products -> salesProductsList.add(products.getProductTitle()));//for api level 24 or above

            for (int i = 0; i < productsList.size(); i++) {
                salesProductsList.add(productsList.get(i).getProductTitle());
            }

            if (salesProductsList.isEmpty()) { // show message in the item if the list is empty
                salesProductsList.add(NO_DATA_FOUND);
            }
            productsArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.filter_model, R.id.customerNameModel, salesProductsList);
            binding.itemSearchEt.setAdapter(productsArrayAdapter);
            binding.itemSearchEt.showDropDown();
            isDataFetching = false;
        });

    }

    @Override
    public void removeBtn(int position) {
        allOk = true;//for  handle submit time validation

        if (position > itemsList.size() - 1) {
            adapter.notifyItemRangeRemoved(0, itemsList.size());
            return;
        }
        int value = myDatabaseHelper.deleteData(itemsList.get(position).getProductID());
        if (value > 0) {
            itemsList.remove(position);
            binding.salesRequisitionProductListRv.getAdapter().notifyItemRemoved(position);
            /**
             * now manage loading time total quantity
             */
            int totalQuantity = 0;
            for (int i = 0; i < itemsList.size(); i++) {
                totalQuantity += Integer.parseInt(itemsList.get(i).getQuantity());
            }
            binding.totalSubmitBtn.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPrice) {
        allOk = true;//for  handle submit time validation
        updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, totalPrice);
    }


    @Override
    public void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem) {
        allOk = true;//for  handle submit time validation
        updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), "0", "0", "0");

    }


    private void updateData(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        Boolean isUpdated = myDatabaseHelper.updateData(productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice);

        if (isUpdated) {
        } else {
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
            item.setPrice(cursor.getString(6));
            item.setDiscount(cursor.getString(7));
            item.setTotalPrice(cursor.getString(8));
            itemsList.add(item);
        }
        updatedQuantityProductList.clear();
        updatedQuantityProductList.addAll(itemsList);

    }

}