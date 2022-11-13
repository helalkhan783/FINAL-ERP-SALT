package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankBalLayoutBinding;
import com.ismos_salt_erp.databinding.BankBalanceLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.BankBalanceList;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class BankBalanceAdapter extends RecyclerView.Adapter<BankBalanceAdapter.ViewHolder> {
    FragmentActivity context;
    List<BankBalanceList> lists;

    public BankBalanceAdapter(FragmentActivity context, List<BankBalanceList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public BankBalanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BankBalLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.bank_bal_layout, parent, false);
        return new BankBalanceAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BankBalanceAdapter.ViewHolder holder, int position) {
        BankBalanceList customerList =  lists.get(position);
        int po=position+1;
        holder.binding.sl.setText(""+po);
        holder.binding.accountName.setText(""+customerList.getAccountName()+"/"+customerList.getAccountNumber());
        holder.binding.bankName.setText(""+customerList.getBankName());
        holder.binding.balance.setText(""+ DataModify.addFourDigit(String.valueOf(customerList.getBalance())));


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BankBalLayoutBinding binding;
        public ViewHolder(@NonNull BankBalLayoutBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
