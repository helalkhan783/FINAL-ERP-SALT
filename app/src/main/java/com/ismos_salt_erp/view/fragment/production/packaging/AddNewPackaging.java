package com.ismos_salt_erp.view.fragment.production.packaging;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.AddNewPackagingAdapter;
import com.ismos_salt_erp.clickHandle.AddNewPackagingToolbarHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewPackagingBinding;
import com.ismos_salt_erp.localDatabase.MyPackagingDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PackagingDatabaseModel;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.PackagingOriginItemList;
import com.ismos_salt_erp.serverResponseModel.PktNameList;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PackagingViewModel;
import com.ismos_salt_erp.viewModel.SaleViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AddNewPackaging extends BaseFragment implements PackagingRecyclerItemClickHandle {

    private boolean isComplete = true;

    public static FragmentAddNewPackagingBinding binding;
    private PackagingViewModel packagingViewModel;
    private MyPackagingDatabaseHelper myPackagingDatabaseHelper;


    private SalesRequisitionViewModel salesRequisitionViewModel;
    private SaleViewModel saleViewModel;


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


    public static String selectedEnterPrice, selectedStore;

    /**
     * For store Recycler Data
     */
    private List<PackagingOriginItemList> packagingOriginItemLists;//itemName
    /**
     * for packed list
     */
    private List<PktNameList> packedNameList;//packedName
    AddNewPackagingAdapter adapter;

    public static List<PackagingDatabaseModel> packagingDatabaseModelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_packaging, container, false);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        packagingViewModel = new ViewModelProvider(this).get(PackagingViewModel.class);
        myPackagingDatabaseHelper = new MyPackagingDatabaseHelper(getContext());
        binding.toolbar.toolbarTitle.setText("Add New Packaging");

        /**
         * For Submit & validation
         */
        binding.setClickHandle(() -> {
            if (selectedStore == null) {
                infoMessage(getActivity().getApplication(), "Please select store");
                return;
            }
            validationAndSubmit();
        });


        binding.toolbar.setClickHandle(new AddNewPackagingToolbarHandle() {
            @Override
            public void back() {
                hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }

            @Override
            public void addMore() {

                if (packagingOriginItemLists == null || packagingOriginItemLists.isEmpty()) {
                    infoMessage(getActivity().getApplication(), "There is no product");
                    return;
                }

                /**
                 * all ok now set recyclerView int... data
                 */
                /**
                 * save Data to local database
                 */
                long rowId = myPackagingDatabaseHelper.insertData("0", "0", "0", "0", "", "");

                if (rowId == -1) {
                    //Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
                }


                /**
                 * now get all local data from db
                 */
                Cursor cursor = myPackagingDatabaseHelper.displayAllData();
                if (cursor.getCount() == 0) {//means didn't have any data
                    Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                packagingDatabaseModelList = new ArrayList<>();
                packagingDatabaseModelList.clear();

                while (cursor.moveToNext()) {
                    PackagingDatabaseModel model = new PackagingDatabaseModel();
                    model.setId(cursor.getInt(0));
                    model.setItemId(cursor.getString(1));
                    model.setPackedId(cursor.getString(2));
                    model.setWeight(cursor.getString(3));
                    model.setQuantity(cursor.getString(4));
                    model.setNote(cursor.getString(5));
                    model.setPktTag(cursor.getString(6));
                    packagingDatabaseModelList.add(model);
                }

                binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new AddNewPackagingAdapter(getActivity(), packagingOriginItemLists, AddNewPackaging.this, packagingDatabaseModelList,AddNewPackaging.this);
                binding.productList.setAdapter(adapter);
            }
        });

        /**
         * for set Page Data
         */
        getPageDataFromServer();

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

    private void getPreviousDataFromServer() {
        /**
         * now get all local data from db
         */
        Cursor cursor = myPackagingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        packagingDatabaseModelList = new ArrayList<>();
        packagingDatabaseModelList.clear();


        while (cursor.moveToNext()) {
            PackagingDatabaseModel model = new PackagingDatabaseModel();
            model.setId(cursor.getInt(0));
            model.setItemId(cursor.getString(1));
            model.setPackedId(cursor.getString(2));
            model.setWeight(cursor.getString(3));
            model.setQuantity(cursor.getString(4));
            model.setNote(cursor.getString(5));
            model.setPktTag(cursor.getString(6));
            packagingDatabaseModelList.add(model);
        }


        binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AddNewPackagingAdapter(getActivity(), packagingOriginItemLists, this, packagingDatabaseModelList,AddNewPackaging.this);
        binding.productList.setAdapter(adapter);

    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

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
        /**
         * for set Recycler Data
         */
        packagingViewModel.getPackagingOriginItems(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    packagingOriginItemLists = new ArrayList<>();
                    packagingOriginItemLists.clear();
                    packagingOriginItemLists.addAll(response.getItems());

                    /**
                     * all ok now set recyclerView int... data
                     */
                    Cursor cursor2 = myPackagingDatabaseHelper.displayAllData();
                    if (cursor2.getCount() == 0) {//means didn't have any data
                        /**
                         * save Data to local database
                         */
                        long rowId = myPackagingDatabaseHelper.insertData("0", "0", "0", "0", "", "");

                        if (rowId == -1) {
                            // Toast.makeText(getContext(), "Failed Inserted ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getContext(), "Successfully Inserted in Row = " + rowId, Toast.LENGTH_SHORT).show();
                        }
                    }


                    /**
                     * now get all local data from db
                     */
                    Cursor cursor = myPackagingDatabaseHelper.displayAllData();
                    if (cursor.getCount() == 0) {//means didn't have any data
                        Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    packagingDatabaseModelList = new ArrayList<>();
                    packagingDatabaseModelList.clear();


                    while (cursor.moveToNext()) {
                        PackagingDatabaseModel model = new PackagingDatabaseModel();
                        model.setId(cursor.getInt(0));
                        model.setItemId(cursor.getString(1));
                        model.setPackedId(cursor.getString(2));
                        model.setWeight(cursor.getString(3));
                        model.setQuantity(cursor.getString(4));
                        model.setNote(cursor.getString(5));
                        model.setPktTag(cursor.getString(6));
                        packagingDatabaseModelList.add(model);
                    }


                    binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new AddNewPackagingAdapter(getActivity(), response.getItems(), this, packagingDatabaseModelList,AddNewPackaging.this);
                    binding.productList.setAdapter(adapter);

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

    @Override
    public void selectItemName(int position, int adapterPosition, PackagingDatabaseModel model) {
        PackagingOriginItemList currentSelectedItemName = packagingOriginItemLists.get(position);
        /**
         * will update the current position object from here
         */

        String currentItemQuantity = null, currentNote = null, currentPktTag = null;

        try {
            currentItemQuantity = ((EditText) binding.productList.getLayoutManager()
                    .findViewByPosition(adapterPosition).findViewById(R.id.packagingQuantity)).getText().toString();
            currentNote = ((EditText) binding.productList.getLayoutManager()
                    .findViewByPosition(adapterPosition).findViewById(R.id.note)).getText().toString();
            currentPktTag = ((TextView) binding.productList.getLayoutManager()
                    .findViewByPosition(adapterPosition).findViewById(R.id.pktTagItems)).getText().toString();

        } catch (Exception e) {
            Log.d("ERROR", "ERROR");
        }

        if (currentItemQuantity == null || currentItemQuantity.equals("0")) {
            currentItemQuantity = "0";
        }


        int currentItemId = packagingDatabaseModelList.get(adapterPosition).getId();


        int value = myPackagingDatabaseHelper.updateData(String.valueOf(model.getId()),
                packagingOriginItemLists.get(position).getProductID(), model.getPackedId(), model.getWeight(), model.getQuantity(), model.getNote(),
                model.getPktTag());

        if (value != -1) {


            /**
             * now get all local data from db
             */
            Cursor cursor = myPackagingDatabaseHelper.displayAllData();
            if (cursor.getCount() == 0) {//means didn't have any data
                Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
                return;
            }
            packagingDatabaseModelList = new ArrayList<>();
            packagingDatabaseModelList.clear();


            while (cursor.moveToNext()) {
                Log.d("OK", "");
                PackagingDatabaseModel model1 = new PackagingDatabaseModel();
                model1.setId(cursor.getInt(0));
                model1.setItemId(cursor.getString(1));
                model1.setPackedId(cursor.getString(2));
                model1.setWeight(cursor.getString(3));
                model1.setQuantity(cursor.getString(4));
                model1.setNote(cursor.getString(5));
                model1.setPktTag(cursor.getString(6));
                packagingDatabaseModelList.add(model1);
            }


            /**
             * now set Packed Name base on item name id
             */
            setPackedNameByItemNameId(packagingOriginItemLists.get(position).getProductID(), position, adapterPosition, packagingDatabaseModelList);
        }


    }

    @Override
    public void setPreviousSelectedItemName(int adapterPosition, String previousItemNameId, String currentSelectedPackedId) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Check Internet Connection");
            return;
        }

        if (previousItemNameId == null) {
            return;
        }
        packagingViewModel.getPktNameListByItemNameId(getActivity(), previousItemNameId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }


                    packedNameList = new ArrayList<>();
                    packedNameList.clear();

                    packedNameList.addAll(response.getItems());

                    List<String> packedNameList = new ArrayList<>();
                    packedNameList.clear();

                    for (int i = 0; i < response.getItems().size(); i++) {
                        packedNameList.add(response.getItems().get(i).getProductTitle());
                    }
                    try {
                        ((SmartMaterialSpinner) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.packedName))
                                .setItem(packedNameList);
                    } catch (Exception e) {
                        Log.d("ERROR", "ERROR");
                    }
                    /**
                     * for select previous selected packed name
                     */
                    for (int i = 0; i < response.getItems().size(); i++) {
                        if (currentSelectedPackedId.equals(response.getItems().get(i))) {
                            ((SmartMaterialSpinner) binding.productList.getLayoutManager()
                                    .findViewByPosition(adapterPosition).findViewById(R.id.packedName))
                                    .setSelection(i);
                        }
                    }
                    /**
                     * set Previous selected
                     */
                });
    }


    @Override
    public void selectPackedName(int position, int adapterPosition, PackagingDatabaseModel model) {
        /**
         * will update the current position object from here
         */

        String currentItemQuantity = null, currentNote = null, currentPktTag = null;

        try {
            currentItemQuantity = ((EditText) binding.productList.getLayoutManager()
                    .findViewByPosition(position).findViewById(R.id.packagingQuantity)).getText().toString();
            currentNote = ((EditText) binding.productList.getLayoutManager()
                    .findViewByPosition(position).findViewById(R.id.note)).getText().toString();
            currentPktTag = ((TextView) binding.productList.getLayoutManager()
                    .findViewByPosition(position).findViewById(R.id.pktTagItems)).getText().toString();

        } catch (Exception e) {
            Log.d("ERROR", "ERROR");
        }

        if (currentItemQuantity == null || currentItemQuantity.equals("0")) {
            currentItemQuantity = "0";
        }


        int value = myPackagingDatabaseHelper.updateData(
                String.valueOf(model.getId()), model.getItemId(), packedNameList.get(position).getProductID(), model.getWeight(), model.getQuantity(), model.getNote(),
                model.getPktTag());

        if (value == -1) {
            return;
        }

        String currentSelectedPackedItemId = packedNameList.get(position).getProductID();


        /**
         * now get all local data from db
         */
        Cursor cursor = myPackagingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        packagingDatabaseModelList = new ArrayList<>();
        packagingDatabaseModelList.clear();


        while (cursor.moveToNext()) {
            Log.d("OK", "");
            PackagingDatabaseModel model1 = new PackagingDatabaseModel();
            model1.setId(cursor.getInt(0));
            model1.setItemId(cursor.getString(1));
            model1.setPackedId(cursor.getString(2));
            model1.setWeight(cursor.getString(3));
            model1.setQuantity(cursor.getString(4));
            model1.setNote(cursor.getString(5));
            model1.setPktTag(cursor.getString(6));
            packagingDatabaseModelList.add(model1);
        }


        /**
         * now set weight and total weight by packed id
         */
        setWeightAndTotalWeightByPackedId(currentSelectedPackedItemId, model, adapterPosition);
    }

    @Override
    public void changeQuantity(String currentQuantity, int adapterPosition, PackagingDatabaseModel model) {
        String currentWeight = ((TextView) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.weight)).getText().toString();
        int rowVal = myPackagingDatabaseHelper.updateData(String.valueOf(model.getId()), model.getItemId(), model.getPackedId(), currentWeight, currentQuantity, model.getNote(), model.getPktTag());
        if (rowVal == -1) {
            return;
        }
        /**
         * now get all local data from db
         */
        Cursor cursor = myPackagingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        packagingDatabaseModelList = new ArrayList<>();
        packagingDatabaseModelList.clear();


        while (cursor.moveToNext()) {
            Log.d("OK", "");
            PackagingDatabaseModel model1 = new PackagingDatabaseModel();
            model1.setId(cursor.getInt(0));
            model1.setItemId(cursor.getString(1));
            model1.setPackedId(cursor.getString(2));
            model1.setWeight(cursor.getString(3));
            model1.setQuantity(cursor.getString(4));
            model1.setNote(cursor.getString(5));
            model1.setPktTag(cursor.getString(6));
            packagingDatabaseModelList.add(model1);
        }


        double totalQuantity = 0.0;
        for (int i = 0; i < packagingDatabaseModelList.size(); i++) {

            if (packagingDatabaseModelList.get(i).getQuantity().isEmpty()) {
                totalQuantity += 0.0;
            } else {
                totalQuantity += Double.parseDouble(packagingDatabaseModelList.get(i).getQuantity());
            }
        }
        binding.totalQuantity.setText("Total Quantity: " + totalQuantity);
    }

    private void setWeightAndTotalWeightByPackedId(String currentSelectedPackedItemId, PackagingDatabaseModel model, int adapterPosition) {

        /**
         * first call api for getWeightAndPktTagItemsBySelected Packed id
         */
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        packagingViewModel.getPackageWeightAndDimensions(getActivity(), currentSelectedPackedItemId, selectedStore)
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
                        String currentQuantity = ((EditText) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.packagingQuantity)).getText().toString();

                        if (currentQuantity.equals("")) {
                            currentQuantity = "1";
                        }
                        double totalQuantity = Double.parseDouble(currentQuantity) * Double.parseDouble(response.getConvertedItemInfo().getProductDimensions());

                        ((TextView) binding.productList.getLayoutManager().findViewByPosition(adapterPosition)
                                .findViewById(R.id.weight)).setText(String.valueOf(response.getConvertedItemInfo().getProductDimensions()));

                        ((TextView) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.totalWeight))
                                .setText(String.valueOf(totalQuantity));


                        if (response.getPacketInfo() != null) {
                            ((TextView) binding.productList.getLayoutManager()
                                    .findViewByPosition(adapterPosition).findViewById(R.id.pktTagItemsAvailableMessage)).setText(response.getPacketInfo().getQuantity());
                            ((TextView) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.pktTagItems)).setText(response.getPacketInfo().getProductTitle());
                        }


                    } catch (Exception e) {
                        Log.d("ERROR", "ERROR");
                    }
                });
        /*
         */
/**
 * now set stock PKT TAG Items by selected packed id
 *//*

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        */
/**
 * now set stock of selected item
 *//*

        packagingViewModel.getPackagingStockByRequiredId(getActivity(), selectedStore, Collections.singletonList(currentSelectedPackedItemId))
                .observe(getViewLifecycleOwner(), stock -> {

                    if (stock == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (stock.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }


                    try {
                        ((TextView) binding.productList.getLayoutManager()
                                .findViewByPosition(adapterPosition).findViewById(R.id.pktTagItemsAvailableMessage)).setText("Available: " + stock.getLists().get(0).getStockQty());
                    } catch (Exception e) {
                        Log.d("ERROR", "ERROR");
                    }
                });
*/


    }

    @Override
    public void changeNote(String currentNote, String currentQuantity, int adapterPosition, PackagingDatabaseModel model) {
        String currentWeight = ((TextView) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.weight)).getText().toString();
        int rowVal = myPackagingDatabaseHelper.updateData(String.valueOf(model.getId()), model.getItemId(), model.getPackedId(), currentWeight, currentQuantity, currentNote, model.getPktTag());
        if (rowVal != -1) {
            return;
        }
        /**
         * now get all local data from db
         */
        Cursor cursor = myPackagingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        packagingDatabaseModelList = new ArrayList<>();
        packagingDatabaseModelList.clear();


        while (cursor.moveToNext()) {
            Log.d("OK", "");
            PackagingDatabaseModel model1 = new PackagingDatabaseModel();
            model1.setId(cursor.getInt(0));
            model1.setItemId(cursor.getString(1));
            model1.setPackedId(cursor.getString(2));
            model1.setWeight(cursor.getString(3));
            model1.setQuantity(cursor.getString(4));
            model1.setNote(cursor.getString(5));
            model1.setPktTag(cursor.getString(6));
            packagingDatabaseModelList.add(model1);
        }
        Log.d("OKK", "TEST");
    }

    @Override
    public void removeItem(int position) {
        try {

            if (position > packagingDatabaseModelList.size() - 1) {
                adapter.notifyItemRangeRemoved(0, packagingDatabaseModelList.size());
                return;
            }
            int value = myPackagingDatabaseHelper.deleteData(String.valueOf(packagingDatabaseModelList.get(position).getId()));
            if (value > 0) {
                packagingDatabaseModelList.remove(position);
                binding.productList.getAdapter().notifyItemRemoved(position);
                /**
                 * now manage loading time total quantity
                 */
                int totalQuantity = 0;
                for (int i = 0; i < packagingDatabaseModelList.size(); i++) {
                    totalQuantity += Integer.parseInt(packagingDatabaseModelList.get(i).getQuantity());
                }
                AddNewPackaging.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));

                return;
            }
            Toast.makeText(getContext(), "delete failed ", Toast.LENGTH_SHORT).show();
        }catch ( Exception e){}
    }


    private void setPackedNameByItemNameId(String itemNameId, int position, int adapterPosition, List<PackagingDatabaseModel> packagingDatabaseModelList) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Check Internet Connection");
            return;
        }

        if (selectedStore == null) {
            try {
                ((SmartMaterialSpinner) binding.productList.getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.itemName)).setEnableErrorLabel(true);
                binding.store.setEnableErrorLabel(true);
                binding.store.setErrorText("Empty");
            } catch (Exception e) {
                Log.d("ERROR", "ERROR");
            }
            return;
        }
        packagingViewModel.getPktNameListByItemNameId(getActivity(), itemNameId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    packedNameList = new ArrayList<>();
                    packedNameList.clear();
                    packedNameList.addAll(response.getItems());

                    List<String> packedNameList = new ArrayList<>();
                    packedNameList.clear();

                    for (int i = 0; i < response.getItems().size(); i++) {
                        packedNameList.add(response.getItems().get(i).getProductTitle());
                    }
                    try {
                        ((SmartMaterialSpinner) binding.productList.
                                getLayoutManager().findViewByPosition(adapterPosition).findViewById(R.id.packedName))
                                .setItem(packedNameList);
                    } catch (Exception e) {
                        Log.d("ERROR", "ERROR");
                    }

                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                        return;
                    }
                    /**
                     * now set stock of selected item
                     */
                    packagingViewModel.getPackagingStockByRequiredId(getActivity(), selectedStore, Collections.singletonList(itemNameId))
                            .observe(getViewLifecycleOwner(), stock -> {

                                if (stock == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (stock.getStatus() != 200) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }


                                try {
                                    ((TextView) binding.productList.getLayoutManager()
                                            .findViewByPosition(adapterPosition).findViewById(R.id.itemAvailableMessage)).setText(" " + stock.getLists().get(0).getStockQty());
                                } catch (Exception e) {
                                    Log.d("ERROR", "ERROR");
                                }
                            });
                });
    }


    private void validationAndSubmit() {
        isComplete = true;
        /**
         * now get all local data from db
         */
        Cursor cursor = myPackagingDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            Toast.makeText(getContext(), "There Is No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        packagingDatabaseModelList = new ArrayList<>();
        packagingDatabaseModelList.clear();


        while (cursor.moveToNext()) {
            PackagingDatabaseModel model = new PackagingDatabaseModel();
            model.setId(cursor.getInt(0));
            model.setItemId(cursor.getString(1));
            model.setPackedId(cursor.getString(2));
            model.setWeight(cursor.getString(3));
            model.setQuantity(cursor.getString(4));
            model.setNote(cursor.getString(5));
            model.setPktTag(cursor.getString(6));
            packagingDatabaseModelList.add(model);
        }

        List<String> selectedItemIdList = new ArrayList<>();
        List<String> selectedItemQuantityList = new ArrayList<>();
        List<String> selectedTotalWeight = new ArrayList<>();
        int totalQuantity = 0;
        for (int i = 0; i < packagingDatabaseModelList.size(); i++) {
            selectedItemIdList.add(packagingDatabaseModelList.get(i).getItemId());
            selectedItemQuantityList.add(packagingDatabaseModelList.get(i).getQuantity());
            totalQuantity += Integer.parseInt(packagingDatabaseModelList.get(i).getQuantity());
            String currentPositionTotalWeight = ((TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.totalWeight)).getText().toString();
            selectedTotalWeight.add(currentPositionTotalWeight);
        }
        if (totalQuantity == 0) {
            return;
        }

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        packagingViewModel.getPackagingStockByRequiredId(getActivity(), selectedStore, selectedItemIdList)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    /**
                     * now check
                     */
                    for (int i = 0; i < selectedItemQuantityList.size(); i++) {
                        if (Integer.parseInt(selectedItemQuantityList.get(i)) <= response.getLists().get(i).getStockQty()) {

                        } else if (Integer.parseInt(selectedItemQuantityList.get(i)) > response.getLists().get(i).getStockQty()) {
                            isComplete = false;
                        }

                        Double currentTotalWeight = 0.0;
                        if (selectedTotalWeight.get(i).isEmpty()) {
                            currentTotalWeight = 0.0;
                        } else {
                            currentTotalWeight = Double.parseDouble(selectedTotalWeight.get(i));
                        }

                        if (currentTotalWeight > response.getLists().get(i).getStockQty()) {
                            isComplete = false;
                        }
                    }
                    if (isComplete) {
                        hideKeyboard(getActivity());
                        Bundle bundle = new Bundle();
                        bundle.putString("selectedStoreId", selectedStore);
                        bundle.putString("selectedEnterPrice", selectedEnterPrice);
                        Navigation.findNavController(getView()).navigate(R.id.action_addNewPackaging_to_confirmPackaging, bundle);
                    }
                });
    }
}