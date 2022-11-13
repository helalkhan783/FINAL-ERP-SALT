package com.ismos_salt_erp.view.fragment.stock;

import android.app.ProgressDialog;
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
import com.ismos_salt_erp.adapter.ReconciliationDeclineListAdapter;
import com.ismos_salt_erp.adapter.ReconciliationPendingListAdapter;
import com.ismos_salt_erp.databinding.FragmentStockAlllistBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.utils.ReconciliationUtils;
import com.ismos_salt_erp.utils.StockUtils;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineReconciliationList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineTransferredList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockItem;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingReconciliationList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingTransferredList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockReconciliationHistoryList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockReconciliationHistoryListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockStore;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockTransferHistoryList;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockTransferHistoryListResponse;
import com.ismos_salt_erp.view.fragment.stock.list_adapter.DeclineTransferredListAdapter;
import com.ismos_salt_erp.view.fragment.stock.list_adapter.StockPendingTransferredLisAdapter;
import com.ismos_salt_erp.view.fragment.stock.list_adapter.StockReconciliationHistoryListAdapter;
import com.ismos_salt_erp.view.fragment.stock.list_adapter.StockTransferHistoryListAdapter;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class StockAllListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener {
    private FragmentStockAlllistBinding binding;
    private String portion;//get previousFragment
    private StockListViewModel stockListViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;
    private MyDatabaseHelper myDatabaseHelper;

    /**
     * for pagination
     */
    private boolean loading = true;
    private ProgressDialog progressDialog;
    private boolean isStartDate = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;
    boolean click = false;
    private boolean endScroll = false;
    LinearLayoutManager linearLayoutManager;


    private List<StockTransferHistoryList> stockTransferHistoryLists = new ArrayList<>();
    private List<StockPendingTransferredList> pendingTransferredLists = new ArrayList<>();
    private List<StockDeclineTransferredList> declineTransferredLists = new ArrayList<>();
    private List<StockReconciliationHistoryList> stockReconciliationHistoryLists = new ArrayList<>();

    private List<StockPendingReconciliationList> stockPendingReconciliationLists = new ArrayList<>();

    private List<StockDeclineReconciliationList> stockDeclineReconciliationLists = new ArrayList<>();


    /**
     * for Item List
     */
    List<String> itemNameList;
    List<StockItem> stockItemList;
    /**
     * for transfer store & reconciliationSore
     */
    List<StockStore> stockStoreList;
    List<String> storeNameList;
    /**
     * for Reconciliation Enterprise
     */

    List<String> enterPriseNameList;

    /**
     * setReconciliationType
     */
    List<String> typeNameList;

    String itemId, startDate, endDate, storeId, enterPriseId, type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_alllist, container, false);
        stockListViewModel = new ViewModelProvider(this).get(StockListViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        progressDialog = new ProgressDialog(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);

        setOnClick();

        /** get previousFragment data*/
        getPreviousFragmentData();

        /** back Control*/

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            pageNumber = 1;
            getActivity().onBackPressed();

        });

        /**
         * get List data from server
         */
        getAllList();


        /** for pagination **/
        binding.stockRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    binding.expandableView.setExpanded(false);//collaps expandable view up scrolling time

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

                            getAllList();

                            loading = true;
                        }
                    }
                }
            }
        });


        binding.sale.selectItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemId = stockItemList.get(position).getProductID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.sale.selectStoreTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enterPriseId = stockStoreList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.sale.selectStoreFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeId = stockStoreList.get(position).getStoreID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.sale.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = typeNameList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void getAllList() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.stockRv, binding.dataNotFound)) {
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


        /** for stock transferred History in */
        if (portion.equals(StockUtils.transferredInHistoryList)) {
            binding.toolbar.toolbarTitle.setText(portion);

            getTransferredHistoryList();//for transfer in
        }

        /** for stock transferred History out */
        if (portion.equals(StockUtils.transferredOutHistoryList)) {
            binding.toolbar.toolbarTitle.setText(portion);
            getTransferHistoryOutList();//for transfer out
        }
        /** pending Transferred List **/
        if (portion.equals(StockUtils.pendingTransferredList)) {
            binding.toolbar.toolbarTitle.setText("Pending Transferred List");

            getPendingTransferredList();
        }
        /**
         * decline Transferred List
         */
        if (portion.equals(StockUtils.declineTransferredList)) {
            binding.toolbar.toolbarTitle.setText("Declined Transferred List");

            getDeclineTransferredList();
        }


        /**
         * reconciliation History List
         */
        if (portion.equals(ReconciliationUtils.reconciliationHistoryList)) {
            binding.toolbar.toolbarTitle.setText("Reconciliation History List");
            getReconciliationHistoryList();
        }


        /**
         pending Reconciliation List
         */
        if (portion.equals(ReconciliationUtils.pendingReconciliationList)) {
            binding.toolbar.toolbarTitle.setText("Pending Reconciliation List");
            getPendingReconciliationList();
        }

        /**
         decline Reconciliation List
         */
        if (portion.equals(ReconciliationUtils.declinedReconciliationList)) {
            binding.toolbar.toolbarTitle.setText("Declined Reconciliation List");
            getDeclinedReconciliationList();
        }


    }

    private void getReconciliationHistoryList() {
        stockListViewModel.getReconciliationHistoryList(getActivity(), String.valueOf(pageNumber), startDate, endDate, storeId, enterPriseId, itemId, type).observe(getViewLifecycleOwner(), new Observer<StockReconciliationHistoryListResponse>() {
            @Override
            public void onChanged(StockReconciliationHistoryListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();
                    } else {

                        manageFilterBtnAndRvAndDataNotFound();

                        stockReconciliationHistoryLists.addAll(response.getLists());
                        StockReconciliationHistoryListAdapter adapter = new StockReconciliationHistoryListAdapter(getActivity(), stockReconciliationHistoryLists);
                        binding.stockRv.setLayoutManager(linearLayoutManager);
                        binding.stockRv.setHasFixedSize(true);
                        binding.stockRv.setAdapter(adapter);

                        /** set data in ItemSelect spinner*/
                        itemSelectList(response.getItemList());
/** set data in storeFrom & storeTo  spinner*/
                        setDataInReconciliationStore(response.getStoreList());
/** set enterideList*/
                        setReconciliationEnterPrise(response.getEnterprizeList());
                        /** setReconciliationType */

                        typeNameList = new ArrayList<>();
                        typeNameList.addAll(Arrays.asList(response.getType().getDamage(), response.getType().getIncrease(), response.getType().getLost(), response.getType().getExpire()));
                        for (int i = 0; i < typeNameList.size(); i++) {
                            if (type != null) {
                                if (type.equals(typeNameList.get(i))) {
                                    binding.sale.type.setSelection(i);
                                }
                            }

                            binding.sale.type.setItem(typeNameList);
                        }

                    }
                }
            }
        });
    }

    private void getPendingReconciliationList() {

        stockListViewModel.getPendingReconciliationList(getActivity(), String.valueOf(pageNumber), startDate, endDate, storeId, enterPriseId, itemId, type).observe(getViewLifecycleOwner(), new Observer<StockPendingReconciliationListResponse>() {
            @Override
            public void onChanged(StockPendingReconciliationListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();

                    } else {
                        manageFilterBtnAndRvAndDataNotFound();

                        /**
                         * now set data to list
                         */
                        stockPendingReconciliationLists.addAll(response.getLists());
                        ReconciliationPendingListAdapter adapter = new ReconciliationPendingListAdapter(getActivity(), stockPendingReconciliationLists);
                        binding.stockRv.setLayoutManager(linearLayoutManager);
                        binding.stockRv.setHasFixedSize(true);
                        binding.stockRv.setAdapter(adapter);

                        /** set data in ItemSelect spinner*/
                        itemSelectList(response.getItemList());
                        /** set data in storeFrom & storeTo  spinner*/
                        setDataInReconciliationStore(response.getStoreList());
                        /** set enterpriseList*/
                        setReconciliationEnterPrise(response.getEnterprizeList());
                        /** setReconciliationType */
                        typeNameList = new ArrayList<>();
                        typeNameList.addAll(Arrays.asList(response.getType().getDamage(), response.getType().getIncrease(), response.getType().getLost(), response.getType().getExpire()));
                        for (int i = 0; i < typeNameList.size(); i++) {
                            if (type != null) {
                                if (type.equals(typeNameList.get(i))) {
                                    binding.sale.type.setSelection(i);
                                }
                            }

                            binding.sale.type.setItem(typeNameList);
                        }
                    }
                }
            }
        });
    }

    private void getDeclineTransferredList() {

        stockListViewModel.getStockDeclineTransferredList(getActivity(), String.valueOf(pageNumber), storeId, enterPriseId, startDate, endDate, itemId).observe(getViewLifecycleOwner(), new Observer<StockDeclineTransferredListResponse>() {
            @Override
            public void onChanged(StockDeclineTransferredListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();


                    } else {
                        manageFilterBtnAndRvAndDataNotFound();
                        declineTransferredLists.addAll(response.getLists());
                        String manageView = "list";

                        DeclineTransferredListAdapter adapter = new DeclineTransferredListAdapter(getActivity(), declineTransferredLists, manageView);
                        binding.stockRv.setLayoutManager(linearLayoutManager);
                        binding.stockRv.setHasFixedSize(true);
                        binding.stockRv.setAdapter(adapter);

                        /** set data in ItemSelect spinner*/
                        itemSelectList(response.getItemList());
/** set data in storeFrom & storeTo  spinner*/
                        setDataInStore(response.getStoreList());
                    }
                }
            }
        });


    }

    private void getPendingTransferredList() {
        stockListViewModel.getStockPendingTransferredList(getActivity(), String.valueOf(pageNumber), storeId, enterPriseId, startDate, endDate, itemId).observe(getViewLifecycleOwner(), new Observer<StockPendingTransferredListResponse>() {
            @Override
            public void onChanged(StockPendingTransferredListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }
                if (response.getLists() == null || response.getLists().isEmpty()) {
                    managePaginationAndFilter();

                } else {
                    manageFilterBtnAndRvAndDataNotFound();

                    pendingTransferredLists.addAll(response.getLists());
                    StockPendingTransferredLisAdapter adapter = new StockPendingTransferredLisAdapter(getActivity(), pendingTransferredLists);
                    binding.stockRv.setLayoutManager(linearLayoutManager);
                    binding.stockRv.setHasFixedSize(true);
                    binding.stockRv.setAdapter(adapter);

                    /** set data in ItemSelect spinner*/
                    itemSelectList(response.getItemList());

                    /** set data in storeFrom & storeTo  spinner*/
                    setDataInStore(response.getStoreList());
                }

            }
        });
    }

    private void getDeclinedReconciliationList() {
        stockListViewModel.getDeclineReconciliationList(getActivity(), String.valueOf(pageNumber), startDate, endDate, storeId, enterPriseId, itemId, type).observe(getViewLifecycleOwner(), new Observer<StockDeclineReconciliationListResponse>() {
            @Override
            public void onChanged(StockDeclineReconciliationListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {
                    if (response.getLists() == null || response.getLists().isEmpty()) {
                        managePaginationAndFilter();
                    } else {
                        manageFilterBtnAndRvAndDataNotFound();

                        stockDeclineReconciliationLists.addAll(response.getLists());
                        ReconciliationDeclineListAdapter adapter = new ReconciliationDeclineListAdapter(getActivity(), stockDeclineReconciliationLists);
                        binding.stockRv.setLayoutManager(linearLayoutManager);
                        binding.stockRv.setHasFixedSize(true);
                        binding.stockRv.setAdapter(adapter);


                        /** set data in ItemSelect spinner*/
                        itemSelectList(response.getItemList());
/** set data in storeFrom & storeTo  spinner*/
                        setDataInReconciliationStore(response.getStoreList());
/** set enterideList*/
                        setReconciliationEnterPrise(response.getEnterprizeList());
                        /** setReconciliationType */
                        typeNameList = new ArrayList<>();
                        typeNameList.addAll(Arrays.asList(response.getType().getDamage(), response.getType().getIncrease(), response.getType().getLost(), response.getType().getExpire()));
                        for (int i = 0; i < typeNameList.size(); i++) {
                            if (type != null) {
                                if (type.equals(typeNameList.get(i))) {
                                    binding.sale.type.setSelection(i);
                                }
                            }

                            binding.sale.type.setItem(typeNameList);
                        }
                    }
                }
            }
        });


    }

    private void getTransferredHistoryList() {
        stockListViewModel.getStockTransferHistoryList(getActivity(),
                String.valueOf(pageNumber), "101", storeId, enterPriseId, startDate, endDate, itemId).observe(getViewLifecycleOwner(), new Observer<StockTransferHistoryListResponse>() {
            @Override
            public void onChanged(StockTransferHistoryListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }
                if (response.getLists() == null || response.getLists().isEmpty()) {
                    managePaginationAndFilter();
                } else {
                    manageFilterBtnAndRvAndDataNotFound();

                    stockTransferHistoryLists.addAll(response.getLists());
                    StockTransferHistoryListAdapter adapter = new StockTransferHistoryListAdapter(getActivity(), stockTransferHistoryLists, true, "101");
                    binding.stockRv.setLayoutManager(linearLayoutManager);
                    binding.stockRv.setHasFixedSize(true);
                    binding.stockRv.setAdapter(adapter);

                    /** set data in ItemSelect spinner*/
                    itemSelectList(response.getItemList());
/** set data in storeFrom & storeTo  spinner*/
                    setDataInStore(response.getStoreList());
                }
            }
        });
    }


    private void setReconciliationEnterPrise(List<StockStore> list) {
        stockStoreList = new ArrayList<>();
        stockStoreList.clear();
        enterPriseNameList = new ArrayList<>();
        enterPriseNameList.clear();
        stockStoreList.addAll(list);
        for (int i = 0; i < list.size(); i++) {
            enterPriseNameList.add("" + list.get(i).getStoreName());
            if (enterPriseId != null) {
                if (list.get(i).getStoreID().equals(enterPriseId)) {
                    binding.sale.selectStoreTo.setSelection(i);
                }
            }
        }
        binding.sale.selectStoreTo.setItem(enterPriseNameList);

    }

    private void setDataInReconciliationStore(List<StockStore> storeList) {
        stockStoreList = new ArrayList<>();
        stockStoreList.clear();
        storeNameList = new ArrayList<>();
        storeNameList.clear();

        stockStoreList.addAll(storeList);

        for (int i = 0; i < storeList.size(); i++) {
            storeNameList.add("" + storeList.get(i).getStoreName());
            if (storeId != null) {
                if (storeList.get(i).getStoreID().equals(storeId)) {
                    binding.sale.selectStoreFrom.setSelection(i);
                }
            }
        }
        binding.sale.selectStoreFrom.setItem(storeNameList);


    }

    private void setDataInStore(List<StockStore> storeList) {
        storeNameList = new ArrayList<>();
        storeNameList.clear();
        stockStoreList = new ArrayList<>();
        stockStoreList.clear();
        stockStoreList.addAll(storeList);

        for (int i = 0; i < storeList.size(); i++) {

            storeNameList.add("" + storeList.get(i).getStoreName());
            if (storeId != null) {
                if (stockStoreList.get(i).getStoreID().equals(storeId)) {
                    binding.sale.selectStoreFrom.setSelection(i);
                }
            }

            if (enterPriseId != null) {
                if (stockStoreList.get(i).getStoreID().equals(enterPriseId)) {
                    binding.sale.selectStoreTo.setSelection(i);
                }
            }
        }
        binding.sale.selectStoreFrom.setItem(storeNameList);
        binding.sale.selectStoreTo.setItem(storeNameList);

    }

    private void itemSelectList(List<StockItem> itemList) {
        itemNameList = new ArrayList<>();
        itemNameList.clear();

        stockItemList = new ArrayList<>();
        stockItemList.clear();
        stockItemList.addAll(itemList);

        for (int i = 0; i < itemList.size(); i++) {
            itemNameList.add("" + itemList.get(i).getProductTitle());
            if (itemId != null) {
                if (stockItemList.get(i).getProductID().equals(itemId)) {
                    binding.sale.selectItem.setSelection(i);
                }
            }
        }

        binding.sale.selectItem.setItem(itemNameList);

    }


    private void getPreviousFragmentData() {
        assert getArguments() != null;
        portion = getArguments().getString("portion");
        try {
            myDatabaseHelper.deleteAllData();
        } catch (Exception e) {
            Log.d("ERROR", e.getLocalizedMessage());
        }
    }


    private void getTransferHistoryOutList() {
        stockListViewModel.getStockTransferHistoryList(getActivity(), String.valueOf(pageNumber), "102", storeId, enterPriseId, startDate, endDate, itemId).observe(getViewLifecycleOwner(), new Observer<StockTransferHistoryListResponse>() {
            @Override
            public void onChanged(StockTransferHistoryListResponse response) {
                progressDialog.dismiss();
                binding.progress.setVisibility(View.GONE);
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }
                if (response.getLists() == null || response.getLists().isEmpty()) {
                    managePaginationAndFilter();

                } else {
                    manageFilterBtnAndRvAndDataNotFound();

                    stockTransferHistoryLists.addAll(response.getLists());
                    StockTransferHistoryListAdapter adapter = new StockTransferHistoryListAdapter(getActivity(), stockTransferHistoryLists, false, "102");
                    binding.stockRv.setLayoutManager(linearLayoutManager);
                    binding.stockRv.setHasFixedSize(true);
                    binding.stockRv.setAdapter(adapter);

                    /** set data in ItemSelect spinner*/
                    itemSelectList(response.getItemList());
/** set data in storeFrom & storeTo  spinner*/
                    setDataInStore(response.getStoreList());
                }
            }
        });
    }

    private void setOnClick() {
        binding.sale.startDate.setOnClickListener(this);
        binding.sale.EndDate.setOnClickListener(this);
        binding.sale.filterSearchBtn.setOnClickListener(this);
        binding.sale.resetBtn.setOnClickListener(this);
        binding.toolbar.backbtn.setOnClickListener(this);
        binding.toolbar.filterBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                timePicker();
                isStartDate = true;
                break;
            case R.id.EndDate:
                timePicker();
                break;

            case R.id.backbtn:
                getActivity().onBackPressed();
                break;
            case R.id.filterBtn:
                ExpandableView response = new ExpandableView(getActivity(), binding.expandableView);
                response.response();
                break;
            case R.id.filterSearchBtn:
                hideKeyboard(getActivity());
                progressDialog.dismiss();
                pageNumber = 1;
                //for filter
                isFirstLoad = 0;
                stockTransferHistoryLists.clear();
                pendingTransferredLists.clear();
                declineTransferredLists.clear();
                stockReconciliationHistoryLists.clear();
                stockPendingReconciliationLists.clear();
                stockDeclineReconciliationLists.clear();
                getAllList();
                break;
            case R.id.resetBtn:
                hideKeyboard(getActivity());
                startDate = null;
                endDate = null;
                itemId = null;
                enterPriseId = null;
                type = null;
                storeId = null;
                binding.sale.startDate.setText("");
                binding.sale.EndDate.setText("");

                //for filter
                isFirstLoad = 0;
                stockTransferHistoryLists.clear();
                pendingTransferredLists.clear();
                declineTransferredLists.clear();
                stockReconciliationHistoryLists.clear();
                stockPendingReconciliationLists.clear();
                stockDeclineReconciliationLists.clear();

                pageNumber = 1;
                if (nullChecked()) {
                    binding.dataNotFound.setVisibility(View.GONE);
                    binding.stockRv.setVisibility(View.VISIBLE);
                    getAllList();

                }

                if (nullChecked() && type == null) {
                    binding.dataNotFound.setVisibility(View.GONE);
                    binding.stockRv.setVisibility(View.VISIBLE);
                    getAllList();
                }


                break;
        }
    }


    private void managePaginationAndFilter() {

        if (isFirstLoad == 0) { // if filter time list is null.  so then, data_not_found will be visible
            binding.stockRv.setVisibility(View.GONE);
            binding.dataNotFound.setVisibility(View.VISIBLE);
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
        binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
        //for filter
        // sometime filter list data came null when, data_not_found have visible,
        // And again search comes data in list by the others filter parameter.that for recycler view visible
        binding.dataNotFound.setVisibility(View.GONE);
        binding.stockRv.setVisibility(View.VISIBLE);
    }

    private boolean nullChecked() {
        if (startDate == null && endDate == null && enterPriseId == null && storeId == null) {
            return true;
        }
        return false;
    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);

        if (!isStartDate) {
            binding.sale.EndDate.setText(selectedDate);
            endDate = binding.sale.EndDate.getText().toString();
        } else {
            binding.sale.startDate.setText(selectedDate);
            startDate = binding.sale.startDate.getText().toString();
            isStartDate = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        pageNumber = 1;
        stockReconciliationHistoryLists.clear();
        stockPendingReconciliationLists.clear();
        stockDeclineReconciliationLists.clear();
        declineTransferredLists.clear();
        stockTransferHistoryLists.clear();
        pendingTransferredLists.clear();
        /** These codes have been created because,
         *  the filter layout of reconciliation and transfer is the same but different view*/

        if (portion.equals(ReconciliationUtils.declinedReconciliationList) || portion.equals(ReconciliationUtils.reconciliationHistoryList) || portion.equals(ReconciliationUtils.pendingReconciliationList)) {
            binding.sale.typeLayout.setVisibility(View.VISIBLE);
            binding.sale.from.setText("Select Store");
            binding.sale.to.setText("Select Enterprise");
        }
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


}