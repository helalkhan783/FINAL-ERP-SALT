package com.ismos_salt_erp.view.fragment.accounts;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ReceiptDetailsListAdapter;
import com.ismos_salt_erp.databinding.FragmentReceiptHistoryDetailsBinding;
import com.ismos_salt_erp.utils.InternetCheckerRecyclerBuddy;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AccountsListViewModel;


public class ReceiptHistoryDetailsFragment extends BaseFragment {
    private FragmentReceiptHistoryDetailsBinding binding;
    private AccountsListViewModel accountsListViewModel;
    private String portion, batchNo,type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt_history_details, container, false);
        accountsListViewModel = new ViewModelProvider(this).get(AccountsListViewModel.class);
        getFragmentData();
        getDetails();


        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        return binding.getRoot();
    }

    private void getDetails() {
        if (!new InternetCheckerRecyclerBuddy(getActivity()).isInternetAvailableHere(binding.recyclerView, binding.noDataFound)) {
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        accountsListViewModel.getAccountDetailsList(getActivity(), batchNo,type).observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        getActivity().onBackPressed();
                        return;
                    }
                    if (response.getStatus() == 500) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        return;
                    }

                    if (response.getStatus() == 200) {
                        if (response.getLists().isEmpty()){
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.noDataFound.setVisibility(View.VISIBLE);
                            return;
                        }

                        ReceiptDetailsListAdapter adapter = new ReceiptDetailsListAdapter(getActivity(), response.getLists(),portion);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setAdapter(adapter);
                    }

                });
    }

    private void getFragmentData() {
        batchNo = getArguments().getString("batchNo");
        type = getArguments().getString("type");
        portion = getArguments().getString("portion");
        binding.toolbar.toolbarTitle.setText(getArguments().getString("pageName"));
    }
}