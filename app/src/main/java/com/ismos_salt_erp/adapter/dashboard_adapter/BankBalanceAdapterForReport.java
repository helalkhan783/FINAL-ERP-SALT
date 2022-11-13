package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;

import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.BankBalanceList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class BankBalanceAdapterForReport extends RecyclerView.Adapter<BankBalanceAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<BankBalanceList> lists;
    String from;

    public BankBalanceAdapterForReport(FragmentActivity context, List<BankBalanceList> lists, String from) {
        this.context = context;
        this.lists = lists;
        this.from = from;
    }

    @NonNull
    @Override
    public BankBalanceAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new BankBalanceAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BankBalanceAdapterForReport.ViewHolder holder, int position) {
        BankBalanceList customerList = lists.get(position);
        holder.binding.amountLay.setVisibility(View.GONE);
        holder.binding.enterPriseTv.setText("Bank A/C Name");
        holder.binding.qtyTv.setText("Current Balance");
        if (from != null) {
            if (from.equals("account")) {
                holder.binding.bankLay.setVisibility(View.VISIBLE);
                holder.binding.branchLay.setVisibility(View.VISIBLE);
                holder.binding.accountNoLayout.setVisibility(View.VISIBLE);
                holder.binding.openingBalanceLay.setVisibility(View.VISIBLE);
                holder.binding.qty.setTextColor(Color.BLUE);
            }
        }

        holder.binding.enterprise.setText(":  " + customerList.getAccountName());
        holder.binding.qty.setText(":  " + DataModify.addFourDigit(String.valueOf(customerList.getBalance())) + " " + MtUtils.priceUnit);
        holder.binding.bank.setText(":  " + customerList.getBankName() );
        holder.binding.branch.setText(":  " + customerList.getBankBranch() );
        holder.binding.accountNo.setText(":  " + customerList.getAccountNumber() );
        String bal = "0.0";
        if (customerList.getInitial() != null){
            bal =String.valueOf(customerList.getInitial());
        }
        holder.binding.openingBalance.setText(":  " + DataModify.addFourDigit(bal) +" "+MtUtils.priceUnit);

        holder.binding.qty.setOnClickListener(v -> {
            if (from !=null){
                if (from.equals("account")){
                    Bundle bundle = new Bundle();
                    bundle.putString("bankId",customerList.getBankID());
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_bankAllListFragment_to_bankAccountDetailsFragment, bundle);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutForDashReportBinding binding;

        public ViewHolder(@NonNull LayoutForDashReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
