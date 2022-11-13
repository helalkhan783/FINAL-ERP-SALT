package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.MonitoringReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.MonitoringReportList;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonitoringReportAdapter extends RecyclerView.Adapter<MonitoringReportAdapter.ViewHolder> {
    FragmentActivity context;
    List<MonitoringReportList> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MonitoringReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.monitoring_report_list_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonitoringReportList currentPosition = list.get(position);
        holder.binding.date.setText(":  " + currentPosition.getMonitoringDate());
        holder.binding.publishedDate.setText(":  " + currentPosition.getPublishDate());

        holder.binding.zone.setText(":  " + currentPosition.getZoneName());

        if (currentPosition.getType() != null) {
            holder.binding.type.setText(":  " + currentPosition.getType());

        }
        if (currentPosition.getMillerName() != null) {
            holder.binding.miller.setText(":  " + currentPosition.getMillerName());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MonitoringReportListLayoutBinding binding;

        public ViewHolder(@NonNull MonitoringReportListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}