package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankBalanceLayBinding;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.QcQaDashBoardList;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class QcQaAdapterForDashReport extends RecyclerView.Adapter<QcQaAdapterForDashReport.ViewHolder> {
    FragmentActivity context;
    List<QcQaDashBoardList> lists;

    public QcQaAdapterForDashReport(FragmentActivity context, List<QcQaDashBoardList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public QcQaAdapterForDashReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new QcQaAdapterForDashReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QcQaAdapterForDashReport.ViewHolder holder, int position) {
        QcQaDashBoardList customerList = lists.get(position);
        holder.binding.amountLay.setVisibility(View.GONE);
        holder.binding.qtyTv.setText("Percentage");
        holder.binding.enterprise.setText(":  " + customerList.getEnterpriseName());
        holder.binding.qty.setText(":  " + customerList.getTotalQcPercent() + " " + MtUtils.percent);
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
