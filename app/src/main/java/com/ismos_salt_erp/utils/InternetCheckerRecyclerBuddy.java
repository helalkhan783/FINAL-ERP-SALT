package com.ismos_salt_erp.utils;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.view.fragment.BaseFragment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InternetCheckerRecyclerBuddy extends BaseFragment {
    FragmentActivity fragmentActivity;

    public boolean isInternetAvailableHere(RecyclerView recyclerView, TextView dataNotFound) {
        if (!isInternetOn(this.fragmentActivity)) {
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
            dataNotFound.setText("Please Check Your Internet Connection");
            return false;
        }
        return true;
    }

}
