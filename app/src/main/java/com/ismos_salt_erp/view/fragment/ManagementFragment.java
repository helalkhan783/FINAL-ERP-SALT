package com.ismos_salt_erp.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.AccountsSubcategoryAdapter;
import com.ismos_salt_erp.adapter.CustomerChildAdapter;
import com.ismos_salt_erp.adapter.DashboarReportAdapter;
import com.ismos_salt_erp.adapter.InboxAdapter;
import com.ismos_salt_erp.adapter.MillerChildAdapter;
import com.ismos_salt_erp.adapter.MonitoringAdapter;
import com.ismos_salt_erp.adapter.ProductionAdapter;
import com.ismos_salt_erp.adapter.PurchaseAdapter;
import com.ismos_salt_erp.adapter.PurchaseReqAdapter;
import com.ismos_salt_erp.adapter.QcQaAdapter;
import com.ismos_salt_erp.adapter.ReconciliationSubAdapter;
import com.ismos_salt_erp.adapter.ReportAdapter;
import com.ismos_salt_erp.adapter.SaleManagementAdapter;
import com.ismos_salt_erp.adapter.SettingsAdapter;
import com.ismos_salt_erp.adapter.SubCategoryAdapter;
import com.ismos_salt_erp.adapter.SupplierAdapter;
import com.ismos_salt_erp.adapter.UserAdapter;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.localDatabase.MyPackagingDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PackatingDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.InboxListResponse;
import com.ismos_salt_erp.serverResponseModel.ListMonitorModel;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.serverResponseModel.SaleDeclinedList;
import com.ismos_salt_erp.serverResponseModel.SaleHistoryList;
import com.ismos_salt_erp.serverResponseModel.SalePendingList;
import com.ismos_salt_erp.serverResponseModel.SaleReturnHistoryList;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.BankmanagementUtils;
import com.ismos_salt_erp.utils.CustomersUtil;
import com.ismos_salt_erp.utils.DashBoardReportUtils;
import com.ismos_salt_erp.utils.HomeUtils;
import com.ismos_salt_erp.utils.ManagementUtils;
import com.ismos_salt_erp.utils.MillerUtils;
import com.ismos_salt_erp.utils.MonitoringUtil;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.ProductionUtils;
import com.ismos_salt_erp.utils.PurchaseRequisitionsUtils;
import com.ismos_salt_erp.utils.PurchaseUtill;
import com.ismos_salt_erp.utils.QcQaUtil;
import com.ismos_salt_erp.utils.ReconciliationUtils;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.utils.SaleUtil;
import com.ismos_salt_erp.utils.SalesRequisitionUtil;
import com.ismos_salt_erp.utils.SettingsUtil;
import com.ismos_salt_erp.utils.SupplierUtils;
import com.ismos_salt_erp.utils.UserUtil;
import com.ismos_salt_erp.view.fragment.accounts.receiveDue.DueCollectionFragment;
import com.ismos_salt_erp.view.fragment.inbox.ClickInboxList;
import com.ismos_salt_erp.view.fragment.salesRequisition.SalesRequisitionAdapter;
import com.ismos_salt_erp.view.fragment.stock.StockAdapter;
import com.ismos_salt_erp.utils.StockUtils;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.InboxViewModel;
import com.ismos_salt_erp.viewModel.PermissionViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import org.jetbrains.annotations.NotNull;

public class ManagementFragment extends BaseFragment implements ClickInboxList {
    private PermissionViewModel permissionViewModel;
    private InboxViewModel inboxViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    private MyPackagingDatabaseHelper helper;
    private PackatingDatabaseHelper packatingDatabaseHelper;
    private CurrentPermissionViewModel currentPermissionViewModel;

    private String root = "Management";
    private View view;
    private String previousParentItemName, inbox;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.subCategoryRv)
    RecyclerView subCategoryRv;
    @BindView(R.id.noDatasFound)
    TextView noDatasFound;
    BottomNavigationView bottomNavigationView;
    /**
     * for pagination
     */
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1;
    private List<Product> productList;
    private List<ListMonitorModel> monitorLists;
    private List<SalePendingList> salePendingLists;
    private List<SaleHistoryList> saleHistoryLists;
    private List<SaleReturnHistoryList> saleReturnHistoryLists;
    private List<SaleDeclinedList> saleDeclinedLists;

    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_management, container, false);
        ButterKnife.bind(this, view);
        DueCollectionFragment.HIDE_KEYBOARD(getActivity());
        permissionViewModel = ViewModelProviders.of(this).get(PermissionViewModel.class);
        inboxViewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);


        getDataFromPreviousFragment();
        loadRecyclerViewData();
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.dayBook:
                    try {
                        bundle.putString("portion", AccountsUtil.dayBook);
                        bundle.putString("PageName", "Day Book");
                        Navigation.findNavController(getView()).navigate(R.id.action_managementFragment_to_accountsListFragment2t, bundle);

                    } catch (Exception e) {
                    }

                    break;

                case R.id.cashBook:
                    try {
                        bundle.putString("portion", AccountsUtil.cash);
                        bundle.putString("PageName", "Cash");
                        Navigation.findNavController(getView()).navigate(R.id.action_managementFragment_to_accountsListFragment2t, bundle);
                    } catch (Exception e) {
                    }

                    break;

                case R.id.creditor:
                    bundle.putString("portion",  "Creditors");
                    bundle.putString("PageName", "Creditors");
                    bundle.putString("from", "HomeFragment");
                    Navigation.findNavController(getView()).navigate(R.id.action_managementFragment_to_transactionListFragment, bundle);                    break;

                case R.id.debitor:
                    bundle.putString("portion", "Debitors");
                    bundle.putString("PageName", "Debitors");
                    bundle.putString("from", "HomeFragment");
                    Navigation.findNavController(getView()).navigate(R.id.action_managementFragment_to_transactionListFragment, bundle);                    break;

                case R.id.home:
                    try {
                        Navigation.findNavController(getView()).navigate(R.id.action_managementFragment_to_homeFragment);

                    } catch (Exception e) {
                    }

                    break;
                /*  case R.id.chat:
                 *//**
                 * For open chat window
                 *//*
                    Crisp.configure(getContext(), CrispUtil.crispSecretKey);
                    Intent crispIntent = new Intent(getActivity(), ChatActivity.class);
                    startActivity(crispIntent);
                    break;*/
            }
            return true;
        });
        try {
            if (inbox.equals("Inbox")) {
                toolbarTitle.setText("Inbox List");


                /** for pagination **/
                subCategoryRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 0) { //check for scroll down
                            visibleItemCount = linearLayoutManager.getChildCount();
                            totalItemCount = linearLayoutManager.getItemCount();
                            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loading = false;
                                    pageNumber += 1;
                                    loadRecyclerViewData();

                                    loading = true;
                                }
                            }
                        }
                    }
                });

            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }


        return view;
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        previousParentItemName = getArguments().getString("Item");
        inbox = getArguments().getString("Item");
    }

    @SuppressLint("SetTextI18n")
    private void loadRecyclerViewData() {

        if (inbox != null) {
            if (inbox.equals("Inbox")) {
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.show();


                inboxViewModel.getInboxList(getActivity(), String.valueOf(pageNumber))
                        .observe(getViewLifecycleOwner(), response -> {
                            progressDialog.dismiss();
                            try {
                                if (response == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                    return;
                                }
                                if (response.getLists().isEmpty()) {
                                    subCategoryRv.setVisibility(View.GONE);
                                    noDatasFound.setVisibility(View.VISIBLE);
                                    return;
                                }
                                linearLayoutManager = new LinearLayoutManager(getContext());
                                subCategoryRv.setLayoutManager(linearLayoutManager);
                                InboxAdapter adapter = new InboxAdapter(getActivity(), response.getLists(), ManagementFragment.this);
                                subCategoryRv.setAdapter(adapter);
                            } catch (Exception e) {
                                Log.d("ERROR", "" + e.getMessage());
                            }

                        });

                return;
            }
        }

        subCategoryRv.setHasFixedSize(true);
        subCategoryRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        subCategoryRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        subCategoryRv.setNestedScrollingEnabled(false);

        permissionViewModel.getAccountPermission(getActivity())
                .observe(getViewLifecycleOwner(), permission -> {
                    SubCategoryAdapter adapter;
                    if (previousParentItemName.equals(HomeUtils.report)) {
                        ReportAdapter reportAdapter = new ReportAdapter(getActivity(), ReportUtils.reportNameList(), ReportUtils.reportImageList());
                        subCategoryRv.setAdapter(reportAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }
                    /**
                     * For item management
                     */
                    if (previousParentItemName.equals(HomeUtils.itemManagement)) {
                        adapter = new SubCategoryAdapter(getActivity(), ManagementUtils.getItemManagementImageList(), ManagementUtils.getItemManagementNameList());
                        subCategoryRv.setAdapter(adapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }
                    if (previousParentItemName.equals(ReportUtils.dashBoardReport)) {
                        DashboarReportAdapter dashboarReportAdapter = new DashboarReportAdapter(getActivity(), DashBoardReportUtils.dashBoardReportNameList(), DashBoardReportUtils.dashBoardReeportImageList(), getView());
                        subCategoryRv.setAdapter(dashboarReportAdapter);
                        toolbarTitle.setText(""+getArguments().get("pageName"));
                        return;
                    }

                    if (previousParentItemName.equals(ReportUtils.accountReports)) {
                        AccountAdapter accountAdapter =   new AccountAdapter(getActivity(), AccountReportUtils.accountReportNameList(), AccountReportUtils.accountReeportImageList(),getView());
                        subCategoryRv.setAdapter(accountAdapter);
                        toolbarTitle.setText(""+getArguments().get("pageName"));
                        return;
                    }

                    if (previousParentItemName.equals(HomeUtils.qcQa)) {
                        QcQaAdapter qcQaAdapter = new QcQaAdapter(getActivity(), QcQaUtil.qcQaNameList(), QcQaUtil.qcQaImageList());
                        subCategoryRv.setAdapter(qcQaAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }
                    if (previousParentItemName.equals("Miller")) {
                        MillerChildAdapter millerChildAdapter = new MillerChildAdapter(getActivity(), MillerUtils.getMillerNameList(), MillerUtils.getMillerImageList());

                        subCategoryRv.setAdapter(millerChildAdapter);
                        toolbarTitle.setText("Miller");
                        return;
                    }
                    if (previousParentItemName.equals("Settings")) {
                        View view = getView();
                        SettingsAdapter settingsAdapter = new SettingsAdapter(getActivity(), SettingsUtil.settingNameList(), SettingsUtil.settingImageList(), view);
                        subCategoryRv.setAdapter(settingsAdapter);
                        toolbarTitle.setText("Settings");
                        return;
                    }


                    if (previousParentItemName.equals(HomeUtils.monitoring)) {
                        MonitoringAdapter monitoringAdapter = new MonitoringAdapter(getActivity(), MonitoringUtil.getMonitoringNameList(), MonitoringUtil.getMonitoringImageList());
                        subCategoryRv.setAdapter(monitoringAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }
                    if (previousParentItemName.equals(HomeUtils.customers)) {
                        CustomerChildAdapter customerAdapter = new CustomerChildAdapter(getActivity(), CustomersUtil.customerNameList(), CustomersUtil.customerImageList());
                        subCategoryRv.setAdapter(customerAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }
                    if (previousParentItemName.equals(HomeUtils.sales)) {
                        List<Integer> saleImageList = SaleUtil.saleImageList();
                        List<String> saleNameList = SaleUtil.saleNameList();
                        SaleManagementAdapter saleManagementAdapter = new SaleManagementAdapter(getActivity(), saleNameList, saleImageList);
                        subCategoryRv.setAdapter(saleManagementAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }


                    if (previousParentItemName.equals(HomeUtils.purchases)) {
                        PurchaseAdapter purchaseAdapter = new PurchaseAdapter(getActivity(), PurchaseUtill.getPurchaseName(), PurchaseUtill.getPurchaseImage());
                        subCategoryRv.setAdapter(purchaseAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;

                    } if (previousParentItemName.equals(HomeUtils.salesReq)) {
                        SalesRequisitionAdapter salesRequisitionAdapter = new SalesRequisitionAdapter(getActivity(), SalesRequisitionUtil.getAllSaleRequisitionTitle(), SalesRequisitionUtil.getAllSaleRequisitionImage());
                        subCategoryRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        subCategoryRv.setAdapter(salesRequisitionAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;

                    }

                    if (previousParentItemName.equals(HomeUtils.production)) {
                        /**
                         * for handle all production with their child
                         */
                        ProductionAdapter purchaseAdapter = new ProductionAdapter(getActivity(), ProductionUtils.productionNameList(), ProductionUtils.productionImageList());
                        subCategoryRv.setAdapter(purchaseAdapter);
                        toolbarTitle.setText(previousParentItemName);
                        return;
                    }


                    if (previousParentItemName.equals(HomeUtils.stock)) {//here handle transfer
                        /**
                         * for handle all transfer
                         */
                        StockAdapter stockAdapter = new StockAdapter(getActivity(), StockUtils.getStockItemName(), StockUtils.getStockItemImage());
                        subCategoryRv.setAdapter(stockAdapter);
                        toolbarTitle.setText("Stock Transferred");
                        return;
                    }

                    if (previousParentItemName.equals(HomeUtils.reconciliation)) {
                        /**
                         * for handle all production with their child
                         */
                        ReconciliationSubAdapter reconciliationAdapter = new ReconciliationSubAdapter(getActivity(), ReconciliationUtils.getReconciliationItemName(), ReconciliationUtils.getReconciliationItemImage());
                        subCategoryRv.setAdapter(reconciliationAdapter);
                        toolbarTitle.setText("Reconciliation");
                        return;
                    }


                    if (previousParentItemName.equals(HomeUtils.user)) {
                        boolean havePermission = false;
                        if (permission.contains(PermissionUtil.UserList) || permission.contains(PermissionUtil.manageAll)) {
                            havePermission = true;
                        }
                        /**
                         * for handle all production with their child
                         */
                        UserAdapter reconciliationAdapter = new UserAdapter(getActivity(), UserUtil.userNameList(), UserUtil.userImageList(), havePermission);
                        subCategoryRv.setAdapter(reconciliationAdapter);
                        toolbarTitle.setText("User");
                        return;
                    }

                    if (previousParentItemName.equals("Purchase Requisition")){
                        PurchaseReqAdapter purchaseReqAdapter = new PurchaseReqAdapter(getActivity(), PurchaseRequisitionsUtils.getAllPurchaseRequisitionTitle(),PurchaseRequisitionsUtils.getAllPurchaseRequisitionImage());
                        subCategoryRv.setAdapter(purchaseReqAdapter);

                        toolbarTitle.setText("Purchase Requisition");

                    }

                    if (previousParentItemName.equals(HomeUtils.suppliers)) {
                        /**
                         * for handle all production with their child
                         */

                        View view = getView();
                        SupplierAdapter reconciliationAdapter = new SupplierAdapter(getActivity(), SupplierUtils.supplierNameList(), SupplierUtils.supplierImageList(), view);
                        subCategoryRv.setAdapter(reconciliationAdapter);
                        toolbarTitle.setText("Supplier");
                        return;
                    }
                    if (previousParentItemName.equals(HomeUtils.account)) {
                        /**
                         * For handle accounts portion
                         */
                        showAccountsPortionBasedOnPermission();
                        return;
                    }

                    if (previousParentItemName.equals("bankManagement")){
                        View view = getView();
                        com.ismos_salt_erp.view.fragment.BankManagementAdapter bankManagementAdapter = new com.ismos_salt_erp.view.fragment.BankManagementAdapter(getActivity(), BankmanagementUtils.bankItemNameList(), BankmanagementUtils.bankImageList(), view);
                        subCategoryRv.setAdapter(bankManagementAdapter);
                        toolbarTitle.setText("Bank Management");
                        return;
                    }


                });


        /**
         * for control home page account
         */
//        if (previousParentItemName.contains(HomeUtils.accounts)) {
    /*    permissionViewModel.getAccountPermission(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> permissions) {
                List<Integer> finalAccountImage = AccountsUtil.getAccountsChildImageList();
                List<String> finalAccountTitle = AccountsUtil.getAccountsChildNameList();

                *//**
         * if user have manage all permission user can access everything
         *//*
                if (permissions.contains(PermissionUtil.manageAll)) {
                    *//**
         * now show view to recyclerview
         *//*
                    AccountsSubcategoryAdapter accountAdapter = new AccountsSubcategoryAdapter(getActivity(), finalAccountImage, finalAccountTitle);
                    subCategoryRv.setAdapter(accountAdapter);
                } else {
                    *//**
         * if don't have manage all permission then user access permission  wise option like below
         *//*
         *//**
         * for show Received due if have permission
         *//*
                    if (!permissions.contains(PermissionUtil.showDueReceived)) {//here showDueReceived = 1058 means Manage receipt
                        if (finalAccountImage.contains(AccountsUtil.receiveDueImage)) {
                            finalAccountImage.remove(AccountsUtil.receiveDueImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.receiveDue)) {
                            finalAccountTitle.remove(AccountsUtil.receiveDue);
                        }
                    }

                    *//**
         * for show pay due if have permission
         *//*
                    if (!permissions.contains(PermissionUtil.showPayDue)) {//here 1062 means show (pay due) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payDueImage)) {
                            finalAccountImage.remove(AccountsUtil.payDueImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payDue)) {
                            finalAccountTitle.remove(AccountsUtil.payDue);
                        }
                    }

                    *//**
         * for show (pay due expense) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payDueExpense)) {//here 1070 means show (pay due expense) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payDueExpenseImage)) {
                            finalAccountImage.remove(AccountsUtil.payDueExpenseImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payDueExpense)) {
                            finalAccountTitle.remove(AccountsUtil.payDueExpense);
                        }
                    }

                    *//**
         * for show (payment instruction) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payInstruction)) {//here 1073 means show (payment instruction) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payInstructionImage)) {
                            finalAccountImage.remove(AccountsUtil.payInstructionImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payInstruction)) {
                            finalAccountTitle.remove(AccountsUtil.payInstruction);
                        }
                    }
                    *//**
         * for show (payment instruction list) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payInstructionList)) {//here 1336 means show (payment instruction) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payInstructionListImage)) {
                            finalAccountImage.remove(AccountsUtil.payInstructionListImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payInstructionList)) {
                            finalAccountTitle.remove(AccountsUtil.payInstructionList);
                        }
                    }
                    *//**
         * now show view to recyclerview
         *//*
                    AccountsSubcategoryAdapter accountAdapter = new AccountsSubcategoryAdapter(getActivity(), finalAccountImage, finalAccountTitle);
                    subCategoryRv.setAdapter(accountAdapter);
                }
            }
        });*/
        assert getArguments() != null;
        toolbarTitle.setText(getArguments().getString("Item"));//set toolbar title
        return;
//        }
       /* if (previousParentItemName.equals(HomeUtils.sales)) {//only for salesRequisition
            toolbarTitle.setText(previousParentItemName);
        } else {*/
//            toolbarTitle.setText(previousParentItemName + root);
//        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        helper = new MyPackagingDatabaseHelper(getContext());
        packatingDatabaseHelper = new PackatingDatabaseHelper(getContext());
        try {
            myDatabaseHelper.deleteAllData();
            helper.deleteAllData();
            packatingDatabaseHelper.deleteAllData();
            /**
             * now update current credentials
             */
            updateCurrentUserPermission(getActivity());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getLocalizedMessage());
        }
    }

    @Override
    public void click(InboxListResponse clickResponse) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        seenMessage(getActivity(), clickResponse);
    }

    public void seenMessage(FragmentActivity context, InboxListResponse response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.message_seen_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Welcome To Mis ERP");//set warning title
        tvMessage.setText("" + response.getMessage().replaceAll("\\<.*?\\>", ""));
        imageIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        bOk.setOnClickListener(v -> {
            inboxViewModel.sendSeenStatus(getActivity(), response.getId())
                    .observe(getViewLifecycleOwner(), seenResponse -> {
                        try {
                            if (seenResponse == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (seenResponse.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                return;
                            }
                            alertDialog.dismiss();
                            loadRecyclerViewData();
                        } catch (Exception e) {
                            alertDialog.dismiss();
                        }
                    });
        });
        alertDialog.show();
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
                return;
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


    private void showAccountsPortionBasedOnPermission() {


        List<Integer> finalAccountImage = AccountsUtil.getAccountsChildImageList();
        List<String> finalAccountTitle = AccountsUtil.getAccountsChildNameList();
        /**
         * now show view to recyclerview
         */
        AccountsSubcategoryAdapter accountAdapter = new AccountsSubcategoryAdapter(getActivity(), finalAccountImage, finalAccountTitle,getViewLifecycleOwner());
        subCategoryRv.setAdapter(accountAdapter);


      /*  permissionViewModel.getAccountPermission(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> permissions) {
                List<Integer> finalAccountImage = AccountsUtil.getAccountsChildImageList();
                List<String> finalAccountTitle = AccountsUtil.getAccountsChildNameList();

                *//**
         * if user have manage all permission user can access everything
         *//*
                if (permissions.contains(PermissionUtil.manageAll)) {
                    *//**
         * now show view to recyclerview
         *//*
                    AccountsSubcategoryAdapter accountAdapter = new AccountsSubcategoryAdapter(getActivity(), finalAccountImage, finalAccountTitle);
                    subCategoryRv.setAdapter(accountAdapter);
                } else {
                    *//**
         * if don't have manage all permission then user access permission  wise option like below
         *//*
         *//**
         * for show Received due if have permission
         *//*
                    if (!permissions.contains(PermissionUtil.showDueReceived)) {//here showDueReceived = 1058 means Manage receipt
                        if (finalAccountImage.contains(AccountsUtil.receiveDueImage)) {
                            finalAccountImage.remove(AccountsUtil.receiveDueImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.receiveDue)) {
                            finalAccountTitle.remove(AccountsUtil.receiveDue);
                        }
                    }

                    *//**
         * for show pay due if have permission
         *//*
                    if (!permissions.contains(PermissionUtil.showPayDue)) {//here 1062 means show (pay due) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payDueImage)) {
                            finalAccountImage.remove(AccountsUtil.payDueImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payDue)) {
                            finalAccountTitle.remove(AccountsUtil.payDue);
                        }
                    }

                    *//**
         * for show (pay due expense) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payDueExpense)) {//here 1070 means show (pay due expense) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payDueExpenseImage)) {
                            finalAccountImage.remove(AccountsUtil.payDueExpenseImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payDueExpense)) {
                            finalAccountTitle.remove(AccountsUtil.payDueExpense);
                        }
                    }

                    *//**
         * for show (payment instruction) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payInstruction)) {//here 1073 means show (payment instruction) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payInstructionImage)) {
                            finalAccountImage.remove(AccountsUtil.payInstructionImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payInstruction)) {
                            finalAccountTitle.remove(AccountsUtil.payInstruction);
                        }
                    }
                    *//**
         * for show (payment instruction list) if have permission
         *//*

                    if (!permissions.contains(PermissionUtil.payInstructionList)) {//here 1336 means show (payment instruction) permission to the user
                        if (finalAccountImage.contains(AccountsUtil.payInstructionListImage)) {
                            finalAccountImage.remove(AccountsUtil.payInstructionListImage);
                        }
                        if (finalAccountTitle.contains(AccountsUtil.payInstructionList)) {
                            finalAccountTitle.remove(AccountsUtil.payInstructionList);
                        }
                    }
                    *//**
         * now show view to recyclerview
         *//*
                    AccountsSubcategoryAdapter accountAdapter = new AccountsSubcategoryAdapter(getActivity(), finalAccountImage, finalAccountTitle);
                    subCategoryRv.setAdapter(accountAdapter);
                }
            }
        });
        toolbarTitle.setText("Accounting Solutions");//set toolbar title
        return;*/
    }
}