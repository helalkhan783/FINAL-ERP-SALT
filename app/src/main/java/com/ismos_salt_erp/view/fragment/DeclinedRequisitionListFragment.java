package com.ismos_salt_erp.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.DeclinedRequisitionListAdapter;
import com.ismos_salt_erp.serverResponseModel.DeclinedRequisitionListResponse;
import com.ismos_salt_erp.serverResponseModel.DeclinedRequisitionResponse;
import com.ismos_salt_erp.viewModel.DeclinedRequisitionListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeclinedRequisitionListFragment extends BaseFragment {
    private DeclinedRequisitionListViewModel declinedRequisitionListViewModel;
    private View view;

    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.declinedRequisitionListRv)
    RecyclerView declinedRequisitionListRv;
    @BindView(R.id.nodataTv)
    TextView nodataTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_declined_requisition_list, container, false);
        ButterKnife.bind(this, view);
        declinedRequisitionListViewModel = ViewModelProviders.of(this).get(DeclinedRequisitionListViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show data in recyclerView from server
         */
        showDeclinedRequisitionListInRecyclerView();
        return view;

    }

    private void getDataFromPreviousFragment() {
        toolBar.setText("Declined Req. List");
    }

    private void showDeclinedRequisitionListInRecyclerView() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        declinedRequisitionListViewModel.getDeclinedRequisitionList(getActivity()).observe(getViewLifecycleOwner(), new Observer<DeclinedRequisitionResponse>() {
            @Override
            public void onChanged(DeclinedRequisitionResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    return;
                }

                if (response.getLists().isEmpty() || response.getLists() == null){
                    declinedRequisitionListRv.setVisibility(View.GONE);
                    nodataTv.setVisibility(View.VISIBLE);
                    return;
                }
                DeclinedRequisitionListAdapter adapter = new DeclinedRequisitionListAdapter(getActivity(), response.getLists());
                declinedRequisitionListRv.setAdapter(adapter);
                declinedRequisitionListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

    }

    @OnClick({R.id.backbtn})
    public void backBtnClick() {
        getActivity().onBackPressed();
    }
}