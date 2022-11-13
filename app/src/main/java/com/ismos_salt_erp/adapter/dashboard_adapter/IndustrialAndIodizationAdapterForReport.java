package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.ProductionList;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class IndustrialAndIodizationAdapterForReport extends RecyclerView.Adapter<IndustrialAndIodizationAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<ProductionList> lists;


    public IndustrialAndIodizationAdapterForReport(FragmentActivity context, List<ProductionList> lists) {
        this.context = context;
        this.lists = lists;

    }

    @NonNull
    @Override
    public IndustrialAndIodizationAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new IndustrialAndIodizationAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustrialAndIodizationAdapterForReport.ViewHolder holder, int position) {
        ProductionList customerList = lists.get(position);
        holder.binding.qtyTv.setText("industrial (%)");
        holder.binding.amountTv.setText("iodization (%)");

        try {
            holder.binding.enterprise.setText(":  " + customerList.getEnterpriseName());
            holder.binding.qty.setText(":  " + customerList.getIndustrial() + MtUtils.percent);
            holder.binding.amount.setText(":  " + customerList.getProduction() + MtUtils.percent);


        } catch (Exception e) {
        }
    }

    public Double kgToTon(Double purchaseCm) {
        double kilogram = purchaseCm;
        return (double) (kilogram * 0.001);
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


