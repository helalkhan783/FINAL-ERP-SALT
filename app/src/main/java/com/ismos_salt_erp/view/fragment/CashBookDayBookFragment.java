package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.viewModel.DayBookCashBookViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashBookDayBookFragment extends Fragment {
    private DayBookCashBookViewModel dayBookCashBookViewModel;
    private View view;
    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.dayBook)
    RecyclerView dayBookCashBookRv;

    //final portion

    @BindView(R.id.receiptTotal)
    TextView receiptTotal;
    @BindView(R.id.paymentTotal)
    TextView paymentTotal;
    @BindView(R.id.totalBalance)
    TextView totalBalance;


    String portion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cash_book_day_book, container, false);
        ButterKnife.bind(this, view);
        dayBookCashBookViewModel = new ViewModelProvider(this).get(DayBookCashBookViewModel.class);
        getDataFromPreviousFragment();
        if (portion.equals("DayBook")) {//only For Day Book
            getDayBookListFRomServer();
        }
        if (portion.equals("CashBook")) {//only For Day Book
            getCashBookListFRomServer();
        }
        return view;
    }

    private void getCashBookListFRomServer() {
      /*  dayBookCashBookViewModel.getCashResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    dayBookCashBookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    dayBookCashBookRv.setHasFixedSize(true);
                    *//**
                     * now set cash book data in recycler view
                     *//*
                    CashBookAdapter cashBookAdapter = new CashBookAdapter(getActivity(), response.getList());
                    dayBookCashBookRv.setAdapter(cashBookAdapter);
                    *//**
                     * now set final portion
                     *//*

                    double totalReceipt = 0;
                    double totalPayment = 0;
                    double balanceTotal = 0;
                    for (int i = 0; i < response.getList().size(); i++) {
                        totalReceipt += response.getList().get(i).getReceipt();
                        totalPayment += Double.parseDouble(response.getList().get(i).getPayment());
                    }
                    balanceTotal = totalPayment - totalReceipt;


                    receiptTotal.setText(String.valueOf(totalReceipt));
                    paymentTotal.setText(String.valueOf(totalPayment));

                    totalBalance.setText(String.valueOf(balanceTotal));

                });
*/

    }
    private void getDayBookListFRomServer() {

    /*    dayBookCashBookViewModel.getDaybookResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {

                    dayBookCashBookRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    dayBookCashBookRv.setHasFixedSize(true);
                    *//**
                     * now set daybook list in recycler view
                     *//*
                    DayBookAdapter dayBookAdapter = new DayBookAdapter(getActivity(), response.getList());
                    dayBookCashBookRv.setAdapter(dayBookAdapter);

                    double totalReceipt = 0;
                    double totalPayment = 0;
                    double balanceTotal = 0;
                    for (int i = 0; i < response.getList().size(); i++) {
                        totalReceipt += response.getList().get(i).getReceipt();
                        totalPayment += response.getList().get(i).getPayment();
                    }
                    balanceTotal = totalPayment - totalReceipt;


                    receiptTotal.setText(String.valueOf(totalReceipt));
                    paymentTotal.setText(String.valueOf(totalPayment));

                    totalBalance.setText(String.valueOf(balanceTotal));


                });
 */   }


    private void getDataFromPreviousFragment() {
        portion = getArguments().getString("portion");//for detect page portion
        toolBar.setText(getArguments().getString("pageName"));//set title name
    }


    @OnClick(R.id.backbtn)
    public void beckBtnClick() {
        getActivity().onBackPressed();
    }
}