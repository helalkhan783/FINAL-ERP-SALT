package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.sale.newSale.ItemClick;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSaleFragment extends BaseFragment implements ItemClick {//this is sales Requisition fragment

    private static final String NO_DATA_FOUND = "No Data Found";
    private List<String> salesProductsList;

    private SalesRequisitionViewModel salesRequisitionViewModel;
    List<SalesRequisitionItemsResponse> productItemsList;
    List<SalesRequisitionItemsResponse> productsList;
    private ArrayAdapter<String> productsArrayAdapter;
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    View view;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.itemSearchEt)
    AutoCompleteTextView itemSearchEt;
    SaleRequisitionAdapterMy adapter;


    public static RecyclerView salesRequisitionProductListRv;
    @SuppressLint("StaticFieldLeak")
    public static Button totalSubmitBtn;
    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<SalesRequisitionItems> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();

    // here
    private MyDatabaseHelper myDatabaseHelper;
    List<SalesRequisitionItems> itemsList;
    private boolean allOk = true;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_sale, container, false);
        ButterKnife.bind(this, view);
        totalSubmitBtn = view.findViewById(R.id.totalSubmitBtn);
        salesRequisitionProductListRv = view.findViewById(R.id.salesRequisitionProductListRv);
        salesRequisitionViewModel = ViewModelProviders.of(this).get(SalesRequisitionViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        context =getActivity();
        getDataFromPreviousFragment();
        getLocalDataIfHave();

        itemSearchEt.setOnItemClickListener((parent, view, position, id) -> {
            hideKeyboard(getActivity());
            if (salesProductsList.get(position).equals(NO_DATA_FOUND)) {
                itemSearchEt.getText().clear();
                return;
            }
            itemSearchEt.getText().clear();


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

            adapter = new SaleRequisitionAdapterMy(getActivity(),itemsList,null,this);
            salesRequisitionProductListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            salesRequisitionProductListRv.setAdapter(adapter);


        });
        /**
         * for control search item text change action
         */
        itemSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!itemSearchEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (itemSearchEt.isPerformingCompletion()) {
                    return;
                }
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    String currentSearchItemName = itemSearchEt.getText().toString();
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


        return view;
    }



    /**
     * method from search search item by name
     *
     * @param
     */
    private void getSearchingDataFromServer(String currentText) {

        isDataFetching = true;
        /**
         * set categoryS id static as like the provided this API so make a list of string
         */
        List<String> categoryS = new ArrayList<>();
        categoryS.addAll(Arrays.asList( "736","737","738", "739", "740", "741"));
        /**
         * now call the api
         */
        salesRequisitionViewModel.apiCallForSearchSalesRequisitionItemByName(getActivity(), categoryS, currentText);
        /**
         * now get the api data
         */
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
            itemSearchEt.setAdapter(productsArrayAdapter);
            itemSearchEt.showDropDown();
            isDataFetching = false;
        });

    }

    private void getDataFromPreviousFragment() {
        toolbar.setText("New Sale Requisition");
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

        adapter = new SaleRequisitionAdapterMy(getActivity(),itemsList,null,this);
        salesRequisitionProductListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        salesRequisitionProductListRv.setAdapter(adapter);

    }


    @OnClick(R.id.backbtn)
    public void onClickBackBtn() {
        myDatabaseHelper.deleteAllData();
        getActivity().onBackPressed();
    }

    @OnClick(R.id.totalSubmitBtn)
    public void totalSubmit() {



        for (int i = 0; i < updatedQuantityProductList.size(); i++) {
            if (Integer.parseInt(updatedQuantityProductList.get(i).getQuantity()) == 0) {
                infoMessage(getActivity().getApplication(), "Please enter quantity");
                return;
            }
            if (Integer.parseInt(updatedQuantityProductList.get(i).getPrice()) == 0) {
                infoMessage(getActivity().getApplication(), "Please enter price");
                return;
            }

        }


        if (updatedQuantityProductList.isEmpty()) {
            infoMessage(getActivity().getApplication(), "Empty Quantity");
            return;
        }
        Bundle bundle = new Bundle();

       /* bundle.putStringArrayList("allPrice", new ArrayList<>(getAllPrice()));
        bundle.putStringArrayList("allQuantity", new ArrayList<>(getAllQuantity()));*/
   //     bundle.putString("total", String.valueOf(currentTotal));
        Navigation.findNavController(getView()).navigate(R.id.action_newSaleFragment_to_newSalesRequisitionFragment2);
    }

    /**
     * for get all title from the current recyclerview
     */

    private static List<String> getAllTitle() {
        List<String> paymentLimitAmount = new ArrayList<>();
        for (int i = 0; i < salesRequisitionProductListRv.getChildCount(); i++) {
            paymentLimitAmount.add(((TextView) salesRequisitionProductListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.productNameTv)).getText().toString());
        }
        return paymentLimitAmount;
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
            salesRequisitionProductListRv.getAdapter().notifyItemRemoved(position);
            /**
             * now manage loading time total quantity
             */
            int totalQuantity = 0;
            for (int i = 0; i < itemsList.size(); i++) {
                totalQuantity += Integer.parseInt(itemsList.get(i).getQuantity());
            }
           totalSubmitBtn.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPrice) {
        allOk = true;//for  handle submit time validation
        updateData(currentItem.getProductID(),currentItem.getProductTitle(),quantity,currentItem.getUnit(),currentItem.getUnit_name(),price,discount,totalPrice);




    }

    @Override
    public void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem) {
        allOk = true;//for  handle submit time validation
        updateData(currentItem.getProductID(),currentItem.getProductTitle(),quantity,currentItem.getUnit(),currentItem.getUnit_name(),"0","0","0");


    }

    private void updateData(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        Boolean isUpdated = myDatabaseHelper.updateData(productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice);

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
            item.setPrice(cursor.getString(6));
            item.setDiscount(cursor.getString(7));
            item.setTotalPrice(cursor.getString(8));
            itemsList.add(item);
        }
        updatedQuantityProductList.clear();
        updatedQuantityProductList.addAll(itemsList);

    }

}
