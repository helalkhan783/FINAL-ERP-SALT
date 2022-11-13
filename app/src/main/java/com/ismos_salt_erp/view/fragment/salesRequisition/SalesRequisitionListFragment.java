package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.RequisitionListAdapter;
import com.ismos_salt_erp.utils.NoDataFoundCheckerShowBuddy;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.SalesRequisitionListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalesRequisitionListFragment extends BaseFragment {
    private SalesRequisitionListViewModel salesRequisitionListViewModel;
    private View view;

    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.requisitionListRv)
    RecyclerView requisitionListRv;
    @BindView(R.id.noDataFound)
    TextView noDataFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales_requisition_list, container, false);
        ButterKnife.bind(this, view);
        salesRequisitionListViewModel = ViewModelProviders.of(this).get(SalesRequisitionListViewModel.class);
        getDataFromPreviousFragment();
        /**
         * show data in recyclerView from server
         */
        showSalesRequisitionListInRecyclerView();
        return view;
    }

    private void getDataFromPreviousFragment() {
        toolBar.setText("Requisition List");
    }

    private void showSalesRequisitionListInRecyclerView() {
        salesRequisitionListViewModel.getSalesRequisitionList(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if ( response == null || response.getStatus() == 500){
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }

            if (response.getStatus() == 400){
                infoMessage(getActivity().getApplication(), ""+response.getMessage());
                getActivity().onBackPressed();
                return;
            }

            if (new NoDataFoundCheckerShowBuddy(getActivity()).isEmptyObjectListWithRecyclerControl(response.getLists(), noDataFound, requisitionListRv)) {
                return;
            }
            requisitionListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            RequisitionListAdapter adapter = new RequisitionListAdapter(getActivity(), response.getLists());
            requisitionListRv.setAdapter(adapter);

        });
    }



    @OnClick({R.id.backbtn})
    public void backBtnClick() {
        getActivity().onBackPressed();
    }
}