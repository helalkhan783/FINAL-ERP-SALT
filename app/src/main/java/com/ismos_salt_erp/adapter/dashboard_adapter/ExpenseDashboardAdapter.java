package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankBalanceLayBinding;
import com.ismos_salt_erp.serverResponseModel.ExpenseList;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class ExpenseDashboardAdapter extends RecyclerView.Adapter<ExpenseDashboardAdapter.ViewHolder> {
    FragmentActivity context;
    List<ExpenseList> lists;

    public ExpenseDashboardAdapter(FragmentActivity context, List<ExpenseList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ExpenseDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BankBalanceLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.bank_balance_lay, parent, false);
        return new ExpenseDashboardAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDashboardAdapter.ViewHolder holder, int position) {
        ExpenseList customerList =  lists.get(position);
        int po=position+1;
        holder.binding.sl.setText(""+po);
        holder.binding.accountName.setText(""+customerList.getEnterpriseName());
        holder.binding.balance.setText(""+ DataModify.addFourDigit(customerList.getExpenseAmount()));


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BankBalanceLayBinding binding;
        public ViewHolder(@NonNull BankBalanceLayBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
