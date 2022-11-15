package com.ismos_salt_erp.view.fragment.monitoring;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CashBookAdapter;
import com.ismos_salt_erp.adapter.DebitorListAdapter;
import com.ismos_salt_erp.adapter.TransactionInAdapter;
import com.ismos_salt_erp.databinding.FragmentTransactionListBinding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.CashBookList;
import com.ismos_salt_erp.serverResponseModel.DayBookList;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseSupplierList;
import com.ismos_salt_erp.serverResponseModel.TransactionCustomer;
import com.ismos_salt_erp.serverResponseModel.TransactionInList;
import com.ismos_salt_erp.serverResponseModel.UserLists;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.permission.Type1;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.filter.FilterClass;
import com.ismos_salt_erp.viewModel.DayBookCashBookViewModel;
import com.ismos_salt_erp.viewModel.TransactionViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TransactionListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, SmartMaterialSpinner.OnItemSelectedListener {
    private ReportViewModel reportViewModel;

    FragmentTransactionListBinding binding;
    String portion, from;
    TransactionViewModel transactionViewModel;
    private boolean isStartDate = false;
    private DayBookCashBookViewModel dayBookCashBookViewModel;
    List<UserLists> userLists;
    List<String> usaerNameList;

    List<TransactionCustomer> transactionCustomers;
    List<String> customerNameList;

    List<String> transactionType;
    String userId, customerId, transactiontype;
    String transactionId = null;
    Type1 typeList[] = new Type1[4];
    boolean dataOk = false;
    /**
     * for supplier list
     */
    private List<String> supplierNameList;
    private List<ReportPurchaseSupplierList> supplierResponseLists;
    List<TransactionInList> transactionInLists;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_list, container, false);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        dayBookCashBookViewModel = new ViewModelProvider(this).get(DayBookCashBookViewModel.class);
        getPreviousFragmentData();
        getAllList();
        setOnClick();
        //  getPageData();
        filterData();
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());

        binding.accountsListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    binding.expandableView.setExpanded(false);//collaps expandable view up scrolling time

                }
            }
        });


        transactionSpinnerHandle();
        binding.company.setOnItemSelectedListener(this);
        binding.user.setOnItemSelectedListener(this);
        binding.transactionType.setOnItemSelectedListener(this);
        binding.selectSupplier.setOnItemSelectedListener(this);


        return binding.getRoot();
    }

    private void transactionSpinnerHandle() {
        transactionType = new ArrayList<>();
        transactionType.clear();
        typeList[0] = new Type1("Online Deposit", "4");
        typeList[1] = new Type1("Transferred", "3");
        typeList[2] = new Type1("Cheque", "2");
        typeList[3] = new Type1("Cash", "1");

        for (int i = 0; i < typeList.length; i++) {
            transactionType.add(typeList[i].getType());
            if (transactionId != null) {
                if (transactionId.equals(typeList[i].getValue())) {
                    binding.transactionType.setSelection(i);
                }
            }

        }
        binding.transactionType.setItem(transactionType);

    }


    private void getAllList() {

        if (portion.equals(getString(R.string.receipt_history_for_dashboard))) {
            binding.toolbar.toolbarTitle.setText(getString(R.string.receipt_history_for_dashboard));
            visibleSearchE();
            manageLayout(getString(R.string.receipt_history_for_dashboard));
            getDashboardData("1");

            return;

        }
        if (portion.equals(getString(R.string.payment_history_for_dashboard))) {
            binding.toolbar.toolbarTitle.setText(getString(R.string.payment_history_for_dashboard));
            visibleSearchE();
            manageLayout(getString(R.string.receipt_history_for_dashboard));
            getDashboardData("2");
            return;
        }
        if (portion.equals(AccountsUtil.transactionIn)) {
            getTransactionInData("1");
            visibleSearchE();
            manageLayout("transactionInOut");
            return;
        }

        if (portion.equals(AccountsUtil.transactionOut)) {
            getTransactionInData("2");
            visibleSearchE();
            manageLayout("transactionInOut");
            return;
        }

        if (portion.equals(AccountReportUtils.creditors)) {//only For Day Book
            creditors("1");
            manageLayout("debitCredit");
            return;
        }
        if (portion.equals(AccountReportUtils.debitors)) {//only For Day Book
            creditors("2");
            manageLayout("debitCredit");
            return;
        }


    }

    private void getDashboardData(String type) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        transactionViewModel.dashBoardData(getActivity(), type, binding.startDate.getText().toString(), binding.EndDate.getText().toString(), customerId, userId, transactionId).observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        return;
                    }

                    if (response.getStatus() == 200) {
                        // now set userList to spinner view
                        userLists = new ArrayList<>();
                        userLists.clear();
                        userLists.addAll(response.getUserList());
                        usaerNameList = new ArrayList<>();
                        usaerNameList.clear();
                        try {
                            for (int i = 0; i < userLists.size(); i++) {
                                usaerNameList.add(userLists.get(i).getFullName());
                                if (userId != null) {
                                    if (userId.equals(userLists.get(i).getUserId())) {
                                        binding.user.setSelection(i);
                                    }
                                }
                            }
                            binding.user.setItem(usaerNameList);

                        } catch (Exception e) {
                        }

                        customerNameList = new ArrayList<>();
                        customerNameList.clear();
                        transactionCustomers = new ArrayList<>();
                        transactionCustomers.clear();
                        transactionCustomers.addAll(response.getCustomerList());
                        try {
                            for (int i = 0; i < transactionCustomers.size(); i++) {
                                customerNameList.add(transactionCustomers.get(i).getCompanyName() + " @ " + transactionCustomers.get(i).getCustomerFname());
                                if (customerId != null) {
                                    if (customerId.equals(transactionCustomers.get(i).getCustomerID())) {
                                        binding.company.setSelection(i);
                                    }
                                }

                            }
                            binding.company.setItem(customerNameList);
                        } catch (Exception e) {
                        }

                        if (response.getList().isEmpty() || response.getList() == null) {
                            binding.accountsListRv.setVisibility(View.GONE);
                            binding.dataNotFound.setVisibility(View.VISIBLE);
                            dataOk = false;
                            return;
                        }
                        binding.dataNotFound.setVisibility(View.GONE);
                        binding.accountsListRv.setVisibility(View.VISIBLE);
                        binding.accountsListRv.setHasFixedSize(true);
                        binding.accountsListRv.setLayoutManager(new LinearLayoutManager(getContext()));

                        addList(response.getList());
                        TransactionInAdapter adapter = new TransactionInAdapter(getActivity(), response.getList(), getView());
                        binding.accountsListRv.setAdapter(adapter);
                        double total = 0.0;
                        for (int i = 0; i < response.getList().size(); i++) {
                            total += Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getList().get(i).getTotalAmount()));
                        }
                        binding.totalAmount.setText(DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);

                    }
                }
        );
    }

    private void visibleSearchE() {
        binding.searchEtlayout.setVisibility(View.VISIBLE);

    }

    private void manageLayout(String from) {
        if (from.equals("transactionInOut")) {
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            binding.userLayout.setVisibility(View.VISIBLE);
            binding.transactionTypeLayout.setVisibility(View.VISIBLE);
            binding.debitCreditLayout.setVisibility(View.GONE);
            return;
        }

        if (from.equals(getString(R.string.receipt_history_for_dashboard))) {
            binding.toolbar.filterBtn.setVisibility(View.GONE);
            binding.userLayout.setVisibility(View.VISIBLE);
            binding.transactionTypeLayout.setVisibility(View.VISIBLE);
            binding.debitCreditLayout.setVisibility(View.GONE);
            return;
        }
        binding.toolbar.filterBtn.setVisibility(View.GONE);

    }

    private void getSupplierList() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        reportViewModel.getPurchaseReportPageData(getActivity(), getProfileId(requireActivity().getApplication())).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "something wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(requireActivity().getApplication(), " " + response.getMessage());
                return;
            }
            if (response.getStatus() == 200) {

                supplierNameList = new ArrayList<>();
                supplierNameList.clear();
                supplierResponseLists = new ArrayList<>();
                supplierResponseLists.addAll(response.getSupplierList());


                for (int i = 0; i < response.getSupplierList().size(); i++) {
                    supplierNameList.add("" + response.getSupplierList().get(i).getCompanyName() + "@" + response.getSupplierList().get(i).getCustomerFname());
                    if (customerId != null) {
                        if (customerId.equals(response.getSupplierList().get(i).getCustomerID())) {
                            binding.selectSupplier.setSelection(i);
                        }
                    }
                }
                binding.selectSupplier.setItem(supplierNameList);
            }
        });

    }

    private void creditors(String type) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        dayBookCashBookViewModel.getDebitorsList(getActivity(), binding.startDate.getText().toString(), binding.EndDate.getText().toString(), customerId, transactionId, type, userId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();

                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        if (response.getList() == null || response.getList().isEmpty()) {
                            binding.accountsListRv.setVisibility(View.GONE);
                            binding.dataNotFound.setVisibility(View.VISIBLE);
                            binding.ioko.setVisibility(View.GONE);
                            return;
                        }
                        binding.ioko.setVisibility(View.VISIBLE);
                        binding.dataNotFound.setVisibility(View.GONE);
                        binding.accountsListRv.setVisibility(View.VISIBLE);

                        binding.accountsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.accountsListRv.setHasFixedSize(true);
                        /**
                         * now set cash book data in recycler view
                         */
                        DebitorListAdapter adapter = new DebitorListAdapter(getActivity(), response.getList(), type);
                        binding.accountsListRv.setAdapter(adapter);
                    }
                    /**
                     * now set final portion
                     */
                    setData(type, response.getTotalDebit(), response.getTotalCredit(), response.getBalanceSummery());

                });


    }

    private void setData(String type, String debit, String credit, String balance) {
        if (type.equals("2")) {//debit
            binding.creditTv.setText("Total Debit");
            binding.debitTv.setText("Total Credit");
            binding.totalCredit.setText("" + debit + MtUtils.priceUnit);
            binding.totalDebit.setText("" + credit + MtUtils.priceUnit);
            binding.totalAmount.setText("" + balance + MtUtils.priceUnit);
            return;
        }

        //credit
        binding.totalDebit.setText("" + debit + MtUtils.priceUnit);
        binding.totalCredit.setText("" + credit + MtUtils.priceUnit);
        binding.totalAmount.setText("" + balance + MtUtils.priceUnit);
    }

    private void getTransactionInData(String type) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        transactionViewModel.getTransactionInData(getActivity(), type, binding.startDate.getText().toString(), binding.EndDate.getText().toString(), customerId, userId, transactionId).observe(
                getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        return;
                    }

                    if (response.getStatus() == 200) {
                        // now set userList to spinner view
                        userLists = new ArrayList<>();
                        userLists.clear();
                        userLists.addAll(response.getUserList());
                        usaerNameList = new ArrayList<>();
                        usaerNameList.clear();
                        try {
                            for (int i = 0; i < userLists.size(); i++) {
                                usaerNameList.add(userLists.get(i).getFullName());
                                if (userId != null) {
                                    if (userId.equals(userLists.get(i).getUserId())) {
                                        binding.user.setSelection(i);
                                    }
                                }
                            }
                            binding.user.setItem(usaerNameList);

                        } catch (Exception e) {
                        }

                        customerNameList = new ArrayList<>();
                        customerNameList.clear();
                        transactionCustomers = new ArrayList<>();
                        transactionCustomers.clear();
                        transactionCustomers.addAll(response.getCustomerList());
                        try {
                            for (int i = 0; i < transactionCustomers.size(); i++) {
                                customerNameList.add(transactionCustomers.get(i).getCompanyName() + " @ " + transactionCustomers.get(i).getCustomerFname());
                                if (customerId != null) {
                                    if (customerId.equals(transactionCustomers.get(i).getCustomerID())) {
                                        binding.company.setSelection(i);
                                    }
                                }

                            }
                            binding.company.setItem(customerNameList);
                        } catch (Exception e) {
                        }

                        if (response.getList().isEmpty() || response.getList() == null) {
                            binding.accountsListRv.setVisibility(View.GONE);
                            binding.dataNotFound.setVisibility(View.VISIBLE);
                            dataOk = false;
                            return;
                        }
                        binding.dataNotFound.setVisibility(View.GONE);
                        binding.accountsListRv.setVisibility(View.VISIBLE);
                        binding.accountsListRv.setHasFixedSize(true);
                        binding.accountsListRv.setLayoutManager(new LinearLayoutManager(getContext()));

                        addList(response.getList());
                        TransactionInAdapter adapter = new TransactionInAdapter(getActivity(), response.getList(), getView());
                        binding.accountsListRv.setAdapter(adapter);
                        double total = 0.0;
                        for (int i = 0; i < response.getList().size(); i++) {
                            total += Double.parseDouble(ReplaceCommaFromString.replaceComma(response.getList().get(i).getTotalAmount()));
                        }
                        binding.totalAmount.setText(DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);

                    }
                }
        );
    }

    private void addList(List<TransactionInList> list) {
        transactionInLists = new ArrayList<>();
        transactionInLists.clear();
        transactionInLists.addAll(list);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getPreviousFragmentData() {
        try {
            portion = getArguments().getString("portion");
            from = getArguments().getString("From");
            binding.toolbar.toolbarTitle.setText(getArguments().getString("PageName"));

        } catch (Exception e) {
        }
    }

    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.filterSearchBtn.setOnClickListener(this);
        binding.resetBtn.setOnClickListener(this);
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

            case R.id.filterBtn:
                if (binding.expandableView.isExpanded()) {
                    binding.expandableView.setExpanded(false);
                    return;
                }
                binding.expandableView.setExpanded(true);
                break;

            case R.id.filterSearchBtn:
                getAllList();
                break;
            case R.id.resetBtn:
                binding.EndDate.setText("");
                binding.startDate.setText("");
                userId = null;
                customerId = null;
                transactionId = null;
                binding.transactionType.clearSelection();
                binding.user.clearSelection();
                binding.company.clearSelection();
                binding.selectSupplier.clearSelection();
                getAllList();
                break;
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        String selectedDate = DateTimePicker.dateSelect(year, monthOfYear, dayOfMonth);

        if (!isStartDate) {
            binding.EndDate.setText(selectedDate);
            binding.EndDate.setError(null);
        } else {
            binding.startDate.setText(selectedDate);
            binding.startDate.setError(null);
            isStartDate = false;
        }

    }

    private void timePicker() {
        DateTimePicker.openDatePicker(this, getActivity());
    }

    private void filterData() {
        binding.searchEt.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
                query = query.toString().toLowerCase();

                if (transactionInLists == null) {
                    Toast.makeText(getContext(), "Empty list", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TransactionInList> filteredList = FilterClass.transactioInOutFilter(transactionInLists, query);
                if (filteredList.isEmpty()) {
                    binding.accountsListRv.setVisibility(View.GONE);
                    binding.dataNotFound.setVisibility(View.VISIBLE);
                    return;
                }
                setDataToRv(filteredList, null);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setDataToRv(List<TransactionInList> filteredList, Object o) {
        binding.accountsListRv.setVisibility(View.VISIBLE);
        binding.dataNotFound.setVisibility(View.GONE);
        binding.accountsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.accountsListRv.setHasFixedSize(true);
        TransactionInAdapter adapter = new TransactionInAdapter(getActivity(), filteredList, getView());
        binding.accountsListRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        // binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.company) {
            customerId = transactionCustomers.get(position).getCustomerID();

        }
        if (parent.getId() == R.id.user) {
            userId = userLists.get(position).getUserId();

        }
        if (parent.getId() == R.id.transactionType) {
            transactionId = typeList[position].getValue();

        }
        if (parent.getId() == R.id.selectSupplier) {
            customerId = supplierResponseLists.get(position).getCustomerID();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}