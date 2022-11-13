package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.databinding.ToptenCustomerLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerList;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class TopTenCustomerAdapterFroReport extends RecyclerView.Adapter<TopTenCustomerAdapterFroReport.ViewHolder> {
    FragmentActivity context;
    List<TopTenSupplierAndCustomerList> lists;
    String type;

    public TopTenCustomerAdapterFroReport(FragmentActivity context, List<TopTenSupplierAndCustomerList> lists, String type) {
        this.context = context;
        this.lists = lists;
        this.type = type;
    }

    @NonNull
    @Override
    public TopTenCustomerAdapterFroReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new TopTenCustomerAdapterFroReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTenCustomerAdapterFroReport.ViewHolder holder, int position) {
        TopTenSupplierAndCustomerList customerList = lists.get(position);
        if (type.equals("2")){
            holder.binding.qtyTv.setText("Sale Qty");
        }if (type.equals("1")){
            holder.binding.qtyTv.setText("Purchase Qty");
        }

        try {
            holder.binding.enterprise.setText(":  " + customerList.getCustomerFname());
            holder.binding.qty.setText(":  " + kgToTon(Double.parseDouble(customerList.getTotalQuantity())) + " " + MtUtils.metricTon);
            holder.binding.amount.setText(":  " + customerList.getGrandTotal() + MtUtils.priceUnit);
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
