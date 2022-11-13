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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CashBookAdapter;
import com.ismos_salt_erp.adapter.DayBookAdapter;
import com.ismos_salt_erp.databinding.FragmentAccountsList2Binding;
import com.ismos_salt_erp.date_time_picker.CurrentDatePick;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.CashBookList;
import com.ismos_salt_erp.serverResponseModel.DayBookList;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.PurchaseMillerList;
import com.ismos_salt_erp.view.fragment.filter.FilterClass;
import com.ismos_salt_erp.viewModel.DayBookCashBookViewModel;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
// account for helal

public class AccountsListFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, HandleReport {
    FragmentAccountsList2Binding binding;
    String portion, from, startDate1, endDate1, pageName, millerProfileId, currentDate, endDate, starDate, transactionTypeId;
    private boolean isStartDate = false;
    private ReportViewModel reportViewModel;
    /**
     * for miller List
     */
    private List<String> millerNameList;
    private List<PurchaseMillerList> millerLists;
    private DayBookCashBookViewModel dayBookCashBookViewModel;
    ProgressDialog progressDialog;

    private boolean footerVisible = false;

    List<CashBookList> cashBookLists;
    List<DayBookList> dayBookLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounts_list2, container, false);
        getPreviousFragmentData();
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        dayBookCashBookViewModel = new ViewModelProvider(this).get(DayBookCashBookViewModel.class);
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());

        binding.accountsListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    binding.expandableView.setExpanded(false);//collaps expandable view up scrolling time
                }
            }
        });
        setOnClick();
        getPageDataFromViewModel();
        getCurrentDate();// dont change state
        binding.toolbar.filterBtn.setVisibility(View.GONE);
        if (from != null) {
            if (from.equals("Report")) {
                binding.dateRange.setText(": All");
                binding.EndDate.setText("");
                binding.startDate.setText("");
                if (!startDate1.equals("")) {
                    setDate(startDate1, endDate1);
                    setRangeDa(startDate1, endDate1);
                }
            }
        }
        filterData();
        getAllData();

        handleLay(binding.startDate.getText().toString());


        binding.miller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                millerProfileId = millerLists.get(position).getStoreID();
                binding.miller.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> transactionType = new ArrayList<>();
        transactionType.add("In");
        transactionType.add("Out");
        binding.transactionType.setItem(transactionType);
        binding.transactionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = transactionType.get(position);
                if (value.equals("In")) {
                    transactionTypeId = "1";
                }
                if (value.equals("Out")) {
                    transactionTypeId = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void filterData() {
        binding.searchEt.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
                query = query.toString().toLowerCase();
                if (ifCashBook()) {
                    if (cashBookLists == null) {
                        Toast.makeText(getContext(), "Empty list", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<CashBookList> filteredList = FilterClass.cashBookFilter(cashBookLists, query);
                    if (filteredList.isEmpty()) {
                        binding.accountsListRv.setVisibility(View.GONE);
                        binding.dataNotFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    setDataToRv(filteredList,null);
                }

                if (ifdayBook()){
                    if (dayBookLists == null) {
                        Toast.makeText(getContext(), "Empty list", Toast.LENGTH_SHORT).show();
                        return;}

                    List<DayBookList> filteredList = FilterClass.dayBookFilter(dayBookLists, query);
                    if (filteredList.isEmpty()) {
                        binding.accountsListRv.setVisibility(View.GONE);
                        binding.dataNotFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    setDataToRv(null,filteredList);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setDataToRv(List<CashBookList> cashBookLists,List<DayBookList> dayBookLists) {
        binding.accountsListRv.setVisibility(View.VISIBLE);
        binding.dataNotFound.setVisibility(View.GONE);
        binding.accountsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.accountsListRv.setHasFixedSize(true);
        if (ifCashBook()) {
            CashBookAdapter cashBookAdapter = new CashBookAdapter(getActivity(), cashBookLists);
            binding.accountsListRv.setAdapter(cashBookAdapter);
            cashBookAdapter.notifyDataSetChanged();
        }

        if (ifdayBook()){
            DayBookAdapter dayBookAdapter = new DayBookAdapter(getActivity(), dayBookLists);
            binding.accountsListRv.setAdapter(dayBookAdapter);
            dayBookAdapter.notifyDataSetChanged();
        }


    }

    private void getAllData() {
        binding.footerLayout.setVisibility(View.VISIBLE);
        if (ifdayBook()) {//only For Day Book
            getDayBookListFRomServer();
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            return;
        }
        if (ifCashBook()) {//only For cash Book
            getCashBookListFRomServer();
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
            return;
        }

    }

    private boolean ifCashBook() {
        if (portion.equals(AccountsUtil.cash) || portion.equals(AccountReportUtils.cashBook)) {
            return true;
        }
        return false;
    }

    private boolean ifdayBook() {
        if (portion.equals(AccountsUtil.dayBook) || portion.equals(AccountReportUtils.dayBook)) {
            return true;
        }
        return false;
    }


    private void getCashBookListFRomServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        dayBookCashBookViewModel.getCashResponse(getActivity(), "1", binding.startDate.getText().toString(), binding.EndDate.getText().toString(), millerProfileId, binding.paymentEt.getText().toString(), binding.receiptEt.getText().toString(), transactionTypeId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();

                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 200) {
                        binding.openingBalance.setText("" + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(response.getOpeningBalance())) + MtUtils.priceUnit);
                        setData(response.getTotalIn(), response.getTotalOut(), response.getTotalBalance());
                        if (response.getList() == null || response.getList().isEmpty()) {
                            binding.accountsListRv.setVisibility(View.GONE);
                            binding.dataNotFound.setVisibility(View.VISIBLE);

                            return;
                        }
                        cashBookLists = new ArrayList<>();
                        cashBookLists.clear();
                        cashBookLists.addAll(response.getList());


                        setDataToRv(cashBookLists,null);

                    }
                    /**
                     * now set final portion
                     */

                });
    }

    private void setData(String totalIn, String totalOut, String totalBalance) {
        binding.totalIn.setText("" + totalIn + MtUtils.priceUnit);
        binding.totalOutt.setText("" + totalOut + MtUtils.priceUnit);
        binding.totalBalance.setText("" + totalBalance + MtUtils.priceUnit);

    }

    private void getDayBookListFRomServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        dayBookCashBookViewModel.getDaybookResponse(getActivity(), binding.startDate.getText().toString(), binding.EndDate.getText().toString(), millerProfileId, binding.paymentEt.getText().toString(), binding.receiptEt.getText().toString(), transactionTypeId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), response.getMessage());
                            getActivity().onBackPressed();
                            return;
                        }
                        if (response.getStatus() == 200) {
                            binding.openingBalance.setText("" + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(response.getOpeningBalance())) + MtUtils.priceUnit);
                            setData(response.getTotalIn(), response.getTotalOut(), response.getTotalBalance());
                            if (response.getList() == null || response.getList().isEmpty()) {
                                binding.accountsListRv.setVisibility(View.GONE);
                                binding.dataNotFound.setVisibility(View.VISIBLE);


                                return;
                            }
                             dayBookLists = new ArrayList<>();
                            dayBookLists.clear();
                            dayBookLists.addAll(response.getList());
                            setDataToRv(null,dayBookLists);
                        }
                    } catch (Exception e) {
                    }

                });
    }


    private void getCurrentDate() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
            currentDate = dateOnly.format(cal.getTime());
            binding.startDate.setText(currentDate);
            binding.EndDate.setText(currentDate);
            binding.dateRange.setText("  :  " + binding.startDate.getText().toString() + " To " + binding.EndDate.getText().toString());
        } catch (Exception e) {
        }
    }


    private void getPageDataFromViewModel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
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


                millerNameList = new ArrayList<>();
                millerNameList.clear();
                millerLists = new ArrayList<>();
                millerLists.clear();

                try {
                    if (response.getMillerList() != null) {
                        millerLists.addAll(response.getMillerList());

                        for (int i = 0; i < response.getMillerList().size(); i++) {
                            millerNameList.add("" + response.getMillerList().get(i).getDisplayName());
                        }
                        binding.miller.setItem(millerNameList);
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }

            }
        });
    }

    private void getPreviousFragmentData() {
        try {
            portion = getArguments().getString("portion");
            from = getArguments().getString("From");
            pageName = getArguments().getString("PageName");
            from = getArguments().getString("from");
            startDate1 = getArguments().getString("startDate");
            endDate1 = getArguments().getString("endDate");
            binding.toolbar.toolbarTitle.setText(pageName);
            binding.toolbar.filterBtn.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    /**
     * for click
     */

    private void setOnClick() {
        binding.startDate.setOnClickListener(this);
        binding.EndDate.setOnClickListener(this);
        binding.toolbar.filterBtn.setOnClickListener(this);
        binding.filterSearchBtn.setOnClickListener(this);
        binding.resetBtn.setOnClickListener(this);
        binding.openingBalance.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDate:
                timePicker();
                isStartDate = true;
                break;

            case R.id.openingBalance:

                setDate("2000-01-01", CurrentDatePick.yesterdayDate(endDate1));
                setRangeDa("2000-01-01", CurrentDatePick.yesterdayDate(endDate1));
                binding.openingBalancelayout.setVisibility(View.GONE);
                getAllData();
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
                hideKeyboard(getActivity());
                setRangeDa(binding.startDate.getText().toString(), binding.EndDate.getText().toString());
                handleLay(binding.startDate.getText().toString());
                getAllData();
                break;
            case R.id.resetBtn:
                hideKeyboard(getActivity());
                binding.searchEt.search.setText("");
                try {
                    // reset button e click korle ajker diner data dekhabe
                    setDate(CurrentDatePick.getCurrentDate(), CurrentDatePick.getCurrentDate());
                    setRangeDa(binding.EndDate.getText().toString(), binding.EndDate.getText().toString());
                    handleLay(binding.startDate.getText().toString());

                } catch (Exception e) {
                }
                getAllData();
                break;
        }
    }

    private void handleLay(String toString) {
        // jodi date na thake tahole opening balance gone thakbe
        if (toString.equals("")) {
            binding.openingBalancelayout.setVisibility(View.GONE);
            return;
        }
        binding.openingBalancelayout.setVisibility(View.VISIBLE);

    }

    private void setDate(String s, String currentDate) {
        binding.EndDate.setText(currentDate);
        binding.startDate.setText(s);

    }

    private void setRangeDa(String startDate, String endDate) {
        binding.dateRange.setText(":  " + startDate + " To " + endDate);
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

    @Override
    public void handleReport(int position, String startDate, String endDate) {
        //   binding.dateRange.setText(":  " + startDate + " To " + endDate);
        binding.EndDate.setText(startDate);
        binding.startDate.setText(endDate);
        getAllData();

    }
}