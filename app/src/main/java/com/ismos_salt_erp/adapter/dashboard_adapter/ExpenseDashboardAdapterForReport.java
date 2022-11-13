package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.ExpenseList;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class ExpenseDashboardAdapterForReport extends RecyclerView.Adapter<ExpenseDashboardAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<ExpenseList> lists;

    public ExpenseDashboardAdapterForReport(FragmentActivity context, List<ExpenseList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ExpenseDashboardAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new ExpenseDashboardAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDashboardAdapterForReport.ViewHolder holder, int position) {
        ExpenseList customerList = lists.get(position);
        holder.binding.amountLay.setVisibility(View.GONE);
        holder.binding.qtyTv.setText("Total Expense");
        holder.binding.enterprise.setText(":  " + customerList.getEnterpriseName());
        holder.binding.qty.setText(":  " + DataModify.addFourDigit(String.valueOf(customerList.getExpenseAmount())));


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
