package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.CreditorsAdapter;
import com.ismos_salt_erp.viewModel.CreditorsViewModel;
import com.ismos_salt_erp.viewModel.DebitorsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditorsDebitorsFragment extends Fragment {
    private View view;
    private CreditorsViewModel creditorsViewModel;
    private DebitorsViewModel debitorsViewModel;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.totalCredit)
    TextView totalCredit;
    @BindView(R.id.creditorsListRv)
    RecyclerView creditorsListRv;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    @BindView(R.id.lastPortion)
    LinearLayout lastPortion;

    String portion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_creditors, container, false);
        ButterKnife.bind(this, view);
        creditorsViewModel = ViewModelProviders.of(this).get(CreditorsViewModel.class);
        debitorsViewModel = ViewModelProviders.of(this).get(DebitorsViewModel.class);


        getDataFromPreviousFragment();
        /**
         * now set data to view
         */

        if (portion.equals("Credit")) {
            setAllCreditorsListToView();
        }
        if (portion.equals("Debit")) {
            setAllDebitorsListToView();
        }
        return view;
    }

    private void setAllDebitorsListToView() {
        debitorsViewModel.getDebitors(getActivity()).observe(getViewLifecycleOwner(), creditorsResponse -> {
            if (creditorsResponse.getLists().isEmpty()) {
                creditorsListRv.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
                lastPortion.setVisibility(View.GONE);
            } else {
                lastPortion.setVisibility(View.VISIBLE);
                /**
                 * at first set the total and total credit
                 */
                double totalAmount = 0;
                for (int i = 0; i < creditorsResponse.getLists().size(); i++) {
                    totalAmount += Double.parseDouble(creditorsResponse.getLists().get(i).getAmount());
                }
                total.setText(String.valueOf(totalAmount));
                totalCredit.setText(String.valueOf(totalAmount));
                /**
                 * now set data to recycler adapter
                 */
                creditorsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                CreditorsAdapter adapter = new CreditorsAdapter(getActivity(), creditorsResponse.getLists());
                creditorsListRv.setHasFixedSize(true);
                creditorsListRv.setAdapter(adapter);
            }


        });

    }

    private void setAllCreditorsListToView() {
        creditorsViewModel.getCreditors(getActivity()).observe(getViewLifecycleOwner(), creditorsResponse -> {

            if (creditorsResponse.getLists().isEmpty()) {
                creditorsListRv.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
                lastPortion.setVisibility(View.GONE);
            } else {
                lastPortion.setVisibility(View.VISIBLE);
                /**
                 * at first set the total and total credit
                 */
                double totalAmount = 0;
                for (int i = 0; i < creditorsResponse.getLists().size(); i++) {
                    totalAmount += Double.parseDouble(creditorsResponse.getLists().get(i).getAmount());
                }
                total.setText(String.valueOf(totalAmount));
                totalCredit.setText(String.valueOf(totalAmount));
                /**
                 * now set data to recycler adapter
                 */
                creditorsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                CreditorsAdapter adapter = new CreditorsAdapter(getActivity(), creditorsResponse.getLists());
                creditorsListRv.setHasFixedSize(true);
                creditorsListRv.setAdapter(adapter);
            }

        });
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText(getArguments().getString("pageName"));
        portion = getArguments().getString("portion");
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }
}