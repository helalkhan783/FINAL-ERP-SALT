package com.ismos_salt_erp.view.fragment.accounts;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ReceiptHistoryListAdapter;
import com.ismos_salt_erp.adapter.account_report.DebitAndCreditVoucherListAdapter;
import com.ismos_salt_erp.databinding.EditVoucherDialogLayoutBinding;
import com.ismos_salt_erp.databinding.FragmentAccountsListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.Company;
import com.ismos_salt_erp.serverResponseModel.DebitAndCreditVoucherList;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentTypes;
import com.ismos_salt_erp.serverResponseModel.ReceiptPaymentHistoryList;
import com.ismos_salt_erp.serverResponseModel.UserList;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AccountsListViewModel;
import com.ismos_salt_erp.viewModel.CustomerReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AccountsListFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, EditDebitVoucher, SmartMaterialSpinner.OnItemSelectedListener {
    private FragmentAccountsListBinding binding;
    private AccountsListViewModel accountsListViewModel;
    private CustomerReportViewModel customerReportViewModel;


    private String portion, From, total = "0.00";


    private String startDate, endDate, company, storeId, payment_type, userNumber;
    /**
     * for pagination
     */
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;
    private boolean endScroll = false;
    private LinearLayoutManager linearLayoutManager;
    private boolean isStartDate = false;

    private List<Company> companyList;
    private List<PaymentTypes> paymentTypes;
    /**
     * for all list
     */
    List<ReceiptPaymentHistoryList> receiptPaymentHistoryLists = new ArrayList<>();
    List<DebitAndCreditVoucherList> debitAndCreditVoucherLists = new ArrayList<>();

    List<UserList> userLists;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounts_list, container, false);
        accountsListViewModel = new ViewModelProvider(this).get(AccountsListViewModel.class);
        customerReportViewModel = new ViewModelProvider(this).get(CustomerReportViewModel.class);
        binding.toolbar.filterBtn.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(getContext());
        setOnclick();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });


        //set item selected listener in spinner
        binding.accountsFilter.user.setOnItemSelectedListener(this);
        binding.accountsFilter.selectCompany.setOnItemSelectedListener(this);
        binding.accountsFilter.transactionType.setOnItemSelectedListener(this);


// get user filter data
        getPageData();

        /** for pagination **/
        binding.accountsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                          /*  if (debitAndCreditVoucherLists != null) {
                                if (debitAndCreditVoucherLists.size() <= 20) {
                                    endScroll = false;
                                    return;
                                }

                            }*/
                            getAllListData();

                            loading = true;
                        }
                    }
                }
            }
        });
        return binding.getRoot();
    }

    private void getPageData() {
        //get user for filter
        customerReportViewModel.getUserpageList(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if (response == null || response.getStatus() == 400) {
                return;
            }
            List<String> userNameList = new ArrayList<>();
            userLists = new ArrayList<>();
            userLists.addAll(response.getUserLists());
            for (int i = 0; i < response.getUserLists().size(); i++) {
                userNameList.add("" + response.getUserLists().get(i).getUserName());
            }

            binding.accountsFilter.user.setItem(userNameList);
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        receiptPaymentHistoryLists.clear();
        debitAndCreditVoucherLists.clear();
        getPreviousFragmentData();
        getAllListData();

    }

    private void getAllListData() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.accountsRv, binding.noDataFound)) {
            return;
        }

        if (pageNumber > 1) {
            binding.progress.setVisibility(View.VISIBLE);
            binding.progress.setProgress(20);
            binding.progress.setMax(100);
        }

        if (portion.equals(AccountsUtil.receiptList)) {
            receiptHistory("receipt");
            return;
        }
        if (portion.equals(AccountsUtil.receiptPendingList)) {
            receiptPending("receipt");
            return;
        }
        if (portion.equals(AccountsUtil.receiptDeclinedList)) {
            reeceiptDeclined("receipt");
            return;
        }


        if (portion.equals(AccountsUtil.paymentList)) {
            paymentHistory("paymentList");//
            return;
        }
        if (portion.equals(AccountsUtil.paymentPendingList)) {
            paymentPending("paymentList");//
            return;
        }
        if (portion.equals(AccountsUtil.paymentDeclinedList)) {
            paymentDeclined("paymentList");//
            return;
        }

        if (portion.equals(AccountsUtil.vendorPaymentList)) {
            vendorPaymentList("expenseList");
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            return;
        }
        if (portion.equals(AccountsUtil.pendingVendorPayment)) {
            vendorPendingList("expenseList");
            return;
        }
        if (portion.equals(AccountsUtil.declinedVendorPayments)) {
            vendorDeclinedList("expenseList");
            return;
        }

        if (portion.equals(AccountsUtil.creditVoucherHistory)) {
            getDebitVoucherList("51");
            binding.accountsFilter.userLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (portion.equals(AccountsUtil.debitVoucherHistory)) {
            getDebitVoucherList("50");
            binding.accountsFilter.userLayout.setVisibility(View.VISIBLE);

            return;
        }

    }

    private void getDebitVoucherList(String voucherType) {


// list data
        accountsListViewModel.getDebitAndCreditVoucherList(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type, voucherType)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            debitAndCreditVoucherLists.addAll(response.getLists());
                            DebitAndCreditVoucherListAdapter adapter = new DebitAndCreditVoucherListAdapter(getActivity(), debitAndCreditVoucherLists, AccountsListFragment.this, voucherType);
                            binding.accountsRv.setLayoutManager(linearLayoutManager);
                            binding.accountsRv.setHasFixedSize(true);
                            binding.accountsRv.setAdapter(adapter);

                            try {
                                double total = 0.00;
                                for (int i = 0; i < response.getLists().size(); i++) {
                                    total = total + Double.parseDouble(response.getLists().get(i).getTotalAmount());
                                }
                                binding.totalAmount.setText("Total: " + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);

                            } catch (Exception e) {
                            }
                        }
                    }
                });

        //gte filter data
        accountsListViewModel.receiptHistory(getActivity(), "", binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());


                    }
                });

    }


    private void paymentHistory(String whoseFor) {
        accountsListViewModel.getPaymentHistoryList(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());
                        setTotalAmount(response.getTotal());
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);


                        }
                    }
                });
    }

    private void paymentDeclined(String whoseFor) {
        accountsListViewModel.paymentDeclined(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }


                    if (response.getStatus() == 200) {
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());
                        setTotalAmount(response.getTotal());
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);


                        }
                    }
                });
    }

    private void paymentPending(String whoseFor) {
        accountsListViewModel.paymentPending(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);
                            setCompanyData(response.getCompanyList());
                            setTransactionType(response.getPaymentTypes());

                        }
                    }
                });
    }


    private void receiptHistory(String forWhose) {
        accountsListViewModel.receiptHistory(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());

                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());

                            setDataInAdapter(forWhose);
                            setCompanyData(response.getCompanyList());
                            setTransactionType(response.getPaymentTypes());
                        }
                    }
                });

    }

    private void receiptPending(String forWhose) {
        accountsListViewModel.reciptPending(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());

                            setDataInAdapter(forWhose);

                        }
                    }
                });

    }

    private void reeceiptDeclined(String forWhose) {
        accountsListViewModel.receiptDeclined(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());

                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());

                            setDataInAdapter(forWhose);

                        }
                    }
                });

    }


    private void setTotalAmount(String total) {
        try {

            binding.totalAmount.setText("Total : " + total + MtUtils.priceUnit);

        } catch (Exception e) {
        }
    }


    private void vendorPaymentList(String whoseFor) {
        accountsListViewModel.vendorPayment(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());

                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());
                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);


                        }
                    }
                });
    }

    private void vendorPendingList(String whoseFor) {
        accountsListViewModel.vendorPending(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());

                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);


                        }
                    }
                });
    }

    private void vendorDeclinedList(String whoseFor) {
        accountsListViewModel.vendorDeclined(getActivity(), String.valueOf(pageNumber), binding.accountsFilter.startDate.getText().toString(), binding.accountsFilter.endDate.getText().toString(), company, userNumber, storeId, payment_type)
                .observe(getViewLifecycleOwner(), response -> {

                    binding.progress.setVisibility(View.GONE);
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        setTotalAmount(response.getTotal());
                        setCompanyData(response.getCompanyList());
                        setTransactionType(response.getPaymentTypes());

                        if (response.getLists().isEmpty() || response.getLists() == null) {
                            managePaginationAndFilter();
                        } else {
                            manageFilterBtnAndRvAndDataNotFound();
                            receiptPaymentHistoryLists.addAll(response.getLists());
                            setDataInAdapter(whoseFor);


                        }
                    }
                });
    }

    private void setDataInAdapter(String whoseFor) {
        ReceiptHistoryListAdapter adapter = new ReceiptHistoryListAdapter(getActivity(), receiptPaymentHistoryLists, portion, whoseFor);
        binding.accountsRv.setLayoutManager(linearLayoutManager);
        binding.accountsRv.setHasFixedSize(true);
        binding.accountsRv.setAdapter(adapter);
    }

    private void setCompanyData(List<Company> list) {
        companyList = new ArrayList<>();
        companyList.clear();
        companyList.addAll(list);
        List<String> companyNameList = new ArrayList<>();
        companyNameList.clear();
        for (int i = 0; i < companyList.size(); i++) {
            companyNameList.add("" + companyList.get(i).getCompanyName() + "@" + companyList.get(i).getCustomerFname());
            if (company != null) {
                if (companyList.get(i).getCustomerID().equals(company)) {
                    binding.accountsFilter.selectCompany.setSelection(i);
                }
            }
        }
        binding.accountsFilter.selectCompany.setItem(companyNameList);
    }

    private void setTransactionType(List<PaymentTypes> list) {
        paymentTypes = new ArrayList<>();
        paymentTypes.clear();
        paymentTypes.addAll(list);
        List<String> paymentNamelList = new ArrayList<>();
        paymentNamelList.clear();
        for (int i = 0; i < paymentTypes.size(); i++) {
            paymentNamelList.add(paymentTypes.get(i).getName());
            if (payment_type != null) {
                if (payment_type.equals(paymentTypes.get(i).getId())) {
                    binding.accountsFilter.transactionType.setSelection(i);
                }
            }

        }

        binding.accountsFilter.transactionType.setItem(paymentNamelList);
    }

    private void managePaginationAndFilter() {
        if (isFirstLoad == 0) { // if filter time list is null.  so then, data_not_found will be visible
            binding.accountsRv.setVisibility(View.GONE);
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
        //sometime filter list data came null when, data_not_found have visible,
        //And again search comes data in list by the others filter parameter.that for recycler view visible
        binding.noDataFound.setVisibility(View.GONE);
        binding.accountsRv.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getPreviousFragmentData() {
        portion = getArguments().getString("portion");
        From = getArguments().getString("From");
        total = getArguments().getString("Total");
        binding.toolbar.toolbarTitle.setText(getArguments().getString("PageName"));

        if (From != null) {
            if (From.equals("Homepage")) {
                binding.accountsFilter.startDate.setText("" + DataModify.currentDate());
                binding.accountsFilter.endDate.setText("" + DataModify.currentDate());
                binding.date.setText("Date : " + binding.accountsFilter.startDate.getText().toString() + " To " + binding.accountsFilter.endDate.getText().toString());
            }
        }
        if (binding.accountsFilter.startDate.getText().toString().isEmpty()) {
            binding.date.setVisibility(View.GONE);
        }
        if (total == null) {
            total = "0.0";
        }
        binding.totalAmount.setText("Total : " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(total)) + MtUtils.priceUnit);

    }

    private void setOnclick() {
        binding.accountsFilter.startDate.setOnClickListener(this);
        binding.accountsFilter.endDate.setOnClickListener(this);
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.accountsFilter.filterSearchBtn.setOnClickListener(this);
        binding.accountsFilter.resetBtn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.filterSearchBtn:
                pageNumber = 1;
                isFirstLoad = 0;
                receiptPaymentHistoryLists.clear();
                debitAndCreditVoucherLists.clear();

                if (binding.accountsFilter.startDate.getText().toString().isEmpty()) {
                    binding.date.setVisibility(View.GONE);
                }
                binding.date.setText("Date : " + binding.accountsFilter.startDate.getText().toString() + " To " + binding.accountsFilter.endDate.getText().toString());
                if (!(binding.accountsFilter.startDate.getText().toString().isEmpty() || binding.accountsFilter.endDate.getText().toString().isEmpty())) {
                    binding.date.setVisibility(View.VISIBLE);
                }
                getAllListData();
                break;

            case R.id.resetBtn:
                pageNumber = 1;
                isFirstLoad = 0;
                startDate = null;
                endDate = null;
                company = null;
                storeId = null;
                userNumber = null;
                payment_type = null;
                binding.accountsFilter.user.clearSelection();
                binding.accountsFilter.startDate.setText("");
                binding.accountsFilter.endDate.setText("");
                binding.date.setVisibility(View.GONE);
                if (From != null) {
                    if (From.equals("Homepage")) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDateTime now = LocalDateTime.now();
                        binding.accountsFilter.startDate.setText("" + dtf.format(now));
                        binding.accountsFilter.endDate.setText("" + dtf.format(now));
                        binding.date.setText("Date : " + binding.accountsFilter.startDate.getText().toString() + " To " + binding.accountsFilter.endDate.getText().toString());
                        if (!binding.accountsFilter.startDate.getText().toString().isEmpty()) {
                            binding.date.setVisibility(View.VISIBLE);
                        }
                    }
                }

                receiptPaymentHistoryLists.clear();
                debitAndCreditVoucherLists.clear();

                getAllListData();
                break;
            case R.id.filterBtn:
                new ExpandableView(getActivity(), binding.expandableView).response();
                break;

            case R.id.startDate:
                timePicker();
                isStartDate = true;
                break;

            case R.id.endDate:
                timePicker();
                break;
        }
    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (!isStartDate) {
            binding.accountsFilter.endDate.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
            endDate = binding.accountsFilter.endDate.getText().toString();
        } else {
            binding.accountsFilter.startDate.setText(DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth));
            startDate = binding.accountsFilter.startDate.getText().toString();
            isStartDate = false;
        }
    }

    @Override
    public void updateDebitAndCreditVoucher(int position, String paymentId, String amount, String date, String voucherType, String paymentType, String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EditVoucherDialogLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.edit_voucher_dialog_layout, null, false);
        AlertDialog alertDialog;
        if (voucherType.equals("50")) {
            binding.updateVoucherTvLevel.setText("Update Payment Amount");
            binding.date.setText("Payment Date: " + date);
            binding.paymentId.setText("Reference No: #" + paymentId);
        }

        if (voucherType.equals("51")) {
            binding.updateVoucherTvLevel.setText("Update Receipt Amount");
            binding.date.setText("Receipt Date: " + date);
            binding.paymentId.setText("Reference. No: #" + paymentId);
        }

        binding.amount.setText("" + amount);


        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        binding.btnOk.setOnClickListener(v -> {
            if (binding.amount.getText().toString().isEmpty()) {
                binding.amount.setError("Please enter amount");
                binding.amount.requestFocus();
                return;
            }
            if (binding.note.getText().toString().isEmpty()) {
                binding.note.setError("Note mandatory");
                binding.note.requestFocus();
                return;
            }
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            accountsListViewModel.updateVoucher(getActivity(), paymentId, voucherType, binding.amount.getText().toString(), "", "", orderId, paymentType).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
                @Override
                public void onChanged(DuePaymentResponse response) {

                    if (response == null || response.getStatus() == 400 || response.getStatus() == 500) {
                        String sms = "Something Wrong";
                        if (response.getMessage().isEmpty() || response.getMessage() == null) {
                            sms = response.getMessage();
                        }
                        errorMessage(getActivity().getApplication(), "" + sms);
                        return;
                    }
                    infoMessage(getActivity().getApplication(), response.getMessage());
                    debitAndCreditVoucherLists.clear();
                    getDebitVoucherList(voucherType);
                }
            });


            alertDialog.dismiss();
        });
        binding.cancel.setOnClickListener(v -> alertDialog.dismiss());


        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.user) {
            userNumber = userLists.get(position).getUserId();

        }
        if (parent.getId() == R.id.selectCompany) {
            company = companyList.get(position).getCustomerID();

        }
        if (parent.getId() == R.id.transactionType) {
            payment_type = paymentTypes.get(position).getId();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}