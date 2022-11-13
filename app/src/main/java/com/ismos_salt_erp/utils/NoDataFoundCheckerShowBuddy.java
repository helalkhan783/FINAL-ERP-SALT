package com.ismos_salt_erp.utils;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class NoDataFoundCheckerShowBuddy {
    FragmentActivity fragmentActivity;



    public boolean isEmptyObjectList(Object objectList, TextView noDataFoundTextView) {
        if (((ArrayList) objectList).isEmpty()) {
            noDataFoundTextView.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    public boolean isEmptyObjectListWithRecyclerControl(Object objectList, TextView noDataFoundTextView, RecyclerView recyclerView) {
        if (((ArrayList) objectList).isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noDataFoundTextView.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

}
