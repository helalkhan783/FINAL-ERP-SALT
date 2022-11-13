package com.ismos_salt_erp.view.fragment.production.washingAndCrushing.edit;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.EditWashingCrushingAdapter;
import com.ismos_salt_erp.databinding.FragmentEditWashingCrushingBinding;
import com.ismos_salt_erp.localDatabase.MyWashingCrushingHelper;
import com.ismos_salt_erp.serverResponseModel.EditWashingCrushingItem;
import com.ismos_salt_erp.serverResponseModel.EditWashingCrushingResponse;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.WashingCrushingModel;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.EditWashingCrushingViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;
import com.ismos_salt_erp.viewModel.UpdateSaleViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditWashingCrushing extends BaseFragment implements EditWashingCrushingItemClickHandle {
    public static FragmentEditWashingCrushingBinding binding;


    private UpdateMillerViewModel updateMillerViewModel;
    private MyWashingCrushingHelper myDatabaseHelper;
    private SaleViewModel saleViewModel;
    private UpdateSaleViewModel updateSaleViewModel;
    private EditWashingCrushingViewModel editWashingCrushingViewModel;

    private final String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    List<WashingCrushingModel> itemsList;
    EditWashingCrushingAdapter adapter;


    private static boolean finalTotalCount = false;
    private boolean allOk = true;

    public static EditWashingCrushingResponse getPreviousSaleInfoResponse;
    public static List<EditWashingCrushingItem> currentSaleItemList;


    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<WashingCrushingModel> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();


    private String siId,   storeId ,referenceId,refName,destinationStoreId,output,note;
    private String selectedEnterprise;
    private String orderId;
    /**
     * For select store
     */
    private List<EnterpriseList> storeResponseList;
    private List<String> storeNameList;
    private String selectedStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_washing_crushing, container, false);
        myDatabaseHelper = new MyWashingCrushingHelper(getContext());
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        updateSaleViewModel = new ViewModelProvider(this).get(UpdateSaleViewModel.class);
        editWashingCrushingViewModel = new ViewModelProvider(this).get(EditWashingCrushingViewModel.class);
        binding.toolbar.toolbarTitle.setText("Edit Washing & Crushing");
        getDataFromPreviousFragment();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * Now get Sale Data from previous Fragment
         */
        getPreviousWashingCrushingDataFromServer();
        /**
         * now validation and submit current page Data
         */


        binding.setClickHandle(() -> {

            if (selectedStore == null) {
             infoMessage(requireActivity().getApplication(), "Please select store");
                return;
            }

            if (itemsList == null || itemsList.isEmpty()) {
                return;
            }
            finalTotalCount = true;
            getFinalAllQuantity();//for count updated quantity object

            updatedQuantityProductList.clear();
            updatedQuantityProductList.addAll(itemsList);


            for (int i = 0; i < updatedQuantityProductList.size(); i++) {
                if (Double.parseDouble(updatedQuantityProductList.get(i).getQuantity()) == 0) {

                    return;
                }
            }

            if (updatedQuantityProductList.isEmpty()) {
                infoMessage(getActivity().getApplication(), "Empty Quantity");
                return;
            }
            validationAndSubmit();
        });


        /**
         * handle selected store
         */
        binding.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStore = storeResponseList.get(position).getStoreID();
                /**
                 * now get current recycler item list data from this selected store
                 */
                setStockQuantityByCurrentItemsSoldFrom(selectedStore);
                binding.store.setEnableErrorLabel(false);
                binding.store.setErrorText("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void setStockQuantityByCurrentItemsSoldFrom(String selectedStoreId) {

        /**
         * after set store load all product stock data------
         */
        List<String> productIdList = new ArrayList<>();
        List<String> slectedStore = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();

        productIdList.clear();
        soldFromList.clear();

        for (int i = 0; i < itemsList.size(); i++) {
            try {
                productIdList.add(itemsList.get(i).getProductID());
                slectedStore.add(EditWashingCrushing.currentSaleItemList.get(i).getSoldFrom());
                soldFromList.add(getPreviousSaleInfoResponse.getOrderDetails().getItems().get(i).getSoldFrom());
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }

        }
        saleViewModel.getItemStockList(getActivity(), productIdList, soldFromList)
                .observe(getViewLifecycleOwner(), stockResponse -> {
                    if (stockResponse == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (stockResponse.getStatus() == 400) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    for (int i = 0; i < stockResponse.getLists().size(); i++) {

                        try {
                            binding.itemListRv.getLayoutManager()
                                    .findViewByPosition(i).findViewById(R.id.stock).setVisibility(View.VISIBLE);
                            ((EditText) binding.itemListRv.getLayoutManager().findViewByPosition(i)
                                    .findViewById(R.id.stock)).setText(stockResponse.getLists().get(i).getStockQty() + " Available");
                        } catch (Exception e) {
                            Log.d("ERROR", "" + e.getMessage());
                        }
                    }

                });
    }

    private void getPreviousWashingCrushingDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        editWashingCrushingViewModel.getEditWashingCrushingInfo(getActivity(), siId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getStatus());
                        return;
                    }
                    getPreviousSaleInfoResponse = response;

                    currentSaleItemList = response.getOrderDetails().getItems();//store current sales items
                     storeId = response.getOrderDetails().getItems().get(0).getSoldFrom();//store current sales items
                     referenceId = response.getOrderDetails().getCustomer().getCustomerId();//store current sales items
                     refName = response.getOrderDetails().getCustomer().getCustomerFname();//store current sales items
                     destinationStoreId = response.getDestination_store();//destination StroreId
                    output = response.getOrder().getStage();//destination StroreId
                    selectedEnterprise = response.getOrder().getStoreID();
                    note = response.getOrder().getNote();
                    orderId = response.getOrder().getOrderID();

                    /**
                     * save data
                     */
                    for (int i = 0; i < response.getOrderDetails().getItems().size(); i++) {
                        long rowId = myDatabaseHelper.insertData(
                                response.getOrderDetails().getItems().get(i).getProductID(),
                                response.getOrderDetails().getItems().get(i).getItem(),
                                response.getOrderDetails().getItems().get(i).getQuantity(),
                                response.getOrderDetails().getItems().get(i).getUnitID(),
                                response.getOrderDetails().getItems().get(i).getSoldFrom(),
                                response.getOrderDetails().getItems().get(i).getUnit()
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
                        WashingCrushingModel item = new WashingCrushingModel();
                        item.setProductID(cursor.getString(1));
                        item.setProductTitle(cursor.getString(2));
                        item.setQuantity(cursor.getString(3));
                        item.setUnit(cursor.getString(4));
                        item.setSoldFrom(cursor.getString(5));
                        item.setUnit_name(cursor.getString(6));
                        itemsList.add(item);
                    }
                    adapter = new EditWashingCrushingAdapter(getActivity(), itemsList, EditWashingCrushing.this);
                    binding.itemListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.itemListRv.setAdapter(adapter);

                    /**
                     * now control stock
                     */

                    /**
                     * after set store load all product stock data------
                     */
                    List<String> productIdList = new ArrayList<>();
                    List<String> slectedStore = new ArrayList<>();

                    productIdList.clear();

                    for (int i = 0; i < itemsList.size(); i++) {
                        productIdList.add(itemsList.get(i).getProductID());
                        slectedStore.add(EditWashingCrushing.currentSaleItemList.get(i).getSoldFrom());
                    }


                });


        /**
         * Now set store list by selected enterprise id
         */
        saleViewModel.getStoreListByOptionalEnterpriseId(getActivity(), selectedEnterprise)
                .observe(getViewLifecycleOwner(), storeResponse -> {
                    if (storeResponse == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (storeResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + "Api ERROR");
                        return;
                    }


                    storeResponseList = new ArrayList<>();
                    storeResponseList.clear();
                    storeNameList = new ArrayList<>();
                    storeNameList.clear();
                    storeResponseList.addAll(storeResponse.getEnterprise());
                    for (int i = 0; i < storeResponse.getEnterprise().size(); i++) {

                        storeNameList.add(""+storeResponse.getEnterprise().get(i).getStoreName());
                  if (storeId !=null){
                      if (storeResponse.getEnterprise().get(i).getStoreID().equals(storeId)){
                          binding.store.setSelection(i);
                      }
                  }

                    }
                    binding.store.setItem(storeNameList);


                });
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        siId = getArguments().getString("sid");
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
            /**
             * now manage loading time total quantity
             */
            double totalQuantity = 0;
            for (int i = 0; i < itemsList.size(); i++) {
                totalQuantity += Double.parseDouble(itemsList.get(i).getQuantity());
            }
            binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));
            return;
        }
        Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void insertQuantity(int position, String quantity, WashingCrushingModel currentItem) {


        allOk = true;//for  handle submit time validation
        Boolean isUpdated = myDatabaseHelper
                .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getSoldFrom(), currentItem.getUnit_name());

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
            WashingCrushingModel item = new WashingCrushingModel();
         /*   item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
*/
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setSoldFrom(cursor.getString(5));
            item.setUnit_name(cursor.getString(6));

            itemsList.add(item);
        }
    }

    @Override
    public void minusQuantity(int position, String quantity, WashingCrushingModel currentItem) {
        allOk = true;//for  handle submit time validation
        Boolean isUpdated = myDatabaseHelper
                .updateData(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getSoldFrom(), currentItem.getUnit_name());

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
            WashingCrushingModel item = new WashingCrushingModel();

            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setSoldFrom(cursor.getString(5));
            item.setUnit_name(cursor.getString(6));

            itemsList.add(item);
        }
    }

    private void validationAndSubmit() {

        /**
         * for set updated productIdList,unitList
         */
        List<String> proDuctIdList = new ArrayList<>();
        List<String> unitList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> previousQuantityList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();

        proDuctIdList.clear();
        unitList.clear();
        productTitleList.clear();
        soldFromList.clear();
        for (int i = 0; i < EditWashingCrushing.updatedQuantityProductList.size(); i++) {
            proDuctIdList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getProductID());
            unitList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getUnit());
            productTitleList.add(EditWashingCrushing.updatedQuantityProductList.get(i).getProductTitle());
            previousQuantityList.add(EditWashingCrushing.currentSaleItemList.get(i).getQuantity());
            soldFromList.add(EditWashingCrushing.currentSaleItemList.get(i).getSoldFrom());
        }


        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }

        editWashingCrushingViewModel.washingCrushingStockMessageCheck(
                getActivity(), orderId, siId, proDuctIdList, Collections.singletonList(selectedStore), EditWashingCrushing.updatedQuantityList,
                productTitleList, previousQuantityList, soldFromList
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
                bundle.putString("selectedEnterprise", selectedEnterprise);
                bundle.putString("selectedStoreId", selectedStore);
                bundle.putString("siId", siId);
                bundle.putString("orderId", orderId);
                bundle.putString("destinationStoreId", destinationStoreId);
                bundle.putString("referenceId", referenceId);
                bundle.putString("refName", refName);
                bundle.putString("output", output);
                bundle.putString("note", note);
                bundle.putString("destinationStore", getPreviousSaleInfoResponse.getDestination_store());
                Navigation.findNavController(getView()).navigate(R.id.action_editWashingCrushing_to_confirmEditWashingAndCrushing, bundle);
                selectedStore = null;
                selectedEnterprise = null;
            } catch (Exception e) {
                Log.d("ERROR", "error");
            }
        });

    }
}