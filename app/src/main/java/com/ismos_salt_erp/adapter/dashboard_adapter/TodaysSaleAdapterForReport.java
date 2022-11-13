package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.SalePurchaseListJust;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class TodaysSaleAdapterForReport  extends RecyclerView.Adapter<TodaysSaleAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<SalePurchaseListJust> lists;
    String type;

    public TodaysSaleAdapterForReport(FragmentActivity context, List<SalePurchaseListJust> lists,String type) {
        this.context = context;
        this.lists = lists;
        this.type = type;
    }

    @NonNull
    @Override
    public TodaysSaleAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new TodaysSaleAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodaysSaleAdapterForReport.ViewHolder holder, int position) {
        SalePurchaseListJust customerList = lists.get(position);

        if (type.equals("2")){
            holder.binding.qtyTv.setText("Sale Qty");
        }

        try {
            holder.binding.enterprise.setText(":  " + customerList.getEnterpriseName());
            holder.binding.amount.setText(":  " + customerList.getGrandTotal()  + MtUtils.priceUnit);
            holder.binding.qty.setText(":  " + DataModify.kgToTon(customerList.getQuantity()) + MtUtils.metricTon);

        }catch (Exception e){}
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


