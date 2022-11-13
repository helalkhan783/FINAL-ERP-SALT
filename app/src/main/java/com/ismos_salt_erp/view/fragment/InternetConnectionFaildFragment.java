package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.utils.InternetConnection;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class InternetConnectionFaildFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_internet_connection_faild, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.retry)
    public void retry() {

        if (!InternetConnection.isOnline(getActivity())) {
            Toasty.info(getActivity(), "Please Check Your Internet Connection", Toasty.LENGTH_LONG).show();
            return;
        }
        getActivity().onBackPressed();
    }
}