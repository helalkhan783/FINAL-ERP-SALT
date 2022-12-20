package com.ismos_salt_erp.view.fragment.store;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.EnterPriseAdapterForStore;
import com.ismos_salt_erp.adapter.StockDetailsAdapter;
import com.ismos_salt_erp.adapter.StockInformationListAdapter;
import com.ismos_salt_erp.adapter.StoreListAdapter;
import com.ismos_salt_erp.databinding.AddStoreDialogLayoutBinding;
import com.ismos_salt_erp.databinding.FragmentStoreListBinding;
import com.ismos_salt_erp.dialog.MyApplication;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.EnterpriseList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportResponse;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseBrandList;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseCategoryList;
import com.ismos_salt_erp.serverResponseModel.StockList;
import com.ismos_salt_erp.serverResponseModel.StockStoreList;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SettingsUtil;
import com.ismos_salt_erp.utils.StockUtils;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.PurchaseMillerList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.view.fragment.customers.CustomerInterface;
import com.ismos_salt_erp.view.fragment.customers.SwitchInterface;
import com.ismos_salt_erp.view.fragment.store.list_response.StoreListViewModel;
import com.ismos_salt_erp.view.fragment.store.list_response.StoreLst;
import com.ismos_salt_erp.viewModel.BrandViewModel;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class StoreListFragment extends AddUpDel implements View.OnClickListener, CustomerInterface, SwitchInterface {
    private FragmentStoreListBinding binding;
    private StoreListViewModel storeListViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;
    private BrandViewModel brandViewModel;

    String porsion, productId;
    List<StockStoreList> stockStoreLists;
    List<String> storeNameList;
    List<Enterprize> enterprizeList;


    /**
     * For enterprise
     */
    List<EnterpriseResponse> enterpriseResponseList;
    List<String> enterpriseNameList;
    /**
     * for store
     */
    List<EnterpriseList> storeResponseList;


    /**
     * for Category List
     */
    private List<String> categoryNameList;
    private List<ReportPurchaseCategoryList> purchaseCategoryLists;

    /**
     * for brand List
     */
    private List<String> brandNameList;
    private List<ReportPurchaseBrandList> brandLists;

    /**
     * for miller List
     */
    private List<String> millerNameList;
    private List<PurchaseMillerList> millerLists;


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;

    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    List<StoreLst> storeLst;
    public static boolean endScroll = false;
    List<StoreLst> storeLists = new ArrayList<>();
    List<StockList> stockLists = new ArrayList<>();

    public static int manage = 0;
    private ReportViewModel reportViewModel;
    String storeSelectedId, millerProfileId, brandId, categoryId;
    String enterPriseId;

    private String storeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_list, container, false);

        storeListViewModel = new ViewModelProvider(this).get(StoreListViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


        setOnClick();


        binding.stockFilter.enterprise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                millerProfileId = millerLists.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.stockFilter.store.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeSelectedId = stockStoreLists.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.stockFilter.selectBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandId = brandLists.get(position).getBrandID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.stockFilter.selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = purchaseCategoryLists.get(position).getCategoryID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** for pagination **/
        binding.storeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    binding.expandableView.collapse();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (endScroll) {
                                return;
                            }
                            loading = false;
                            pageNumber += 1;

                            getAllListFromServer();
                            loading = true;
                        }
                    }
                }
            }
        });

        return binding.getRoot();
    }


    private void getStockDetails() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        storeListViewModel.stockDetails(getActivity(), productId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(requireActivity().getApplication(), response.getMessage());
                return;
            }
            if (response.getStatus() == 200) {
                binding.stockDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                StockDetailsAdapter adapter = new StockDetailsAdapter(getActivity(), response.getStockDetails());
                binding.stockDetailsRecyclerView.setAdapter(adapter);
            }
        });

    }

    public void getAllListFromServer() {
        if (porsion.equals("stockDetails")) {
            try {
                binding.toolbar.addBtn.setVisibility(View.GONE);
                binding.storeRv.setVisibility(View.GONE);
                binding.stockDetailsRecyclerView.setVisibility(View.VISIBLE);
                getStockDetails();
            } catch (Exception e) {
            }
            return;
        }
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        if (pageNumber == 1) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
        }
        if (pageNumber > 1) {
            binding.progress.setVisibility(View.VISIBLE);
            binding.progress.setProgress(20);
            binding.progress.setMax(100);
        }
        if (porsion.equals(StockUtils.stockInfo)) {
            try {
                binding.stockFilter.zoneLayout.setVisibility(View.GONE);
                getStockList();
                getPageData();
            } catch (Exception e) {
            }
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
        }
        if (porsion.equals(SettingsUtil.storeList)) {
            binding.toolbar.addBtn.setVisibility(View.VISIBLE);
            binding.storeManagementLayout.setVisibility(View.VISIBLE);
            getStoreListFromServer();
        }
        return;
    }

    private void getPageData() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        reportViewModel.getPurchaseReportPageData(getActivity(), getProfileId(requireActivity().getApplication())).observe(getViewLifecycleOwner(), new Observer<PurchaseReportResponse>() {
            @Override
            public void onChanged(PurchaseReportResponse response) {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    errorMessage(requireActivity().getApplication(), " " + response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {

                    setDataInAssociationSpinner(response);

                }
            }
        });
// for page data
        try {
            storeListViewModel.getStockList(getActivity(), "", binding.stockFilter.itemNameEt.getText().toString(), "", "", "", "").observe(getViewLifecycleOwner(), response -> {

                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getLists().isEmpty() || response.getLists() == null) {
                    managePaginationAndFilter();
                    return;
                }
                stockStoreLists = new ArrayList<>();
                stockStoreLists.clear();
                stockStoreLists.addAll(response.getStoreList());
                storeNameList = new ArrayList<>();
                storeNameList.clear();

                try {
                    for (int i = 0; i < stockStoreLists.size(); i++) {
                        storeNameList.add("" + stockStoreLists.get(i).getStoreName());
                        if (storeSelectedId != null) {
                            if (stockStoreLists.get(i).getStoreID().equals(storeSelectedId)) {
                                binding.stockFilter.store.setSelection(i);
                            }
                        }
                    }
                    binding.stockFilter.store.setItem(storeNameList);

                } catch (Exception e) {
                }
            });

        } catch (Exception e) {
        }

    }

    private void setDataInAssociationSpinner(PurchaseReportResponse response) {

        /** for category List */
        categoryNameList = new ArrayList<>();
        categoryNameList.clear();
        purchaseCategoryLists = new ArrayList<>();
        purchaseCategoryLists.clear();
        purchaseCategoryLists.addAll(response.getCategoryList());

        try {
            for (int i = 0; i < response.getCategoryList().size(); i++) {
                categoryNameList.add("" + response.getCategoryList().get(i).getCategory());
                if (categoryId != null) {
                    if (categoryId.equals(response.getCategoryList().get(i).getCategoryID())) {
                        binding.stockFilter.selectCategory.setSelection(i);
                    }
                }
            }
        } catch (Exception e) {
        }
        binding.stockFilter.selectCategory.setItem(categoryNameList);


        /** for brandList */
        brandNameList = new ArrayList<>();
        brandNameList.clear();

        brandLists = new ArrayList<>();
        brandLists.clear();
        brandLists.addAll(response.getBrandList());

        try {
            for (int i = 0; i < response.getBrandList().size(); i++) {
                brandNameList.add("" + response.getBrandList().get(i).getBrandName());
                if (brandId != null) {
                    if (brandId.equals(response.getBrandList().get(i).getBrandID())) {
                        binding.stockFilter.selectBrand.setSelection(i);
                    }
                }
            }
        } catch (Exception e) {
        }
        binding.stockFilter.selectBrand.setItem(brandNameList);


        millerNameList = new ArrayList<>();
        millerNameList.clear();
        millerLists = new ArrayList<>();
        millerLists.clear();

        try {
            if (response.getMillerList() != null) {
                millerLists.addAll(response.getMillerList());

                for (int i = 0; i < response.getMillerList().size(); i++) {
                    millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
                    if (millerProfileId != null) {
                        if (millerProfileId.equals(response.getMillerList().get(i).getStoreID())) {
                            binding.stockFilter.enterprise.setSelection(i);
                        }
                    }
                }
                binding.stockFilter.enterprise.setItem(millerNameList);
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    private void getStoreListFromServer() {
        storeListViewModel.getStoreList(getActivity(), String.valueOf(pageNumber)).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);

            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(requireActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }

            // for enter prise list
            enterprizeList = new ArrayList<>();
            enterprizeList.addAll(response.getEnterprizeList());
            if (!response.getEnterprizeList().isEmpty()) {
                EnterPriseAdapterForStore adapter = new EnterPriseAdapterForStore(getActivity(), response.getEnterprizeList(), this);
                binding.enterPriseList.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.enterPriseList.setAdapter(adapter);
            }

            if (response.getEnterprizeList().isEmpty() || response.getEnterprizeList() == null) {
                binding.enterPriseList.setVisibility(View.GONE);
                binding.enterPriseListNotFound.setVisibility(View.VISIBLE);
            }


            // for store list
            if (response.getStoreLst().isEmpty() || response.getStoreLst() == null) {
                if (isFirstLoad > 0) {
                    endScroll = true;
                    pageNumber -= 1;
                    return;
                }
                binding.storeRv.setVisibility(View.GONE);
                binding.storeListDataNoFoundTv.setVisibility(View.VISIBLE);
                return;
            }

            isFirstLoad += 1;
            linearLayoutManager = new LinearLayoutManager(getContext());
            storeLists.addAll(response.getStoreLst());
            binding.storeRv.setLayoutManager(linearLayoutManager);
            StoreListAdapter storeListAdapter = new StoreListAdapter(getActivity(), storeLists, StoreListFragment.this);
            binding.storeRv.setAdapter(storeListAdapter);
        });

    }

    private void getStockList() {
        if (manage == 0) {
            storeListViewModel.getStockList(getActivity(), String.valueOf(pageNumber), binding.stockFilter.itemNameEt.getText().toString(), storeSelectedId, brandId, categoryId, millerProfileId).observe(getViewLifecycleOwner(), response -> {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);

                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getLists().isEmpty() || response.getLists() == null) {
                    managePaginationAndFilter();
                    return;
                }

                manageFilterBtnAndRvAndDataNotFound();
                stockLists.addAll(response.getLists());
                linearLayoutManager = new LinearLayoutManager(getContext());
                binding.storeRv.setLayoutManager(linearLayoutManager);
                StockInformationListAdapter stockListAdapter = new StockInformationListAdapter(getActivity(), stockLists, getView());
                binding.storeRv.setAdapter(stockListAdapter);


            });

        }

        if (manage == 1) {
            progressDialog.dismiss();
            binding.progress.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getContext());
            binding.storeRv.setLayoutManager(linearLayoutManager);
            StockInformationListAdapter stockListAdapter = new StockInformationListAdapter(getActivity(), stockLists, getView());
            binding.storeRv.setAdapter(stockListAdapter);
        }


    }


    private void getPreviousFragmentData() {
        try {
            productId = getArguments().getString("productId");
            porsion = getArguments().getString("porson");
            binding.toolbar.toolbarTitle.setText(getArguments().getString("pageName"));
        } catch (Exception e) {
        }
    }

    private void managePaginationAndFilter() {
        if (isFirstLoad == 0) { // if filter time list data is  null.  so then, data_not_found will be visible
            binding.storeRv.setVisibility(View.GONE);
            binding.noDataFound.setVisibility(View.VISIBLE);
            return;
        }
        if (isFirstLoad > 0) {//for scrolling off
            endScroll = true;//means scroll off
            pageNumber -= 1;
            return;
        }
        return;
    }

    private void manageFilterBtnAndRvAndDataNotFound() {
        isFirstLoad += 1;
        //for filter
        // sometime filter list data came null when, data_not_found have visible,
        // And again search comes data in list by the others filter parameter.that for recycler view visible
        binding.noDataFound.setVisibility(View.GONE);
        binding.storeRv.setVisibility(View.VISIBLE);
    }


    @Override
    public void onStart() {
        super.onStart();
        getPreviousFragmentData();
        if (manage == 0) {
            storeLists.clear();
            stockLists.clear();
        }

        storeSelectedId = null;
        millerProfileId = null;
        brandId = null;
        categoryId = null;
        /** get previous fragment data*/

        getAllListFromServer();
        try {
            updateCurrentUserPermission(getActivity());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }


    public void updateCurrentUserPermission(FragmentActivity activity) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        currentPermissionViewModel.getCurrentUserRealtimePermissions(
                PreferenceManager.getInstance(activity).getUserCredentials().getToken(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserId(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserName()
        ).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                Toasty.error(activity, "Something Wrong", Toasty.LENGTH_LONG).show();
                return;
            }
            if (response.getStatus() == 400) {
                Toasty.info(activity, "" + response.getMessage(), Toasty.LENGTH_LONG).show();
            }
            try {
                LoginResponse loginResponse = PreferenceManager.getInstance(activity).getUserCredentials();
                if (loginResponse != null) {
                    loginResponse.setPermissions(response.getMessage());
                    loginResponse.setToken(response.getToken());
                    PreferenceManager.getInstance(activity).saveUserCredentials(loginResponse);
                }
            } catch (Exception e) {
                infoMessage(getActivity().getApplication(), "" + e.getMessage());
                Log.d("ERROR", "" + e.getMessage());
            }
        });
    }


    private void setOnClick() {
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.toolbar.addBtn.setOnClickListener(this);
        binding.stockFilter.filterSearchBtn.setOnClickListener(this);
        binding.stockFilter.resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterBtn:
                if (binding.expandableView.isExpanded()) {
                    binding.expandableView.setExpanded(false);
                    return;
                }
                binding.expandableView.setExpanded(true);
                break;
            case R.id.filterSearchBtn:
                manage = 0;
                pageNumber = 1;
                endScroll = false;
                isFirstLoad = 0;
                stockLists.clear();


                if(!binding.stockFilter.itemNameEt.getText().toString().isEmpty()){
                    storeSelectedId=null;
                    brandId=null;
                    categoryId=null;
                    millerProfileId=null;
                    binding.stockFilter.store.clearSelection();
                    binding.stockFilter.selectBrand.clearSelection();
                    binding.stockFilter.selectCategory.clearSelection();
                    binding.stockFilter.enterprise.clearSelection();
                }

                getAllListFromServer();
                break;
            case R.id.resetBtn:
                manage = 0;
                pageNumber = 1;
                storeSelectedId = null;
                millerProfileId = null;
                brandId = null;
                categoryId = null;
                endScroll = false;
                isFirstLoad = 0;
                stockLists.clear();
                binding.stockFilter.itemNameEt.setText("");
                getAllListFromServer();
                break;
            case R.id.addBtn:
                try {
                    /**
                     * only miller can create store
                     */

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getActivity()).getUserCredentials().getPermissions()).contains(15)) {
                        MyApplication.addStore(getActivity(), enterprizeList, getViewLifecycleOwner());
                    } else {
                        infoMessage(getActivity().getApplication(), PermissionUtil.permissionMessage);
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
                break;
        }
    }

    @Override
    public void statusManage(int position, String id) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        storeListViewModel.statusManage(getActivity(), id).observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 500) {
                        return;
                    }
                    if (response.getStatus() == 200) {
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        storeLists.clear();
                        getAllListFromServer();
                    }
                }
        );
    }

    @Override
    public void getPosition(int position, String id) {
        this.storeId = id;
        showDialog("Do You Want to delete store ?");
    }

    @Override
    public void getSupplierPosition(int position, String id) {

    }

    @Override
    public void getDataForEditStore(int position, String enterPriseID, String storeFullName, String storeShortname, String storeAddress, String storeId) {
        enterPriseId = enterPriseID;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams")
        AddStoreDialogLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.add_store_dialog_layout, null, false);
        AlertDialog alertDialog;
        binding.btnOk.setText("Update");
        binding.addStoreTvLevel.setText("Update Store");

        //set previous data
        binding.fullNameEt.setText("" + storeFullName);
        binding.shortNameEt.setText("" + storeShortname);
        if (storeAddress != null) {
            binding.addressEt.setText("" + storeAddress);
        }

        if (enterprizeList != null) {
            List<String> nameList = new ArrayList<>();
            nameList.clear();
            try {
                for (int i = 0; i < enterprizeList.size(); i++) {
                    if (enterPriseID != null) {
                        if (enterPriseId.equals(enterprizeList.get(i).getStoreID())) {
                            binding.enterprise.setSelection(i);
                        }
                    }
                    nameList.add("" + enterprizeList.get(i).getStoreName());
                }
            } catch (Exception e) {
            }
            binding.enterprise.setItem(nameList);
        }


        builder.setView(binding.getRoot());

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        binding.btnOk.setOnClickListener(v -> {
            if (binding.fullNameEt.getText().toString().isEmpty()) {
                binding.fullNameEt.setError("Empty");
                binding.fullNameEt.requestFocus();
                return;
            }
            if (binding.shortNameEt.getText().toString().isEmpty()) {
                binding.shortNameEt.setError("Empty");
                binding.shortNameEt.requestFocus();
                return;
            }

            storeListViewModel.editStore(getActivity(), enterPriseId, binding.fullNameEt.getText().toString(), binding.shortNameEt.getText()
                    .toString(), binding.addressEt.getText().toString(), storeId).observe(getViewLifecycleOwner(), response -> {
                if (response == null) {
                    Toasty.error(getContext(), "Something Wrong Contact to Support \n", Toasty.LENGTH_LONG).show();
                    return;
                }
                if (response.getStatus() == 400) {
                    Toasty.info(getContext(), "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                    return;
                }
                Toasty.success(getContext(), "Successful \n", Toasty.LENGTH_LONG).show();
                try {
                    storeLists.clear();
                    getStoreListFromServer();

                } catch (Exception e) {
                    Log.d("dfsaa", e.getMessage());
                }
            });

            alertDialog.dismiss();
            return;

        });
        binding.enterprise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enterPriseId = enterprizeList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.cancel.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }


    @Override
    public void save() {
        submit();
    }

    private void submit() {
        brandViewModel.deleteStore(getActivity(), storeId).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            storeLists.clear();
            pageNumber = 1;
            getStoreListFromServer();
            binding.storeRv.getAdapter().notifyDataSetChanged();
        });

    }

    @Override
    public void imageUri(Intent uri) {

    }
}