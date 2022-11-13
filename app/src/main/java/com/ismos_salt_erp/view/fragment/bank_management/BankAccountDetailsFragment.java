package com.ismos_salt_erp.view.fragment.bank_management;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentBankAccountDetailsBinding;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountDetailsResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.BankManageMentViewModel;

public class BankAccountDetailsFragment extends BaseFragment {
    FragmentBankAccountDetailsBinding binding;
    BankManageMentViewModel bankManageMentViewModel;

    String bankId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_account_details, container, false);
        bankManageMentViewModel = new ViewModelProvider(this).get(BankManageMentViewModel.class);
        getPreviousData();
        binding.toolbar.toolbarTitle.setText("Account Details");
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());

        getDetails();


        return binding.getRoot();
    }

    private void getDetails() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        bankManageMentViewModel.getBankAccountDetails(getActivity(), bankId).observe(getViewLifecycleOwner(), response -> {

            if (response == null || response.getStatus() == 400 || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 200) {
                binding.bankName.setText(":  " + response.getBankinfo().getBankName());
                binding.branch.setText(":  " + response.getBankinfo().getBankBranch());
                binding.accountNumber.setText(":  " + response.getBankinfo().getAccountNumber());
                binding.routingNumber.setText(":  " + response.getBankinfo().getRoutingNo());
                binding.accountType.setText(":  " + response.getBankinfo().getAccountType());
                double in = 0.0, out = 0.0, total = 0.0;
                for (int i = 0; i < response.getList().size(); i++) {
                    in += Double.parseDouble(response.getList().get(i).getIn());
                    out += Double.parseDouble(response.getList().get(i).getOut());
                }
                total = in - out;
                String initialAmount = "0";
                if (!response.getInitial().isEmpty()) {
                    initialAmount = response.getInitial();
                }
                binding.openingBalance.setText(":  " + DataModify.addFourDigit(initialAmount) + MtUtils.priceUnit);
                binding.totalIn.setText(":  " + DataModify.addFourDigit(String.valueOf(in)) + MtUtils.priceUnit);
                binding.totalOut.setText(":  " + DataModify.addFourDigit(String.valueOf(out)) + MtUtils.priceUnit);
                binding.currentBalance.setText(":  " + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);


                if (!response.getList().isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            AccountDetailsAdapter accountDetailsAdapter = new AccountDetailsAdapter(getActivity(), response.getList());
                            binding.recyclerView.setAdapter(accountDetailsAdapter);
                             binding.recyclerView.setVisibility(View.VISIBLE);
                            binding.progress.setVisibility(View.GONE);
                        }
                    }, 800);


                }

            }
        });

    }

    private void getPreviousData() {
        bankId = getArguments().getString("bankId");
    }
}