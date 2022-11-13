package com.ismos_salt_erp.view.fragment.filter;

import android.view.View;

import com.ismos_salt_erp.serverResponseModel.CashBookList;
import com.ismos_salt_erp.serverResponseModel.DayBookList;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.serverResponseModel.TransactionInList;

import java.util.ArrayList;
import java.util.List;

public class FilterClass {
    public static List<CashBookList> cashBookFilter(List<CashBookList> cashBookLists,CharSequence query) {
        List<CashBookList> filteredList = new ArrayList<>();

        for (int i = 0; i < cashBookLists.size(); i++) {
            final String text = cashBookLists.get(i).toString().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(cashBookLists.get(i));
            }
        }

        return filteredList;
    }

    public static   List<SalesRequisitionItemsResponse>   productFilter(List<SalesRequisitionItemsResponse> productList, CharSequence query) {
        List<SalesRequisitionItemsResponse> filteredList = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            final String text = productList.get(i).toString().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(productList.get(i));
            }
        }

        return filteredList;
    } public static List<DayBookList> dayBookFilter(List<DayBookList> dayBookLists, CharSequence query) {
        List<DayBookList> filteredList = new ArrayList<>();

        for (int i = 0; i < dayBookLists.size(); i++) {
            final String text = dayBookLists.get(i).toString().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(dayBookLists.get(i));
            }
        }

        return filteredList;
    }public static List<TransactionInList> transactioInOutFilter(List<TransactionInList> lists, CharSequence query) {
        List<TransactionInList> filteredList = new ArrayList<>();

        for (int i = 0; i < lists.size(); i++) {
            final String text = lists.get(i).toString().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(lists.get(i));
            }
        }

        return filteredList;
    }
}
