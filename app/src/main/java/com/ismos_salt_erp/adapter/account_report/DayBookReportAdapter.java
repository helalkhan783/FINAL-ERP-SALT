package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.DaybooReportLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.account_report.DayBookReportList;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DayBookReportAdapter extends RecyclerView.Adapter<DayBookReportAdapter.viewHolder> {
    private FragmentActivity context;
    List<DayBookReportList> saleReports;

    public DayBookReportAdapter(FragmentActivity context, List<DayBookReportList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public DayBookReportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DaybooReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.dayboo_report_layout, parent, false);
        return new DayBookReportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DayBookReportAdapter.viewHolder holder, int position) {
        DayBookReportList currentitem = saleReports.get(position);
        int po=0;
        po += position+1;

        try {
            holder.itembinding.sl.setText(":  " + po);
            holder.itembinding.date.setText(":  " + currentitem.getPaymentDate());
            holder.itembinding.enterprise.setText(":  " + currentitem.getStoreName());
            holder.itembinding.particuler.setText(":  " + currentitem.getParticularsName());
            holder.itembinding.in.setText(":  " + currentitem.getReceipt());
            holder.itembinding.out.setText(":  " + DataModify.addFourDigit(currentitem.getPayment()));
            holder.itembinding.balance.setText(":  " + DataModify.addFourDigit(String.valueOf(currentitem.getTotal())));
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private DaybooReportLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull DaybooReportLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}


