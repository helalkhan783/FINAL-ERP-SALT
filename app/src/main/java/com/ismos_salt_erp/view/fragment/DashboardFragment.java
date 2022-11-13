package com.ismos_salt_erp.view.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChartView;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.BankBalanceAdapter;
import com.ismos_salt_erp.adapter.ReceiptReportAdapter;
import com.ismos_salt_erp.adapter.account_report.PaymentRReportAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.ExpenseDashboardAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.IndustrialAndIodizationAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.ProductionAdapterJust;
import com.ismos_salt_erp.adapter.dashboard_adapter.PurchaseDashBoardAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.QcQaDashAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.SalePurchaseAdapterJust;
import com.ismos_salt_erp.adapter.dashboard_adapter.TopTenCustomerAdapter;
import com.ismos_salt_erp.databinding.FragmentDashboardBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report_response.ReceiptReportResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.LastMonthIodine;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.Production;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.Sales;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerResponse;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.MillerUtils;
import com.ismos_salt_erp.utils.MonitoringUtil;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringListFragment;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.ismos_salt_erp.viewModel.DashBoardViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {
    private FragmentDashboardBinding binding;
    private AnyChartView view;
    private String portion;

    private DashBoardViewModel dashBoardViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;
    public static boolean manageMonitor = false;
    private CustomerReportViewModel customerReportViewModel;
    double totalReceipt = 0.0, totalPayment = 0.0;

    String expense, receipt, payment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);

        getPeriviousFragmentData();
        backControl();
        getDashBoardData();
        dashBoardPermissionManage();
        setClick();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {

            Bundle bundle = new Bundle();

            switch (item.getItemId()) {

                case R.id.dayBook:
                    try {
                        bundle.putString("portion", AccountsUtil.dayBook);
                        bundle.putString("PageName", "Day Book");
                        Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_accountsListFragment2, bundle);

                    } catch (Exception e) {
                    }

                    break;

                case R.id.cashBook:
                    try {
                        bundle.putString("portion", AccountsUtil.cash);
                        bundle.putString("PageName", "Cash");
                        Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_accountsListFragment2, bundle);
                    } catch (Exception e) {
                    }

                    break;

                case R.id.creditor:
                    bundle.putString("portion", "Creditors");
                    bundle.putString("PageName", "Creditors");
                    bundle.putString("from", "HomeFragment");

                    Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_transactionListFragment, bundle);
                    break;
                case R.id.debitor:
                    bundle.putString("portion", "Debitors");
                    bundle.putString("PageName", "Debitors");
                    bundle.putString("from", "HomeFragment");

                    Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_transactionListFragment, bundle);
                    break;
                case R.id.home:
                    Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_homeFragment);

                    break;

            }
            return true;
        });
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDashBoardData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }


        dashBoardViewModel.dashboardData(getActivity()).observe(getViewLifecycleOwner(), response -> {

            binding.receipt.setText("" + DataModify.addFourDigit(response.getTotalReceipt()) + MtUtils.priceUnit);
            binding.payment.setText("" + DataModify.addFourDigit(String.valueOf(response.getTotalPayment())) + MtUtils.priceUnit);
            /* try {

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1347)) {
                    binding.totalMillTv.setText("" + response.getTotalMills());
                    binding.activeMillTv.setText("" + response.getActiveMills());
                    binding.inactiveMillTv.setText("" + response.getInactiveMills());
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1278)) {

                    binding.purchase.setText("" + kgToTon(response.getPurchaseCm()) + MtUtils.metricTon);
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1535)) {
                    binding.totalProductionTv.setText("" + kgToTon(response.getProductionCm()) + MtUtils.metricTon);
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1277)) {
                    binding.totalSale.setText("" + kgToTon(response.getTotalSaleCm()) + MtUtils.metricTon);
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1541)) {
                    binding.iodinePurchase.setText("" + kgToTon(response.getIodinePurchaseCm()) + MtUtils.metricTon);
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1277)) {
            H        binding.iodizedSaltSaleTv.setText("" + kgToTon(response.getIodizedSaleCm()) + MtUtils.metricTon);
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1602)) {
                    binding.totalZone.setText("" + response.getTotalZone());
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1603)) {
                    binding.devlopPartnar.setText("" + response.getTotalUnOrganization());
                }
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1604)) {
                    binding.govtAgencyTv.setText("" + response.getGoAgencies());
                }

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1427)) {
                    binding.totalMonitoringTv.setText("" + response.getTotalMonitoring());
                }
            } catch (Exception e) {

            }*/
            try {


                //  binding.purchaseQty.setText("" + kgToTon(Integer.parseInt(response.getPurchaseQty())) + MtUtils.metricTon);
                binding.purchaseQty.setText("" + DataModify.kgToTon(ReplaceCommaFromString.replaceComma(response.getPurchaseQty())) + MtUtils.metricTon);
                if (!(response.getTodayPurchaseTotalAmount() == 0)) {
                    binding.purchaseTK.setText("" + DataModify.addFourDigit(String.valueOf(response.getTodayPurchaseTotalAmount())) + MtUtils.priceUnit);
                }

                // binding.totalProductionTv.setText("" + kgToTon(response.getProductionQty()) + MtUtils.metricTon);
                binding.totalProductionTv.setText("" + DataModify.kgToTon(String.valueOf(response.getProductionQty())) + MtUtils.metricTon);
                if (!(response.getTodaySaleToalAmount() == 0)) {
                    binding.saleTk.setText("" + DataModify.addFourDigit(String.valueOf(response.getTodaySaleToalAmount())) + MtUtils.priceUnit);
                }

                if (response.getTotalSaleQty() != null) {
                    //  binding.saleQty.setText("" + kgToTon(Integer.parseInt(String.valueOf(response.getTotalSaleQty()))) + MtUtils.metricTon);
                    binding.saleQty.setText("" + DataModify.addThreeDigit(String.valueOf(response.getTotalSaleQty())) + MtUtils.metricTon);
                }
                //  binding.totalIodinePurchase.setText("" + kgToTon(Integer.parseInt(response.getIodinePurchaseQty())) + MtUtils.metricTon);
                binding.totalIodinePurchase.setText("" + DataModify.kgToTon(ReplaceCommaFromString.replaceComma(response.getIodinePurchaseQty())) + MtUtils.metricTon);
                if (response.getIodizedSaleQty() != null) {
                    //     binding.iodizedSaltSale.setText("" + kgToTon(Integer.parseInt(String.valueOf(response.getIodizedSaleQty()))) + MtUtils.metricTon);
                    binding.iodizedSaltSale.setText("" + DataModify.addThreeDigit(String.valueOf(response.getIodizedSaleQty())) + MtUtils.metricTon);
                }

                if (response.getTotalExpense() != null) {
                    binding.expense.setText("" + DataModify.addFourDigit(String.valueOf(response.getTotalExpense())) + MtUtils.priceUnit);
                }


                binding.creditors.setText("" + DataModify.addFourDigit(String.valueOf(response.getCreditors())) + MtUtils.priceUnit);

                binding.debitors.setText("" + DataModify.addFourDigit(String.valueOf(response.getDebitors())) + MtUtils.priceUnit);

            } catch (Exception e) {
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String firstDay = year.format(now) + "-01" + "-01";

        dashBoardViewModel.getTopTenCustomerList(getActivity(), "1", firstDay, DataModify.currentDate(), "10").observe(getViewLifecycleOwner(), new Observer<TopTenSupplierAndCustomerResponse>() {
            @Override
            public void onChanged(TopTenSupplierAndCustomerResponse response) {
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                    return;
                }
                if (response.getStatus() == 400) {
                    binding.listLayout.topTenSupplierLayout.setVisibility(View.GONE);
                    return;
                }

                if (response.getStatus() == 200) {
                    if (response.getList() == null || response.getList().isEmpty()) {
                        binding.listLayout.topTenCustomerRv.setVisibility(View.GONE);
                        binding.listLayout.topTenCustomerTv.setVisibility(View.VISIBLE);
                        return;
                    }

                    TopTenCustomerAdapter adapter = new TopTenCustomerAdapter(getActivity(), response.getList());
                    binding.listLayout.topTenCustomerRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.listLayout.topTenCustomerRv.setAdapter(adapter);

                }
            }
        });
        dashBoardViewModel.getTopTenCustomerList(getActivity(), "2,5", firstDay, dtf.format(now), "10").observe(getViewLifecycleOwner(), new Observer<TopTenSupplierAndCustomerResponse>() {
            @Override
            public void onChanged(TopTenSupplierAndCustomerResponse response) {
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                    return;
                }
                if (response.getStatus() == 400) {
                    binding.listLayout.topTeSupplierLayout.setVisibility(View.GONE);
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getList() == null || response.getList().isEmpty()) {
                        binding.listLayout.topTenupplierRv.setVisibility(View.GONE);
                        binding.listLayout.topTenupplierTv.setVisibility(View.VISIBLE);
                        return;
                    }

                    TopTenCustomerAdapter adapter = new TopTenCustomerAdapter(getActivity(), response.getList());
                    binding.listLayout.topTenupplierRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.listLayout.topTenupplierRv.setAdapter(adapter);

                }
            }
        });


        dashBoardViewModel.getBankBalanceList(getActivity(), "", "").observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400) {
                binding.listLayout.currentAvailableBalanceLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.currentAvailAbleBalanceRv.setVisibility(View.GONE);
                    binding.listLayout.currentAvailAbleBalanceTv.setVisibility(View.VISIBLE);
                    return;
                }
                BankBalanceAdapter adapter1 = new BankBalanceAdapter(getActivity(), response.getList());
                binding.listLayout.currentAvailAbleBalanceRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.currentAvailAbleBalanceRv.setAdapter(adapter1);

            }
        });

        dashBoardViewModel.getExpenseList(getActivity(), "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400) {
                binding.listLayout.todaysExpenseLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.totalExpenseRv.setVisibility(View.GONE);
                    binding.listLayout.totalExpenseTv.setVisibility(View.VISIBLE);
                    return;
                }
                ExpenseDashboardAdapter adapter1 = new ExpenseDashboardAdapter(getActivity(), response.getList());
                binding.listLayout.totalExpenseRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.totalExpenseRv.setAdapter(adapter1);

            }
        });
        // here 1 means purchase
        dashBoardViewModel.getSaleOrPurchaseList(getActivity(), "1", dtf.format(now), dtf.format(now), null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                binding.listLayout.todaysSaleBasedOnCustomerLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysPurchaseBasedOnSupplierRv.setVisibility(View.GONE);
                    binding.listLayout.todaysPurchaseBasedOnSupplierTv.setVisibility(View.VISIBLE);
                    return;
                }
                PurchaseDashBoardAdapter adapter1 = new PurchaseDashBoardAdapter(getActivity(), response.getList());
                binding.listLayout.todaysPurchaseBasedOnSupplierRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysPurchaseBasedOnSupplierRv.setAdapter(adapter1);

            }
        });

        // here 2 means sale
        dashBoardViewModel.getSaleOrPurchaseList(getActivity(), "2", dtf.format(now), dtf.format(now), null).observe(getViewLifecycleOwner(), response -> {
            if (response == null   || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400 ) {
                binding.listLayout.todaysSaleLayout.setVisibility(View.GONE);
                 return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysSaleBasedOnCustomerRv.setVisibility(View.GONE);
                    binding.listLayout.todaysSaleBasedOnCustomerTv.setVisibility(View.VISIBLE);
                    return;
                }
                PurchaseDashBoardAdapter adapter1 = new PurchaseDashBoardAdapter(getActivity(), response.getList());
                binding.listLayout.todaysSaleBasedOnCustomerRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysSaleBasedOnCustomerRv.setAdapter(adapter1);

            }
        });

        dashBoardViewModel.getProductionList(getActivity(), "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                binding.listLayout.todaysIndustrialLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysIndustrialIodizationRv.setVisibility(View.GONE);
                    binding.listLayout.todaysIndustrialIodizationTv.setVisibility(View.VISIBLE);
                    return;
                }

                IndustrialAndIodizationAdapter adapter = new IndustrialAndIodizationAdapter(getActivity(), response.getList());
                binding.listLayout.todaysIndustrialIodizationRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysIndustrialIodizationRv.setAdapter(adapter);

            }
        });

        dashBoardViewModel.getJustSalePurchaseList(getActivity(), "1", "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400) {
                binding.listLayout.todaysSaleLayout1.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysPurchaseRv.setVisibility(View.GONE);
                    binding.listLayout.todaysPurchaseTv.setVisibility(View.VISIBLE);
                    return;
                }

                SalePurchaseAdapterJust adapter = new SalePurchaseAdapterJust(getActivity(), response.getList());
                binding.listLayout.todaysPurchaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysPurchaseRv.setAdapter(adapter);

            }
        });

        dashBoardViewModel.getJustSalePurchaseList(getActivity(), "2", "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400) {
                binding.listLayout.todaysPurchaselayout.setVisibility(View.GONE);
                return;
            }

            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysSaleRv.setVisibility(View.GONE);
                    binding.listLayout.todaysSaleTv.setVisibility(View.VISIBLE);
                    return;
                }

                SalePurchaseAdapterJust adapter = new SalePurchaseAdapterJust(getActivity(), response.getList());
                binding.listLayout.todaysSaleRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysSaleRv.setAdapter(adapter);

            }
        });

        dashBoardViewModel.getProductionListJust(getActivity(), "25,26", "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                binding.listLayout.todaysProductionLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.todaysProductionRv.setVisibility(View.GONE);
                    binding.listLayout.todaysProductionTv.setVisibility(View.VISIBLE);
                    return;
                }
                ProductionAdapterJust adapterJust = new ProductionAdapterJust(getActivity(), response.getList());
                binding.listLayout.todaysProductionRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.listLayout.todaysProductionRv.setAdapter(adapterJust);

            }
        });

        dashBoardViewModel.getQcQaList(getActivity(), "", "", null).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                binding.listLayout.lastMonthQcQaLayout.setVisibility(View.GONE);
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.listLayout.qcQARv.setVisibility(View.GONE);
                    binding.listLayout.qcQaTv.setVisibility(View.VISIBLE);
                    return;
                }
                try {
                    QcQaDashAdapter adapterJust = new QcQaDashAdapter(getActivity(), response.getList());
                    binding.listLayout.qcQARv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.listLayout.qcQARv.setAdapter(adapterJust);

                } catch (Exception e) {
                }
            }
        });

        /**
         * pie chart data Set
         */
        dashBoardViewModel.pieChartData(getActivity()).observe(getViewLifecycleOwner(), dataresponse -> {
            if (dataresponse.getStatus() == 200) {
                showPieChart(dataresponse.getData().getSales());
                showPieChart1(dataresponse.getData().getProduction());
                showPieChart$(dataresponse.getData().getLastMonthIodine());
            }

        });
    }


    private void dashBoardPermissionManage() {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1606)) {
//production pie chart
            binding.productionPieChartLayout.setVisibility(View.GONE);
        }

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1607)) {
//sale piechart
            binding.salePieChartLayout.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1617)) {
//iodine
            //      binding.listLayout.iodineUsedPieChartLayout.setVisibility(View.GONE);
        }

    }

    private void showPieChart1(Production production) {
        try {
            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            String label = "";

            //initializing data
            Map<String, Float> typeAmountMap = new HashMap<>();
            typeAmountMap.put("Industrial", Float.parseFloat(production.getIndustrial()));
            String a = String.valueOf(production.getIodized());
            typeAmountMap.put("Iodized", Float.parseFloat(a));


            //initializing colors for the entries
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#D15700"));
            colors.add(Color.parseColor("#782B44"));

            //input data and fit data into pie chart entry
            for (String type : typeAmountMap.keySet()) {
                pieEntries.add(new PieEntry(typeAmountMap.get(type), type));
            }

            //collecting the entries with label name
            PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
            //setting text size of the value
            pieDataSet.setValueTextSize(10f);
            //providing color list for coloring different entries
            pieDataSet.setColors(colors);
            //grouping the data set from entry to chart
            PieData pieData = new PieData(pieDataSet);
            //showing the value of the entries, default true if not set
            pieData.setDrawValues(true);

            binding.pieChartlayout1.pieChartView.setData(pieData);
            binding.pieChartlayout1.pieChartView.invalidate();
            binding.pieChartlayout1.pieChartView.setDescription(null);
            binding.pieChartlayout1.pieChartView.setHoleColor(ContextCompat.getColor(getContext(), R.color.violet_color));
            binding.pieChartlayout1.pieChartView.setHoleRadius(0f);
            binding.pieChartlayout1.pieChartView.setTransparentCircleRadius(0f);
        } catch (Exception e) {
        }
    }

    private void showPieChart(Sales sales) {
        try {
            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            String label = "";
            //initializing data
            Map<String, Float> typeAmountMap = new HashMap<>();
            typeAmountMap.put("Industrial", Float.parseFloat(sales.getIndustrial()));

            typeAmountMap.put("Iodized", Float.parseFloat(sales.getIodized()));

            //initializing colors for the entries
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#D15700"));
            colors.add(Color.parseColor("#782B44"));


            //input data and fit data into pie chart entry
            for (String type : typeAmountMap.keySet()) {
                pieEntries.add(new PieEntry(typeAmountMap.get(type), type));
            }

            //collecting the entries with label name
            PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
            //setting text size of the value
            pieDataSet.setValueTextSize(10f);
            //providing color list for coloring different entries
            pieDataSet.setColors(colors);
            //grouping the data set from entry to chart
            PieData pieData = new PieData(pieDataSet);
            //showing the value of the entries, default true if not set
            pieData.setDrawValues(true);

            binding.pieChartlayout2.pieChartView.setData(pieData);
            binding.pieChartlayout2.pieChartView.invalidate();
            binding.pieChartlayout2.pieChartView.setDescription(null);
            binding.pieChartlayout2.pieChartView.setHoleColor(ContextCompat.getColor(getContext(), R.color.violet_color));
            binding.pieChartlayout2.pieChartView.setHoleRadius(0f);
            binding.pieChartlayout2.pieChartView.setTransparentCircleRadius(0f);
        } catch (Exception e) {
        }
    }

    private void showPieChart$(LastMonthIodine lastMonthIodine) {
        try {
            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            String label = "";

            //initializing data
            String purchase = "Purchase: " + lastMonthIodine.getPurchase();
            String used = "Used: " + lastMonthIodine.getSell();
            String inStock = "In Stock: " + lastMonthIodine.getInStock();
            String inStock1 = ReplaceCommaFromString.replaceComma(lastMonthIodine.getInStock());

            Map<String, Float> typeAmountMap = new HashMap<>();
            typeAmountMap.put(purchase, Float.parseFloat(lastMonthIodine.getPurchase()));
            typeAmountMap.put(used, Float.parseFloat(lastMonthIodine.getSell()));
            typeAmountMap.put(inStock, Float.parseFloat(inStock1));


            //initializing colors for the entries
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#FF7D7D"));
            colors.add(Color.parseColor("#937DFF"));
            colors.add(Color.parseColor("#83B094"));


            //input data and fit data into pie chart entry
            for (String type : typeAmountMap.keySet()) {
                pieEntries.add(new PieEntry(typeAmountMap.get(type), type));
            }


            //collecting the entries with label name
            PieDataSet pieDataSet = new PieDataSet(pieEntries, label);

            //setting text size of the value
            pieDataSet.setValueTextSize(0f);
            //providing color list for coloring different entries
            pieDataSet.setColors(colors);
            //grouping the data set from entry to chart
            PieData pieData = new PieData(pieDataSet);
            //showing the value of the entries, default true if not set
            pieData.setDrawValues(true);
            binding.listLayout.pieChartlayout3.pieChartView.setData(pieData);
            binding.listLayout.pieChartlayout3.pieChartView.invalidate();
            binding.listLayout.pieChartlayout3.pieChartView.setDescription(null);
            binding.listLayout.pieChartlayout3.pieChartView.setHoleColor(ContextCompat.getColor(getContext(), R.color.violet_color));
            binding.listLayout.pieChartlayout3.pieChartView.setHoleRadius(0f);
            binding.listLayout.pieChartlayout3.pieChartView.setTransparentCircleRadius(0f);

        } catch (Exception e) {
        }
    }


    private void backControl() {
        binding.back.setOnClickListener(v -> getActivity().onBackPressed());
    }

    private void getPeriviousFragmentData() {
        try {
            getArguments().getString("portion");
            receipt = getArguments().getString("receipt");
            payment = getArguments().getString("payment");
            expense = getArguments().getString("expense");


        } catch (Exception e) {
        }
    }


    private void setClick() {
        binding.totalMill.setOnClickListener(this);
        binding.activeMills.setOnClickListener(this);
        binding.inActiveMills.setOnClickListener(this);
        binding.totalPurchaseLayout.setOnClickListener(this);
        binding.totalProduction.setOnClickListener(this);
        binding.totalSaleLayout.setOnClickListener(this);
        binding.totalIodinePurchaseLayout.setOnClickListener(this);
        binding.totalIodideSaltSale.setOnClickListener(this);
        //   binding.totalZone.setOnClickListener(this);
        binding.totalDeveloperPartnerLayout.setOnClickListener(this);
        binding.totalGovtAgency.setOnClickListener(this);
        binding.totalConsolidoid.setOnClickListener(this);
        //binding.swiprRefresjh.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if (v.getId() == R.id.totalMill) {
            goToMillHistory(null, "Mill History Lists");
        }


        if (v.getId() == R.id.activeMills) {
            goToMillHistory("1", "Active Mills");
        }


        if (v.getId() == R.id.inActiveMills) {
            goToMillHistory("0", "Inactive Mills");
        }


        if (v.getId() == R.id.totalPurchaseLayout) {
            try {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1278)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                    Date date = new Date();
                    // for first day of current month.
                    LocalDate now = LocalDate.now();
                    LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                    bundle.putString("startDate", String.valueOf(firstDay));
                    bundle.putString("endDate", formatter.format(date));
                    bundle.putString("pageName", "Purchase Report");
                    bundle.putString("portion", ReportUtils.purchaseReport);
                    //  Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_purchaseReturnListFragment, bundle);
                    return;
                } else {
                    Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }
            } catch (Exception e) {
            }
        }


        if (v.getId() == R.id.totalProduction) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1535)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                Date date = new Date();
                // for first day of current month.
                LocalDate now = LocalDate.now();
                LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                bundle.putString("startDate", String.valueOf(firstDay));
                bundle.putString("endDate", formatter.format(date));
                bundle.putString("pageName", "Production Report");
                //   bundle.putString("portion", ReportUtils.productionReport);
                //    Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_purchaseReturnListFragment, bundle);
                return;
            } else {
                Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }
        }


        if (v.getId() == R.id.totalSaleLayout) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1277)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                Date date = new Date();
                // for first day of current month.
                LocalDate now = LocalDate.now();
                LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                bundle.putString("startDate", String.valueOf(firstDay));
                bundle.putString("endDate", formatter.format(date));
                bundle.putString("portion", ReportUtils.saleReport);
                bundle.putString("pageName", "Sale Report");
                //     Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_purchaseReturnListFragment, bundle);
                return;
            } else {
                Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }
        }


        if (v.getId() == R.id.totalIodinePurchaseLayout) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1541)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                Date date = new Date();
                // for first day of current month.
                LocalDate now = LocalDate.now();
                LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                bundle.putString("startDate", String.valueOf(firstDay));
                bundle.putString("endDate", formatter.format(date));
                bundle.putString("portion", ReportUtils.iodineUsedReport);
                bundle.putString("pageName", "Iodine Used Report");
                //      Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_purchaseReturnListFragment, bundle);
                return;
            } else {
                Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }
        }


        if (v.getId() == R.id.totalIodideSaltSale) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1277)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                Date date = new Date();
                // for first day of current month.
                LocalDate now = LocalDate.now();
                LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                bundle.putString("startDate", String.valueOf(firstDay));
                bundle.putString("endDate", formatter.format(date));
                bundle.putString("categoryId", "738");
                bundle.putString("portion", ReportUtils.saleReport);
                bundle.putString("pageName", "Sale Report");
                //   Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_purchaseReturnListFragment, bundle);
                return;
            } else {
                Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }
        }


        if (v.getId() == R.id.totalZone) {
            //   Toast.makeText(getContext(), "Total Zone", Toast.LENGTH_SHORT).show();
        }


        if (v.getId() == R.id.totalDeveloperPartnerLayout) {
            //  Toast.makeText(getContext(), "Total Developer partner", Toast.LENGTH_SHORT).show();
        }


        if (v.getId() == R.id.totalGovtAgency) {
            //  Toast.makeText(getContext(), "Total Government Agency", Toast.LENGTH_SHORT).show();
        }


        if (v.getId() == R.id.totalConsolidoid) {
            try {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1427)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
                    Date date = new Date();
                    // for first day of current month.
                    LocalDate now = LocalDate.now();
                    LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
                    bundle.putString("startDate", String.valueOf(firstDay));
                    bundle.putString("endDate", formatter.format(date));
                    bundle.putString("portion", MonitoringUtil.monitoringHistory);
                    MonitoringListFragment.isFirstLoad = 0;
                    MonitoringListFragment.pageNumber = 1;
                    manageMonitor = true;
                    //  Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_monitoringListFragment, bundle);
                } else {
                    Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }


            } catch (Exception e) {
            }
        }

    }

    private void goToMillHistory(String s, String pageName) {
        try {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1347)) {
                Bundle bundle = new Bundle();
                bundle.putString("porson", MillerUtils.millerHistoryList);
                bundle.putString("status", s);
                bundle.putString("pageName", pageName);
                // Navigation.findNavController(getView()).navigate(R.id.action_dashboardFragment_to_millerAllListFragment, bundle);
                return;
            } else {
                Toasty.info(getContext(), "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
       /* currentPermissionViewModel.getCurrentUserRealtimePermissions(
                PreferenceManager.getInstance(getActivity()).getUserCredentials().getToken(),
                PreferenceManager.getInstance(getActivity()).getUserCredentials().getUserId()
        ).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                Toasty.error(getActivity(), "Something Wrong", Toasty.LENGTH_LONG).show();
                return;
            }
            if (response.getStatus() == 400) {
                Toasty.info(getActivity(), "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                return;
            }
            try {
                LoginResponse loginResponse = PreferenceManager.getInstance(getActivity()).getUserCredentials();
                if (loginResponse != null) {
                    loginResponse.setPermissions(response.getMessage());
                    loginResponse.setToken(response.getToken());
                    PreferenceManager.getInstance(getActivity()).saveUserCredentials(loginResponse);
                }
            } catch (Exception e) {
                infoMessage(getActivity().getApplication(), "" + e.getMessage());
                Log.d("ERROR", "" + e.getMessage());
            }
        });*/
    }


}
