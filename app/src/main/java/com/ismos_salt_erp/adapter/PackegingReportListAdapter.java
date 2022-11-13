package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PackegingReportListLayoutBinding;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.list.ReportPackegingList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PackegingReportListAdapter extends RecyclerView.Adapter<PackegingReportListAdapter.viewHolder> {
    private FragmentActivity activity;
    private List<ReportPackegingList> list;

    @NonNull
    @NotNull
    @Override
    public PackegingReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PackegingReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.packeging_report_list_layout, parent, false);
        return new PackegingReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PackegingReportListAdapter.viewHolder holder, int position) {
        ReportPackegingList currentitem = list.get(position);
        holder.itembinding.date.setText(":  "+currentitem.getEntryDate());
        holder.itembinding.product.setText(":  "+currentitem.getProductTitle());
        holder.itembinding.miller.setText(":  "+currentitem.getEnterprizeName());
        holder.itembinding.store.setText( ":  "+currentitem.getStoreName());
        holder.itembinding.referrer.setText(":  "+currentitem.getCustomerFname());

        holder.itembinding.quantity.setText(":  "+ DataModify.addThreeDigit(currentitem.getQuantity())  +" "+currentitem.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PackegingReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull PackegingReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
