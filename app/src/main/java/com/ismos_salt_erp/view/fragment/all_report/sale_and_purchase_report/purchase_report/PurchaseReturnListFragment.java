package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.DistrictWiseSaleReportAdapter;
import com.ismos_salt_erp.adapter.ExpenseReportListAdapter;
import com.ismos_salt_erp.adapter.IodineReportListAdapter;
import com.ismos_salt_erp.adapter.MonitoringReportAdapter;
import com.ismos_salt_erp.adapter.PackegingReportListAdapter;
import com.ismos_salt_erp.adapter.PackettingReportListAdapter;
import com.ismos_salt_erp.adapter.ProductionReportLisAdapter;
import com.ismos_salt_erp.adapter.PurchaseReportAdapterByDate;
import com.ismos_salt_erp.adapter.PurchaseReturnReportListAdapter;
import com.ismos_salt_erp.adapter.QcQaReportListAdapter;
import com.ismos_salt_erp.adapter.ReceiptReportAdapter;
import com.ismos_salt_erp.adapter.SupplierAdapterForReport;
import com.ismos_salt_erp.adapter.account_report.BankReportListAdapter;
import com.ismos_salt_erp.adapter.account_report.DayBookReportAdapter;
import com.ismos_salt_erp.adapter.account_report.DebitAndCreditReportAdapter;
import com.ismos_salt_erp.adapter.account_report.InstructionreportAdapter;
import com.ismos_salt_erp.adapter.account_report.PaymentInstructionReportListadapter;
import com.ismos_salt_erp.adapter.account_report.PaymentRReportAdapter;
import com.ismos_salt_erp.adapter.dashboard_adapter.BankBalanceAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.ExpenseDashboardAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.IndustrialAndIodizationAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.ProductionAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.PurchaseDashBoardAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.QcQaAdapterForDashReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.TodaysSaleAdapterForReport;
import com.ismos_salt_erp.adapter.dashboard_adapter.TopTenCustomerAdapterForReport;
import com.ismos_salt_erp.databinding.FragmentPurchaseReturnListBinding;
import com.ismos_salt_erp.date_time_picker.CurrentDatePick;
import com.ismos_salt_erp.retrofit.VendorReportList;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportByDateResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.BankLedgerReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DayBookReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DebitorAndCreditorsReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentInstructionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.TransactionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report_response.ReceiptReportResponse;
import com.ismos_salt_erp.serverResponseModel.customer_report.CustomerReportList1;
import com.ismos_salt_erp.serverResponseModel.customer_report.SupplierRepostList1;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerResponse;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.DashBoardReportUtils;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseReportListReponse;
import com.ismos_salt_erp.view.fragment.stock.list_adapter.DeclineTransferredListAdapter;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.ismos_salt_erp.viewModel.DashBoardViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.EmployeeReportViewModel;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.list.EmployeeReportListResponse;
import com.ismos_salt_erp.viewModel.report_all_view_model.IodineReportViewModel;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.list.IodineReportListResponse;
import com.ismos_salt_erp.viewModel.report_all_view_model.LicenceExpireViewModel;
import com.ismos_salt_erp.view.fragment.all_report.lisence_report.LicenceReportViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.PackagingViewModel;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.list.PacketingReportListResponse;
import com.ismos_salt_erp.viewModel.report_all_view_model.PacketingReportViewModel;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.list.PackegingReportListResponse;
import com.ismos_salt_erp.adapter.MillerEmployeeReportListAdapter;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.ReconciliationReportListAdapter;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReconciliationReportViewModel;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.list_response.ReconciliationReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.SaleReportListAdapter;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.SaleReturnReportListAdapter;
import com.ismos_salt_erp.viewModel.report_all_view_model.SaleReturnReportViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.StockIOReportViewModel;
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.StockIoReportAdapter;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.SaleReportViewModel;

import java.util.ArrayList;
import java.util.List;


public class PurchaseReturnListFragment extends BaseFragment implements ReportInterface {
    private FragmentPurchaseReturnListBinding binding;
    private String portion, startDate, from, endDate, supplierId, SupplierID, referrerId, brandId, itemId, storeId, millerprofileId, districId, categoryId, certificateTypeID, selectAssociationId; //get previous fragment
    private ReportViewModel reportViewModel;
    private DashBoardViewModel dashBoardViewModel;
    private LicenceReportViewModel licenceReportViewModel;
    private LicenceExpireViewModel licenceExpireViewModel;
    private EmployeeReportViewModel employeeReportViewModel;
    private ReconciliationReportViewModel reconciliationViewModel;
    private SaleReportViewModel saleReportViewModel;
    private SaleReturnReportViewModel saleReturnReportViewModel;
    private StockIOReportViewModel stockIOReportViewModel;
    private PacketingReportViewModel packetingReportViewModel;
    private PackagingViewModel packagingViewModel;
    private IodineReportViewModel iodineReportViewModel;
    private CustomerReportViewModel customerReportViewModel;
    private String selectReconciliation, userId, transactionId, expenseTypeId, withdrawPosition, bankId, pageName, monitoringType, zone;
    ProgressDialog progressDialog;

    private String transferItemId, fromStoreId, toStoreId;
    private double total = 0.0, in = 0.0, out = 0.0;
    private String date = "0";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_return_list, container, false);

        /** object create for viewModelProvider*/
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        licenceReportViewModel = new ViewModelProvider(this).get(LicenceReportViewModel.class);
        licenceExpireViewModel = new ViewModelProvider(this).get(LicenceExpireViewModel.class);
        employeeReportViewModel = new ViewModelProvider(this).get(EmployeeReportViewModel.class);
        reconciliationViewModel = new ViewModelProvider(this).get(ReconciliationReportViewModel.class);
        saleReportViewModel = new ViewModelProvider(this).get(SaleReportViewModel.class);
        saleReturnReportViewModel = new ViewModelProvider(this).get(SaleReturnReportViewModel.class);
        stockIOReportViewModel = new ViewModelProvider(this).get(StockIOReportViewModel.class);
        packetingReportViewModel = new ViewModelProvider(this).get(PacketingReportViewModel.class);
        packagingViewModel = new ViewModelProvider(this).get(PackagingViewModel.class);
        iodineReportViewModel = new ViewModelProvider(this).get(IodineReportViewModel.class);
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);

        progressDialog = new ProgressDialog(getContext());

        /** get previous fragment data */
        getPreviousFragmentData();

        /** back control*/
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
/**
 * all Report List
 */

        getAllData();
        manageLayout();
        binding.openingBalance.setOnClickListener(v -> {
            endDate = CurrentDatePick.yesterdayDate(endDate);
            startDate = "2000-01-01";
            binding.dateRange.setText(startDate + " To " + endDate);
            binding.openingBalancelayout.setVisibility(View.GONE);
            getAllData();
        });
        return binding.getRoot();
    }

    private void getAllData() {
        if (from != null) {
            if (from.equals("FromDashboard")) {
                onlyDashBoardReport();
                return;
            }
        }

        allReportListData();
    }

    private void allReportListData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        progressDialog.show();

        if (portion.equals(ReportUtils.purchaseReport)) {
            manageLayoytData("1");
            getPurchaseReportDataFromViewModelByDate();
            return;
        }
        if (portion.equals(ReportUtils.purchaseReturnReport)) {
            manageLayoytData("1");
            purchaseReturnReportList();

            return;
        }
        if (portion.equals(ReportUtils.saleReport)) {
            manageLayoytData("2");
            getSaleReportListFromViewModel();
            return;
        }
        if (portion.equals(ReportUtils.saleReturnReport)) {
            manageLayoytData("2");
            getSaleReturnReportList();
            return;
        }

        if (portion.equals(ReportUtils.districtSaleReport)) {
            getDistrictWiseSaleReport();
            return;
        }
       /* if (portion.equals(ReportUtils.licenceReport)) {
            getDataFromLicenceReportViewModel();
            return;
        }
        if (portion.equals(ReportUtils.licenceExpireReport)) {
            getDataFromLicenceExpireViewModel();
            return;
        }
        if (portion.equals(ReportUtils.qcqaReport)) {
            getQCQAList();
            return;
        }

        if (portion.equals(ReportUtils.monitoringReport)) {
            getMonitoringReportLst();
            return;
        }*/
        if (portion.equals(ReportUtils.stockInOutReport)) {
            getStockIOList();
            return;
        }

        if (portion.equals(ReportUtils.employeeReport)) {
            getDataFromEmployeeReportViewModel();
            return;
        }
        if (portion.equals(ReportUtils.reconciliation)) {
            getDataFromReconciliationViewModel();
            return;
        }
        /** for Packaging Report */
        if (portion.equals(ReportUtils.productionReport)) {
            getProductionReport();
            return;
        } /** for Packaging Report */
        if (portion.equals(ReportUtils.PacketingReport)) {
            getPackegingReportListFromViewModel();
            return;
        }
        /** for packeting Report */
        if (portion.equals(ReportUtils.packagingReport)) {
            getPacketingReportData();
            return;
        }

        /** for iodine Report */
        if (portion.equals(ReportUtils.iodineUsedReport)) {
            getIodineReportList();
            return;
        }


        if (portion.equals(ReportUtils.transferReport)) {
            getTransferReportData();
            return;
        }

        if (portion.equals(AccountReportUtils.customerLedgerReport)) {

            getCustomerReportList();
            return;
        }

        if (portion.equals(AccountReportUtils.supplierLedgerReport)) {

            getSupplierList();
            return;
        }
        if (portion.equals(AccountReportUtils.vendorLedgerReport)) {
            getVendorReportList();
            return;
        }

        if (portion.equals(AccountReportUtils.bankLedger)) {
            // bankId ,withdrawPosition,transactionId
            getBankReportList();
            return;
        }
        if (portion.equals(AccountReportUtils.receipt)) {
            getReceiptList();

            return;
        }
        if (portion.equals(AccountReportUtils.paymentInstructions)) {
            getPaymentInstruction();
            return;
        }
        if (portion.equals(AccountReportUtils.payment)) {
            getPaymnet();

            return;
        }
        if (portion.equals(AccountReportUtils.expense)) {

            getExpenselist();
            return;
        }
        if (portion.equals(AccountReportUtils.transactionIn)) {
            getTransactionIn("1");
            return;
        }
        if (portion.equals(AccountReportUtils.transactionOut)) {
            getTransactionIn("2");
            return;
        }

        if (portion.equals(AccountReportUtils.creditors)) {
            getCreditData("1");
            return;
        }
        if (portion.equals(AccountReportUtils.debitors)) {
            getCreditData("2");
            return;
        }
        if (portion.equals(AccountReportUtils.dayBook)) {
            getDayBook("2");
            return;
        }


        if (portion.equals(AccountReportUtils.cashBook)) {
            //  getDayBook("1");

            return;
        }


    }

    private void getExpenselist() {
        customerReportViewModel.getExpenseReportList(getActivity(), startDate, endDate, storeId, expenseTypeId).observe(getViewLifecycleOwner(), new Observer<ExpenseReportListReponse>() {
            @Override
            public void onChanged(ExpenseReportListReponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), getString(R.string.wrong_msg));
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }

                if (response.getLists().isEmpty() || response.getLists() == null) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setHasFixedSize(true);
                ExpenseReportListAdapter adapter = new ExpenseReportListAdapter(getActivity(), response.getLists());
                binding.recyclerView.setAdapter(adapter);
                setTotalDataToView(response.getGrandTotal(), null, null);
            }
        });
    }

    private void manageLayout() {

        if (portion.equals(AccountReportUtils.customerLedgerReport) || portion.equals(AccountReportUtils.supplierLedgerReport) || portion.equals(AccountReportUtils.vendorLedgerReport)) {
            if (!startDate.isEmpty()) {
                binding.openingBalancelayout.setVisibility(View.VISIBLE);

            }
            return;
        }


    }

    private void visibleAmountLy(String openingBalance, String dueBalance, String totalAmount) {

        if (openingBalance != null) {
            binding.openingBalance.setText(openingBalance);
        }
        if (dueBalance != null) {
            binding.due.setText("" + dueBalance);
        }
        if (totalAmount != null) {
            binding.total.setText("" + totalAmount);
        }


    }

    private void showAmountLayout() {
        binding.amountLay.setVisibility(View.VISIBLE);
    }

    private void getDayBook(String type) {
        customerReportViewModel.getDayBook(getActivity(), startDate, endDate, userId, transactionId, supplierId, type, SupplierID).observe(getViewLifecycleOwner(), new Observer<DayBookReportResponse>() {
            @Override
            public void onChanged(DayBookReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        DayBookReportAdapter adapter = new DayBookReportAdapter(getActivity(), response.getLists());
                        binding.recyclerView.setAdapter(adapter);

                        for (int i = 0; i < response.getLists().size(); i++) {
                            in += Double.parseDouble(ReplaceCommaFromString.replaceComma(String.valueOf(response.getLists().get(i).getReceipt())));
                            out += Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getLists().get(i).getPayment()));
                        }
                        total = in - out;
                        setTotalDataToView(total, String.valueOf(in), String.valueOf(out));
                    }
                }


            }

        });

    }

    private void getCreditData(String type) {
        customerReportViewModel.creditReport(getActivity(), startDate, endDate, userId, transactionId, type, storeId).observe(getViewLifecycleOwner(), new Observer<DebitorAndCreditorsReportResponse>() {
            @Override
            public void onChanged(DebitorAndCreditorsReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        DebitAndCreditReportAdapter adapter = new DebitAndCreditReportAdapter(getActivity(), response.getLists(), type);
                        binding.recyclerView.setAdapter(adapter);

                        for (int i = 0; i < response.getLists().size(); i++) {

                            total += Double.parseDouble(String.valueOf(response.getLists().get(i).getBalanceAmount()));
                        }
                        setTotalDataToView(total, null, null);

                    }
                }


            }

        });

    }

    private void getTransactionIn(String type) {
        customerReportViewModel.getIntruction(getActivity(), startDate, endDate, userId, transactionId, type, supplierId, storeId).observe(getViewLifecycleOwner(), new Observer<TransactionReportResponse>() {
            @Override
            public void onChanged(TransactionReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        InstructionreportAdapter adapter = new InstructionreportAdapter(getActivity(), response.getLists());
                        binding.recyclerView.setAdapter(adapter);

                        for (int i = 0; i < response.getLists().size(); i++) {
                            total += Double.parseDouble(ReplaceCommaFromString.replaceComma(String.valueOf(response.getLists().get(i).getTotalAmount())));
                        }

                        setTotalDataToView(total, null, null);

                    }
                }


            }

        });

    }

    private void getPaymnet() {
        customerReportViewModel.paymentReport(getActivity(), startDate, endDate, userId, transactionId, supplierId).observe(getViewLifecycleOwner(), new Observer<PaymentReportResponse>() {
            @Override
            public void onChanged(PaymentReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }

                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        PaymentRReportAdapter adapter = new PaymentRReportAdapter(getActivity(), response.getLists());
                        binding.recyclerView.setAdapter(adapter);
                        for (int i = 0; i < response.getLists().size(); i++) {
                            total += Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getLists().get(i).getPaid_amount()));
                        }
                        setTotalDataToView(total, null, null);

                    }
                }


            }

        });

    }

    private void getPaymentInstruction() {
        customerReportViewModel.paymentInstructionReportList(getActivity(), startDate, endDate, userId, transactionId).observe(getViewLifecycleOwner(), new Observer<PaymentInstructionReportResponse>() {
            @Override
            public void onChanged(PaymentInstructionReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }

                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        PaymentInstructionReportListadapter adapter = new PaymentInstructionReportListadapter(getActivity(), response.getLists());
                        binding.recyclerView.setAdapter(adapter);

                    }
                }


            }

        });


    }

    private void getBankReportList() {
        customerReportViewModel.getBankLedgerList(getActivity(), startDate, endDate, withdrawPosition, transactionId, bankId).observe(getViewLifecycleOwner(), new Observer<BankLedgerReportResponse>() {
            @Override
            public void onChanged(BankLedgerReportResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        BankReportListAdapter adapter = new BankReportListAdapter(getActivity(), response.getLists(), null);
                        binding.recyclerView.setAdapter(adapter);

                        for (int i = 0; i < response.getLists().size(); i++) {
                            in += response.getLists().get(i).getDepositeAmountIn();
                            out += Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getLists().get(i).getDepositeAmountOut()));
                        }
                        total = in - out;

                        setTotalDataToView(total, String.valueOf(in), String.valueOf(out));
                    }
                }
            }

        });
    }

    private void getReceiptList() {
        customerReportViewModel.getReceiptList(getActivity(), startDate, endDate, supplierId, userId, transactionId).observe(getViewLifecycleOwner(), new Observer<ReceiptReportResponse>() {
            @Override
            public void onChanged(ReceiptReportResponse response) {
                progressDialog.dismiss();

                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {

                    if (response.getLists().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    } else {

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        ReceiptReportAdapter adapter = new ReceiptReportAdapter(getActivity(), response.getLists());
                        binding.recyclerView.setAdapter(adapter);


                        setTotalDataToView(Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getGrandTotal())), null, null);

                    }
                }
            }
        });

    }

    private void setTotalDataToView(double total, String in, String out) {
        binding.totalTvLevel.setText("Grand Total:");
        binding.total.setText("" + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);
        binding.amountLay.setVisibility(View.VISIBLE);
        binding.totalAmountLay.setVisibility(View.VISIBLE);
        binding.dueBalanceLayout.setVisibility(View.GONE);
        if (in != null && out != null) {
            binding.in.setText("" + DataModify.addFourDigit(in) + MtUtils.priceUnit);
            binding.out.setText("" + DataModify.addFourDigit(out) + MtUtils.priceUnit);
            binding.inOutLayoyt.setVisibility(View.VISIBLE);
        }


    }

    private void getDistrictWiseSaleReport() {
        saleReturnReportViewModel.getDistrictWiseSaleReport(getActivity(), startDate, endDate, selectAssociationId, millerprofileId, districId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {

                if (response.getSaleList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                } else {

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setHasFixedSize(true);
                    DistrictWiseSaleReportAdapter adapter = new DistrictWiseSaleReportAdapter(getActivity(), response.getSaleList());
                    binding.recyclerView.setAdapter(adapter);

                }
            }
        });

    }

    private void onlyDashBoardReport() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        progressDialog.show();
        if (portion.equals(DashBoardReportUtils.todaysPurchase)) {
            getTodaysPurchase("1");
            return;
        }
        if (portion.equals(DashBoardReportUtils.todaysSale)) {
            getTodaysPurchase("2");
            return;
        }
        if (portion.equals(DashBoardReportUtils.todaysIndustrialIodization)) {
            gettodaysIndustrialIodization();
            return;
        }
        if (portion.equals(DashBoardReportUtils.lastMonthQcQa)) {
            getQcForDashBoard();
            return;
        }
        if (portion.equals(DashBoardReportUtils.todaysExpense)) {
            getTodaysExpense();
            return;
        }
        if (portion.equals(DashBoardReportUtils.currentAbailAbleBalance)) {
            currentAvailBalance();
            return;
        }

        if (portion.equals(DashBoardReportUtils.todaysSaleBasedOnCustomer)) {
            todaySaleBaseCustomer("2");
            return;
        }
        if (portion.equals(DashBoardReportUtils.todaysPurchaseBasedOnSupplier)) {
            todaySaleBaseCustomer("1");
            return;
        }
        if (portion.equals(DashBoardReportUtils.topTenSupplierBasedOnPurchase)) {
            toTenSupplier("1");
            return;
        }

        if (portion.equals(DashBoardReportUtils.topTenCustomerBasedOnSale)) {
            toTenSupplier("2,5");
            return;
        }
        if (portion.equals(DashBoardReportUtils.todaysProduction)) {
            getProductionDashboardData();
            return;
        }
    }

    private void getProductionDashboardData() {
        dashBoardViewModel.getProductionListJust(getActivity(), "25,26", startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null  || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400){
                infoMessage(getActivity().getApplication(),""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                ProductionAdapterForReport adapter = new ProductionAdapterForReport(getActivity(), response.getList());

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter);

            }
        });

    }

    private void toTenSupplier(String type) {
   /*     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String firstDay = year.format(now) + "-01" + "-01";*/
        dashBoardViewModel.getTopTenCustomerList(getActivity(), type, startDate, endDate, "10").observe(getViewLifecycleOwner(), new Observer<TopTenSupplierAndCustomerResponse>() {
            @Override
            public void onChanged(TopTenSupplierAndCustomerResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }

                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getList() == null || response.getList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                        return;
                    }

                    TopTenCustomerAdapterForReport adapter = new TopTenCustomerAdapterForReport(getActivity(), response.getList(), type);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setAdapter(adapter);

                }
            }
        });

    }

    private void currentAvailBalance() {
        dashBoardViewModel.getBankBalanceList(getActivity(), startDate, endDate).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }

            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                BankBalanceAdapterForReport adapter1 = new BankBalanceAdapterForReport(getActivity(), response.getList(), null);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter1);

            }
        });

    }


    private void todaySaleBaseCustomer(String type) {
        // here 2 means sale
        dashBoardViewModel.getSaleOrPurchaseList(getActivity(), type, startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
                return;
            }

            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                PurchaseDashBoardAdapterForReport adapter1 = new PurchaseDashBoardAdapterForReport(getActivity(), response.getList(), type);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter1);

            }
        });


    }

    private void getTodaysExpense() {
        dashBoardViewModel.getExpenseList(getActivity(), startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null  || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }  if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), ""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                ExpenseDashboardAdapterForReport adapter1 = new ExpenseDashboardAdapterForReport(getActivity(), response.getList());
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter1);

            }
        });

    }

    private void getQcForDashBoard() {
        dashBoardViewModel.getQcQaList(getActivity(), startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null  || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }    if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), ""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                try {
                    QcQaAdapterForDashReport adapterJust = new QcQaAdapterForDashReport(getActivity(), response.getList());
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setAdapter(adapterJust);

                } catch (Exception e) {
                }
            }
        });

    }

    private void gettodaysIndustrialIodization() {
        dashBoardViewModel.getProductionList(getActivity(), startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null  || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }if ( response.getStatus() == 400  ) {
                infoMessage(getActivity().getApplication(), ""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }
                IndustrialAndIodizationAdapterForReport adapter = new IndustrialAndIodizationAdapterForReport(getActivity(), response.getList());
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter);

            }
        });

    }

    private void getTodaysPurchase(String type) {
        dashBoardViewModel.getJustSalePurchaseList(getActivity(), type, startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something wrong contact to support");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getList() == null || response.getList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                }

                TodaysSaleAdapterForReport adapter = new TodaysSaleAdapterForReport(getActivity(), response.getList(), type);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter);

            }
        });


    }

    private void manageLayoytData(String orderType) {
        if (getArguments().getString("customerName") != null) {
            if (orderType.equals("1")) {
                binding.saleCustomerTv.setText("Supplier Name ");
            }
            binding.saleCustomerName.setText(":  " + getArguments().getString("customerName"));
            binding.customerLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getVendorReportList() {
        customerReportViewModel.getVendorReportList(getActivity(), startDate, endDate, storeId, supplierId, date).observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        visibleAmountLy(response.getOpeningBalance(), response.getDueBalance(), response.getTotalBalance());
                        binding.totalAmountLay.setVisibility(View.VISIBLE);
                        if (response.getList() == null || response.getList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                            return;
                        }
                        handleRv();


                        List<VendorReportList> vendorReportLists = new ArrayList<>();
                        vendorReportLists.clear();
                        vendorReportLists.addAll(response.getList());
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        VendorReportListAdapter adapter = new VendorReportListAdapter(getActivity(), vendorReportLists);
                        binding.recyclerView.setAdapter(adapter);
                    }

                }
        );
    }

    private void getSupplierList() {
//         here 2 means supplier
        customerReportViewModel.getSupplierReportList(getActivity(), startDate, endDate, storeId, supplierId, date, "2").observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();

                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        binding.totalAmountLay.setVisibility(View.VISIBLE);
                        visibleAmountLy(response.getOpeningBalance(), response.getDueBalance(), response.getTotalBalance());

                        if (response.getList() == null || response.getList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                            return;
                        }
                        handleRv();
                        List<SupplierRepostList1> supplierRepostList1s = new ArrayList<>();
                        supplierRepostList1s.clear();
                        supplierRepostList1s.addAll(response.getList());
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        SupplierAdapterForReport adapter = new SupplierAdapterForReport(getActivity(), supplierRepostList1s);
                        binding.recyclerView.setAdapter(adapter);
                    }

                }
        );
    }

    private void getCustomerReportList() {
        // here 1 means customer
        customerReportViewModel.getCustomerReportList(getActivity(), startDate, endDate, storeId, supplierId, date, "1").observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        visibleAmountLy(response.getOpeningBalance(), response.getDueBalance(), response.getTotalBalance());

                        binding.totalAmountLay.setVisibility(View.VISIBLE);
                        if (response.getList() == null || response.getList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                            return;
                        }
                        handleRv();
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);

                        List<CustomerReportList1> customerReportList1s = new ArrayList<>();
                        customerReportList1s.clear();
                        customerReportList1s.addAll(response.getList());
                        CustomerReportListAdapter adapter = new CustomerReportListAdapter(getActivity(), customerReportList1s);
                        binding.recyclerView.setAdapter(adapter);

                    }
                }
        );
    }

    private void handleRv() {
        binding.textView.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void getTransferReportData() {
        reconciliationViewModel.transferReportList(getActivity(), fromStoreId, toStoreId, startDate, endDate, transferItemId).observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getTransferedList() == null || response.getTransferedList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                        } else {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.recyclerView.setHasFixedSize(true);

                            String manageView = "reportList";
                            DeclineTransferredListAdapter adapter = new DeclineTransferredListAdapter(getActivity(), response.getTransferedList(), manageView);
                            binding.recyclerView.setAdapter(adapter);

                        }
                    }
                }
        );
    }

    private void getMonitoringReportLst() {
        licenceExpireViewModel.monitoringReportList(getActivity(), startDate, endDate, zone, monitoringType).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getMonitoringList() == null || response.getMonitoringList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                        } else {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.recyclerView.setHasFixedSize(true);

                            MonitoringReportAdapter adapter = new MonitoringReportAdapter(getActivity(), response.getMonitoringList());
                            binding.recyclerView.setAdapter(adapter);

                        }
                    }

                });
    }

    private void getQCQAList() {
        licenceExpireViewModel.qcQaReportList(getActivity(), startDate, endDate, millerprofileId).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getQcqaList() == null || response.getQcqaList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                        } else {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.recyclerView.setHasFixedSize(true);

                            QcQaReportListAdapter adapter = new QcQaReportListAdapter(getActivity(), response.getQcqaList());
                            binding.recyclerView.setAdapter(adapter);

                        }
                    }

                });
    }

    private void getDataFromReconciliationViewModel() {
        reconciliationViewModel.getReconciliationReportList(getActivity(), selectReconciliation, startDate, endDate, millerprofileId, storeId, brandId, categoryId).observe(getViewLifecycleOwner(), new Observer<ReconciliationReportListResponse>() {
            @Override
            public void onChanged(ReconciliationReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getProfuctList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        ReconciliationReportListAdapter adapter = new ReconciliationReportListAdapter(getActivity(), response.getProfuctList());
                        binding.recyclerView.setAdapter(adapter);

                    }
                }
            }
        });

    }

    private void getPurchaseReportDataFromViewModelByDate() {
        reportViewModel.getPurchaseReportByDate(getActivity(), startDate, endDate, brandId, millerprofileId, categoryId, supplierId, storeId).observe(getViewLifecycleOwner(), new Observer<PurchaseReportByDateResponse>() {
            @Override
            public void onChanged(PurchaseReportByDateResponse response) {
                progressDialog.dismiss();
                if (response == null || response.getStatus() ==500) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getProfuctList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        PurchaseReportAdapterByDate adapter = new PurchaseReportAdapterByDate(getActivity(), response.getProfuctList(), PurchaseReturnListFragment.this);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setAdapter(adapter);
                    }
                }

            }
        });
    }

    private void getSaleReportListFromViewModel() {
        saleReportViewModel.getSaleReportList(getActivity(), startDate, endDate,
                millerprofileId, supplierId, storeId, brandId, categoryId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(requireActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }

            if (response.getProfuctList().isEmpty()) {
                binding.recyclerView.setVisibility(View.GONE);
                binding.textView.setVisibility(View.VISIBLE);
            }
            if (response.getStatus() == 200) {
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setHasFixedSize(true);
                SaleReportListAdapter adapter = new SaleReportListAdapter(getActivity(), response.getProfuctList());
                binding.recyclerView.setAdapter(adapter);
            }
        });
    }

    private void getSaleReturnReportList() {
        saleReturnReportViewModel.saleReturnReportList(getActivity(), startDate, endDate,
                millerprofileId, null, supplierId, brandId, itemId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {

                if (response.getProfuctList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setHasFixedSize(true);
                    SaleReturnReportListAdapter adapter = new SaleReturnReportListAdapter(getActivity(), response.getProfuctList());
                    binding.recyclerView.setAdapter(adapter);

                }
            }
        });
    }

    private void getDataFromEmployeeReportViewModel() {
        employeeReportViewModel.getEmployeeReportList(getActivity(), millerprofileId).observe(getViewLifecycleOwner(), new Observer<EmployeeReportListResponse>() {
            @Override
            public void onChanged(EmployeeReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getProfuctList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        MillerEmployeeReportListAdapter adapter = new MillerEmployeeReportListAdapter(getActivity(), response.getProfuctList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
    }

  /*  private void getDataFromLicenceExpireViewModel() {
        licenceExpireViewModel.getMillerLicenceExpireReportList(getActivity(), startDate, endDate, millerprofileId, certificateTypeID).observe(getViewLifecycleOwner(), new Observer<MillerLicenceExpireReportListResponse>() {
            @Override
            public void onChanged(MillerLicenceExpireReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getExpireList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        MillerLicenceExpireReportListAdapter adapter = new MillerLicenceExpireReportListAdapter(getActivity(), response.getExpireList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

    }
    private void getDataFromLicenceReportViewModel() {
        licenceReportViewModel.licenceReportList(getActivity(), startDate, endDate, millerprofileId, certificateTypeID).observe(getViewLifecycleOwner(), new Observer<MillerLicenceReportListResponse>() {
            @Override
            public void onChanged(MillerLicenceReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getProfuctList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        MillerLicenceReportList adapter = new MillerLicenceReportList(getActivity(), response.getProfuctList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
    }
*/

    private void getStockIOList() {
        stockIOReportViewModel.getStockIOReportList(getActivity(),
                startDate, endDate, millerprofileId,
                storeId, supplierId, brandId, categoryId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getStockReport().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setHasFixedSize(true);
                    StockIoReportAdapter adapter = new StockIoReportAdapter(getActivity(), response.getStockReport());
                    binding.recyclerView.setAdapter(adapter);

                }
            }

        });
    }

    private void purchaseReturnReportList() {
        reportViewModel.getPurchaseReturnReportList(getActivity(), startDate
                , endDate, brandId, millerprofileId, categoryId, supplierId, storeId).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    if (response == null || response.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getProfuctList().isEmpty()) {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.textView.setVisibility(View.VISIBLE);
                        }
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        PurchaseReturnReportListAdapter adapter = new PurchaseReturnReportListAdapter(getActivity(), response.getProfuctList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                });
    }

    private void getIodineReportList() {
        iodineReportViewModel.getIodineReportList(getActivity(), startDate, endDate, millerprofileId, storeId).observe(getViewLifecycleOwner(), new Observer<IodineReportListResponse>() {
            @Override
            public void onChanged(IodineReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getIodineReport() == null || response.getIodineReport().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        IodineReportListAdapter adapter = new IodineReportListAdapter(getActivity(), response.getIodineReport());
                        binding.recyclerView.setAdapter(adapter);

                    }
                }
            }
        });
    }

    private void getPacketingReportData() {
        packetingReportViewModel.gePacketingReportList(getActivity(), startDate, endDate, millerprofileId, storeId, referrerId).observe(getViewLifecycleOwner(), new Observer<PacketingReportListResponse>() {
            @Override
            public void onChanged(PacketingReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getPacketingList() == null || response.getPacketingList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        PackettingReportListAdapter adapter = new PackettingReportListAdapter(getActivity(), response.getPacketingList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

    }

    private void getPackegingReportListFromViewModel() {
        packetingReportViewModel.getPackegingList(getActivity(), startDate, endDate, millerprofileId, storeId, referrerId).observe(getViewLifecycleOwner(), new Observer<PackegingReportListResponse>() {
            @Override
            public void onChanged(PackegingReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getPackegingList() == null || response.getPackegingList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        PackegingReportListAdapter adapter = new PackegingReportListAdapter(getActivity(), response.getPackegingList());
                        binding.recyclerView.setAdapter(adapter);
                    }
                }
            }
        });
    }

    private void getProductionReport() {
        packetingReportViewModel.getProductionList(getActivity(), startDate, endDate, selectAssociationId, millerprofileId, storeId, "").observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(requireActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            if (response.getStatus() == 200) {
                if (response.getProfuctList() == null || response.getProfuctList().isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    return;
                } else {

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setHasFixedSize(true);

                    ProductionReportLisAdapter adapter = new ProductionReportLisAdapter(getActivity(), response.getProfuctList());
                    binding.recyclerView.setAdapter(adapter);

                }
            }
        });

    }

    private void getPreviousFragmentData() {
        assert getArguments() != null;
        startDate = getArguments().getString("startDate");
        from = getArguments().getString("from");
        endDate = getArguments().getString("endDate");
        portion = getArguments().getString("portion");

        supplierId = getArguments().getString("supplierId");
        SupplierID = getArguments().getString("SupplierID");

        brandId = getArguments().getString("brandId");
        millerprofileId = getArguments().getString("millerProfileId");
        districId = getArguments().getString("districId");

        categoryId = getArguments().getString("categoryId");
        //   selectAssociationId = getArguments().getString("selectAssociationId");
        certificateTypeID = getArguments().getString("certificateTypeID");
        portion = getArguments().getString("portion");
        itemId = getArguments().getString("itemId");
        storeId = getArguments().getString("storeId");
        pageName = getArguments().getString("pageName");
        referrerId = getArguments().getString("referrerId");
        monitoringType = getArguments().getString("monitoringType");
        zone = getArguments().getString("zone");
        //for transfer report
        fromStoreId = getArguments().getString("fromStoreId");
        toStoreId = getArguments().getString("toStoreId");
        transferItemId = getArguments().getString("transferItemId");
        selectReconciliation = getArguments().getString("selectReconciliation");
        userId = getArguments().getString("userId");
        transactionId = getArguments().getString("transactionId");
        expenseTypeId = getArguments().getString("expenseTypeId");


        // for bakn ledger
        withdrawPosition = getArguments().getString("withdrawPosition");
        bankId = getArguments().getString("bankId");
        binding.toolbar.toolbarTitle.setText(pageName);

       if (startDate !=null){
           if (!startDate.isEmpty()) {
               date = "7";
           }

       }

        if (portion.equals(AccountReportUtils.customerLedgerReport)
                || portion.equals(AccountReportUtils.supplierLedgerReport)
                || portion.equals(AccountReportUtils.vendorLedgerReport)) {
            binding.toolbar.toolbarTitle.setText(pageName);
            binding.layoutOne.setVisibility(View.VISIBLE);


            if (portion.equals(AccountReportUtils.vendorLedgerReport)) {
                binding.customerLevel.setText("Vendor");
            }
            binding.companyName.setText(":  " + getArguments().getString("companyName") +"@"+getArguments().getString("customerName"));


        }
        if (startDate.isEmpty() || startDate == null) {
            binding.dateRange.setText(":  All");
            return;
        }

        binding.dateRange.setText(":  " + startDate + " To " + endDate);

    }

    @Override
    public Double calculation(String discou, String qty, String price) {
        double totalPrice = 0.0, discount = 0.0, returnTotalPrice = 0.0;
        totalPrice = Double.parseDouble(price) * Double.parseDouble(qty);

        if (discou != null || !discou.isEmpty()) {
            discount = Double.parseDouble(discou);
            returnTotalPrice = totalPrice - discount;
        }
        return totalPrice;
    }
}