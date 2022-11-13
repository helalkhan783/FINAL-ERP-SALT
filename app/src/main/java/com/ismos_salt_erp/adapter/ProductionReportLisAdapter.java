package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ProductionReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.ProductionReportList;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductionReportLisAdapter extends RecyclerView.Adapter<ProductionReportLisAdapter.viewHolder> {
    private FragmentActivity activity;
    private List<ProductionReportList> list;

    public ProductionReportLisAdapter(FragmentActivity activity, List<ProductionReportList> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductionReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.production_report_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        ProductionReportList currentitem = list.get(position);
        holder.itembinding.date.setText(":  "+new DateFormatRight(activity,currentitem.getEntryDate()).onlyDayMonthYear() );
        holder.itembinding.product.setText(":  "+currentitem.getProductTitle());
        holder.itembinding.category.setText(":  "+currentitem.getEnterprizeName());
        holder.itembinding.store.setText( ":  "+currentitem.getStoreName());
        holder.itembinding.quantity.setText(":  "+ currentitem.getQuantity() + MtUtils.kg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ProductionReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull ProductionReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
