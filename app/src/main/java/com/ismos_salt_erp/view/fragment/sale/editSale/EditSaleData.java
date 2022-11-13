package com.ismos_salt_erp.view.fragment.sale.editSale;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.EditSaleAdapter;
import com.ismos_salt_erp.databinding.FragmentEditSaleDataBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.EditSaleItemsResponse;
import com.ismos_salt_erp.serverResponseModel.GetPreviousSaleInfoResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;
import com.ismos_salt_erp.viewModel.UpdateSaleViewModel;

import java.util.ArrayList;
import java.util.List;


public class EditSaleData extends BaseFragment implements EditItemClick {
    public static String discountType;
    public static String discountAmount,percentDiscount;
    public static String getPaymentTYpe,collectedAmount,companyName,customerPhone, customerName,address,customerId;
    public static FragmentEditSaleDataBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    private SaleViewModel saleViewModel;
    private UpdateSaleViewModel updateSaleViewModel;

    private final String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    List<SalesRequisitionItems> itemsList;
    EditSaleAdapter adapter;


    private static boolean finalTotalCount = false;
    private boolean allOk = true;

    private GetPreviousSaleInfoResponse getPreviousSaleInfoResponse;
    public static List<EditSaleItemsResponse> currentSaleItemList;


    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<SalesRequisitionItems> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();


    private String siId;
    private String selectedStore;
    private String orderId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_sale_data, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        updateSaleViewModel = new ViewModelProvider(this).get(UpdateSaleViewModel.class);
        binding.toolbar.toolbarTitle.setText("Edit Sale");
        getDataFromPreviousFragment();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * Now get Sale Data from previous Fragment
         */
        getSaleDataFromServer();
        /**
         * now submit edit ProductInfo
         */
        binding.setClickHandle(() -> {
            if (itemsList == null || itemsList.isEmpty()) {
                return;
            }
            finalTotalCount = true;
            getFinalAllQuantity();//for count updated quantity object

            updatedQuantityProductList.clear();
            updatedQuantityProductList.addAll(itemsList);
            if (updatedQuantityProductList.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Empty Quantity");
                return;
            }

            /**
             * validation and save
             */
            validationRecyclerData();

        });


        return binding.getRoot();
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        siId = getArguments().getString("sid");
    }

    private void getSaleDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        updateMillerViewModel.getEditSalePageInfo(getActivity(), siId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getStatus());
                        return;
                    }
                    getPreviousSaleInfoResponse = response;//store current page whole data
                    currentSaleItemList = response.getOrderDetails().getItems();//store current sales items


                    discountType = response.getOrder().getDiscountType();
                    discountAmount = response.getOrder().getDiscountAmount();
                    percentDiscount = response.getOrder().getDiscountRate();
                    getPaymentTYpe =  response.getOrderDetails().getPaymentType();
                    collectedAmount = String.valueOf(response.getOrderDetails().getCollected());
                    companyName = response.getOrderDetails().getCustomer().getCompanyName();
                    customerPhone = response.getOrderDetails().getCustomer().getPhone();
                    customerName = response.getOrderDetails().getCustomer().getCustomerFname();
                    address = response.getOrderDetails().getCustomer().getAddress();
                    customerId = response.getOrder().getCustomerID();


                    /**
                     * now set previous selected store
                     */
                    selectedStore = response.getOrder().getStoreID();

                    orderId = response.getOrder().getOrderID();
                    /**
                     * save data
                     */
                    for (int i = 0; i < response.getOrderDetails().getItems().size(); i++) {
                        String totalAmount = String.valueOf(response.getOrderDetails().getItems().get(i).getTotalAmount());

                        long rowId = myDatabaseHelper.insertData(
                                response.getOrderDetails().getItems().get(i).getProductID(),
                                response.getOrderDetails().getItems().get(i).getItem(),
                                response.getOrderDetails().getItems().get(i).getQuantity(),
                                response.getOrderDetails().getItems().get(i).getUnit(),
                                "",
                                response.getOrderDetails().getItems().get(i).getSellingPrice(),
                                "0", totalAmount
                        );

                        if (rowId == -1) {
                            // Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
                        }
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
                    adapter = new EditSaleAdapter(getActivity(), itemsList, EditSaleData.this);
                    binding.itemListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.itemListRv.setAdapter(adapter);

                    /**
                     * after set store load all product stock data------
                     */
                    List<String> productIdList = new ArrayList<>();
                    List<String> soldFromList = new ArrayList<>();
                    productIdList.clear();
                    soldFromList.clear();


                    for (int i = 0; i < itemsList.size(); i++) {
                        try {
                            productIdList.add(itemsList.get(i).getProductID());
                            soldFromList.add(currentSaleItemList.get(i).getSoldFrom());
                        } catch (Exception e) {
                            Log.d("Error", "" + e.getMessage());
                        }
                    }
                    saleViewModel.getItemStockList(getActivity(), productIdList, soldFromList)
                            .observe(getViewLifecycleOwner(), stockResponse -> {
                                if (stockResponse == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }

                                if (stockResponse.getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), " ");
                                    return;
                                }

                                for (int i = 0; i < stockResponse.getLists().size(); i++) {
                                    try {
                                        binding.itemListRv.getLayoutManager()
                                                .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);
                                        ((TextView) binding.itemListRv.getLayoutManager().findViewByPosition(i)
                                                .findViewById(R.id.stock)).setText(stockResponse.getLists().get(i).getStockQty() + "");
                                    } catch (Exception e) {
                                        Log.d("ERROR", "" + e.getMessage());
                                    }
                                }

                            });

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
            binding.itemListRv.getAdapter().notifyItemRemoved(position);
//            /**
//             * now manage loading time total quantity
//             */
//            int totalQuantity = 0;
//            for (int i = 0; i < itemsList.size(); i++) {
//                totalQuantity += Integer.parseInt(itemsList.get(i).getQuantity());
//            }
//            AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem,String price ,String discount ,String totalPrice) {
try {


    allOk = true;//for  handle submit time validation
    Boolean isUpdated = myDatabaseHelper
            .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(),price,discount,totalPrice);

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
}catch (Exception e){}
    }

    @Override
    public void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem,String price,String discount ,String totalPrice) {
        allOk = true;//for  handle submit time validation
        Boolean isUpdated = myDatabaseHelper
                .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(),price,discount,totalPrice);

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
    }


    public static List<String> getAllQuantity() {
        List<String> paymentLimitAmount = new ArrayList<>();
        try {
            for (int i = 0; i < binding.itemListRv.getChildCount(); i++) {
                try {
                    paymentLimitAmount.add(((EditText) binding.itemListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString());
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
        for (int i = 0; i < binding.itemListRv.getChildCount(); i++) {
            try {
                /**
                 * for handle updated quantity object
                 */
                if (finalTotalCount) {
                    String currentQuantity = ((EditText) binding.itemListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.quantityEt)).getText().toString();
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


    private void validationRecyclerData() {
        hideKeyboard(getActivity());


        for (int i = 0; i < updatedQuantityProductList.size(); i++) {
            if (Double.parseDouble(updatedQuantityProductList.get(i).getQuantity()) == 0) {
                infoMessage(getActivity().getApplication(), "Please insert quantity");
                return;
            }
        }



        List<String> idList = new ArrayList<>();
        idList.clear();
        for (int i = 0; i < updatedQuantityProductList.size(); i++) {
            idList.add(updatedQuantityProductList.get(i).getProductID());
        }
        saleViewModel.getProductStockDataByProductId(getActivity(), idList, selectedStore)
                .observe(getViewLifecycleOwner(), response -> {
                    /**
                     * for set updated productIdList,unitList
                     */
                    List<String> proDuctIdList = new ArrayList<>();
                    List<String> unitList = new ArrayList<>();
                    List<String> productTitleList = new ArrayList<>();
                    List<String> sellingPriceList = new ArrayList<>();
                    List<String> previousQuantityList = new ArrayList<>();
                    List<String> soldFromList = new ArrayList<>();

                    proDuctIdList.clear();
                    unitList.clear();
                    productTitleList.clear();
                    soldFromList.clear();
                    try {
                        for (int i = 0; i < EditSaleData.updatedQuantityProductList.size(); i++) {
                            proDuctIdList.add(EditSaleData.updatedQuantityProductList.get(i).getProductID());
                            unitList.add(EditSaleData.updatedQuantityProductList.get(i).getUnit());
                            productTitleList.add(EditSaleData.updatedQuantityProductList.get(i).getProductTitle());
                            sellingPriceList.add(EditSaleData.currentSaleItemList.get(i).getSellingPrice());
                            previousQuantityList.add(EditSaleData.currentSaleItemList.get(i).getQuantity());
                            soldFromList.add(currentSaleItemList.get(i).getSoldFrom());
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }
                    updateSaleViewModel.salesEditPageStockCheck(
                            getActivity(), orderId, siId, proDuctIdList, soldFromList, EditSaleData.updatedQuantityList,
                            productTitleList, previousQuantityList
                    ).observe(getViewLifecycleOwner(), response1 -> {
                        if (response1 == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response1.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response1.getMessage());
                            return;
                        }


                        try {
                            Bundle bundle = new Bundle();
                            if (selectedStore == null) {
                                bundle.putString("selectedStoreId", "");
                            } else {
                                bundle.putString("selectedStoreId", selectedStore);
                            }
                            bundle.putString("siId", siId);
                            bundle.putString("orderId", orderId);

                            Navigation.findNavController(getView()).navigate(R.id.action_editSaleData_to_confirmSaleEdit, bundle);

                        } catch (Exception e) {
                            Log.d("ERROR", "error");
                        }


                    });


                    //  }
                });


    }


}