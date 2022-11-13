package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;

import com.ismos_salt_erp.databinding.ExpenseReportListLayBinding;

import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseReportList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpenseReportListAdapter extends RecyclerView.Adapter<ExpenseReportListAdapter.viewHolder> {
    private FragmentActivity context;
    List<ExpenseReportList> saleReports;

    public ExpenseReportListAdapter(FragmentActivity context, List<ExpenseReportList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public ExpenseReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ExpenseReportListLayBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.expense_report_list_lay, parent, false);
        return new ExpenseReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExpenseReportListAdapter.viewHolder holder, int position) {
        ExpenseReportList currentitem = saleReports.get(position);

        try {

            holder.itembinding.date.setText(":  " + currentitem.getDate());
            holder.itembinding.expId.setText(":  " + currentitem.getExpId());
            holder.itembinding.vendor.setText(":  " + currentitem.getVendor());
            holder.itembinding.particuler.setText(":  " + currentitem.getParticular());
            holder.itembinding.expenseType.setText(":  " + currentitem.getExpenseType());
            holder.itembinding.total.setText(":  " + currentitem.getTotal());

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ExpenseReportListLayBinding itembinding;

        public viewHolder(@NonNull @NotNull ExpenseReportListLayBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}
