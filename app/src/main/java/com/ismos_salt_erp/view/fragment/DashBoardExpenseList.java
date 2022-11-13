package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.DashBoardExpenseListAdapter;
import com.ismos_salt_erp.viewModel.DashboardExpenseViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardExpenseList extends Fragment {

    private View view;
    private DashboardExpenseViewModel dashboardExpenseViewModel;

    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.dashBoardExpenseListRv)
    RecyclerView dashBoardExpenseListRv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dash_board_expense_list, container, false);
        ButterKnife.bind(this, view);
        dashboardExpenseViewModel = new ViewModelProvider(this).get(DashboardExpenseViewModel.class);
        getDataFromPreviousFragment();

        /**
         * now get dashboard expense list from server
         */
        dashBoardExpenseListFromServer();

        return view;
    }

    private void dashBoardExpenseListFromServer() {
        dashboardExpenseViewModel.getDashboardExpenseList(getActivity()).observe(getViewLifecycleOwner(), dashBoardExpenseList -> {


            dashBoardExpenseListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            dashBoardExpenseListRv.setHasFixedSize(true);


            /**
             * now set the list in recyclerview
             */

            DashBoardExpenseListAdapter adapter = new DashBoardExpenseListAdapter(getActivity(), dashBoardExpenseList.getLists());

            dashBoardExpenseListRv.setAdapter(adapter);


        });
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText("Expense list");
    }

    @OnClick(R.id.backbtn)
    public void backBtn() {
        getActivity().onBackPressed();
    }
}